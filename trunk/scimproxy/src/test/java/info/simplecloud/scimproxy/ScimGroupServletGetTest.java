package info.simplecloud.scimproxy;

import info.simplecloud.core.Group;
import info.simplecloud.scimproxy.test.ScimGroupServletTest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimGroupServletGetTest {

    private static HttpTester     request  = new HttpTester();
    private static HttpTester     response = new HttpTester();
    private static ServletTester  tester   = null;

    private static String id       = "";

    @BeforeClass
    public static void setUp() throws Exception {
        tester = new ServletTester();
        tester.addServlet(ScimGroupServletTest.class, "/v1/Groups/*");
        tester.addServlet(ScimGroupServletTest.class, "/v1/Groups");
        tester.addServlet(ScimGroupServletTest.class, "/v1/Groups.xml");
        tester.addServlet(ScimGroupServletTest.class, "/v1/Groups.json");
        tester.addServlet(DefaultServlet.class, "/");
        tester.start();

        Group scimGroup = new Group();
        scimGroup.setDisplayName("mygroup");

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setURI("/v1/Groups");
        request.setHeader("Content-Length", Integer.toString(scimGroup.getGroup(Group.ENCODING_JSON).length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setHeader("Authorization", "Basic dXNyOnB3");
        request.setContent(scimGroup.getGroup(Group.ENCODING_JSON));
        response.parse(tester.getResponses(request.generate()));

        Group tmp = new Group(response.getContent(), Group.ENCODING_JSON);
        id = tmp.getId();
    }

    @Test
    public void getGroup() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups/" + id);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());

        Group scimGroup = new Group(response.getContent(), Group.ENCODING_JSON);

        Assert.assertEquals(id, scimGroup.getId());
    }
    
    
    @Test
    public void getGroupJson() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups/" + id + ".json");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());

        Group scimGroup = new Group(response.getContent(), Group.ENCODING_JSON);

        Assert.assertEquals(id, scimGroup.getId());
        Assert.assertEquals("mygroup", scimGroup.getDisplayName());
    }

/*
    @Test
    public void getGroupXml() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups/" + id + ".json");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());

        Group scimGroup = new Group(response.getContent(), Group.ENCODING_XML);

        Assert.assertEquals(id, scimGroup.getId());
        Assert.assertEquals("mygroup", scimGroup.getDisplayName());
    }
    */

    @Test
    public void missingGroup() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups/asdasdasdasd");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void userInput1() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups/asdasdasdasd");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void userInput2() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups/as+da+sd%20as%20da{}0w92827:;pi9u3jwpsd");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void userInput3() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        String longUrl = "/v1/Groups/as+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsd";
        request.setURI(longUrl);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(404, response.getStatus());
    }

}
