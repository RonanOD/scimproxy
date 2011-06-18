package info.simplecloud.scimproxy;

import junit.framework.TestCase; 

import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUsersServletTest extends TestCase {

	ServletTester tester = new ServletTester();
	HttpTester request = new HttpTester();
	HttpTester response = new HttpTester();

	public void setUp() throws Exception {
		tester.addServlet(ScimUsersServlet.class, "/Users");
		tester.start();

		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
	}

	public void testUsers() throws Exception {
		request.setURI("/Users");
		response.parse(tester.getResponses(request.generate()));

		assertTrue(response.getMethod() == null);
		assertEquals(200, response.getStatus());

		String users = response.getContent();
		
		int i = users.indexOf("totalResults");
		
	//	assertTrue(i == 5);
	}

}

