package info.simplecloud.scimproxy;

import info.simplecloud.core.Group;
import info.simplecloud.core.User;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimGroupServletPutTest {

    private static HttpTester     request  = new HttpTester();
    private static HttpTester     response = new HttpTester();
    private static ServletTester  tester   = null;

    private static String id       = "";
    private static String etag       = "";

    @BeforeClass
    public static void setUp() throws Exception {
        tester = new ServletTester();
        tester.addServlet(ScimGroupServlet.class, "/v1/Group/*");
        tester.addServlet(DefaultServlet.class, "/");
        tester.start();

        Group scimGroup = new Group("ABC123-put");

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Group");
        request.setHeader("Content-Length", Integer.toString(scimGroup.getGroup(Group.ENCODING_JSON).length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(scimGroup.getGroup(Group.ENCODING_JSON));
        response.parse(tester.getResponses(request.generate()));

        Group tmp = new Group(response.getContent(), Group.ENCODING_JSON);
        id = tmp.getId();
        etag = tmp.getMeta().getVersion();
    }

    @Test
    public void putGroup() throws Exception {
    	Group scimGroup = new Group("ABC123-put");
        scimGroup.setId(id);

        request.setMethod("PUT");
        request.setVersion("HTTP/1.0");

        request.setURI("/v1/Group/" + id);

        request.setHeader("Content-Length", Integer.toString(scimGroup.getGroup(Group.ENCODING_JSON).length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(scimGroup.getGroup(Group.ENCODING_JSON));
        request.setHeader("ETag", etag);

        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());

        Group returnedGroup = new Group(response.getContent(), Group.ENCODING_JSON);

        Assert.assertEquals(id, returnedGroup.getId());
    }

    @Test
    public void putInvalidGroup() throws Exception {
        request.setMethod("PUT");
        request.setVersion("HTTP/1.0");

        request.setURI("/v1/Group/" + id);

        request.setHeader("Content-Length", Integer.toString("very invalid Group".length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent("very invalid Group");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(400, response.getStatus());

    }

}
