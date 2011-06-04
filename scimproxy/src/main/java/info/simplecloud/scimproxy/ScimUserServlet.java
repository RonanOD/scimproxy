package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.InvalidUser;
import info.simplecloud.core.execeptions.UnknownEncoding;
import info.simplecloud.scimproxy.storage.dummy.DummyStorage;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/*

 GET:
 curl -i -H "Accept: application/json" http://localhost:8080/User/3f62ce30-dcd6-4dd7-abfe-2352a76f9978

 POST:
 curl -i -H "Accept: application/json" -X POST -d "{%22schemas%22: [%22urn:scim:schemas:core:1.0%22],%22userName%22: %22bjensen@example.com%22,%22name%22: {%22familyName%22: %22Jensen%22,%22givenName%22: %22Barbara%22},%22displayName%22: %22Babs Jensen%22, %22emails%22: [{%22value%22: %22bjensen@example.com%22,%22type%22: %22work%22,%22primary%22: true}]}" http://localhost:8080/User

 PUT:
 curl -i -H "Accept: application/json" -X PUT -d "phone=1-800-999-9999" http://localhost:8080/User

 DELETE:
 curl -i -H "Accept: application/json" -X DELETE http://localhost:8080/User

 PUT OVER POST:
 curl -i -H "Accept: application/json" -H "X-HTTP-Method-Override: PUT" -X POST -d "phone=1-800-999-9999" http://localhost:8080/User

 DELETE OVER POST:
 curl -i -H "Accept: application/json" -H "X-HTTP-Method-Override: DELETE" -X POST  http://localhost:8080/User

 */

@SuppressWarnings("serial")
public class ScimUserServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(ScimUserServlet.class.getName());

	String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";
	String CONTENT_TYPE_XML = "text/xml; charset=UTF-8";

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String user = req.getRequestURI();

		// TODO: get user id from request, now hardcoded
		DummyStorage storage = DummyStorage.getInstance();
		ScimUser scimUser = storage.getUserForId("3f62ce30-dcd6-4dd7-abfe-2352a76f9978");

		if (scimUser != null) {
			resp.setContentType(CONTENT_TYPE_XML);

			// TODO: SPEC: REST: Should the Location header be included in more then POST? Like in this response?
			// TODO: SPEC: REST: Should ETag be added in more places in spec?

			resp.setStatus(HttpServletResponse.SC_OK); // 200
			resp.getWriter().print(scimUser.toString());
		} else {
			// TODO: SPEC: REST: What to respond when token not there? (4.2.1. Retrieving a known Resource)
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// TODO: How to create clients? Who should add it to the database?

		String query = "";

		// TODO: SPEC: REST: Shouln't the POST message have a specific name for
		// the value? Right now the name is the value.
		Enumeration<?> paramNames = req.getParameterNames();
		while (paramNames.hasMoreElements()) {
			query = (String) paramNames.nextElement();
		}

		
		ScimUser u = null;
		try {
			u = new ScimUser(query, "json");
			u.setUserName("Alice");
		} catch (UnknownEncoding e) {
			e.printStackTrace();
		} catch (InvalidUser e) {
			e.printStackTrace();
		}
		
		DummyStorage storage = DummyStorage.getInstance();
		storage.addUser(u);
/*		
		JSONObject scimUserJson;
		try {
			scimUserJson = new JSONObject(query);
			String userName = (String) scimUserJson.get("userName");
			log.info("User created with userName " + userName);
		} catch (JSONException e) {
			log.severe(e.toString());
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
		}
*/
		String response = "" + "{\n" + "\t\"id\" : \"3f62ce30-dcd6-4dd7-abfe-2352a76f9978\",\n" + "\t\"errors\" : [ ],  //TODO - not in core schema\n"
				+ "\t\"success\" : true //TODO - not in core schema\n" + "}\n";

		resp.setContentType(CONTENT_TYPE_JSON);

		// generate the Location url
		String scheme = req.getScheme(); // http
		String serverName = req.getServerName(); // acme.com
		int serverPort = req.getServerPort(); // 80
		String serverPortStr = "";
		if (("http".equals(scheme) && serverPort != 80) || ("https".equals(scheme) && serverPort != 443)) {
			serverPortStr = Integer.toString(serverPort);
		}
		String contextPath = req.getContextPath(); // /mywebapp
		String location = scheme + "://" + serverName + ":" + serverPortStr + contextPath + "/User/3f62ce30-dcd6-4dd7-abfe-2352a76f9978";

		resp.setHeader("Location", location);

		resp.setStatus(HttpServletResponse.SC_CREATED); // 201
		resp.getWriter().print(response);

	}

	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}

	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// TODO: SPEC: REST: Should X-HTTP-Method-Override: DELETE be sent over
		// GET instead of POST?
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}

	public void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// TODO: SPEC: REST: Should we really use the PATCH method? It's not
		// part of HTTP 1.1.
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}

}
