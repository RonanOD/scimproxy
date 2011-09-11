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


public class ScimBatchServlet extends ScimResourceServlet {

	private static final long serialVersionUID = 3404477020945307825L;

	private Log log = LogFactory.getLog(ScimBatchServlet.class);

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    	ArrayList<ResourceJob> resources = new ArrayList<ResourceJob>();
    	
        String query = Util.getContent(req);
        String batchLocation = HttpGenerator.getBatchLocation(Util.generateVersionString(), req);
        
        String server = HttpGenerator.getServer(req);
        String outEncoding = HttpGenerator.getEncoding(req);
        AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");

        String response = "{\n" + 
        					"\t\"schemas\": [\"urn:scim:schemas:core:1.0\"],\n" + 
        					"\t\"location\":\"" + batchLocation + "\",\n" + 
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
				    String batchId = "";
				    if(!entity.isNull("batchId")) {
				    	batchId = entity.getString("batchId");
				    }
				    String location = "";
				    if(!entity.isNull("location")) {
				    	location = entity.getString("location");
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
				    
				    resources.add(new ResourceJob(method, batchId, location, etag, type, data, ""));
				}
				
				boolean firstItem = true;
				for (ResourceJob batchResource : resources) {

					if(ResourceJob.TYPE_USER.equalsIgnoreCase(batchResource.getType())) {
					    if(firstItem) {
					    	firstItem = false;
					    }
					    else {
					    	response += "\t\t,\n";
					    }
					    
					    response += parseResource(batchResource, server, outEncoding, authUser);
					}
				}

				for (ResourceJob batchResource : resources) {

					if(ResourceJob.TYPE_GROUP.equalsIgnoreCase(batchResource.getType())) {
					    if(firstItem) {
					    	firstItem = false;
					    }
					    else {
					    	response += "\t\t,\n";
					    }

					    // find any references to a batchId, and replace it with of earlier created resource id´s.
					    
					    // parse group for batchID instead of resourceId
					    String data = batchResource.getData();
						JSONObject dataObj = new JSONObject(data);
						
						// get all entries and load into memory 
						// TODO: parse and handle job as file stream to support larger files
						JSONArray members = dataObj.getJSONArray("members");
						for (int i = 0; i < members.length(); ++i) {
							try {
							    JSONObject entity = members.getJSONObject(i);
							    String value = entity.getString("value");
							    if(value.indexOf("batchid:") != -1) {
							    	for (ResourceJob resourceJob : resources) {
										if(resourceJob.getBatchId().equals(value.substring("batchid:".length()))) {
											batchResource.setData(batchResource.getData().replaceFirst(value, resourceJob.getId()));
										}
									}
							    }
							}
							catch (Exception e) {
								// do nothing
							}
						}

					    response += parseResource(batchResource, server, outEncoding, authUser);
					}
				}

				response += "\t\t]\n" +
							"}\n";
				
				response = Util.formatJsonPretty(response);

