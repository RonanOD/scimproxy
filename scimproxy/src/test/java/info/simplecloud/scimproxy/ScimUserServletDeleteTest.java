package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUserServletDeleteTest {

    private HttpTester    request  = new HttpTester();
    private HttpTester    response = new HttpTester();
    private ServletTester tester   = null;

    private String        id       = "";

    @BeforeClass
    public void setUp() throws Exception {
        tester = new ServletTester();
        tester.addServlet(ScimUserServlet.class, "/User/*");
        tester.addServlet(DefaultServlet.class, "/");
        tester.start();

        ScimUser scimUser = new ScimUser();
        scimUser.setUserName("Alice");

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/User");
        request.setHeader("Content-Length", Integer.toString(scimUser.getUser("JSON").length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(scimUser.getUser("JSON"));
        response.parse(tester.getResponses(request.generate()));

        ScimUser tmp = new ScimUser(response.getContent(), "JSON");
        id = tmp.getId();
    }

    @Test
    public void deleteUser() throws Exception {

        request.setMethod("GET");
        request.setVersion("HTTP/1.0");

        // get resource to see if it's there
        request.setURI("/User/" + id);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());

        ScimUser scimUser = new ScimUser(response.getContent(), "JSON");

        Assert.assertEquals(id, scimUser.getId());

        // delete resource

        request.setMethod("DELETE");
        request.setVersion("HTTP/1.0");
        request.setHeader("ETag", scimUser.getMeta().getVersion());

        response.parse(tester.getResponses(request.generate()));

        Assert.assertTrue(response.getMethod() == null);
        Assert.assertEquals(200, response.getStatus());

        // next request should be 404
        request.setURI("/User/" + id);
        response.parse(tester.getResponses(request.generate()));

        Assert.assertTrue(response.getMethod() == null);
        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void deleteNonExistingUser() throws Exception {
        request.setMethod("DELETE");
        request.setVersion("HTTP/1.0");

        // get resource to see if it's there
        request.setURI("/User/jsjsjsjsjsjs");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(404, response.getStatus());
    }

}
