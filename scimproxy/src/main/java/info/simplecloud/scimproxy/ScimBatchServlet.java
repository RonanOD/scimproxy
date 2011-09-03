package info.simplecloud.scimproxy;

import info.simplecloud.scimproxy.exception.PreconditionException;
import info.simplecloud.scimproxy.storage.dummy.UserNotFoundException;
import info.simplecloud.scimproxy.util.Util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ScimBatchServlet extends ScimUserUpdatesServlet {

	private static final long serialVersionUID = 3404477020945307825L;

	private Log log = LogFactory.getLog(ScimBatchServlet.class);


    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String query = getContent(req);
        String batchLocation = HttpGenerator.getBatchLocation(Util.generateVersionString(), req);
        
        String response = "{" + 
        					"\"schemas\": [\"urn:scim:schemas:core:1.0\"]," + 
        					"\"location\":\"" + batchLocation + "\"," + 
        					"\"Entries\":[";
        
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
				    JSONObject data = null;
				    if(!entity.isNull("data")) {
				    	data = entity.getJSONObject("data");
				    }
				    
		            if("post".equalsIgnoreCase(method)) {
	                	response += "{" +
		        	      "\"method\":\"POST\"," +
		        	      "\"batchId\":\"" + batchId + "\"," +
		        	      "\"status\":{";

		                try {
		                	info.simplecloud.core.User scimUser = internalPost(data.toString(), req);
		                	// TODO: move this into storage? 
		                	scimUser.getMeta().setLocation(HttpGenerator.getLocation(scimUser, req));
		                	
		                	response += "\"code\":\"201\"," +
			        	        		"\"reason\":\"Created\"" + 
		                				"},";
			        	        
		                	response += "\"etag\": \"" + scimUser.getMeta().getVersion() + "\"," +
			        	      			"\"data\":" + scimUser.getUser(HttpGenerator.getEncoding(req)) + "," +
			        	      			"\"location\":\"" + scimUser.getMeta().getLocation() + "\"";
		                 
		                	// creating user in downstream CSP, any communication errors is handled in triggered and ignored here
		                	// trigger.create(scimUser);				
		                } catch (Exception e) {
		                	response += "\"code\":\"400\"," + 
		                				"\"reason\":\"BAD REQUEST\"," + 
		                				"\"error\":\"Request is unparseable, syntactically incorrect, or violates schema.\"" +
		                				"}";
		                }
		                response += "}";
		            }
		            
		            if("put".equalsIgnoreCase(method)) {
	                	response += "{" +
		        	      "\"method\":\"PUT\"," +
		        	      "\"status\":{";

	                	String id = getIdFromUri(location);
		                try {
	                		info.simplecloud.core.User scimUser = internalPut(id, etag, data.toString(), req);
	                		// TODO: move this into storage? 
	                		scimUser.getMeta().setLocation(HttpGenerator.getLocation(scimUser, req));

	                		response += "\"code\":\"200\"," +
		        	        		"\"reason\":\"Updated\"" + 
	                				"},";
		        	        
	                		response += "\"etag\": \"" + scimUser.getMeta().getVersion() + "\"," +
		        	      				"\"data\":" + scimUser.getUser(HttpGenerator.getEncoding(req)) + "," +
	                					"\"location\":\"" + scimUser.getMeta().getLocation() + "\"";
		        	      
	                		// creating user in downstream CSP, any communication errors is handled in triggered and ignored here
	                		// trigger.create(scimUser);

		                } catch (PreconditionException e) {
		                	response += "\"code\":\"412\"," + 
		                				"\"reason\":\"SC_PRECONDITION_FAILED\"," + 
		                				"\"error\":\"Failed to update as resource " + id + " changed on the server since you last retrieved it.\"" +
		                				"}";
		                } catch (UserNotFoundException e) {
		                	response += "\"code\":\"404\"," + 
	            						"\"reason\":\"NOT FOUND\"," + 
	            						"\"error\":\"Specified resource; e.g., User, does not exist.\"" +
	            						"}";
		                } catch (Exception e) {
		                	response += "\"code\":\"400\"," + 
		                				"\"reason\":\"BAD REQUEST\"," + 
		                				"\"error\":\"Request is unparseable, syntactically incorrect, or violates schema.\"" +
		                				"}";
		                }
		                response += "}";
		            }
		            
		            if("patch".equalsIgnoreCase(method)) {
	                	response += "{" +
		        	      "\"method\":\"PATCH\"," +
		        	      "\"status\":{";

	                	String id = getIdFromUri(location);
		                try {
	                		info.simplecloud.core.User scimUser = internalPatch(id, etag, data.toString(), req);
	                		// TODO: move this into storage? 
	                		scimUser.getMeta().setLocation(HttpGenerator.getLocation(scimUser, req));

	                		response += "\"code\":\"200\"," +
		        	        		"\"reason\":\"Patched\"" + 
	                				"},";
		        	        
	                		response += "\"etag\": \"" + scimUser.getMeta().getVersion() + "\"," +
		        	      				"\"data\":" + scimUser.getUser(HttpGenerator.getEncoding(req)) + "," +
	                					"\"location\":\"" + scimUser.getMeta().getLocation() + "\"";
		        	      
	                		// creating user in downstream CSP, any communication errors is handled in triggered and ignored here
	                		// trigger.create(scimUser);

		                } catch (PreconditionException e) {
		                	response += "\"code\":\"412\"," + 
		                				"\"reason\":\"SC_PRECONDITION_FAILED\"," + 
		                				"\"error\":\"Failed to update as resource " + id + " changed on the server since you last retrieved it.\"" +
		                				"}";
		                } catch (UserNotFoundException e) {
		                	response += "\"code\":\"404\"," + 
	            						"\"reason\":\"NOT FOUND\"," + 
	            						"\"error\":\"Specified resource; e.g., User, does not exist.\"" +
	            						"}";
		                } catch (Exception e) {
		                	response += "\"code\":\"400\"," + 
		                				"\"reason\":\"BAD REQUEST\"," + 
		                				"\"error\":\"Request is unparseable, syntactically incorrect, or violates schema.\"" +
		                				"}";
		                }
		                response += "}";
		            }		            
		            if("delete".equalsIgnoreCase(method)) {
	                	response += "{" +
		        	      "\"method\":\"DELETE\"," +
		        	      "\"status\":{";

	                	String id = getIdFromUri(location);
		                try {
	                		internalDelete(id, etag, req);

	                		response += "\"code\":\"200\"," +
		        	        		"\"reason\":\"Deleted\"" + 
	                				"},";
		                	
		                } catch (PreconditionException e) {
		                	response += "\"code\":\"412\"," + 
		                				"\"reason\":\"SC_PRECONDITION_FAILED\"," + 
		                				"\"error\":\"Failed to update as resource " + id + " changed on the server since you last retrieved it.\"" +
		                				"}";
		                } catch (UserNotFoundException e) {
		                	response += "\"code\":\"404\"," + 
	            						"\"reason\":\"NOT FOUND\"," + 
	            						"\"error\":\"Specified resource; e.g., User, does not exist.\"" +
	            						"}";
		                } catch (Exception e) {
		                	response += "\"code\":\"400\"," + 
		                				"\"reason\":\"BAD REQUEST\"," + 
		                				"\"error\":\"Request is unparseable, syntactically incorrect, or violates schema.\"" +
		                				"}";
		                }
                		response += "\"location\":\"" + location + "\"";

		                response += "}";
		            }		            
				}
			
				response += "]" +
							"}";

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
    
    
    
}
