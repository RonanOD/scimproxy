package info.simplecloud.scimproxy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A abstract REST servlet that extends HTTP with the PATCH method according to
 * RFC 5789. Class also makes sure that user is authenticated and provides a
 * valid OAUTH 2.0 bearer token.
 */

@SuppressWarnings("serial")
public abstract class RestServlet extends HttpServlet {

	// TODO: Verify that user is authenticated using OAuth 2.0!

	/**
	 * Extends HttpServlet with the PATCH method acording to RFC 5789.
	 */
	private static final String METHOD_PATCH = "PATCH";

	/**
	 * Extends HttpServlet with the PATCH method acording to RFC 5789. Override
	 * this method to implement PATCH.
	 */
	protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		} else {
			super.service(req, resp);
		}

	}

}
