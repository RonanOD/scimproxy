package info.simplecloud.scimproxy;

import javax.servlet.http.HttpServlet;

/*

Base clase for OAuth 2.0 authentication. Verifies that the token is present and makes sure that the client gets a token if it's missing.
 */

@SuppressWarnings("serial")
public class Authenticated extends HttpServlet {

	// TODO: How to verify that user is authenticated using OAuth 2.0? 

	String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";
	String CONTENT_TYPE_XML = "text/xml; charset=UTF-8";
	
}
