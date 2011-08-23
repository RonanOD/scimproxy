package info.simplecloud.scimproxy;

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
        	
            log.error(query);

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
				    	batchId = entity.getString("location");
				    }
				    String etag = "";
				    if(!entity.isNull("etag")) {
				    	batchId = entity.getString("etag");
				    }
				    String type = "";
				    if(!entity.isNull("type")) {
				    	type = entity.getString("type");
				    }

				    JSONObject data = entity.getJSONObject("data");
		            
		            if("post".equalsIgnoreCase(method)) {
	                	response += "{" +
		        	      "\"method\":\"POST\"," +
		        	      "\"batchId\":\"" + batchId + "\"," +
		        	      "\"status\":{";

		                try {
		                	
		                	if("user".equalsIgnoreCase(type)) {
			                	info.simplecloud.core.User scimUser = internalPost(data.toString(), req);
			                	// TODO: move this into storage? 
			                	scimUser.getMeta().setLocation(HttpGenerator.getLocation(scimUser, req));
			                	
			                	response += "\"code\":\"201\"," +
				        	        		"\"reason\":\"Created\"" + 
			                				"},";
				        	        
			                	response += "\"etag\": \"" + scimUser.getMeta().getVersion() + "\"," +
				        	      			"\"data\":" + scimUser.getUser(HttpGenerator.getEncoding(req)) + "," +
				        	      			"\"location\":\"" + scimUser.getMeta().getLocation() + "\"";
		                	}
		                	else {
		                		// todo GROUPS
		                	}
			        	      
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

		                try {
		                	
		                	if("user".equalsIgnoreCase(type)) {
		                		info.simplecloud.core.User scimUser = internalPut(getIdFromUri(location), etag, data.toString(), req);
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
		                	}
		                	else {
		                		// todo: implement groups
		                	}
		                } catch (Exception e) {
		                	response += "\"code\":\"400\"," + 
		                				"\"reason\":\"BAD REQUEST\"," + 
		                				"\"error\":\"Request is unparseable, syntactically incorrect, or violates schema.\"" +
		                				"}";
		                }
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
