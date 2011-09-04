package info.simplecloud.scimproxy;

import info.simplecloud.core.User;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Generates different types of HTTP response codes and includes error messages
 * according to the SCIM REST specification.
 */
public class HttpGenerator {

	/**
	 * Generates a HTTP 200, OK message.
	 * 
	 * @param resp
	 *            The http response object to set status on.
	 */
	public static void ok(HttpServletResponse resp) {
		ok(resp, "");
	}

	/**
	 * Generates a HTTP 200, OK message with a content.
	 * 
	 * @param resp
	 *            The http response object to set status on.
	 * @param m
	 *            Content do be returned to the client.
	 */
	public static void ok(HttpServletResponse resp, String m) {
		resp.setStatus(HttpServletResponse.SC_OK); // 200
		try {
			resp.getWriter().print(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates a HTTP 412, precondition failed message.
	 * 
	 * @param resp
	 *            The http response object to set status on.
	 * @param scimUser
	 *            User that was changed since loaded from client.
	 */
	public static void preconditionFailed(HttpServletResponse resp, String userId) {
		resp.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED); // 412
		try {
			resp.getWriter().print("Failed to update as resource " + userId + " changed on the server since you last retrieved it.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates a HTTP 404, file not found.
	 * 
	 * @param resp
	 *            The http response object to set status on.
	 */
	public static void notFound(HttpServletResponse resp) {
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
		try {
			resp.getWriter().print("Specified resource does not exist.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates a HTTP 500, internal server error message. Includes a detailed
	 * message.
	 * 
	 * @param resp
	 *            The http response object to set status on.
	 */
	public static void serverError(HttpServletResponse resp) {
		serverError(resp, "");
	}

	/**
	 * Generates a HTTP 500, internal server error message. Includes a detailed
	 * message.
	 * 
	 * @param resp
	 *            The http response object to set status on.
	 * @param m
	 *            Detailed message of what's wrong.
	 */
	public static void serverError(HttpServletResponse resp, String m) {
		resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
		try {
			resp.getWriter().print("An internal error. " + m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates a HTTP 400, bad request message. Includes a detailed message.
	 * 
	 * @param resp
	 *            The http response object to set status on.
	 * @param m
	 *            Detailed message of what's wrong with the request.
	 */
	public static void badRequest(HttpServletResponse resp, String m) {
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
		try {
			resp.getWriter().print("Request is unparseable, syntactically incorrect, or violates schema. " + m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates a HTTP 400, bad request message.
	 * 
	 * @param resp
	 *            The http response object to set status on.
	 */
	public static void badRequest(HttpServletResponse resp) {
		badRequest(resp, "");
	}

	/**
	 * Generates a HTTP 201, created, response. Includes a serialized version of
	 * the created resource.
	 * 
	 * @param resp
	 *            The http response object to set status on.
	 * @param m
	 *            A serialized version of the created resource.
	 */
	public static void created(HttpServletResponse resp, String m) {
		resp.setStatus(HttpServletResponse.SC_CREATED); // 201
		try {
			resp.getWriter().print(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the wanted encoding. JSON or XML.
	 * 
	 * @param req
	 *            The request to parse for wanted encoding.
	 * @return XML if xml otherwise JSON.
	 */
	public static String getEncoding(HttpServletRequest req) {
		String encoding = User.ENCODING_JSON; // default to JSON
		
		String uri = req.getRequestURI();
		if(uri == null) {
			uri = "";
		}
		uri = uri.toLowerCase();
		String acceptHeader = req.getHeader("Accept");
		if(acceptHeader == null) {
			acceptHeader = "";
		}
		acceptHeader = acceptHeader.toLowerCase();
		
		if(acceptHeader.indexOf("application/json") != -1  || uri.endsWith(".json")) {
			encoding = User.ENCODING_JSON;
		}

		if(acceptHeader.indexOf("application/xml") != -1 || uri.endsWith(".xml")) {
			encoding = User.ENCODING_XML;
		}

		return encoding;
	}

	/**
	 * Returns the wanted content type string to be set as header.
	 * 
	 * @param req
	 *            The request to parse for wanted content type.
	 * @return The content type to be set as http header.
	 */
	public static String getContentType(HttpServletRequest req) {
		// TODO: get wanted content type from request
		return "application/json; charset=UTF-8";
	}

	/**
	 * Generating a location for a user on the current web server.
	 * 
	 * @param user
	 *            The user we want the location for.
	 * @param req
	 *            A servlet request for retrieving paths and server names.
	 * @return A URI to a scim user to be used as a Location HTTP header.
	 */
	public static String getLocation(User user, HttpServletRequest req) {
		return getInternalLocation("/v1/User/" + user.getId(), req);
	}

	public static String getBatchUserLocation(String batch, HttpServletRequest req) {
		return getInternalLocation("/v1/Batch/User/" + batch, req);
	}

	public static String getBatchGroupLocation(String batch, HttpServletRequest req) {
		return getInternalLocation("/v1/Batch/Group/" + batch, req);
	}

	private static String getInternalLocation(String path, HttpServletRequest req) {
		// generate the Location url
		String scheme = req.getScheme(); // http
		String serverName = req.getServerName(); // acme.com
		int serverPort = req.getServerPort(); // 80
		String serverPortStr = "";
		if (("http".equals(scheme) && serverPort != 80) || ("https".equals(scheme) && serverPort != 443)) {
			serverPortStr = ":" + Integer.toString(serverPort);
		}
		String contextPath = req.getContextPath();
		String location = scheme + "://" + serverName + serverPortStr + contextPath + path;

		return location;
	}	
}
