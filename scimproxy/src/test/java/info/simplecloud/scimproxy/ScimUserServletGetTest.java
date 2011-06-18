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

	private String id = "";
	
	public void setUp() throws Exception {
		tester = new ServletTester();
		tester.addServlet(ScimUserServlet.class, "/User/*");
	    tester.addServlet(DefaultServlet.class, "/");
	    tester.start();
	    
	    ScimUser scimUser = new ScimUser();
	    scimUser.setUserName("Alice");

		request.setMethod("POST");
		request.setVersion("HTTP/1.0");
		request.setURI("/User");
		request.setHeader("Content-Length", Integer.toString(scimUser.getUser("JSON").length()));
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		request.setContent(scimUser.getUser("JSON"));
		response.parse(tester.getResponses(request.generate()));

		ScimUser tmp = new ScimUser(response.getContent(), "JSON");
		id = tmp.getId();
	}
	

	public void testGetUser() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");

		request.setURI("/User/" + id);
		response.parse(tester.getResponses(request.generate()));

		assertEquals(200, response.getStatus());
		
        ScimUser scimUser = new ScimUser(response.getContent(), "JSON");
        
        assertEquals(id, scimUser.getId());
        assertEquals("Alice", scimUser.getUserName());
	}

	public void testMissingUser() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");

		request.setURI("/User/asdasdasdasd");
		response.parse(tester.getResponses(request.generate()));

		assertEquals(404, response.getStatus());
	}
	
}

