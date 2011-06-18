package info.simplecloud.scimproxy;

import info.simplecloud.core.Meta;
import info.simplecloud.core.ScimUser;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUserServletPatchTest extends TestCase {

	HttpTester request = new HttpTester();
	HttpTester response = new HttpTester();
	ServletTester tester = null;

	/**
	 * Setup tests. Binding servlet to /User
	 */
	public void setUp() throws Exception {
	}
	
	/**
	 * Test update user with PATCH.
	 * @throws Exception
	 */
	public void testPatchUser() throws Exception {
		tester = new ServletTester();
		tester.addServlet(ScimUserServlet.class, "/User/*");
	    tester.addServlet(DefaultServlet.class, "/");
		tester.start();

		// get resource to see if it's there
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/User/yhgty-ujhyu-iolki");
		response.parse(tester.getResponses(request.generate()));
		assertEquals(200, response.getStatus());
		
        ScimUser scimUser = new ScimUser(response.getContent(), "JSON");
        assertEquals(null, scimUser.getDisplayName());

        // edit user by adding display name
        scimUser.setDisplayName("Samuel Erdtman");

        HttpTester request2 = new HttpTester();
    	HttpTester response2 = new HttpTester();
    	request2.setMethod("PATCH");
		request2.setVersion("HTTP/1.0");
		request2.setURI("/User/yhgty-ujhyu-iolki");
		request2.setContent(scimUser.getUser("JSON"));
		request2.setHeader("Content-Length", Integer.toString(scimUser.getUser("JSON").length()));
		request2.setHeader("Content-Type", "application/x-www-form-urlencoded");
		request2.setHeader("ETag", scimUser.getMeta().getVersion());
		response2.parse(tester.getResponses(request2.generate()));

		assertEquals(200, response2.getStatus());

		// next request should be have displayName
        HttpTester request3 = new HttpTester();
    	HttpTester response3 = new HttpTester();
		request3.setMethod("GET");
		request3.setVersion("HTTP/1.0");
		request3.setURI("/User/yhgty-ujhyu-iolki");
		response3.parse(tester.getResponses(request3.generate()));
        
		String r = response3.getContent();
        assertEquals("Samuel Erdtman", new ScimUser(r, "JSON").getDisplayName());
	}
	
	public void testPatchUserMissingContent() throws Exception {
		tester = new ServletTester();
		tester.addServlet(ScimUserServlet.class, "/User/*");
	    tester.addServlet(DefaultServlet.class, "/");
		tester.start();

    	request.setMethod("PATCH");
		request.setVersion("HTTP/1.0");
		request.setURI("/User/yhgty-ujhyu-iolki");
		response.parse(tester.getResponses(request.generate()));

		assertEquals(400, response.getStatus());
	}

	public void testPatchUserMissingContentZeroLength() throws Exception {
		tester = new ServletTester();
		tester.addServlet(ScimUserServlet.class, "/User/*");
	    tester.addServlet(DefaultServlet.class, "/");
		tester.start();

    	request.setMethod("PATCH");
		request.setVersion("HTTP/1.0");
		request.setURI("/User/yhgty-ujhyu-iolki");
		request.setHeader("Content-Length", "0");
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");

		response.parse(tester.getResponses(request.generate()));

		assertEquals(400, response.getStatus());
	}

	/**
	 * Test update user with PATCH, missing ETag.
	 * @throws Exception
	 */
	public void testPatchUserWrongETag() throws Exception {
		tester = new ServletTester();
		tester.addServlet(ScimUserServlet.class, "/User/*");
	    tester.addServlet(DefaultServlet.class, "/");
		tester.start();

		// get resource to see if it's there
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/User/yhgty-ujhyu-iolki");
		response.parse(tester.getResponses(request.generate()));
		assertEquals(200, response.getStatus());
		
        ScimUser scimUser = new ScimUser(response.getContent(), "JSON");

        // edit user by adding display name
        scimUser.setDisplayName("Samuel Erdtman");

        HttpTester request2 = new HttpTester();
    	HttpTester response2 = new HttpTester();
    	request2.setMethod("PATCH");
		request2.setVersion("HTTP/1.0");
		request2.setURI("/User/yhgty-ujhyu-iolki");
		request2.setContent(scimUser.getUser("JSON"));
		request2.setHeader("Content-Length", Integer.toString(scimUser.getUser("JSON").length()));
		request2.setHeader("Content-Type", "application/x-www-form-urlencoded");

		// missing etag
		response2.parse(tester.getResponses(request2.generate())); 
		assertEquals(400, response2.getStatus()); // HttpServletResponse.SC_PRECONDITION_FAILED

		// empty etag
		request2.setHeader("ETag", ""); 
		response2.parse(tester.getResponses(request2.generate()));
		assertEquals(400, response2.getStatus()); // HttpServletResponse.SC_PRECONDITION_FAILED

		// wrong etag
		request2.setHeader("ETag", "wrongETag"); 
		response2.parse(tester.getResponses(request2.generate()));
		assertEquals(412, response2.getStatus()); // HttpServletResponse.SC_PRECONDITION_FAILED

	}

	
	/**
	 * Test update user with PATCH. Removing emails.
	 * @throws Exception
	 */
	public void testPatchUserRemoveAttribs() throws Exception {
		tester = new ServletTester();
		tester.addServlet(ScimUserServlet.class, "/User/*");
	    tester.addServlet(DefaultServlet.class, "/");
		tester.start();

		// get resource to see if it's there
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/User/yhgty-ujhyu-iolki");
		response.parse(tester.getResponses(request.generate()));

		assertEquals(200, response.getStatus());
		
        ScimUser scimUser = new ScimUser(response.getContent(), "JSON");
        
        // set display name and remove email attributes
        scimUser.setDisplayName("Samuel Erdtman");
        Meta meta = new Meta();
        ArrayList<String> l = new ArrayList<String>();
        l.add("emails");
        l.add("department");
        meta.setAttributes(l);
        scimUser.setMeta(meta);
        
        HttpTester request2 = new HttpTester();
    	HttpTester response2 = new HttpTester();
    	request2.setMethod("PATCH");
		request2.setVersion("HTTP/1.0");
		request2.setURI("/User/yhgty-ujhyu-iolki");
		request2.setContent(scimUser.getUser("JSON"));
		request2.setHeader("Content-Length", Integer.toString(scimUser.getUser("JSON").length()));
		request2.setHeader("Content-Type", "application/x-www-form-urlencoded");
		request2.setHeader("ETag", scimUser.getMeta().getVersion());
		response2.parse(tester.getResponses(request2.generate()));

		assertEquals(200, response2.getStatus());

		// next request should be have displayName but no email addresses
        HttpTester request3 = new HttpTester();
    	HttpTester response3 = new HttpTester();
		request3.setURI("/User/yhgty-ujhyu-iolki");
		response3.parse(tester.getResponses(request2.generate()));
		
        ScimUser scimUser2 = new ScimUser(response3.getContent(), "JSON");
        
        assertEquals("Samuel Erdtman", scimUser2.getDisplayName());
        assertEquals(null, scimUser2.getEmails());
       
	}
	
}

