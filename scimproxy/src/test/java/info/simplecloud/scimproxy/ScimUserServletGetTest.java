package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;
import junit.framework.TestCase;

import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUserServletGetTest extends TestCase {

	HttpTester request = new HttpTester();
	HttpTester response = new HttpTester();
	ServletTester tester = null;

	/**
	 * Setup tests. Binding servlet to /User
	 */
	public void setUp() throws Exception {
	}

	public void testGetUser() throws Exception {
		tester = new ServletTester();
		tester.addServlet(ScimUserServlet.class, "/User/*");
	    tester.addServlet(DefaultServlet.class, "/");
	    
		tester.start();
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");

		request.setURI("/User/erwah-1234-5678");
		response.parse(tester.getResponses(request.generate()));

		assertEquals(200, response.getStatus());
		
        ScimUser scimUser = new ScimUser(response.getContent(), "JSON");
        
        assertEquals("erwah-1234-5678", scimUser.getId());
        assertEquals("Erik Wahlström", scimUser.getDisplayName());
	}

	public void testMissingUser() throws Exception {
		tester = new ServletTester();
		tester.addServlet(ScimUserServlet.class, "/User/*");
	    tester.addServlet(DefaultServlet.class, "/");
	    
		tester.start();
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");

		request.setURI("/User/asdasdasdasd");
		response.parse(tester.getResponses(request.generate()));

		assertEquals(404, response.getStatus());
	}
	
}

