package info.simplecloud.scimproxy;

import info.simplecloud.core.Group;
import info.simplecloud.core.Resource;
import info.simplecloud.core.User;
import info.simplecloud.scimproxy.authentication.AuthenticateUser;
import info.simplecloud.scimproxy.exception.PreconditionException;
import info.simplecloud.scimproxy.storage.dummy.ResourceNotFoundException;
import info.simplecloud.scimproxy.util.Util;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ScimBulkServlet extends ScimResourceServlet {

	private static final long serialVersionUID = 3404477020945307825L;

	private Log log = LogFactory.getLog(ScimBulkServlet.class);

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    	ArrayList<ResourceJob> resources = new ArrayList<ResourceJob>();
    	ArrayList<ResourceJob> notProcessed = new ArrayList<ResourceJob>();
    	
        String query = Util.getContent(req);
        String bulkLocation = HttpGenerator.getBulkLocation(Util.generateVersionString(), req);
        
        String server = HttpGenerator.getServer(req);
        String outEncoding = HttpGenerator.getEncoding(req);
        AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");

        String response = "{\n" + 
        					"\t\"schemas\": [\"urn:scim:schemas:core:1.0\"],\n" + 
        					"\t\"location\":\"" + bulkLocation + "\",\n" + 
        					"\t\"Entries\":[\n";
        
        if (query != null && !"".equals(query)) {
        	
        	try {
				JSONObject jsonObj = new JSONObject(query);
				
				// get all entries and load into memory 
				// TODO: parse and handle job as file stream to support larger files
				JSONArray entities = jsonObj.getJSONArray("Entries");
				for (int i = 0; i < entities.length(); ++i) {
				    JSONObject entity = entities.getJSONObject(i);

				    String method = entity.getString("method");
				    String bulkId = "";
				    if(!entity.isNull("bulkId")) {
				    	bulkId = entity.getString("bulkId");
				    }
				    String id = "";
				    if(!entity.isNull("id")) {
				    	id = entity.getString("id");
				    }
				    String etag = "";
				    if(!entity.isNull("etag")) {
				    	etag = entity.getString("etag");
				    }
				    String type = "";
				    if(!entity.isNull("type")) {
				    	type = entity.getString("type");
				    }
				    String data = null;
				    if(!entity.isNull("data")) {
				    	data = entity.getJSONObject("data").toString();
				    }
				    
				    resources.add(new ResourceJob(method, bulkId, id, etag, type, data));
				}
				
				
				// handle all bulk jobs
				boolean firstItem = true;
				boolean done = false;
				int counter = 0;
				
				while(!done) {
					
					for (ResourceJob bulkResource : resources) {
						boolean success = true;
						
					    if(firstItem) {
					    	firstItem = false;
					    }
					    else {
					    	response += "\t\t,\n";
					    }

					    if(ResourceJob.TYPE_GROUP.equalsIgnoreCase(bulkResource.getType())) {
					    	
						    // search for bulkid:s and replace them with already created resource id
						    String data = bulkResource.getData();
							JSONObject dataObj = new JSONObject(data);
							
							JSONArray members = dataObj.getJSONArray("members");
							for (int i = 0; i < members.length(); ++i) {
								try {
								    JSONObject entity = members.getJSONObject(i);
								    String value = entity.getString("value");
								    if(value.indexOf("bulkid:") != -1) {
								    	for (ResourceJob resourceJob : resources) {
											if(resourceJob.getBulkId().equals(value.substring("bulkid:".length()))) {
												if(resourceJob.getId() != null && !"".equals(resourceJob.getId())) {
													bulkResource.setData(bulkResource.getData().replaceFirst(value, resourceJob.getId()));
												}
												else {
													success = false;
												}
											}
										}
								    }
								}
								catch (Exception e) {
									// do nothing
								}
							}
					    }

					    response += parseResource(bulkResource, server, outEncoding, authUser);

					    if(!success) {
					    	notProcessed.add(bulkResource);
					    }
					    
					}

				    counter++;
					if(counter == 3 || notProcessed.size() == 0) {
						done = true;
					}

					resources = notProcessed;
				}

				response += "\t\t]\n" +
							"}\n";
				
				response = Util.formatJsonPretty(response);

				resp.setHeader("Location", bulkLocation);
				HttpGenerator.ok(resp, response);
		        				
			} catch (JSONException e) {
	            HttpGenerator.badRequest(resp, "Malformed bulk request.");
			}
        	
        } else {
            HttpGenerator.badRequest(resp);
        }
    }

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	doPost(req, resp);
    }
	
	
    
    private String handleExceptions(Exception e, String id) {
    	e.printStackTrace();
    	String response = "";
    	if(e instanceof PreconditionException) {
    		response += "\t\t\t\t\"code\":\"412\",\n" + 
    				"\t\t\t\t\"reason\":\"SC_PRECONDITION_FAILED\",\n" + 
    				"\t\t\t\t\"error\":\"Failed to update as resource " + id + " changed on the server since you last retrieved it.\"\n" +
    				"\t\t\t}\n";
    	}
    	else if(e instanceof ResourceNotFoundException) {
        	response += "\t\t\t\t\"code\":\"404\",\n" + 
        			"\t\t\t\t\"reason\":\"NOT FOUND\",\n" + 
        			"\t\t\t\t\"error\":\"Specified resource does not exist.\"\n" +
        			"\t\t\t}\n";
    	}
    	else if(e instanceof Exception) {
    		response += "\t\t\t\t\"code\":\"400\",\n" + 
    				"\t\t\t\t\"reason\":\"BAD REQUEST\",\n" + 
    				"\t\t\t\t\"error\":\"Request is unparseable, syntactically incorrect, or violates schema.\"\n" +
    				"\t\t\t}\n";
    	}
    	return response;
    }
    
    private String parseResource(ResourceJob bulkResource, String server, String encoding, AuthenticateUser authUser) {
    	String response = "";
	    
        if("post".equalsIgnoreCase(bulkResource.getMethod())) {
        	response += "\t\t{\n" +
    	      "\t\t\t\"method\":\"POST\",\n" +
    	      "\t\t\t\"bulkId\":\"" + bulkResource.getBulkId() + "\",\n" +
    	      "\t\t\t\"status\":{\n";

            try {
            	Resource scimResource = null;
            	String scimResourceString = "";
            	
            	if("user".equalsIgnoreCase(bulkResource.getType())) {
                	scimResource = internalUserPost(bulkResource, server, encoding, authUser);
                	scimResourceString = ((User)scimResource).getUser(encoding);
            	}
            	else {
                	scimResource = internalGroupPost(bulkResource, server, encoding, authUser);
                	scimResourceString = ((Group)scimResource).getGroup(encoding);
            	}
            	// TODO: move this into storage? 
            	scimResource.getMeta().setLocation(HttpGenerator.getLocation(scimResource, server));
            	
            	bulkResource.setId(scimResource.getId());
            	
            	response += "\t\t\t\t\"code\":\"201\",\n" +
        	        		"\t\t\t\t\"reason\":\"Created\"\n" + 
            				"\t\t\t},\n";
        	        
            	response += "\t\t\t\"etag\": \"" + scimResource.getMeta().getVersion() + "\",\n" +
        	      			"\t\t\t\"data\":" + scimResourceString + ",\n" +
        	      			"\t\t\t\"location\":\"" + scimResource.getMeta().getLocation() + "\"\n";
             
            	// creating user in downstream CSP, any communication errors is handled in triggered and ignored here
            	// trigger.create(scimUser);				
            } catch (Exception e) {
            	response += "\t\t\t\t\"code\":\"400\",\n" + 
            				"\t\t\t\t\"reason\":\"BAD REQUEST\",\n" + 
            				"\t\t\t\t\"error\":\"Request is unparseable, syntactically incorrect, or violates schema.\"\n" +
            				"\t\t\t}\n";
            }
            response += "\t\t}\n";
        }
        
        if("put".equalsIgnoreCase(bulkResource.getMethod())) {
        	
        	
        	response += "\t\t{\n" +
        	  "\t\t\t\"location\":\"" + HttpGenerator.getLocation(bulkResource.getId(), bulkResource.getType(), server) + "\",\n" +
    	      "\t\t\t\"method\":\"PUT\",\n" +
    	      "\t\t\t\"status\":{\n";

        	String id = "";
            try {
            	Resource scimResource = null;
            	String scimResourceString = "";
            	
            	if("user".equalsIgnoreCase(bulkResource.getType())) {
                	bulkResource.setId(bulkResource.getId());
                	scimResource = internalUserPut(bulkResource, server, encoding, authUser);
                	scimResourceString = ((User)scimResource).getUser(encoding);
            	}
            	else {
                	bulkResource.setId(bulkResource.getId());
                	scimResource = internalGroupPut(bulkResource, server, encoding, authUser);
                	scimResourceString = ((Group)scimResource).getGroup(encoding);
            	}
            	
        		// TODO: move this into storage? 
            	scimResource.getMeta().setLocation(HttpGenerator.getLocation(scimResource, server));

        		response += "\t\t\t\t\"code\":\"200\",\n" +
    	        		"\t\t\t\t\"reason\":\"Updated\"\n" + 
        				"\t\t\t},\n";
    	        
        		response += "\t\t\t\"etag\": \"" + scimResource.getMeta().getVersion() + "\",\n" +
    	      				"\t\t\t\"data\":" + scimResourceString + "\n";

        		// creating user in downstream CSP, any communication errors is handled in triggered and ignored here
        		// trigger.create(scimUser);

            } catch (Exception e) {
            	response += handleExceptions(e, id);
            }

            response += "\t\t}\n";
        }
        
        if("patch".equalsIgnoreCase(bulkResource.getMethod())) {
        	response += "\t\t{\n" +
        	  "\t\t\t\"location\":\"" + HttpGenerator.getLocation(bulkResource.getId(), bulkResource.getType(), server) + "\",\n" +
    	      "\t\t\t\"method\":\"PATCH\",\n" +
    	      "\t\t\t\"status\":{\n";

        	String id = "";
            try {
            	Resource scimResource = null;
            	String scimResourceString = "";
            	
            	if("user".equalsIgnoreCase(bulkResource.getType())) {
                	bulkResource.setId(bulkResource.getId());
                	scimResource = internalUserPatch(bulkResource, server, encoding, authUser);
                	scimResourceString = ((User)scimResource).getUser(encoding);
            	}
            	else {
                	bulkResource.setId(bulkResource.getId());
                	scimResource = internalGroupPatch(bulkResource, server, encoding, authUser);
                	scimResourceString = ((Group)scimResource).getGroup(encoding);
            	}

        		// TODO: move this into storage? 
            	scimResource.getMeta().setLocation(HttpGenerator.getLocation(scimResource, server));

        		response += "\t\t\t\t\"code\":\"200\",\n" +
    	        		"\t\t\t\t\"reason\":\"Patched\"\n" + 
        				"\t\t\t},\n";
    	        
        		response += "\t\t\t\"etag\": \"" + scimResource.getMeta().getVersion() + "\",\n" +
    	      				"\t\t\t\"data\":" + scimResourceString + "\n";
        		
        		// creating user in downstream CSP, any communication errors is handled in triggered and ignored here
        		// trigger.create(scimUser);

            } catch (Exception e) {
            	response += handleExceptions(e, id);
            }

            response += "\t\t}\n";
        }		            
        if("delete".equalsIgnoreCase(bulkResource.getMethod())) {
        	response += "\t\t{\n" +
    			"\t\t\t\"location\":\"" + HttpGenerator.getLocation(bulkResource.getId(), bulkResource.getType(), server) + "\",\n" + 
    			"\t\t\t\"method\":\"DELETE\",\n" +
    			"\t\t\t\"status\":{\n";

        	String id = "";
            try {
            	if("user".equalsIgnoreCase(bulkResource.getType())) {
                	bulkResource.setId(bulkResource.getId());
            		internalUserDelete(bulkResource, server, encoding, authUser);
            	}
            	else {
                	bulkResource.setId(bulkResource.getId());
            		internalGroupDelete(bulkResource, server, encoding, authUser);
            	}
            	
        		response += "\t\t\t\t\"code\":\"200\",\n" +
    	        		"\t\t\t\t\"reason\":\"Deleted\"\n" + 
        				"\t\t\t}\n";
            
            } catch (Exception e) {
            	response += handleExceptions(e, id);
            }


            response += "\t\t}\n";
        }	
        
        return response;
    }
    
}
