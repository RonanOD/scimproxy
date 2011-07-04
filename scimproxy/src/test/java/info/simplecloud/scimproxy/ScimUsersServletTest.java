package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimUsersServletTest extends TestCase {

	ServletTester tester = new ServletTester();
	HttpTester request = new HttpTester();
	HttpTester response = new HttpTester();

	String aliceId = "";
	String bobId = "";
    ScimUser alice = new ScimUser();
    ScimUser bob = new ScimUser();

	
	public void setUp() throws Exception {
		tester = new ServletTester();
		tester.addServlet(ScimUserServlet.class, "/User/*");
		tester.addServlet(ScimUsersServlet.class, "/Users");
	    tester.addServlet(DefaultServlet.class, "/");
		tester.start();

	    alice.setUserName("Alice");
	    alice.setNickName("A");

		HttpTester aliceRequest = new HttpTester();
		HttpTester aliceResponse = new HttpTester();
		aliceRequest.setMethod("POST");
		aliceRequest.setVersion("HTTP/1.0");
		aliceRequest.setURI("/User");
		aliceRequest.setHeader("Content-Length", Integer.toString(alice.getUser("JSON").length()));
		aliceRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		aliceRequest.setContent(alice.getUser("JSON"));
		aliceResponse.parse(tester.getResponses(aliceRequest.generate()));

		ScimUser addedAlice = new ScimUser(aliceResponse.getContent(), "JSON");
		aliceId = addedAlice.getId();
		
	    bob.setUserName("Bob");
	    bob.setNickName("B");

		HttpTester bobRequest = new HttpTester();
		HttpTester bobResponse = new HttpTester();
		bobRequest.setMethod("POST");
		bobRequest.setVersion("HTTP/1.0");
		bobRequest.setURI("/User");
		bobRequest.setHeader("Content-Length", Integer.toString(bob.getUser("JSON").length()));
		bobRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		bobRequest.setContent(bob.getUser("JSON"));
		bobResponse.parse(tester.getResponses(bobRequest.generate()));

		ScimUser addedBob = new ScimUser(bobResponse.getContent(), "JSON");
		bobId = addedBob.getId();
		
	}

	public void testGetAllAndFindAliceAndBob() throws Exception {

		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/Users");
		response.parse(tester.getResponses(request.generate()));

		String users = response.getContent();
		
		ArrayList<ScimUser> userList = ScimUser.getScimUsers(users, "JSON");

		boolean aliceFound = false;
		boolean bobFound = false;
		
		for (ScimUser scimUser : userList) {
			if(bobId.equals(scimUser.getId())) {
				bobFound = true;
			}
			if(aliceId.equals(scimUser.getId())) {
				aliceFound = true;
			}
		}
		
		assertEquals(true, bobFound);
		assertEquals(true, aliceFound);
	}

	
	public void testSortUserNameAsc() throws Exception {

		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/Users?sortBy=userName&sortOrder=ascending");
		response.parse(tester.getResponses(request.generate()));

		String users = response.getContent();
		
		ArrayList<ScimUser> userList = ScimUser.getScimUsers(users, "JSON");

		boolean aliceFoundFirst = false;
		
		for (ScimUser scimUser : userList) {
			if(bobId.equals(scimUser.getId())) {
				assertEquals(true, aliceFoundFirst);
			}
			if(aliceId.equals(scimUser.getId())) {
				aliceFoundFirst = true;
			}
		}
	}
	

	public void testSortUserNameDesc() throws Exception {

		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/Users?sortBy=userName&sortOrder=descending");
		response.parse(tester.getResponses(request.generate()));

		String users = response.getContent();
		
		ArrayList<ScimUser> userList = ScimUser.getScimUsers(users, "JSON");

		boolean bobFoundFirst = false;
		
		for (ScimUser scimUser : userList) {
			if(bobId.equals(scimUser.getId())) {
				bobFoundFirst = true;
			}
			if(aliceId.equals(scimUser.getId())) {
				assertEquals(true, bobFoundFirst);
			}
		}
	}
	

	public void testSortNickDesc() throws Exception {

		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/Users?sortBy=userName&sortOrder=descending&attributes=nickName");
		response.parse(tester.getResponses(request.generate()));

		String users = response.getContent();
		// TODO: SPEC: REST: Should users that's missing attribute nickName be returned?
		ArrayList<ScimUser> userList = ScimUser.getScimUsers(users, "JSON");

		boolean bobFoundFirst = false;
		
		for (ScimUser scimUser : userList) {
			if(bobId.equals(scimUser.getId())) {
				bobFoundFirst = true;
			}
			if(aliceId.equals(scimUser.getId())) {
				assertEquals(true, bobFoundFirst);
			}
		}
	}
	
	public void testSortNoAttribs() throws Exception {

		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/Users?sortBy=userName&sortOrder=descending&attributes=");
		response.parse(tester.getResponses(request.generate()));

		String users = response.getContent();
		// TODO: SPEC: REST: Should users that's missing attribute nickName be returned?
		ArrayList<ScimUser> userList = ScimUser.getScimUsers(users, "JSON");

		boolean bobFoundFirst = false;
		
		for (ScimUser scimUser : userList) {
			if(bobId.equals(scimUser.getId())) {
				bobFoundFirst = true;
			}
			if(aliceId.equals(scimUser.getId())) {
				assertEquals(true, bobFoundFirst);
			}
		}
	}


	public void testFilterByEaualsNickName() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/Users?filterBy=nickName&filterValue=B&filterOp=equals");
		response.parse(tester.getResponses(request.generate()));

		String users = response.getContent();
		// TODO: SPEC: REST: Should users that's missing attribute nickName be returned?
		ArrayList<ScimUser> userList = ScimUser.getScimUsers(users, "JSON");

		boolean bobFound = false;
		boolean aliceFound = false;

		for (ScimUser scimUser : userList) {
			if(bobId.equals(scimUser.getId())) {
				bobFound = true;
			}
			if(aliceId.equals(scimUser.getId())) {
				aliceFound = true;
			}

		}
		
		assertEquals(true, bobFound);
		assertEquals(false, aliceFound);
	}

	public void testFilterByEaualsNoMatch() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/Users?filterBy=nickName&filterValue=asdasdasd&filterOp=equals");
		response.parse(tester.getResponses(request.generate()));

		String users = response.getContent();
		// TODO: SPEC: REST: Should users that's missing attribute nickName be returned?
		ArrayList<ScimUser> userList = ScimUser.getScimUsers(users, "JSON");

		assertEquals(0, userList.size());
	}

	public void testFilterByEaualsUserNameOnly() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/Users?filterBy=userName&filterValue=Bob&filterOp=equals&attributes=userName");
		response.parse(tester.getResponses(request.generate()));

		String users = response.getContent();
		// TODO: SPEC: REST: Should users that's missing attribute nickName be returned?
		ArrayList<ScimUser> userList = ScimUser.getScimUsers(users, "JSON");

		boolean onlyBobs = true;
		boolean noNicks = true;
		
		for (ScimUser scimUser : userList) {
			if(!"Bob".equals(scimUser.getUserName())) {
				onlyBobs = false;
			}
			if("B".equals(scimUser.getNickName())) {
				noNicks = false;
			}
		}

		assertEquals(true, onlyBobs);
		assertEquals(true, noNicks);
	}
	
	public void testFilterByNoneAttribute() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/Users?filterBy=sjshjsdfhjkshdfjsdf&filterValue=Bob&filterOp=equals");
		response.parse(tester.getResponses(request.generate()));

		String users = response.getContent();
		// TODO: SPEC: REST: Should users that's missing attribute nickName be returned?
		ArrayList<ScimUser> userList = ScimUser.getScimUsers(users, "JSON");

		assertEquals(0, userList.size());
	}	

	public void testFilterByEqualsIgnoreCase() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/Users?filterBy=userName&filterValue=bob&filterOp=equalsIgnoreCase");
		response.parse(tester.getResponses(request.generate()));

		String users = response.getContent();
		// TODO: SPEC: REST: Should users that's missing attribute nickName be returned?
		ArrayList<ScimUser> userList = ScimUser.getScimUsers(users, "JSON");

		boolean onlyBobs = true;
		
		for (ScimUser scimUser : userList) {
			if(!"Bob".equals(scimUser.getUserName())) {
				onlyBobs = false;
			}
		}

		assertEquals(true, onlyBobs);		
		assertEquals(true, (userList.size() > 0));	
	}
	

	public void testFilterByContains() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/Users?filterBy=userName&filterValue=ob&filterOp=contains");
		response.parse(tester.getResponses(request.generate()));

		String users = response.getContent();
		// TODO: SPEC: REST: Should users that's missing attribute nickName be returned?
		ArrayList<ScimUser> userList = ScimUser.getScimUsers(users, "JSON");

		boolean onlyBobs = true;
		
		for (ScimUser scimUser : userList) {
			if(!"Bob".equals(scimUser.getUserName())) {
				onlyBobs = false;
			}
		}

		assertEquals(true, onlyBobs);		
		assertEquals(true, (userList.size() > 0));	
	}	
	
	public void testFilterByStartsWith() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/Users?filterBy=userName&filterValue=Bo&filterOp=startsWith");
		response.parse(tester.getResponses(request.generate()));

		String users = response.getContent();
		// TODO: SPEC: REST: Should users that's missing attribute nickName be returned?
		ArrayList<ScimUser> userList = ScimUser.getScimUsers(users, "JSON");

		boolean onlyBobs = true;
		
		for (ScimUser scimUser : userList) {
			if(!"Bob".equals(scimUser.getUserName())) {
				onlyBobs = false;
			}
		}

		assertEquals(true, onlyBobs);		
		assertEquals(true, (userList.size() > 0));	
	}	
	

	public void testFilterByPresent() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/Users?filterBy=nickName&filterOp=present");
		response.parse(tester.getResponses(request.generate()));

		String users = response.getContent();
		// TODO: SPEC: REST: Should users that's missing attribute nickName be returned?
		ArrayList<ScimUser> userList = ScimUser.getScimUsers(users, "JSON");

		boolean bobFound = false;
		boolean aliceFound = false;

		for (ScimUser scimUser : userList) {
			if(bobId.equals(scimUser.getId())) {
				bobFound = true;
			}
			if(aliceId.equals(scimUser.getId())) {
				aliceFound = true;
			}

		}
		
		assertEquals(true, bobFound);
		assertEquals(true, aliceFound);
	}	

	public void testPagingIndexOutOfBound() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/Users?startIndex=10000&count=20000");
		response.parse(tester.getResponses(request.generate()));

		String users = response.getContent();
		// TODO: SPEC: REST: Should users that's missing attribute nickName be returned?
		ArrayList<ScimUser> userList = ScimUser.getScimUsers(users, "JSON");

		assertEquals(true, (userList.size() == 0));
	}	

	public void testPaging10to20() throws Exception {
		request.setMethod("GET");
		request.setVersion("HTTP/1.0");
		request.setURI("/Users?startIndex=2&count=10");
		response.parse(tester.getResponses(request.generate()));

		String users = response.getContent();
		// TODO: SPEC: REST: Should users that's missing attribute nickName be returned?
		ArrayList<ScimUser> userList = ScimUser.getScimUsers(users, "JSON");

		assertEquals(true, (userList.size() == 10));
	}	

}

