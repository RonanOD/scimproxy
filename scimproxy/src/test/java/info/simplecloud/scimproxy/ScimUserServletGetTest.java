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
	

	public void testGetUser() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setHeader("Authorization", "Basic dXNyOnB3");

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
		request.setHeader("Authorization", "Basic dXNyOnB3");

		request.setURI("/User/asdasdasdasd");
		response.parse(tester.getResponses(request.generate()));

		assertEquals(404, response.getStatus());
	}

	public void testUserInput1() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setHeader("Authorization", "Basic dXNyOnB3");

		request.setURI("/1/User/asdasdasdasd");
		response.parse(tester.getResponses(request.generate()));

		assertEquals(404, response.getStatus());
	}

	public void testUserInput2() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setHeader("Authorization", "Basic dXNyOnB3");

		request.setURI("/1/User/as+da+sd%20as%20da{}0w92827:;pi9u3jwpsd");
		response.parse(tester.getResponses(request.generate()));

		assertEquals(404, response.getStatus());
	}


	public void testUserInput3() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setHeader("Authorization", "Basic dXNyOnB3");

		String longUrl = "/User/as+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsdas+da+sd%20as%20da{}0w92827:;pi9u3jwpsd";
		request.setURI(longUrl);
		response.parse(tester.getResponses(request.generate()));

		assertEquals(404, response.getStatus());
	}


	public void testNoAttribs() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setHeader("Authorization", "Basic dXNyOnB3");

		request.setURI("/User/" + id + "?attributes=");

		response.parse(tester.getResponses(request.generate()));

		assertEquals(200, response.getStatus());
		
        ScimUser scimUser = new ScimUser(response.getContent(), "JSON");
        
        assertEquals(id, scimUser.getId());
        assertEquals("Alice", scimUser.getUserName());
	}	

	public void testOnlyUserName() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setHeader("Authorization", "Basic dXNyOnB3");

		request.setURI("/User/" + id + "?attributes=nickName");

		response.parse(tester.getResponses(request.generate()));

		assertEquals(200, response.getStatus());
		
        ScimUser scimUser = new ScimUser(response.getContent(), "JSON");
        
        assertEquals(id, scimUser.getId());
        assertEquals(null, scimUser.getUserName());
        assertEquals("A", scimUser.getNickName());
	}	
	

	public void testUnknownAttrib() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setHeader("Authorization", "Basic dXNyOnB3");

		request.setURI("/User/" + id + "?attributes=asdasdasdasdasdasd");

		response.parse(tester.getResponses(request.generate()));

		assertEquals(400, response.getStatus());
	}	
	

}

