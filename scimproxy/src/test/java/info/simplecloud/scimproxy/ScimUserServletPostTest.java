package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;
import junit.framework.TestCase;

import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUserServletPostTest extends TestCase {

	HttpTester request = new HttpTester();
	HttpTester response = new HttpTester();
	ServletTester tester = null;
	
	public void setUp() throws Exception {
		tester = new ServletTester();
		tester.addServlet(ScimUserServlet.class, "/User/*");
	    tester.addServlet(DefaultServlet.class, "/");
		tester.start();
	}

	public void testCreateUser() throws Exception {
	    
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

		assertEquals(201, response.getStatus());
	}
	
}

