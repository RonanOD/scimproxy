package info.simplecloud.scimproxy;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class UserServletTest extends TestCase{
	
	  private ServletTester tester;
	  private HttpTester request;
	  private HttpTester response;
	 
	  @Before
	  public void setUp() throws Exception {
	    this.tester = new ServletTester();
	    this.tester.setContextPath("/");
	    this.tester.addServlet(ScimUserServlet.class, "/");
	    this.tester.start();
	 
	    this.request = new HttpTester();
	    this.response = new HttpTester();
	    this.request.setMethod("GET");
	    this.request.setHeader("yesyes", "tester");
	    this.request.setVersion("HTTP/1.0");
	  }

	  @Test
	  public void testUser() throws Exception {
	    this.request.setURI("/");
	    this.response.parse(tester.getResponses(request.generate()));

	    assertTrue(this.response.getMethod() == null);
//	    assertEquals(200, this.response.getStatus());
	    
	    String content = this.response.getContent();
		assertTrue(content.contains("Ms. Barbara J Jensen III"));
	  }

}
