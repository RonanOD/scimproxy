package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;
import junit.framework.TestCase;

import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUserServletDeleteTest extends TestCase {

	HttpTester request = new HttpTester();
	HttpTester response = new HttpTester();
	ServletTester tester = null;

	/**
	 * Setup tests. Binding servlet to /User
	 */
	public void setUp() throws Exception {
	}


	public void testDeleteUser() throws Exception {
		tester = new ServletTester();
		tester.addServlet(ScimUserServlet.class, "/User/*");
	    tester.addServlet(DefaultServlet.class, "/");
	    
		tester.start();
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");

		// get resource to see if it's there
		request.setURI("/User/erwah-1234-5678");
		response.parse(tester.getResponses(request.generate()));

		assertEquals(200, response.getStatus());
		
        ScimUser scimUser = new ScimUser(response.getContent(), "JSON");
        
        assertEquals("erwah-1234-5678", scimUser.getId());

        // delete resource

    	request.setMethod("DELETE");
    	request.setVersion("HTTP/1.0");

		response.parse(tester.getResponses(request.generate()));

		assertTrue(response.getMethod() == null);
		assertEquals(200, response.getStatus());

		// next request should be 404
		request.setURI("/User/erwah-1234-5678");
		response.parse(tester.getResponses(request.generate()));

		assertTrue(response.getMethod() == null);
		assertEquals(404, response.getStatus());
	}
	
}

