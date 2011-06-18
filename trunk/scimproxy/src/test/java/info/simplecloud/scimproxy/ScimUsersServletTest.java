package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;
import junit.framework.TestCase; 

import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUsersServletTest extends TestCase {

	ServletTester tester = new ServletTester();
	HttpTester request = new HttpTester();
	HttpTester response = new HttpTester();

	String aliceId = "";
	String bobId = "";
	
	public void setUp() throws Exception {
		tester = new ServletTester();
		tester.addServlet(ScimUserServlet.class, "/User/*");
		tester.addServlet(ScimUsersServlet.class, "/Users");
	    tester.addServlet(DefaultServlet.class, "/");
		tester.start();

	    ScimUser alice = new ScimUser();
	    alice.setUserName("Alice");

		HttpTester aliceRequest = new HttpTester();
		HttpTester aliceResponse = new HttpTester();
		aliceRequest.setMethod("POST");
		aliceRequest.setVersion("HTTP/1.0");
		aliceRequest.setURI("/User");
		aliceRequest.setHeader("Content-Length", Integer.toString(alice.getUser("JSON").length()));
		aliceRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		aliceRequest.setContent(alice.getUser("JSON"));
		aliceResponse.parse(tester.getResponses(aliceRequest.generate()));

		ScimUser addedAlice = new ScimUser(aliceResponse.getContent(), "JSON");
		aliceId = addedAlice.getId();
		
	    ScimUser bob = new ScimUser();
	    bob.setUserName("Bob");

		HttpTester bobRequest = new HttpTester();
		HttpTester bobResponse = new HttpTester();
		bobRequest.setMethod("POST");
		bobRequest.setVersion("HTTP/1.0");
		bobRequest.setURI("/User");
		bobRequest.setHeader("Content-Length", Integer.toString(bob.getUser("JSON").length()));
		bobRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		bobRequest.setContent(bob.getUser("JSON"));
		bobResponse.parse(tester.getResponses(bobRequest.generate()));

		ScimUser addedBob = new ScimUser(bobResponse.getContent(), "JSON");
		bobId = addedBob.getId();
		
	}

	public void testUsers() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/Users");
		response.parse(tester.getResponses(request.generate()));
		
//		assertEquals(200, response.getStatus());

		String users = response.getContent();
		
		int i = users.indexOf("totalResults");
		
	//	assertTrue(i == 5);
	}

}

