package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUserServletPutTest {

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
    public void putUser() throws Exception {
        ScimUser scimUser = new ScimUser();
        scimUser.setId(id);
        scimUser.setUserName("Bob");

        request.setMethod("PUT");
        request.setVersion("HTTP/1.0");

        request.setURI("/User/" + id);

        request.setHeader("Content-Length", Integer.toString(scimUser.getUser("JSON").length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(scimUser.getUser("JSON"));
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());

        ScimUser returnedUser = new ScimUser(response.getContent(), "JSON");

        Assert.assertEquals(id, returnedUser.getId());
        Assert.assertEquals("Bob", returnedUser.getUserName());
    }

    @Test
    public void putInvalidUser() throws Exception {
        request.setMethod("PUT");
        request.setVersion("HTTP/1.0");

        request.setURI("/User/" + id);

        request.setHeader("Content-Length", Integer.toString("very invalid user".length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent("very invalid user");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(400, response.getStatus());

    }

}
