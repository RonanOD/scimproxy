package info.simplecloud.scimproxy;

import java.util.ArrayList;
import java.util.List;

import info.simplecloud.core.Resource;
import info.simplecloud.core.User;
import info.simplecloud.core.extensions.EnterpriseAttributes;
import info.simplecloud.core.extensions.types.Manager;
import info.simplecloud.core.types.PluralType;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUserServletPostTest {

    private static HttpTester    request  = new HttpTester();
    private static HttpTester    response = new HttpTester();
    private static ServletTester tester   = null;

    @BeforeClass
    public static void setUp() throws Exception {
        tester = new ServletTester();
        tester.addServlet(ScimUserServlet.class, "/v1/User/*");
        tester.addServlet(DefaultServlet.class, "/");
        tester.start();
    }

    @Test
    public void createUserJson() throws Exception {

        User scimUserJson = new User("ABC123json");
        scimUserJson.setUserName("Alice");

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User");
        request.setHeader("Content-Length", Integer.toString(scimUserJson.getUser(User.ENCODING_JSON).length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(scimUserJson.getUser(User.ENCODING_JSON));
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(201, response.getStatus());
    }

    
/*
    @Test
    public void createUserXml() throws Exception {

        User scimUserXml = new User("ABC123xml");
        scimUserXml.setUserName("Bob");

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User");
        request.setHeader("Content-Length", Integer.toString(scimUserXml.getUser(User.ENCODING_XML).length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setHeader("Accept", "application/xml");
        request.setContent(scimUserXml.getUser(User.ENCODING_XML));
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(201, response.getStatus());
    }
*/
}
