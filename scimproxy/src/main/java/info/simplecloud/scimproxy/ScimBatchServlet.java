package info.simplecloud.scimproxy;

import info.simplecloud.core.Group;
import info.simplecloud.core.Resource;
import info.simplecloud.core.User;
import info.simplecloud.scimproxy.exception.PreconditionException;
import info.simplecloud.scimproxy.storage.dummy.ResourceNotFoundException;
import info.simplecloud.scimproxy.util.Util;

import java.io.IOException;

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

        String query = Util.getContent(req);
        String batchLocation = HttpGenerator.getBatchLocation(Util.generateVersionString(), req);
        
        String response = "{\n" + 
        					"\t\"schemas\": [\"urn:scim:schemas:core:1.0\"],\n" + 
        					"\t\"location\":\"" + batchLocation + "\",\n" + 
        					"\t\"Entries\":[\n";
        
        if (query != null && !"".equals(query)) {
        	
        	try {
				JSONObject jsonObj = new JSONObject(query);
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
				    JSONObject data = null;
				    if(!entity.isNull("data")) {
				    	data = entity.getJSONObject("data");
				    }
				    
				    if(i != 0) {
				    	response += "\t\t,\n";
				    }
				    
		            if("post".equalsIgnoreCase(method)) {
	                	response += "\t\t{\n" +
		        	      "\t\t\t\"method\":\"POST\",\n" +
		        	      "\t\t\t\"batchId\":\"" + batchId + "\",\n" +
		        	      "\t\t\t\"status\":{\n";

		                try {
		                	Resource scimResource = null;
		                	String scimResourceString = "";
		                	
		                	if("user".equalsIgnoreCase(type)) {
			                	scimResource = internalUserPost(data.toString(), req);
			                	scimResourceString = ((User)scimResource).getUser(HttpGenerator.getEncoding(req));
		                	}
		                	else {
			                	scimResource = internalGroupPost(data.toString(), req);
			                	scimResourceString = ((Group)scimResource).getGroup(HttpGenerator.getEncoding(req));
		                	}
		                	// TODO: move this into storage? 
		                	scimResource.getMeta().setLocation(HttpGenerator.getLocation(scimResource, req));
		                	
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
		            
		            if("put".equalsIgnoreCase(method)) {
	                	response += "\t\t{\n" +
	                	  "\t\t\t\"location\":\"" + location + "\",\n" +
		        	      "\t\t\t\"method\":\"PUT\",\n" +
		        	      "\t\t\t\"status\":{\n";

	                	String id = "";
		                try {
		                	Resource scimResource = null;
		                	String scimResourceString = "";
		                	
		                	if("user".equalsIgnoreCase(type)) {
			                	id = Util.getUserIdFromUri(location);
			                	scimResource = internalUserPut(id, etag, data.toString(), req);
			                	scimResourceString = ((User)scimResource).getUser(HttpGenerator.getEncoding(req));
		                	}
		                	else {
			                	id = Util.getGroupIdFromUri(location);
			                	scimResource = internalGroupPut(id, etag, data.toString(), req);
			                	scimResourceString = ((Group)scimResource).getGroup(HttpGenerator.getEncoding(req));
		                	}
		                	
	                		// TODO: move this into storage? 
		                	scimResource.getMeta().setLocation(HttpGenerator.getLocation(scimResource, req));

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
		            
		            if("patch".equalsIgnoreCase(method)) {
	                	response += "\t\t{\n" +
	                	  "\t\t\t\"location\":\"" + location + "\"\n" +
		        	      "\t\t\t\"method\":\"PATCH\",\n" +
		        	      "\t\t\t\"status\":{\n";

	                	String id = "";
		                try {
		                	Resource scimResource = null;
		                	String scimResourceString = "";
		                	
		                	if("user".equalsIgnoreCase(type)) {
			                	id = Util.getUserIdFromUri(location);
			                	scimResource = internalUserPatch(id, etag, data.toString(), req);
			                	scimResourceString = ((User)scimResource).getUser(HttpGenerator.getEncoding(req));
		                	}
		                	else {
			                	id = Util.getGroupIdFromUri(location);
			                	scimResource = internalGroupPatch(id, etag, data.toString(), req);
			                	scimResourceString = ((Group)scimResource).getGroup(HttpGenerator.getEncoding(req));
		                	}

	                		// TODO: move this into storage? 
		                	scimResource.getMeta().setLocation(HttpGenerator.getLocation(scimResource, req));

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
		            if("delete".equalsIgnoreCase(method)) {
	                	response += "\t\t{\n" +
                			"\t\t\t\"location\":\"" + location + "\",\n" + 
                			"\t\t\t\"method\":\"DELETE\",\n" +
                			"\t\t\t\"status\":{\n";

	                	String id = "";
		                try {
		                	if("user".equalsIgnoreCase(type)) {
			                	id = Util.getUserIdFromUri(location);
		                		internalUserDelete(id, etag, req);
		                	}
		                	else {
			                	id = Util.getGroupIdFromUri(location);
		                		internalGroupDelete(id, etag, req);
		                	}
		                	
	                		response += "\t\t\t\t\"code\":\"200\",\n" +
		        	        		"\t\t\t\t\"reason\":\"Deleted\"\n" + 
	                				"\t\t\t}\n";
		                
		                } catch (Exception e) {
		                	response += handleExceptions(e, id);
		                }


		                response += "\t\t}\n";
		            }		            
				}
			
				response += "\t\t]\n" +
							"}\n";

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
    
}
