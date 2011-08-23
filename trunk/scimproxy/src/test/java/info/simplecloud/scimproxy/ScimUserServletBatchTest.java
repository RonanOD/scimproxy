package info.simplecloud.scimproxy;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUserServletBatchTest {

    private static HttpTester    request  = new HttpTester();
    private static HttpTester    response = new HttpTester();
    private static ServletTester tester   = null;

    @BeforeClass
    public static void setUp() throws Exception {
        tester = new ServletTester();
        tester.addServlet(ScimBatchServlet.class, "/v1/Batch");
        tester.addServlet(DefaultServlet.class, "/");
        tester.start();
    }
    
	String postJson = "{" + 
	  "\"schemas\": [\"urn:scim:schemas:core:1.0\"]," + 
	  "\"Entries\":[" + 
	    "{" + 
	      "\"method\":\"POST\"," + 
	      "\"type\":\"User\"," + 
	      "\"batchId\":\"qwerty\"," + 
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
	      "\"type\":\"User\"," + 
	      "\"location\":\"http://localhost/v1/User/uid=bob,dc=example,dc=com\"," +
	      "\"etag\":\"b431af54f0671a1\"," +
	      "\"data\":{" + 
	        "\"schemas\": [\"urn:scim:schemas:core:1.0\"]," +
	        "\"id\":\"uid=bob,dc=example,dc=com\"," + 
	        "\"userName\":\"Bob\"," + 
	        "\"name\":{" + 
	          "\"formatted\":\"Bob Doe\"," + 
	          "\"familyName\":\"Doe\"," + 
	          "\"givenName\":\"Bob\"" + 
	        "}," +
	        "\"meta\":{" + 
	            "\"location\":\"https://example.com/v1/User/uid=bob,dc=example,dc=com\"," + 
	            "\"version\":\"b431af54f0671a1\"," + 
	            "\"created\":\"2010-05-01T21:32:44.882Z\"," + 
	            "\"lastModified\":\"2010-05-01T21:32:44.882Z\"" + 
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

        request.setURI("/v1/Batch");
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

        request.setURI("/v1/Batch");
        request.setHeader("Content-Length", Integer.toString(putJson.length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(postJson);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());
        postTester(response.getContent(), "qwerty", "alice");

        request.setURI("/v1/Batch");
        request.setHeader("Content-Length", Integer.toString(putJson.length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(putJson);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());
    }

    
    private void postTester(String content, String batchId, String userName) throws Exception {
		JSONObject jsonObj = new JSONObject(content);
		JSONArray entities = jsonObj.getJSONArray("Entries");
		for (int i = 0; i < entities.length(); ++i) {
		    JSONObject entity = entities.getJSONObject(i);

		    Assert.assertEquals("POST", entity.getString("method"));
		    JSONObject status = entity.getJSONObject("status");
		    Assert.assertEquals("201", status.getString("code"));
		    Assert.assertEquals("Created", status.getString("reason"));
		    Assert.assertEquals(batchId, entity.getString("batchId"));
		    
		    JSONObject data = entity.getJSONObject("data");
		    Assert.assertEquals(userName, data.getString("userName"));
		    JSONObject meta = data.getJSONObject("meta");
		    Assert.assertEquals(meta.getString("location"), entity.getString("location"));
		    Assert.assertEquals(meta.getString("version"), entity.getString("etag"));
		}
    }

}
