package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;
import junit.framework.TestCase;

import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUserServletAuthTest extends TestCase {

	HttpTester request = new HttpTester();
	HttpTester response = new HttpTester();
	ServletTester tester = null;

	private String id = "";
	
	public void setUp() throws Exception {
		tester = new ServletTester();
		tester.addServlet(ScimUserServlet.class, "/User/*");
	    tester.addServlet(DefaultServlet.class, "/");
	    tester.start();
	    
	    ScimUser scimUser = new ScimUser();
	    scimUser.setUserName("Alice");
	    scimUser.setNickName("A");

		request.setMethod("POST");
		request.setVersion("HTTP/1.0");
		request.setURI("/User");
		request.setHeader("Content-Length", Integer.toString(scimUser.getUser("JSON").length()));
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		request.setHeader("Authorization", "Basic dXNyOnB3");
		request.setContent(scimUser.getUser("JSON"));
		response.parse(tester.getResponses(request.generate()));

		ScimUser tmp = new ScimUser(response.getContent(), "JSON");
		id = tmp.getId();
	}
	

	public void testNotAuthenticated() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.removeHeader("Authorization");

		request.setURI("/User/" + id);

		response.parse(tester.getResponses(request.generate()));

		assertEquals(401, response.getStatus());
	}	

	public void testMalformedAuth1() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.removeHeader("Authorization");
		request.setHeader("Authorizationsasdasd", "Basic dXNyOnB3");

		request.setURI("/User/" + id);

		response.parse(tester.getResponses(request.generate()));

		assertEquals(401, response.getStatus());
	}	
	

	public void testMalformedAuth2() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setHeader("Authorization", "dXNyOnB3");

		request.setURI("/User/" + id);

		response.parse(tester.getResponses(request.generate()));

		assertEquals(401, response.getStatus());
	}	

	public void testMalformedAuth3() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setHeader("Authorization", "Basic asdXNyOnB3");

		request.setURI("/User/" + id);

		response.parse(tester.getResponses(request.generate()));

		assertEquals(401, response.getStatus());
	}	

}