				resp.setHeader("Location", batchLocation);
				HttpGenerator.ok(resp, response);
		        				
			} catch (JSONException e) {
	            HttpGenerator.badRequest(resp, "Malformed batch request.");
			}
        	
        } else {
            HttpGenerator.badRequest(resp);
        }
    }

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.error("BATCH get");
    	doPost(req, resp);
    }
	
	
    
    private String handleExceptions(Exception e, String id) {
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
    
    private String parseResource(ResourceJob batchResource, String server, String encoding, AuthenticateUser authUser) {
    	String response = "";
	    
        if("post".equalsIgnoreCase(batchResource.getMethod())) {
        	response += "\t\t{\n" +
    	      "\t\t\t\"method\":\"POST\",\n" +
    	      "\t\t\t\"batchId\":\"" + batchResource.getBatchId() + "\",\n" +
    	      "\t\t\t\"status\":{\n";

            try {
            	Resource scimResource = null;
            	String scimResourceString = "";
            	
            	// om det är en grupp, 
            	//		finns det ett batchid i members value, byt ut mot riktiga ID:t.
            	// 		
            	
            	if("user".equalsIgnoreCase(batchResource.getType())) {
                	scimResource = internalUserPost(batchResource, server, encoding, authUser);
                	scimResourceString = ((User)scimResource).getUser(encoding);
            	}
            	else {
                	scimResource = internalGroupPost(batchResource, server, encoding, authUser);
                	scimResourceString = ((Group)scimResource).getGroup(encoding);
            	}
            	// TODO: move this into storage? 
            	scimResource.getMeta().setLocation(HttpGenerator.getLocation(scimResource, server));
            	
            	batchResource.setId(scimResource.getId());
            	
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
        
        if("put".equalsIgnoreCase(batchResource.getMethod())) {
        	response += "\t\t{\n" +
        	  "\t\t\t\"location\":\"" + batchResource.getLocation() + "\",\n" +
    	      "\t\t\t\"method\":\"PUT\",\n" +
    	      "\t\t\t\"status\":{\n";

        	String id = "";
            try {
            	Resource scimResource = null;
            	String scimResourceString = "";
            	
            	if("user".equalsIgnoreCase(batchResource.getType())) {
                	id = Util.getUserIdFromUri(batchResource.getLocation());
                	batchResource.setId(id);
                	scimResource = internalUserPut(batchResource, server, encoding, authUser);
                	scimResourceString = ((User)scimResource).getUser(encoding);
            	}
            	else {
                	id = Util.getGroupIdFromUri(batchResource.getLocation());
                	batchResource.setId(id);
                	scimResource = internalGroupPut(batchResource, server, encoding, authUser);
                	scimResourceString = ((Group)scimResource).getGroup(encoding);
            	}
            	
        		// TODO: move this into storage? 
            	scimResource.getMeta().setLocation(HttpGenerator.getLocation(scimResource, server));

        		response += "\t\t\t\t\"code\":\"200\",\n" +
    	        		"\t\t\t\t\"reason\":\"Updated\"\n" + 
        				"\t\t\t},\n";
    	        
        		response += "\t\t\t\"etag\": \"" + scimResource.getMeta().getVersion() + "\",\n" +
    	      				"\t\t\t\"data\":" + scimResourceString + ",\n" +
        					"\t\t\t\"location\":\"" + scimResource.getMeta().getLocation() + "\"\n";
    	      
        		// creating user in downstream CSP, any communication errors is handled in triggered and ignored here
        		// trigger.create(scimUser);

            } catch (Exception e) {
            	response += handleExceptions(e, id);
            }

            response += "\t\t}\n";
        }
        
        if("patch".equalsIgnoreCase(batchResource.getMethod())) {
        	response += "\t\t{\n" +
        	  "\t\t\t\"location\":\"" + batchResource.getLocation() + "\"\n" +
    	      "\t\t\t\"method\":\"PATCH\",\n" +
    	      "\t\t\t\"status\":{\n";

        	String id = "";
            try {
            	Resource scimResource = null;
            	String scimResourceString = "";
            	
            	if("user".equalsIgnoreCase(batchResource.getType())) {
                	id = Util.getUserIdFromUri(batchResource.getLocation());
                	batchResource.setId(id);
                	scimResource = internalUserPatch(batchResource, server, encoding, authUser);
                	scimResourceString = ((User)scimResource).getUser(encoding);
            	}
            	else {
                	id = Util.getGroupIdFromUri(batchResource.getLocation());
                	batchResource.setId(id);
                	scimResource = internalGroupPatch(batchResource, server, encoding, authUser);
                	scimResourceString = ((Group)scimResource).getGroup(encoding);
            	}

        		// TODO: move this into storage? 
            	scimResource.getMeta().setLocation(HttpGenerator.getLocation(scimResource, server));

        		response += "\t\t\t\t\"code\":\"200\",\n" +
    	        		"\t\t\t\t\"reason\":\"Patched\"\n" + 
        				"\t\t\t}\n";
    	        
        		response += "\t\t\t\"etag\": \"" + scimResource.getMeta().getVersion() + "\",\n" +
    	      				"\t\t\t\"data\":" + scimResourceString + ",\n" +
        					"\t\t\t\"location\":\"" + scimResource.getMeta().getLocation() + "\"\n";
    	      
        		// creating user in downstream CSP, any communication errors is handled in triggered and ignored here
        		// trigger.create(scimUser);

            } catch (Exception e) {
            	response += handleExceptions(e, id);
            }

            response += "\t\t}\n";
        }		            
        if("delete".equalsIgnoreCase(batchResource.getMethod())) {
        	response += "\t\t{\n" +
    			"\t\t\t\"location\":\"" + batchResource.getLocation() + "\",\n" + 
    			"\t\t\t\"method\":\"DELETE\",\n" +
    			"\t\t\t\"status\":{\n";

        	String id = "";
            try {
            	if("user".equalsIgnoreCase(batchResource.getType())) {
                	id = Util.getUserIdFromUri(batchResource.getLocation());
                	batchResource.setId(id);
            		internalUserDelete(batchResource, server, encoding, authUser);
            	}
            	else {
                	id = Util.getGroupIdFromUri(batchResource.getLocation());
                	batchResource.setId(id);
            		internalGroupDelete(batchResource, server, encoding, authUser);
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
