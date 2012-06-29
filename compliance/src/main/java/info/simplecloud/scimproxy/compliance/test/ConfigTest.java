package info.simplecloud.scimproxy.compliance.test;

import info.simplecloud.scimproxy.compliance.CSP;
import info.simplecloud.scimproxy.compliance.ComplienceUtils;
import info.simplecloud.scimproxy.compliance.Result;
import info.simplecloud.scimproxy.compliance.Schema;
import info.simplecloud.scimproxy.compliance.ServiceProviderConfig;
import info.simplecloud.scimproxy.compliance.exception.CritialComplienceException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONObject;

public class ConfigTest {


	/**
	 * Reads the servers configuration.
	 * @param csp The server.
	 * @param spc The servers supported features.
	 * @return The test Result.
	 * @throws Exception Throws CritialComplienceException if we could not read configuration from server, otherwise fatal exceptions.
	 */
	public Result getConfiguration(CSP csp) throws Exception {
        String url = csp.getUrl() + csp.getVersion() + "/ServiceProviderConfigs";

        GetMethod method = null;
        method = new GetMethod(url );
        
        // create client with the correct authn for server
        HttpClient client = ComplienceUtils.getHttpClientWithAuth(csp, method);

        // Create a method instance.
        ComplienceUtils.configureMethod(method);

        method.setRequestHeader(new Header("Accept", "application/json"));
        method.setRequestHeader(new Header("Content-Type", "application/json"));

        byte[] responseBody;

    	// Execute the method.
        try {
        	int statusCode = client.executeMethod(method);
            // Read the response body.
            responseBody = method.getResponseBody();
            if(statusCode != 200) {
            	throw new Exception(); // catching in next row
            }
        }
        catch (Exception e) {
        	throw new CritialComplienceException(new Result(Result.CRITICAL, "Read ServiceProviderConfig", "Could not get ServiceProviderConfig at url " + url, ComplienceUtils.getWire(method, "")));
        }

        try {
        	
        	ServiceProviderConfig spc = new ServiceProviderConfig();
        	
            String configuration = new String(responseBody);
            JSONObject jsonObj = new JSONObject(configuration);

    		spc.setPatch(jsonObj.getJSONObject("patch").getBoolean("supported"));

    		JSONObject bulkObj = jsonObj.getJSONObject("bulk");
    		spc.setBulk(bulkObj.getBoolean("supported"));
    		spc.setBulkMaxOperations(bulkObj.getInt("maxOperations"));
    		spc.setBulkMaxPayloadSize(bulkObj.getInt("maxPayloadSize"));
    		
    		JSONObject filterObj = jsonObj.getJSONObject("filter");
    		spc.setFilter(filterObj.getBoolean("supported"));
    		spc.setFilderMaxResults(filterObj.getInt("maxResults"));
    		
    		spc.setChangePassword(jsonObj.getJSONObject("changePassword").getBoolean("supported"));
    		spc.setSort(jsonObj.getJSONObject("sort").getBoolean("supported"));
    		spc.setEtag(jsonObj.getJSONObject("etag").getBoolean("supported"));
    		spc.setXmlDataFormat(jsonObj.getJSONObject("xmlDataFormat").getBoolean("supported"));

    		JSONArray authArray = jsonObj.getJSONArray("authenticationSchemes");
    		String auth = "";
    		for(int i=0; i< authArray.length(); i++) {
        		JSONObject authObj = (JSONObject) authArray.get(i);
        		auth += "," + authObj.getString("name");
    		}
    		spc.setAuthenticationSchemes(auth);

    		csp.setSpc(spc);
    		
    		return new Result(Result.SUCCESS, "Read ServiceProviderConfig", "success", ComplienceUtils.getWire(method, ""));
    		
        }
        catch (Exception e) {
        	throw new CritialComplienceException(new Result(Result.CRITICAL, "Parse ServiceProviderConfig", "Could not parse the json format returned from ServiceProviderConfig. " + e.getMessage(), ComplienceUtils.getWire(method, "")));
        }
	}
	
	/**
	 * Reads schemas from the server (could be used for both Users, Groups and other schemas)
	 * @param type The schema to read, example Users or Groups
	 * @param csp The server.
	 * @param schema The resulted schema.
	 * @return The test Result.
	 * @throws Exception Throws CritialComplienceException if we could not read configuration from server, otherwise fatal exceptions.
	 */
	public Result getSchema(String type, CSP csp) throws Exception {
        String url = csp.getUrl() + csp.getVersion() + "/Schemas/" + type;

        GetMethod method = null;
        method = new GetMethod(url );
        
        // create client with the correct authn for server
        HttpClient client = ComplienceUtils.getHttpClientWithAuth(csp, method);

        // Create a method instance.
        ComplienceUtils.configureMethod(method);

        method.setRequestHeader(new Header("Accept", "application/json"));
        method.setRequestHeader(new Header("Content-Type", "application/json"));

        byte[] responseBody;
        
    	// Execute the method.
        try {
        	int statusCode = client.executeMethod(method);
            // Read the response body.
            responseBody = method.getResponseBody();
            if(statusCode != 200) {
            	throw new Exception(); // caching next row
            }
        }
        catch (Exception e) {
        	throw new CritialComplienceException(new Result(Result.CRITICAL, "Get schema for " + type, "Could not get " + type + " Schema at url " + url, ComplienceUtils.getWire(method, "")));
        }

        try {
        	
        	// TODO: add support for subAttributes and canonicalValues
        	Schema schema = new Schema();
            String configuration = new String(responseBody);
            JSONObject jsonObj = new JSONObject(configuration);

            JSONArray jsonArray = jsonObj.getJSONArray("attributes");
    		for(int i=0; i< jsonArray.length(); i++) {
        		JSONObject authObj = (JSONObject) jsonArray.get(i);
        		if(authObj.getBoolean("required")) {
        			schema.addItem(authObj.getString("name"));
        		}
    		}
    		
    		if("Users".equalsIgnoreCase(type)) {
    			csp.setUserSchema(schema);
        	}
    		else {
    			csp.setGroupSchema(schema);
    		}
            
    		return new Result(Result.SUCCESS, "Read schema for " + type, "success", ComplienceUtils.getWire(method, ""));
        }
        catch (Exception e) {
        	throw new CritialComplienceException(new Result(Result.CRITICAL, "Parse schema for " + type, "Could not parse the json format returned when getting " + type + " schema. " + e.getMessage(), ComplienceUtils.getWire(method, "")));
        }
	}
	
}
