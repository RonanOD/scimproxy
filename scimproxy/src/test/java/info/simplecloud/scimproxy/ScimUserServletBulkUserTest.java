package info.simplecloud.scimproxy;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUserServletBulkUserTest {

    private static HttpTester    request  = new HttpTester();
    private static HttpTester    response = new HttpTester();
    private static ServletTester tester   = null;

    @BeforeClass
    public static void setUp() throws Exception {
        tester = new ServletTester();
        tester.addServlet(ScimBulkServlet.class, "/v1/Bulk");
        tester.addServlet(DefaultServlet.class, "/");
        tester.start();
    }
    
	String postJson = "{" + 
	  "\"schemas\": [\"urn:scim:schemas:core:1.0\"]," + 
	  "\"Entries\":[" + 
	    "{" + 
	      "\"method\":\"POST\"," + 
	      "\"type\":\"user\"," + 
	      "\"bulkId\":\"qwerty\"," + 
	      "\"data\":{" + 
	        "\"schemas\": [\"urn:scim:schemas:core:1.0\"]," + 
	        "\"userName\":\"alice\"," + 
	        "\"name\":{" + 
	          "\"formatted\":\"Alice Doe\"," + 
	          "\"familyName\":\"Doe\"," + 
	          "\"givenName\":\"Alice\"" + 
	        "}" + 
	      "}" + 
	    "}" + 
	  "]" + 
	"}";

	String putJson = "{" + 
	  "\"schemas\": [\"urn:scim:schemas:core:1.0\"]," + 
	  "\"Entries\":[" + 
	    "{" + 
	      "\"method\":\"PUT\"," + 
	      "\"type\":\"user\"," + 
	      "\"id\":\"IDPLACEHOLDER\"," +
	      "\"etag\":\"ETAGPLACEHOLDER\"," +
	      "\"data\":{" + 
	        "\"schemas\": [\"urn:scim:schemas:core:1.0\"]," +
	        "\"id\":\"IDPLACEHOLDER\"," + 
	        "\"userName\":\"Bob\"," + 
	        "\"name\":{" + 
	          "\"formatted\":\"Bob Doe\"," + 
	          "\"familyName\":\"Doe\"," + 
	          "\"givenName\":\"Bob\"" + 
	        "}" +
	      "}" + 
	    "}" + 
	  "]" + 
	"}";
	
	
	String deleteJson = "{" + 
	  "\"schemas\": [\"urn:scim:schemas:core:1.0\"]," + 
	  "\"Entries\":[" + 
	    "{" + 
	      "\"method\":\"DELETE\"," + 
	      "\"type\":\"user\"," + 
	      "\"id\":\"IDPLACEHOLDER\"," +
	      "\"etag\":\"ETAGPLACEHOLDER\"" +
	    "}" + 
	  "]" + 
	"}";
	

	String patchJson = "{" + 
	  "\"schemas\": [\"urn:scim:schemas:core:1.0\"]," + 
	  "\"Entries\":[" + 
	    "{" + 
	      "\"method\":\"PATCH\"," +
	      "\"type\":\"user\"," + 
	      "\"id\":\"IDPLACEHOLDER\"," +
	      "\"etag\":\"ETAGPLACEHOLDER\"," +
	      "\"data\":{" + 
	        "\"schemas\": [\"urn:scim:schemas:core:1.0\"]," +
	        "\"id\":\"IDPLACEHOLDER\"," + 
	        "\"userName\":\"Bob\"," + 
	        "\"name\":{" + 
	          "\"formatted\":\"Bob Doe\"," + 
	          "\"familyName\":\"Doe\"," + 
	          "\"givenName\":\"Bob\"" + 
	        "}" + 
	      "}" + 
	    "}" + 
	  "]" + 
	"}";
		
	
    @Test
    public void postUser() throws Exception {

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Bulk");
        request.setHeader("Content-Length", Integer.toString(postJson.length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(postJson);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());
        postTester(response.getContent(), "qwerty", "alice");
    }
    
    @Test
    public void putUser() throws Exception {
    	
        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Bulk");
        request.setHeader("Content-Length", Integer.toString(putJson.length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(postJson);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());
        String content = response.getContent();
        
        postTester(content, "qwerty", "alice");

        String aliceId = "";
        String aliceEtag = "";

		JSONObject jsonObj = new JSONObject(content);
		JSONArray entities = jsonObj.getJSONArray("Entries");
		for (int i = 0; i < entities.length(); ++i) {
		    JSONObject entity = entities.getJSONObject(i);
		    
		    JSONObject data = entity.getJSONObject("data");
		    aliceId = data.getString("id");
		    aliceEtag = entity.getString("etag");

		}
        
		putJson = putJson.replaceAll("IDPLACEHOLDER", aliceId);
		putJson = putJson.replaceAll("ETAGPLACEHOLDER", aliceEtag);

		
        request.setURI("/v1/Bulk");
        request.setHeader("Content-Length", Integer.toString(putJson.length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(putJson);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());

        putTester(response.getContent(), aliceId, "Bob");
    }

    
    @Test
    public void patchUser() throws Exception {
    	
        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Bulk");
        request.setHeader("Content-Length", Integer.toString(putJson.length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(postJson);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());
        String content = response.getContent();
        
        postTester(content, "qwerty", "alice");

        String aliceId = "";
        String aliceEtag = "";

		JSONObject jsonObj = new JSONObject(content);
		JSONArray entities = jsonObj.getJSONArray("Entries");
		for (int i = 0; i < entities.length(); ++i) {
		    JSONObject entity = entities.getJSONObject(i);
		    
		    JSONObject data = entity.getJSONObject("data");
		    aliceId = data.getString("id");
		    aliceEtag = entity.getString("etag");

		}
        
		patchJson = patchJson.replaceAll("IDPLACEHOLDER", aliceId);
		patchJson = patchJson.replaceAll("ETAGPLACEHOLDER", aliceEtag);

        request.setMethod("POST");
        request.setURI("/v1/Bulk");
        request.setHeader("Content-Length", Integer.toString(putJson.length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(patchJson);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());

        patchTester(response.getContent(), aliceId, "Bob");
    }

	@Test
    public void deleteUser() throws Exception {
    	
        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Bulk");
        request.setHeader("Content-Length", Integer.toString(putJson.length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(postJson);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());
        String content = response.getContent();
        
        postTester(content, "qwerty", "alice");

        String aliceId = "";
        String aliceEtag = "";
		JSONObject jsonObj = new JSONObject(content);
		JSONArray entities = jsonObj.getJSONArray("Entries");
		for (int i = 0; i < entities.length(); ++i) {
		    JSONObject entity = entities.getJSONObject(i);
		    
		    JSONObject data = entity.getJSONObject("data");
		    aliceId = data.getString("id");

		    aliceEtag = entity.getString("etag");

		}
        
		deleteJson = deleteJson.replaceAll("IDPLACEHOLDER", aliceId);
		deleteJson = deleteJson.replaceAll("ETAGPLACEHOLDER", aliceEtag);
		
        request.setURI("/v1/Bulk");
        request.setHeader("Content-Length", Integer.toString(deleteJson.length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(deleteJson);
        response.parse(tester.getResponses(request.generate()));

        deleteTester(response.getContent());
        Assert.assertEquals(200, response.getStatus());

    }

    
    private void postTester(String content, String bulkId, String userName) throws Exception {
		JSONObject jsonObj = new JSONObject(content);
		JSONArray entities = jsonObj.getJSONArray("Entries");
		for (int i = 0; i < entities.length(); ++i) {
		    JSONObject entity = entities.getJSONObject(i);

		    Assert.assertEquals("POST", entity.getString("method"));
		    JSONObject status = entity.getJSONObject("status");
		    Assert.assertEquals("201", status.getString("code"));
		    Assert.assertEquals("Created", status.getString("reason"));
		    Assert.assertEquals(bulkId, entity.getString("bulkId"));
		    
		    JSONObject data = entity.getJSONObject("data");
		    Assert.assertEquals(userName, data.getString("userName"));
		    JSONObject meta = data.getJSONObject("meta");
		    Assert.assertEquals(meta.getString("location"), entity.getString("location"));
		    Assert.assertEquals(meta.getString("version"), entity.getString("etag"));
		}
    }

    private void putTester(String content, String bulkId, String userName) throws Exception {
		JSONObject jsonObj = new JSONObject(content);
		JSONArray entities = jsonObj.getJSONArray("Entries");
		for (int i = 0; i < entities.length(); ++i) {
		    JSONObject entity = entities.getJSONObject(i);

		    Assert.assertEquals("PUT", entity.getString("method"));
		    JSONObject status = entity.getJSONObject("status");
		    Assert.assertEquals("200", status.getString("code"));
		    Assert.assertEquals("Updated", status.getString("reason"));
		    
		    JSONObject data = entity.getJSONObject("data");
		    Assert.assertEquals(userName, data.getString("userName"));
		    JSONObject meta = data.getJSONObject("meta");
		    Assert.assertEquals(meta.getString("location"), entity.getString("location"));
		    Assert.assertEquals(meta.getString("version"), entity.getString("etag"));
		}
    }
    
    
    private void patchTester(String content, String aliceId, String userName) throws Exception {
		JSONObject jsonObj = new JSONObject(content);
		JSONArray entities = jsonObj.getJSONArray("Entries");
		for (int i = 0; i < entities.length(); ++i) {
		    JSONObject entity = entities.getJSONObject(i);

		    Assert.assertEquals("PATCH", entity.getString("method"));
		    JSONObject status = entity.getJSONObject("status");
		    Assert.assertEquals("200", status.getString("code"));
		    Assert.assertEquals("Patched", status.getString("reason"));
		    
		    JSONObject data = entity.getJSONObject("data");
		    Assert.assertEquals(userName, data.getString("userName"));
		    JSONObject meta = data.getJSONObject("meta");
		    Assert.assertEquals(meta.getString("location"), entity.getString("location"));
		    Assert.assertEquals(meta.getString("version"), entity.getString("etag"));
		}
	}


    private void deleteTester(String content) throws Exception {
		JSONObject jsonObj = new JSONObject(content);
		JSONArray entities = jsonObj.getJSONArray("Entries");
		for (int i = 0; i < entities.length(); ++i) {
		    JSONObject entity = entities.getJSONObject(i);

		    Assert.assertEquals("DELETE", entity.getString("method"));
		    JSONObject status = entity.getJSONObject("status");
		    Assert.assertEquals("200", status.getString("code"));
		    Assert.assertEquals("Deleted", status.getString("reason"));
		}
    }

}
