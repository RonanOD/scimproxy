package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;
import junit.framework.TestCase;

import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUserServletPutTest extends TestCase {

	HttpTester request = new HttpTester();
	HttpTester response = new HttpTester();
	ServletTester tester = null;

	/**
	 * Setup tests. Binding servlet to /User
	 */
	public void setUp() throws Exception {
	}

	public void testPutUser() throws Exception {
		tester = new ServletTester();
		tester.addServlet(ScimUserServlet.class, "/User/*");
	    tester.addServlet(DefaultServlet.class, "/");
	    
	    ScimUser scimUser = new ScimUser();
	    scimUser.setId("erwah-1234-5678");
	    scimUser.setUserName("Erik W");
	    
		tester.start();
		request.setMethod("PUT");
		request.setVersion("HTTP/1.0");

		request.setURI("/User/erwah-1234-5678");

		request.setHeader("Content-Length", Integer.toString(scimUser.getUser("JSON").length()));
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		request.setContent(scimUser.getUser("JSON"));
		response.parse(tester.getResponses(request.generate()));

		assertEquals(201, response.getStatus());
		        
        ScimUser returnedUser = new ScimUser(response.getContent(), "JSON");
        
        assertEquals("erwah-1234-5678", returnedUser.getId());
        assertEquals("Erik W", returnedUser.getUserName());
	}
	

	public void testPutInvalidUser() throws Exception {
		tester = new ServletTester();
		tester.addServlet(ScimUserServlet.class, "/User/*");
	    tester.addServlet(DefaultServlet.class, "/");
	    	    
		tester.start();
		request.setMethod("PUT");
		request.setVersion("HTTP/1.0");

		request.setURI("/User/erwah-1234-5678");

		request.setHeader("Content-Length", Integer.toString("very invalid user".length()));
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		request.setContent("very invalid user");
		response.parse(tester.getResponses(request.generate()));

		assertEquals(400, response.getStatus());
		        
	}


}

