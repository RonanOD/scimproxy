package info.simplecloud.scimproxy;

import info.simplecloud.core.User;
import info.simplecloud.scimproxy.test.ScimUserServletTest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUserServletPasswordPatchTest {

    private static HttpTester     request  = new HttpTester();
    private static HttpTester     response = new HttpTester();
    private static ServletTester  tester   = null;

    private static String id       = "";

    @BeforeClass
    public static void setUp() throws Exception {
        tester = new ServletTester();
        tester.addServlet(ScimUserServletTest.class, "/v1/Users/*");
        tester.addServlet(DefaultServlet.class, "/");
        tester.start();

        User scimUser = new User();
        scimUser.setUserName("Alice");

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Users");
        request.setHeader("Content-Length", Integer.toString(scimUser.getUser(User.ENCODING_JSON).length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(scimUser.getUser(User.ENCODING_JSON));
        response.parse(tester.getResponses(request.generate()));

        User tmp = new User(response.getContent(), User.ENCODING_JSON);
        id = tmp.getId();
    }

    @Test
    public void patchPassword() throws Exception {
        // get resource to see if it's there
        request.setMethod("PATCH");
        request.setVersion("HTTP/1.0");
        request.setURI("/v1/Users/" + id + "/password");
        
        String query = "{" +
        					"\"schemas\":[\"urn:scim:schemas:core:1.0\"]," +
        					"\"password\":\"t1meMa$heen\"" + 
        				"}";
        request.setContent(query);

        response.parse(tester.getResponses(request.generate()));
        Assert.assertEquals(204, response.getStatus());
    }
   

    @Test
    public void patchPasswordXml() throws Exception {
        // get resource to see if it's there
        request.setMethod("PATCH");
        request.setVersion("HTTP/1.0");
        request.setURI("/v1/Users/" + id + "/password");
        
        String query = "{" +
        					"\"schemas\":[\"urn:scim:schemas:core:1.0\"]," +
        					"\"password\":\"t1meMa$heen\"" + 
        				"}";
        request.setContent(query);

        response.parse(tester.getResponses(request.generate()));
        Assert.assertEquals(204, response.getStatus());
    }
    
    @Test
    public void patchPasswordJson() throws Exception {
        // get resource to see if it's there
        request.setMethod("PATCH");
        request.setVersion("HTTP/1.0");
        request.setURI("/v1/Users/" + id + "/password");
        
        String query = "{" +
        					"\"schemas\":[\"urn:scim:schemas:core:1.0\"]," +
        					"\"password\":\"t1meMa$heen\"" + 
        				"}";
        request.setContent(query);

        response.parse(tester.getResponses(request.generate()));
        Assert.assertEquals(204, response.getStatus());
    }

}
