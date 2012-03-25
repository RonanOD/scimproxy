package info.simplecloud.scimproxy;

import info.simplecloud.core.Group;
import info.simplecloud.core.User;
import info.simplecloud.scimproxy.test.ScimGroupServletTest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimGroupServletPostTest {

    private static HttpTester    request  = new HttpTester();
    private static HttpTester    response = new HttpTester();
    private static ServletTester tester   = null;
    
    @BeforeClass
    public static void setUp() throws Exception {
        tester = new ServletTester();
        tester.addServlet(ScimGroupServletTest.class, "/v1/Groups/*");
        tester.addServlet(ScimGroupServletTest.class, "/v1/Groups");
        tester.addServlet(ScimGroupServletTest.class, "/v1/Groups.xml");
        tester.addServlet(ScimGroupServletTest.class, "/v1/Groups.json");
        tester.addServlet(DefaultServlet.class, "/");
        tester.start();
    }

    @Test
    public void createGroupJson() throws Exception {

        Group scimGroup = new Group();
        scimGroup.setDisplayName("name");

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups");
        request.setHeader("Content-Length", Integer.toString(scimGroup.getGroup(Group.ENCODING_JSON).length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(scimGroup.getGroup(Group.ENCODING_JSON));
        response.parse(tester.getResponses(request.generate()));

        postTesterJson(response);
    }
    
    @Test
    public void createGroupJsonEmpty() throws Exception {

        Group scimGroup = new Group();
        scimGroup.setDisplayName("name");

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups");
        request.setHeader("Content-Length", Integer.toString(scimGroup.getGroup(Group.ENCODING_JSON).length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(scimGroup.getGroup(Group.ENCODING_JSON));
        response.parse(tester.getResponses(request.generate()));

        postTesterJson(response);
    }
    

    @Test
    public void createGroupJson2() throws Exception {

        Group scimGroup = new Group();
        scimGroup.setDisplayName("name");

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups");
        request.setHeader("Content-Length", Integer.toString(scimGroup.getGroup(Group.ENCODING_JSON).length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setHeader("Accept", "application/json");
        request.setContent(scimGroup.getGroup(Group.ENCODING_JSON));
        response.parse(tester.getResponses(request.generate()));

        postTesterJson(response);
    }
    

    @Test
    public void createGroupJson3() throws Exception {

        Group scimGroup = new Group();
        scimGroup.setDisplayName("name");

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups.json");
        request.setHeader("Content-Length", Integer.toString(scimGroup.getGroup(Group.ENCODING_JSON).length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setHeader("Accept", "application/json");
        request.setContent(scimGroup.getGroup(Group.ENCODING_JSON));
        response.parse(tester.getResponses(request.generate()));

        postTesterJson(response);
    }
    

    @Test
    public void createGroupJson4() throws Exception {

        Group scimGroup = new Group();
        scimGroup.setDisplayName("name");

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups.json");
        request.setHeader("Content-Length", Integer.toString(scimGroup.getGroup(Group.ENCODING_JSON).length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(scimGroup.getGroup(Group.ENCODING_JSON));
        response.parse(tester.getResponses(request.generate()));

        postTesterJson(response);
    }    
    
    
    @Test
    public void createGroupXml() throws Exception {

        Group scimGroup = new Group();
        scimGroup.setDisplayName("name");

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups.xml");
        request.setHeader("Content-Length", Integer.toString(scimGroup.getGroup(Group.ENCODING_XML).length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setContent(scimGroup.getGroup(Group.ENCODING_XML));
        response.parse(tester.getResponses(request.generate()));

        postTesterXml(response);
    }
    
    @Test
    public void createGroupXml2() throws Exception {

        Group scimGroup = new Group();
        scimGroup.setDisplayName("name");

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups");
        request.setHeader("Content-Length", Integer.toString(scimGroup.getGroup(Group.ENCODING_XML).length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setHeader("Accept", "application/xml");
        request.setContent(scimGroup.getGroup(Group.ENCODING_XML));
        response.parse(tester.getResponses(request.generate()));

        postTesterXml(response);
    }
    

    @Test
    public void createGroupXml3() throws Exception {

        Group scimGroup = new Group();
        scimGroup.setDisplayName("name");

        request.setMethod("POST");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups.xml");
        request.setHeader("Content-Length", Integer.toString(scimGroup.getGroup(Group.ENCODING_XML).length()));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setHeader("Accept", "application/xml");
        request.setContent(scimGroup.getGroup(Group.ENCODING_XML));
        response.parse(tester.getResponses(request.generate()));

        postTesterXml(response);
    }

    
    private void postTesterJson(HttpTester response) throws Exception {
    	Group g = new Group(response.getContent(), User.ENCODING_JSON);
    	Assert.assertEquals(201, response.getStatus());
    	postTester(response, g);
    }
    
    private void postTesterXml(HttpTester response) throws Exception {
    	Group g = new Group(response.getContent(), User.ENCODING_XML);
    	Assert.assertEquals(201, response.getStatus());
    	postTester(response, g);
    }

    private void postTester(HttpTester response, Group g) throws Exception {

    	Assert.assertNotNull("id is null", g.getId());
    	Assert.assertEquals("name", g.getDisplayName());

    }

}
