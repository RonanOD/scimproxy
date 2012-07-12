package info.simplecloud.scimproxy;

import info.simplecloud.core.User;
import info.simplecloud.scimproxy.test.ScimUserServletTest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUserServletPatchTest {

    private static HttpTester     request  = new HttpTester();
    private static HttpTester     response = new HttpTester();
    private static ServletTester  tester   = null;

    private static String id       = "";

    @BeforeClass
    public static void setUp() throws Exception {
        tester = new ServletTester();
        tester.addServlet(ScimUserServletTest.class, "/v1/Users/*");
        tester.addServlet(ScimUserServletTest.class, "/v1/Users");
        tester.addServlet(ScimUserServletTest.class, "/v1/Users.xml");
        tester.addServlet(ScimUserServletTest.class, "/v1/Users.json");
        tester.addServlet(DefaultServlet.class, "/");
        tester.start();

        User scimUser = new User("ABC123-patch");
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

    /**
     * Test update user with PATCH.
     * 
     * @throws Exception
     */
    @Test
    public void patchUser() throws Exception {
        // get resource to see if it's there
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setURI("/v1/Users/" + id);
        response.parse(tester.getResponses(request.generate()));
        Assert.assertEquals(200, response.getStatus());

        User scimUser = new User(response.getContent(), User.ENCODING_JSON);
        Assert.assertEquals(null, scimUser.getDisplayName());

        // edit user by adding display name
        scimUser.setDisplayName("Bob");

        HttpTester request2 = new HttpTester();
        HttpTester response2 = new HttpTester();
        request2.setMethod("PATCH");
        request2.setVersion("HTTP/1.0");
        request2.setHeader("Authorization", "Basic dXNyOnB3");

        request2.setURI("/v1/Users/" + id);
        request2.setContent(scimUser.getUser(User.ENCODING_JSON));
        request2.setHeader("Content-Length", Integer.toString(scimUser.getUser(User.ENCODING_JSON).length()));
        request2.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request2.setHeader("If-Match", scimUser.getMeta().getVersion());
        response2.parse(tester.getResponses(request2.generate()));

        Assert.assertEquals(200, response2.getStatus());

        // next request should be have displayName
        HttpTester request3 = new HttpTester();
        HttpTester response3 = new HttpTester();
        request3.setMethod("GET");
        request3.setVersion("HTTP/1.0");
        request3.setHeader("Authorization", "Basic dXNyOnB3");
        request3.setURI("/v1/Users/" + id);
        response3.parse(tester.getResponses(request3.generate()));

        String r = response3.getContent();
//        Assert.assertEquals("Bob", new User(r, User.ENCODING_JSON).getDisplayName());
    }

    @Test
    public void patchUserMissingContent() throws Exception {
        request.setMethod("PATCH");
        request.setVersion("HTTP/1.0");
        request.setURI("/v1/Users/" + id);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void testPatchUserMissingContentZeroLength() throws Exception {
        request.setMethod("PATCH");
        request.setVersion("HTTP/1.0");
        request.setURI("/v1/Users/" + id);
        request.setHeader("Content-Length", "0");
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");

        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(400, response.getStatus());
    }

    /**
     * Test update user with PATCH, missing ETag.
     * 
     * @throws Exception
     */
/*    @Test
    public void patchUserWrongETag() throws Exception {
        // get resource to see if it's there
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setURI("/v1/Users/" + id);
        response.parse(tester.getResponses(request.generate()));
        Assert.assertEquals(200, response.getStatus());

        User scimUser = new User(response.getContent(), User.ENCODING_JSON);

        // edit user by adding display name
        scimUser.setDisplayName("Bob");

        HttpTester request2 = new HttpTester();
        HttpTester response2 = new HttpTester();
        request2.setMethod("PATCH");
        request2.setVersion("HTTP/1.0");
        request2.setHeader("Authorization", "Basic dXNyOnB3");

        request2.setURI("/v1/Users/" + id);
        request2.setContent(scimUser.getUser(User.ENCODING_JSON));
        request2.setHeader("Content-Length", Integer.toString(scimUser.getUser(User.ENCODING_JSON).length()));
        request2.setHeader("Content-Type", "application/x-www-form-urlencoded");

        // missing etag
        response2.parse(tester.getResponses(request2.generate()));
        Assert.assertEquals(400, response2.getStatus()); // HttpServletResponse.SC_PRECONDITION_FAILED

        // empty etag
        request2.setHeader("ETag", "");
        response2.parse(tester.getResponses(request2.generate()));
        Assert.assertEquals(400, response2.getStatus()); // HttpServletResponse.SC_PRECONDITION_FAILED

        // wrong etag
        request2.setHeader("ETag", "wrongETag");
        response2.parse(tester.getResponses(request2.generate()));
        Assert.assertEquals(412, response2.getStatus()); // HttpServletResponse.SC_PRECONDITION_FAILED

    }
*/
    /**
     * Test update user with PATCH. Removing emails.
     * 
     * @throws Exception
     */
    @Test
    public void patchUserRemoveAttribs() throws Exception {
        /*
         * tester = new ServletTester();
         * tester.addServlet(ScimUserServlet.class, "/Users/*");
         * tester.addServlet(DefaultServlet.class, "/"); tester.start();
         * 
         * // get resource to see if it's there request.setMethod("GET");
         * request.setVersion("HTTP/1.0"); request.setURI("/Users/" + id);
         * response.parse(tester.getResponses(request.generate()));
         * 
         * assertEquals(200, response.getStatus());
         * 
         * ScimUser scimUser = new ScimUser(response.getContent(), User.ENCODING_JSON);
         * 
         * // set display name and remove email attributes
         * scimUser.setDisplayName("Bob"); Meta meta = new Meta();
         * ArrayList<String> l = new ArrayList<String>(); l.add("emails");
         * l.add("department"); meta.setAttributes(l); scimUser.setMeta(meta);
         * 
         * HttpTester request2 = new HttpTester(); HttpTester response2 = new
         * HttpTester(); request2.setMethod("PATCH");
         * request2.setVersion("HTTP/1.0"); request2.setURI("/Users/" + id);
         * request2.setContent(scimUser.getUser(User.ENCODING_JSON));
         * request2.setHeader("Content-Length",
         * Integer.toString(scimUser.getUser(User.ENCODING_JSON).length()));
         * request2.setHeader("Content-Type",
         * "application/x-www-form-urlencoded"); request2.setHeader("ETag",
         * scimUser.getMeta().getVersion());
         * response2.parse(tester.getResponses(request2.generate()));
         * 
         * assertEquals(200, response2.getStatus());
         * 
         * // next request should be have displayName but no email addresses
         * HttpTester request3 = new HttpTester(); HttpTester response3 = new
         * HttpTester(); request3.setURI("/Users/" + id);
         * response3.parse(tester.getResponses(request2.generate()));
         * 
         * ScimUser scimUser2 = new ScimUser(response3.getContent(), User.ENCODING_JSON);
         * 
         * assertEquals("Bob", scimUser2.getDisplayName()); assertEquals(null,
         * scimUser2.getEmails());
         */
    }

}
