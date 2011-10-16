package info.simplecloud.scimproxy;

import info.simplecloud.core.User;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUserServletGetTest {

    private static HttpTester     request  = new HttpTester();
    private static HttpTester     response = new HttpTester();
    private static ServletTester  tester   = null;

    private static String id       = "";

    @BeforeClass
    public static void setUp() throws Exception {
        tester = new ServletTester();
        tester.addServlet(ScimUserServlet.class, "/v1/User/*");
        tester.addServlet(DefaultServlet.class, "/");
        tester.start();

        User scimUser = new User("ABC123-get");
        scimUser.setUserName("Alice");
        scimUser.setNickName("A");

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setURI("/v1/User");
        request.setHeader("Content-Length", Integer.toString(scimUser.getUser(User.ENCODING_JSON).length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setHeader("Authorization", "Basic dXNyOnB3");
        request.setContent(scimUser.getUser(User.ENCODING_JSON));
        response.parse(tester.getResponses(request.generate()));

        User tmp = new User(response.getContent(), User.ENCODING_JSON);
        id = tmp.getId();
    }

    @Test
    public void getUser() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User/" + id);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());

        User scimUser = new User(response.getContent(), User.ENCODING_JSON);

        Assert.assertEquals(id, scimUser.getId());
        Assert.assertEquals("Alice", scimUser.getUserName());
    }


    @Test
    public void getUserJson() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User/" + id + ".json");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());

        User scimUser = new User(response.getContent(), User.ENCODING_JSON);

        Assert.assertEquals(id, scimUser.getId());
        Assert.assertEquals("Alice", scimUser.getUserName());
    }


    @Test
    public void getUserXml() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User/" + id + ".xml");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());

        User scimUser = new User(response.getContent(), User.ENCODING_XML);

        Assert.assertEquals(id, scimUser.getId());
        Assert.assertEquals("Alice", scimUser.getUserName());
    }
    
    
    @Test
    public void missingUser() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User/asdasdasdasd");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void userInput1() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User/asdasdasdasd");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void userInput2() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User/as+da+sd%20as%20da{}0w92827:;pi9u3jwpsd");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void userInput3() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        String longUrl = "/v1/User/as+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsd";
        request.setURI(longUrl);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void noAttribs() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User/" + id + "?attributes=");

        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());

        User scimUser = new User(response.getContent(), User.ENCODING_JSON);
        Assert.assertEquals(id, scimUser.getId());
        Assert.assertNull(scimUser.getUserName());
    }

    @Test
    public void onlyUserName() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User/" + id + "?attributes=nickName");

        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());

        User scimUser = new User(response.getContent(), User.ENCODING_JSON);

        Assert.assertEquals(id, scimUser.getId());
        Assert.assertEquals(null, scimUser.getUserName());
        Assert.assertEquals("A", scimUser.getNickName());
    }
}
