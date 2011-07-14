package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUserServletGetTest {

    HttpTester     request  = new HttpTester();
    HttpTester     response = new HttpTester();
    ServletTester  tester   = null;

    private String id       = "";

    @BeforeClass
    public void setUp() throws Exception {
        tester = new ServletTester();
        tester.addServlet(ScimUserServlet.class, "/User/*");
        tester.addServlet(DefaultServlet.class, "/");
        tester.start();

        ScimUser scimUser = new ScimUser();
        scimUser.setUserName("Alice");
        scimUser.setNickName("A");

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setURI("/User");
        request.setHeader("Content-Length", Integer.toString(scimUser.getUser("JSON").length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setHeader("Authorization", "Basic dXNyOnB3");
        request.setContent(scimUser.getUser("JSON"));
        response.parse(tester.getResponses(request.generate()));

        ScimUser tmp = new ScimUser(response.getContent(), "JSON");
        id = tmp.getId();
    }

    @Test
    public void getUser() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/User/" + id);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());

        ScimUser scimUser = new ScimUser(response.getContent(), "JSON");

        Assert.assertEquals(id, scimUser.getId());
        Assert.assertEquals("Alice", scimUser.getUserName());
    }

    @Test
    public void missingUser() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/User/asdasdasdasd");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void userInput1() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/1/User/asdasdasdasd");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void userInput2() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/1/User/as+da+sd%20as%20da{}0w92827:;pi9u3jwpsd");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void userInput3() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        String longUrl = "/User/as+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsd";
        request.setURI(longUrl);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void noAttribs() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/User/" + id + "?attributes=");

        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());

        ScimUser scimUser = new ScimUser(response.getContent(), "JSON");

        Assert.assertEquals(id, scimUser.getId());
        Assert.assertEquals("Alice", scimUser.getUserName());
    }

    @Test
    public void onlyUserName() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/User/" + id + "?attributes=nickName");

        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());

        ScimUser scimUser = new ScimUser(response.getContent(), "JSON");

        Assert.assertEquals(id, scimUser.getId());
        Assert.assertEquals(null, scimUser.getUserName());
        Assert.assertEquals("A", scimUser.getNickName());
    }

    @Test
    public void testUnknownAttrib() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/User/" + id + "?attributes=asdasdasdasdasdasd");

        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(400, response.getStatus());
    }

}
