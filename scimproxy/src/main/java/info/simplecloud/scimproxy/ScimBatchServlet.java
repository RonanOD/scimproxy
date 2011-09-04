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
        String batchLocation = HttpGenerator.getBatchUserLocation(Util.generateVersionString(), req);
        
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
				    JSONObject data = null;
				    if(!entity.isNull("data")) {
				    	data = entity.getJSONObject("data");
				    }
				    
		            if("post".equalsIgnoreCase(method)) {
	                	response += "\t\t{\n" +
		        	      "\t\t\t\"method\":\"POST\",\n" +
		        	      "\t\t\t\"batchId\":\"" + batchId + "\",\n" +
		        	      "\t\t\t\"status\":{\n";

		                try {
		                	info.simplecloud.core.User scimUser = internalPost(data.toString(), req);
		                	// TODO: move this into storage? 
		                	scimUser.getMeta().setLocation(HttpGenerator.getLocation(scimUser, req));
		                	
		                	response += "\t\t\t\t\"code\":\"201\",\n" +
			        	        		"\t\t\t\t\"reason\":\"Created\"\n" + 
		                				"\t\t\t},\n";
			        	        
		                	response += "\t\t\t\"etag\": \"" + scimUser.getMeta().getVersion() + "\",\n" +
			        	      			"\t\t\t\"data\":" + scimUser.getUser(HttpGenerator.getEncoding(req)) + ",\n" +
			        	      			"\t\t\t\"location\":\"" + scimUser.getMeta().getLocation() + "\"\n";
		                 
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
		        	      "\t\t\t\"method\":\"PUT\",\n" +
		        	      "\t\t\t\"status\":{\n";

	                	String id = getIdFromUri(location);
		                try {
	                		info.simplecloud.core.User scimUser = internalPut(id, etag, data.toString(), req);
	                		// TODO: move this into storage? 
	                		scimUser.getMeta().setLocation(HttpGenerator.getLocation(scimUser, req));

	                		response += "\t\t\t\t\"code\":\"200\",\n" +
		        	        		"\t\t\t\t\"reason\":\"Updated\"\n" + 
	                				"\t\t\t},\n";
		        	        
	                		response += "\t\t\t\"etag\": \"" + scimUser.getMeta().getVersion() + "\",\n" +
		        	      				"\t\t\t\"data\":" + scimUser.getUser(HttpGenerator.getEncoding(req)) + ",\n" +
	                					"\t\t\t\"location\":\"" + scimUser.getMeta().getLocation() + "\"\n";
		        	      
	                		// creating user in downstream CSP, any communication errors is handled in triggered and ignored here
	                		// trigger.create(scimUser);

		                } catch (PreconditionException e) {
		                	response += "\t\t\t\t\"code\":\"412\",\n" + 
		                				"\t\t\t\t\"reason\":\"SC_PRECONDITION_FAILED\",\n" + 
		                				"\t\t\t\t\"error\":\"Failed to update as resource " + id + " changed on the server since you last retrieved it.\"\n" +
		                				"\t\t\t}\n";
		                } catch (UserNotFoundException e) {
		                	response += "\t\t\t\t\"code\":\"404\",\n" + 
	            						"\t\t\t\t\"reason\":\"NOT FOUND\",\n" + 
	            						"\t\t\t\t\"error\":\"Specified resource; e.g., User, does not exist.\"\n" +
	            						"\t\t\t}\n";
		                } catch (Exception e) {
		                	response += "\t\t\t\t\"code\":\"400\",\n" + 
		                				"\t\t\t\t\"reason\":\"BAD REQUEST\",\n" + 
		                				"\t\t\t\t\"error\":\"Request is unparseable, syntactically incorrect, or violates schema.\"\n" +
		                				"\t\t\t}\n";
		                }
		                response += "\t\t}\n";
		            }
		            
		            if("patch".equalsIgnoreCase(method)) {
	                	response += "\t\t{\n" +
		        	      "\t\t\t\"method\":\"PATCH\",\n" +
		        	      "\t\t\t\"status\":{\n";

	                	String id = getIdFromUri(location);
		                try {
	                		info.simplecloud.core.User scimUser = internalPatch(id, etag, data.toString(), req);
	                		// TODO: move this into storage? 
	                		scimUser.getMeta().setLocation(HttpGenerator.getLocation(scimUser, req));

	                		response += "\t\t\t\t\"code\":\"200\",\n" +
		        	        		"\t\t\t\t\"reason\":\"Patched\"\n" + 
	                				"\t\t\t},\n";
		        	        
	                		response += "\t\t\t\"etag\": \"" + scimUser.getMeta().getVersion() + "\",\n" +
		        	      				"\t\t\t\"data\":" + scimUser.getUser(HttpGenerator.getEncoding(req)) + ",\n" +
	                					"\t\t\t\"location\":\"" + scimUser.getMeta().getLocation() + "\"\n";
		        	      
	                		// creating user in downstream CSP, any communication errors is handled in triggered and ignored here
	                		// trigger.create(scimUser);

		                } catch (PreconditionException e) {
		                	response += "\t\t\t\t\"code\":\"412\",\n" + 
		                				"\t\t\t\t\"reason\":\"SC_PRECONDITION_FAILED\",\n" + 
		                				"\t\t\t\t\"error\":\"Failed to update as resource " + id + " changed on the server since you last retrieved it.\"\n" +
		                				"\t\t\t}\n";
		                } catch (UserNotFoundException e) {
		                	response += "\t\t\t\t\"code\":\"404\",\n" + 
	            						"\t\t\t\t\"reason\":\"NOT FOUND\",\n" + 
	            						"\t\t\t\t\"error\":\"Specified resource; e.g., User, does not exist.\"\n" +
	            						"\t\t\t}\n";
		                } catch (Exception e) {
		                	response += "\t\t\t\t\"code\":\"400\",\n" + 
		                				"\t\t\t\t\"reason\":\"BAD REQUEST\",\n" + 
		                				"\t\t\t\t\"error\":\"Request is unparseable, syntactically incorrect, or violates schema.\"\n" +
		                				"\t\t\t}\n";
		                }
		                response += "\t\t}\n";
		            }		            
		            if("delete".equalsIgnoreCase(method)) {
	                	response += "\t\t{\n" +
		        	      "\t\t\t\"method\":\"DELETE\",\n" +
		        	      "\t\t\t\"status\":{\n";

	                	String id = getIdFromUri(location);
		                try {
	                		internalDelete(id, etag, req);

	                		response += "\t\t\t\t\"code\":\"200\",\n" +
		        	        		"\t\t\t\"reason\":\"Deleted\"\n" + 
	                				"\t\t\t},\n";
		                	
		                } catch (PreconditionException e) {
		                	response += "\t\t\t\t\"code\":\"412\",\n" + 
		                				"\t\t\t\t\"reason\":\"SC_PRECONDITION_FAILED\",\n" + 
		                				"\t\t\t\t\"error\":\"Failed to update as resource " + id + " changed on the server since you last retrieved it.\"\n" +
		                				"\t\t\t}\n";
		                } catch (UserNotFoundException e) {
		                	response += "\t\t\t\t\"code\":\"404\",\n" + 
	            						"\t\t\t\t\"reason\":\"NOT FOUND\",\n" + 
	            						"\t\t\t\t\"error\":\"Specified resource; e.g., User, does not exist.\"\n" +
	            						"\t\t\t}\n";
		                } catch (Exception e) {
		                	response += "\t\t\t\t\"code\":\"400\",\n" + 
		                				"\t\t\t\t\"reason\":\"BAD REQUEST\",\n" + 
		                				"\t\t\t\t\"error\":\"Request is unparseable, syntactically incorrect, or violates schema.\"\n" +
		                				"\t\t\t}\n";
		                }
                		response += "\"location\":\"" + location + "\"\n";

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
    
    
    
}
