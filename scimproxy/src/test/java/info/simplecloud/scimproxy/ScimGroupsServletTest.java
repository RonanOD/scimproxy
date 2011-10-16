package info.simplecloud.scimproxy;

import info.simplecloud.core.Group;
import info.simplecloud.core.User;
import info.simplecloud.core.types.PluralType;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimGroupsServletTest {

    private static ServletTester tester   = new ServletTester();
    private static HttpTester    request  = new HttpTester();
    private static HttpTester    response = new HttpTester();

    private static String        managersId  = "";
    private static Group      managers    = new Group("qwerty123456");

    @BeforeClass
    public static void setUp() throws Exception {
        tester = new ServletTester();
        tester.addServlet(ScimGroupServlet.class, "/v1/Group/*");
        tester.addServlet(ScimGroupsServlet.class, "/v1/Groups");
        tester.addServlet(ScimGroupsServlet.class, "/v1/Groups.xml");
        tester.addServlet(ScimGroupsServlet.class, "/v1/Groups.json");
        tester.addServlet(DefaultServlet.class, "/");
        tester.start();

        managers.setDisplayName("Managers");
        List<PluralType<String>> members = new ArrayList<PluralType<String>>();
        members.add(new PluralType<String>("User1", null, false, false));
        managers.setMembers(members);
        
        HttpTester aliceRequest = new HttpTester();
        HttpTester aliceResponse = new HttpTester();
        aliceRequest.setMethod("POST");
        aliceRequest.setVersion("HTTP/1.0");
        aliceRequest.setHeader("Authorization", "Basic dXNyOnB3");

        aliceRequest.setURI("/v1/Group");
        aliceRequest.setHeader("Content-Length", Integer.toString(managers.getGroup(Group.ENCODING_JSON).length()));
        aliceRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
        aliceRequest.setContent(managers.getGroup(Group.ENCODING_JSON));
        aliceResponse.parse(tester.getResponses(aliceRequest.generate()));

        User addedAlice = new User(aliceResponse.getContent(), Group.ENCODING_JSON);
        managersId = addedAlice.getId();
    }

    @Test
    public void getAllGroups() throws Exception {

        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups");
        response.parse(tester.getResponses(request.generate()));

        String groups = response.getContent();

        List<Group> groupList = Group.getGroups(groups, User.ENCODING_JSON);

        boolean aliceFound = false;

        for (Group scimGroup : groupList) {
            if (managersId.equals(scimGroup.getId())) {
                aliceFound = true;
                Assert.assertEquals(scimGroup.getDisplayName(), "Managers");
            }
        }

        Assert.assertTrue(aliceFound);
    }
    /*
    @Test
    public void getAllGroupsXml() throws Exception {

        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups.xml");
        response.parse(tester.getResponses(request.generate()));

        String groups = response.getContent();

        List<Group> groupList = Group.getGroups(groups, User.ENCODING_XML);

        boolean aliceFound = false;

        for (Group scimGroup : groupList) {
            if (managersId.equals(scimGroup.getId())) {
                aliceFound = true;
                Assert.assertEquals(scimGroup.getDisplayName(), "Managers");
            }
        }

        Assert.assertTrue(aliceFound);
    }  
    */
    @Test
    public void getAllGroupsJson() throws Exception {

        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups.json");
        response.parse(tester.getResponses(request.generate()));

        String groups = response.getContent();

        List<Group> groupList = Group.getGroups(groups, User.ENCODING_JSON);

        boolean aliceFound = false;

        for (Group scimGroup : groupList) {
            if (managersId.equals(scimGroup.getId())) {
                aliceFound = true;
                Assert.assertEquals(scimGroup.getDisplayName(), "Managers");
            }
        }

        Assert.assertTrue(aliceFound);
    }    
    
    

    @Test
    public void getAllGroupsJsonArgument() throws Exception {

        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/Groups.json?asdasdasd");
        response.parse(tester.getResponses(request.generate()));

        String groups = response.getContent();

        List<Group> groupList = Group.getGroups(groups, User.ENCODING_JSON);

        boolean aliceFound = false;

        for (Group scimGroup : groupList) {
            if (managersId.equals(scimGroup.getId())) {
                aliceFound = true;
                Assert.assertEquals(scimGroup.getDisplayName(), "Managers");
            }
        }

        Assert.assertTrue(aliceFound);
    }    
}
