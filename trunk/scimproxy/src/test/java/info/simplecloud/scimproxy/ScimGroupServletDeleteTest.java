package info.simplecloud.scimproxy;

import info.simplecloud.core.Group;
import info.simplecloud.scimproxy.test.ScimGroupServletTest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimGroupServletDeleteTest {

    private static HttpTester    request  = new HttpTester();
    private static HttpTester    response = new HttpTester();
    private static ServletTester tester   = null;

    private static String        id       = "";

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

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups");
        request.setHeader("Content-Length", Integer.toString(scimGroup.getGroup(Group.ENCODING_JSON).length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(scimGroup.getGroup(Group.ENCODING_JSON));
        response.parse(tester.getResponses(request.generate()));

        Group tmp = new Group(response.getContent(), Group.ENCODING_JSON);
        id = tmp.getId();
    }

    @Test
    public void deleteGroup() throws Exception {

        request.setMethod("GET");
        request.setVersion("HTTP/1.0");

        // get resource to see if it's there
        request.setURI("/v1/Groups/" + id);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());

        Group scimGroup = new Group(response.getContent(), Group.ENCODING_JSON);
        Assert.assertEquals(id, scimGroup.getId());

        // delete resource

        request.setMethod("DELETE");
        request.setVersion("HTTP/1.0");
        request.setHeader("If-Match", scimGroup.getMeta().getVersion());
        response.parse(tester.getResponses(request.generate()));

        Assert.assertTrue(response.getMethod() == null);
        Assert.assertEquals(200, response.getStatus());

        // next request should be 404

        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.removeHeader("ETag");
        response.parse(tester.getResponses(request.generate()));
        Assert.assertTrue(response.getMethod() == null);
        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void deleteNonExistingUser() throws Exception {
        request.setMethod("DELETE");
        request.setVersion("HTTP/1.0");

        // get resource to see if it's there
        request.setURI("/v1/Groups/jsjsjsjsjsjs");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(404, response.getStatus());
    }

}
