package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.decoding.JsonDecoder;
import info.simplecloud.core.encoding.JsonEncoder;
import junit.framework.TestCase; 

import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUserServletTest extends TestCase {

	ServletTester tester = new ServletTester();
	HttpTester request = new HttpTester();
	HttpTester response = new HttpTester();

	public void setUp() throws Exception {
		tester.addServlet(ScimUserServlet.class, "/User/*");
		tester.start();
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
	}

	public void testGetUser() throws Exception {
		request.setURI("/User/erwah-1234-5678");
		response.parse(tester.getResponses(request.generate()));

		assertTrue(response.getMethod() == null);
		assertEquals(200, response.getStatus());
		
		String jsonUser = response.getContent();
		
        ScimUser scimUser = new ScimUser();
		// decode the json user into an ScimUser object
        JsonDecoder decoder = new JsonDecoder();
        decoder.decode(jsonUser, scimUser);
        
        assertEquals("erwah-1234-5678", scimUser.getId());
	}

	public void testMissingUser() throws Exception {
		request.setURI("/User/asdasdasdasd");
		response.parse(tester.getResponses(request.generate()));

		assertTrue(response.getMethod() == null);
		assertEquals(404, response.getStatus());
	}
	

	public void testDeleteUser() throws Exception {
		// get resource to see if it's there
		request.setURI("/User/erwah-1234-5678");
		response.parse(tester.getResponses(request.generate()));

		assertTrue(response.getMethod() == null);
		assertEquals(200, response.getStatus());
		
		String jsonUser = response.getContent();
		
        ScimUser scimUser = new ScimUser();
        JsonDecoder decoder = new JsonDecoder();
        decoder.decode(jsonUser, scimUser);
        
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

