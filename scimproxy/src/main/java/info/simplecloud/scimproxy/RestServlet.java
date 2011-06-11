package info.simplecloud.scimproxy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*

Base clase for OAuth 2.0 authentication. Verifies that the token is present and makes sure that the client gets a token if it's missing.
 */

@SuppressWarnings("serial")
public class RestServlet extends HttpServlet {

	// TODO: How to verify that user is authenticated using OAuth 2.0? 

    private static final String METHOD_PATCH= "PATCH";

	String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";
	String CONTENT_TYPE_XML = "text/xml; charset=UTF-8";

	protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String protocol = req.getProtocol();
		String msg = "Method PATCH not supported.";
		if (protocol.endsWith("1.1")) {
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
		} else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
		}
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();

		if (method.equals(METHOD_PATCH)) {
            doPatch(req, resp);
		}
		else {
			super.service(req, resp);
		}

	}

}
