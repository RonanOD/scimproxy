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


	public void testPutUser() throws Exception {
	    ScimUser scimUser = new ScimUser();
	    scimUser.setId(id);
	    scimUser.setUserName("Bob");
	    
		request.setMethod("PUT");
		request.setVersion("HTTP/1.0");

		request.setURI("/User/" + id);

		request.setHeader("Content-Length", Integer.toString(scimUser.getUser("JSON").length()));
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		request.setContent(scimUser.getUser("JSON"));
		response.parse(tester.getResponses(request.generate()));

		assertEquals(200, response.getStatus());
		        
        ScimUser returnedUser = new ScimUser(response.getContent(), "JSON");
        
        assertEquals(id, returnedUser.getId());
        assertEquals("Bob", returnedUser.getUserName());
	}
	

	public void testPutInvalidUser() throws Exception {
		request.setMethod("PUT");
		request.setVersion("HTTP/1.0");

		request.setURI("/User/" + id);

		request.setHeader("Content-Length", Integer.toString("very invalid user".length()));
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		request.setContent("very invalid user");
		response.parse(tester.getResponses(request.generate()));

		assertEquals(400, response.getStatus());
		        
	}


}

