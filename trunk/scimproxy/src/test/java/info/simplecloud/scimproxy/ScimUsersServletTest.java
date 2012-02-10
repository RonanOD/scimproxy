package info.simplecloud.scimproxy;

import info.simplecloud.core.Resource;
import info.simplecloud.core.User;

import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUsersServletTest {

    private static ServletTester tester   = new ServletTester();
    private static HttpTester    request  = new HttpTester();
    private static HttpTester    response = new HttpTester();

    private static String        aliceId  = "";
    private static String        bobId    = "";
    private static User          alice    = new User("ABC123-alice");
    private static User          bob      = new User("ABC123-bob");

    @BeforeClass
    public static void setUp() throws Exception {
        tester = new ServletTester();
        tester.addServlet(ScimUserServlet.class, "/v1/User/*");
        tester.addServlet(ScimUserServlet.class, "/v1/User");
        tester.addServlet(ScimUserServlet.class, "/v1/User.xml");
        tester.addServlet(ScimUserServlet.class, "/v1/User.json");
        tester.addServlet(DefaultServlet.class, "/");
        tester.start();

        alice.setUserName("Alice");
        alice.setNickName("A");

        HttpTester aliceRequest = new HttpTester();
        HttpTester aliceResponse = new HttpTester();
        aliceRequest.setMethod("POST");
        aliceRequest.setVersion("HTTP/1.0");
        aliceRequest.setHeader("Authorization", "Basic dXNyOnB3");

        aliceRequest.setURI("/v1/User");
        aliceRequest.setHeader("Content-Length", Integer.toString(alice.getUser(User.ENCODING_JSON).length()));
        aliceRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
        aliceRequest.setContent(alice.getUser(User.ENCODING_JSON));
        aliceResponse.parse(tester.getResponses(aliceRequest.generate()));

        User addedAlice = new User(aliceResponse.getContent(), User.ENCODING_JSON);
        aliceId = addedAlice.getId();

        bob.setUserName("Bob");
        bob.setNickName("B");

        HttpTester bobRequest = new HttpTester();
        HttpTester bobResponse = new HttpTester();
        bobRequest.setMethod("POST");
        bobRequest.setVersion("HTTP/1.0");
        bobRequest.setHeader("Authorization", "Basic dXNyOnB3");
        bobRequest.setURI("/v1/User");
        bobRequest.setHeader("Content-Length", Integer.toString(bob.getUser(User.ENCODING_JSON).length()));
        bobRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
        bobRequest.setContent(bob.getUser(User.ENCODING_JSON));
        bobResponse.parse(tester.getResponses(bobRequest.generate()));

        User addedBob = new User(bobResponse.getContent(), User.ENCODING_JSON);
        bobId = addedBob.getId();

    }

    @Test
    public void getAllAndFindAliceAndBob() throws Exception {

        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User");
        response.parse(tester.getResponses(request.generate()));

        String users = response.getContent();

        List<User> userList = User.getUsers(users, User.ENCODING_JSON);

        boolean aliceFound = false;
        boolean bobFound = false;

        for (User scimUser : userList) {
            if (bobId.equals(scimUser.getId())) {
                bobFound = true;
            }
            if (aliceId.equals(scimUser.getId())) {
                aliceFound = true;
            }
        }

        Assert.assertTrue(bobFound);
        Assert.assertTrue(aliceFound);
    }

    @Test
    public void getAllAndFindAliceAndBobJson() throws Exception {

        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User.json");
        response.parse(tester.getResponses(request.generate()));

        String users = response.getContent();

        List<User> userList = User.getUsers(users, User.ENCODING_JSON);

        boolean aliceFound = false;
        boolean bobFound = false;

        for (User scimUser : userList) {
            if (bobId.equals(scimUser.getId())) {
                bobFound = true;
            }
            if (aliceId.equals(scimUser.getId())) {
                aliceFound = true;
            }
        }

        Assert.assertTrue(bobFound);
        Assert.assertTrue(aliceFound);
    }

    @Test
    public void getAllAndFindAliceAndBobXml() throws Exception {

        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User.xml");
        response.parse(tester.getResponses(request.generate()));

        String users = response.getContent();

        List<User> userList = User.getUsers(users, Resource.ENCODING_XML);

        boolean aliceFound = false;
        boolean bobFound = false;

        for (User scimUser : userList) {
            if (bobId.equals(scimUser.getId())) {
                bobFound = true;
            }
            if (aliceId.equals(scimUser.getId())) {
                aliceFound = true;
            }
        }

        Assert.assertTrue(bobFound);
        Assert.assertTrue(aliceFound);
    }

    @Test
    public void sortUserNameAsc() throws Exception {

        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User?sortBy=userName&sortOrder=ascending");
        response.parse(tester.getResponses(request.generate()));

        String users = response.getContent();

        List<User> userList = User.getUsers(users, User.ENCODING_JSON);

        boolean aliceFoundFirst = false;

        for (User scimUser : userList) {
            if (bobId.equals(scimUser.getId())) {
                Assert.assertEquals(true, aliceFoundFirst);
            }
            if (aliceId.equals(scimUser.getId())) {
                aliceFoundFirst = true;
            }
        }
    }

    @Test
    public void sortUserNameAscJson() throws Exception {

        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User.json?sortBy=userName&sortOrder=ascending");
        response.parse(tester.getResponses(request.generate()));

        String users = response.getContent();

        List<User> userList = User.getUsers(users, User.ENCODING_JSON);

        boolean aliceFoundFirst = false;

        for (User scimUser : userList) {
            if (bobId.equals(scimUser.getId())) {
                Assert.assertEquals(true, aliceFoundFirst);
            }
            if (aliceId.equals(scimUser.getId())) {
                aliceFoundFirst = true;
            }
        }
    }

    @Test
    public void sortNickDesc() throws Exception {

        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User?sortBy=userName&sortOrder=descending&attributes=nickName");
        response.parse(tester.getResponses(request.generate()));

        String users = response.getContent();
        // TODO: SPEC: REST: Should users that's missing attribute nickName be
        // returned?
        List<User> userList = User.getUsers(users, User.ENCODING_JSON);

        boolean bobFoundFirst = false;

        for (User scimUser : userList) {
            if (bobId.equals(scimUser.getId())) {
                bobFoundFirst = true;
            }
            if (aliceId.equals(scimUser.getId())) {
                Assert.assertEquals(true, bobFoundFirst);
            }
        }
    }

    @Test
    public void sortNoAttribs() throws Exception {

        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User?sortBy=userName&sortOrder=descending&attributes=");
        response.parse(tester.getResponses(request.generate()));

        String users = response.getContent();
        // TODO: SPEC: REST: Should users that's missing attribute nickName be
        // returned?
        List<User> userList = User.getUsers(users, User.ENCODING_JSON);

        boolean bobFoundFirst = false;

        for (User scimUser : userList) {
            if (bobId.equals(scimUser.getId())) {
                bobFoundFirst = true;
            }
            if (aliceId.equals(scimUser.getId())) {
                Assert.assertEquals(true, bobFoundFirst);
            }
        }
    }

    @Test
    public void filterByEaualsNickName() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User?filter=nickName%20eq%20B");
        response.parse(tester.getResponses(request.generate()));

        String users = response.getContent();
        // TODO: SPEC: REST: Should users that's missing attribute nickName be
        // returned?
        List<User> userList = User.getUsers(users, User.ENCODING_JSON);

        boolean bobFound = false;
        boolean aliceFound = false;

        for (User scimUser : userList) {
            if (bobId.equals(scimUser.getId())) {
                bobFound = true;
            }
            if (aliceId.equals(scimUser.getId())) {
                aliceFound = true;
            }

        }

        Assert.assertEquals(true, bobFound);
        Assert.assertEquals(false, aliceFound);
    }

    @Test
    public void filterByEaualsNoMatch() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User?filter=nickName%20eq%20asdasdasd");
        response.parse(tester.getResponses(request.generate()));

        String users = response.getContent();
        // TODO: SPEC: REST: Should users that's missing attribute nickName be
        // returned?
        List<User> userList = User.getUsers(users, User.ENCODING_JSON);

        Assert.assertEquals(0, userList.size());
    }

    @Test
    public void filterByEaualsUserNameOnly() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User?filter=userName%20eq%20Bob&attributes=userName");
        response.parse(tester.getResponses(request.generate()));

        String users = response.getContent();
        // TODO: SPEC: REST: Should users that's missing attribute nickName be
        // returned?
        List<User> userList = User.getUsers(users, User.ENCODING_JSON);

        boolean onlyBobs = true;
        boolean noNicks = true;

        for (User scimUser : userList) {
            if (!"Bob".equals(scimUser.getUserName())) {
                onlyBobs = false;
            }
            if ("B".equals(scimUser.getNickName())) {
                noNicks = false;
            }
        }

        Assert.assertEquals(true, onlyBobs);
        Assert.assertEquals(true, noNicks);
    }

    @Test
    public void filterByNoneAttribute() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User?filter=sjshjsdfhjkshdfjsdf%20eq%20Bob");
        response.parse(tester.getResponses(request.generate()));

        String users = response.getContent();
        // TODO: SPEC: REST: Should users that's missing attribute nickName be
        // returned?
        List<User> userList = User.getUsers(users, User.ENCODING_JSON);

        Assert.assertEquals(0, userList.size());
    }

    @Test
    public void filterByContains() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User?filter=userName%20co%20ob");
        response.parse(tester.getResponses(request.generate()));

        String users = response.getContent();
        // TODO: SPEC: REST: Should users that's missing attribute nickName be
        // returned?
        List<User> userList = User.getUsers(users, User.ENCODING_JSON);

        boolean onlyBobs = true;

        for (User scimUser : userList) {
            if (!"Bob".equals(scimUser.getUserName())) {
                onlyBobs = false;
            }
        }

        Assert.assertEquals(true, onlyBobs);
        Assert.assertEquals(true, (userList.size() > 0));
    }

    @Test
    public void filterByStartsWith() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User?filter=userName%20sw%20Bo");
        response.parse(tester.getResponses(request.generate()));

        String users = response.getContent();
        // TODO: SPEC: REST: Should users that's missing attribute nickName be
        // returned?
        List<User> userList = User.getUsers(users, User.ENCODING_JSON);

        boolean onlyBobs = true;

        for (User scimUser : userList) {
            if (!"Bob".equals(scimUser.getUserName())) {
                onlyBobs = false;
            }
        }

        Assert.assertEquals(true, onlyBobs);
        Assert.assertEquals(true, (userList.size() > 0));
    }

    @Test
    public void filterByPresent() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User?filter=nickName%20pr%20nickName");
        response.parse(tester.getResponses(request.generate()));

        String users = response.getContent();
        // TODO: SPEC: REST: Should users that's missing attribute nickName be
        // returned?
        List<User> userList = User.getUsers(users, User.ENCODING_JSON);

        boolean bobFound = false;
        boolean aliceFound = false;

        for (User scimUser : userList) {
            if (bobId.equals(scimUser.getId())) {
                bobFound = true;
            }
            if (aliceId.equals(scimUser.getId())) {
                aliceFound = true;
            }

        }

        Assert.assertEquals(true, bobFound);
        Assert.assertEquals(true, aliceFound);
    }

    @Test
    public void pagingIndexOutOfBound() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/User?startIndex=10000&count=20000");
        response.parse(tester.getResponses(request.generate()));

        String users = response.getContent();
        // TODO: SPEC: REST: Should users that's missing attribute nickName be
        // returned?
        List<User> userList = User.getUsers(users, User.ENCODING_JSON);

        Assert.assertEquals(true, (userList.size() == 0));
    }

}
