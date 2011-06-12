package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.decoding.JsonDecoder;
import info.simplecloud.core.encoding.JsonEncoder;
import info.simplecloud.core.execeptions.EncodingFailed;
import info.simplecloud.core.execeptions.InvalidUser;
import info.simplecloud.scimproxy.storage.dummy.DummyStorage;
import info.simplecloud.scimproxy.storage.dummy.UserNotFoundException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

/*

GET:
curl -i -H "Accept: application/json" http://localhost:8080/User/erwah-1234-5678

POST:
curl -i -H "Accept: application/json" -X POST -d "{%22schemas%22: [%22urn:scim:schemas:core:1.0%22],%22userName%22: %22bjensen@example.com%22,%22name%22: {%22familyName%22: %22Jensen%22,%22givenName%22: %22Barbara%22},%22displayName%22: %22Babs Jensen%22, %22emails%22: [{%22value%22: %22bjensen@example.com%22,%22type%22: %22work%22,%22primary%22: true}]}" http://localhost:8080/User
BASE64 encoded POST (NOT SUPPORTED YET)
curl -i -H "Accept: application/json" -X POST -d "ewoJICAgICJzY2hlbWFzIjogWyJ1cm46c2NpbTpzY2hlbWFzOmNvcmU6MS4wIl0sCgkgICAgImlkIjogIjAwNUQwMDAwMDAxQXoxdSIsCgkgICAgImV4dGVybmFsSWQiOiAiNzAxOTg0IiwKCSAgICAidXNlck5hbWUiOiAiYmplbnNlbkBleGFtcGxlLmNvbSIsCgkgICAgIm5hbWUiOiB7CgkgICAgCSJmb3JtYXR0ZWQiOiAiTXMuIEJhcmJhcmEgSiBKZW5zZW4gSUlJIiwKCSAgICAgICAgImZhbWlseU5hbWUiOiAiSmVuc2VuIiwKCSAgICAgICAgImdpdmVuTmFtZSI6ICJCYXJiYXJhIiwKCSAgICAgICAgIm1pZGRsZU5hbWUiOiAiSmFuZSIsCgkgICAgICAgICJob25vcmlmaWNQcmVmaXgiOiAiTXMuIiwKCSAgICAgICAgImhvbm9yaWZpY1N1ZmZpeCI6ICJJSUkiCgkgICAgfSwKCSAgICAiZGlzcGxheU5hbWUiOiAiQmFicyBKZW5zZW4iLAoJICAgICJuaWNrTmFtZSI6ICJCYWJzIiwKCSAgICAicHJvZmlsZVVybCI6ICJodHRwczovL2xvZ2luLmV4YW1wbGUuY29tL2JqZW5zZW4iLAoJICAgICJlbWFpbHMiOiBbCgkgICAgICAgIHsKCSAgICAgICAgICAidmFsdWUiOiAiYmplbnNlbkBleGFtcGxlLmNvbSIsCgkgICAgICAgICAgInR5cGUiOiAid29yayIsCgkgICAgICAgICAgInByaW1hcnkiOiB0cnVlCgkgICAgICAgIH0sCgkgICAgICAgIHsKCSAgICAgICAgICAidmFsdWUiOiAiYmFic0BqZW5zZW4ub3JnIiwKCSAgICAgICAgICAidHlwZSI6ICJob21lIgoJICAgICAgICB9CgkgICAgXSwKCSAgICAiYWRkcmVzc2VzIjogWwoJICAgICAgICB7CgkgICAgICAgICAgInR5cGUiOiAid29yayIsCgkgICAgICAgICAgInN0cmVldEFkZHJlc3MiOiAiMTAwIFVuaXZlcnNhbCBDaXR5IFBsYXphIiwKCSAgICAgICAgICAibG9jYWxpdHkiOiAiSG9sbHl3b29kIiwKCSAgICAgICAgICAicmVnaW9uIjogIkNBIiwKCSAgICAgICAgICAicG9zdGFsQ29kZSI6ICI5MTYwOCIsCgkgICAgICAgICAgImNvdW50cnkiOiAiVVNBIiwKCSAgICAgICAgICAiZm9ybWF0dGVkIjogIjEwMCBVbml2ZXJzYWwgQ2l0eSBQbGF6YVxuSG9sbHl3b29kLCBDQSA5MTYwOCBVU0EiLAoJICAgICAgICAgICJwcmltYXJ5IjogdHJ1ZQoJICAgICAgICB9LAoJICAgICAgICB7CgkgICAgICAgICAgInR5cGUiOiAiaG9tZSIsCgkgICAgICAgICAgInN0cmVldEFkZHJlc3MiOiAiNDU2IEhvbGx5d29vZCBCbHZkIiwKCSAgICAgICAgICAibG9jYWxpdHkiOiAiSG9sbHl3b29kIiwKCSAgICAgICAgICAicmVnaW9uIjogIkNBIiwKCSAgICAgICAgICAicG9zdGFsQ29kZSI6ICI5MTYwOCIsCgkgICAgICAgICAgImNvdW50cnkiOiAiVVNBIiwKCSAgICAgICAgICAiZm9ybWF0dGVkIjogIjQ1NiBIb2xseXdvb2QgQmx2ZFxuSG9sbHl3b29kLCBDQSA5MTYwOCBVU0EiCgkgICAgICAgIH0KCSAgICBdLAoJICAgICJwaG9uZU51bWJlcnMiOiBbCgkgICAgICAgIHsKCSAgICAgICAgICAidmFsdWUiOiAiODAwLTg2NC04Mzc3IiwKCSAgICAgICAgICAidHlwZSI6ICJ3b3JrIgoJICAgICAgICB9LAoJICAgICAgICB7CgkgICAgICAgICAgInZhbHVlIjogIjgxOC0xMjMtNDU2NyIsCgkgICAgICAgICAgInR5cGUiOiAibW9iaWxlIgoJICAgICAgICB9CgkgICAgXSwKCSAgICAiaW1zIjogWwoJICAgICAgICB7CgkgICAgICAgICAgInZhbHVlIjogInNvbWVhaW1oYW5kbGUiLAoJICAgICAgICAgICJ0eXBlIjogImFpbSIKCSAgICAgICAgfQoJICAgIF0sCgkgICAgInBob3RvcyI6IFsKCSAgICAgICAgewoJICAgICAgICAgICJ2YWx1ZSI6ICJodHRwczovL3Bob3Rvcy5leGFtcGxlLmNvbS9wcm9maWxlcGhvdG8vNzI5MzAwMDAwMDBDY25lL0YiLAoJICAgICAgICAgICJ0eXBlIjogInBob3RvIgoJICAgICAgICB9LAoJICAgICAgICB7CgkgICAgICAgICAgInZhbHVlIjogImh0dHBzOi8vcGhvdG9zLmV4YW1wbGUuY29tL3Byb2ZpbGVwaG90by83MjkzMDAwMDAwMENjbmUvVCIsCgkgICAgICAgICAgInR5cGUiOiAidGh1bWJuYWlsIgoJICAgICAgICB9CgkgICAgXSwKCSAgICAiZW1wbG95ZWVOdW1iZXIiOiAiNzAxOTg0IiwKCSAgICAidXNlclR5cGUiOiAiRW1wbG95ZWUiLAoJICAgICJ0aXRsZSI6ICJUb3VyIEd1aWRlIiwKCSAgICAibWFuYWdlciI6ICJNYW5keSBQZXBwZXJpZGdlIiwKCSAgICAicHJlZmVycmVkTGFuZ3VhZ2UiOiAiZW5fVVMiLAoJICAgICJsb2NhbGUiOiAiZW5fVVMiLAoJICAgICJ1dGNPZmZzZXQiOiAiLTA4OjAwIiwKCSAgICAiY29zdENlbnRlciI6ICI0MTMwIiwKCSAgICAib3JnYW5pemF0aW9uIjogIlVuaXZlcnNhbCBTdHVkaW9zIiwKCSAgICAiZGl2aXNpb24iOiAiVGhlbWUgUGFyayIsCgkgICAgImRlcGFydG1lbnQiOiAiVG91ciBPcGVyYXRpb25zIiwKCSAgICAiZ3JvdXBzIjogWwoJICAgICAgICB7CgkgICAgICAgICAgInZhbHVlIjogIlRvdXIgR3VpZGVzIiwKCSAgICAgICAgICAicHJpbWFyeSI6IHRydWUKCSAgICAgICAgfSwKCSAgICAgICAgewoJICAgICAgICAgICJ2YWx1ZSI6ICJFbXBsb3llZXMiLAoJICAgICAgICB9LAoJICAgICAgICB7CgkgICAgICAgICAgInZhbHVlIjogIlVTIEVtcGxveWVlcyIsCgkgICAgICAgIH0KCSAgICBdLAoJICAgICJtZXRhIjogewoJICAgICAgICAiY3JlYXRlZCI6ICIyMDEwLTAxLTIzVDA0OjU2OjIyWiIsCgkgICAgICAgICJsYXN0TW9kaWZpZWQiOiAiMjAxMS0wNS0xM1QwNDo0MjozNFoiCgkgICAgfQoJfQ==" http://localhost:8080/User

PUT:
curl -i -H "Accept: application/json" -X PUT -d "{%22schemas%22: [%22urn:scim:schemas:core:1.0%22],%22userName%22: %22bjensen@example.com%22,%22name%22: {%22familyName%22: %22Jensen%22,%22givenName%22: %22Barbara%22},%22displayName%22: %22Babs Jensen%22, %22emails%22: [{%22value%22: %22bjensen@example.com%22,%22type%22: %22work%22,%22primary%22: true}]}" http://localhost:8080/User/erwah-1234-5678

DELETE:
curl -i -H "Accept: application/json" -X DELETE http://localhost:8080/User/erwah-1234-5678

PUT OVER POST:
curl -i -H "Accept: application/json" -H "X-HTTP-Method-Override: PUT" -X POST -d "phone=1-800-999-9999" http://localhost:8080/User

DELETE OVER POST:
curl -i -H "Accept: application/json" -H "X-HTTP-Method-Override: DELETE" -X POST  http://localhost:8080/User/erwah-1234-5678

PATCH
curl -i -H "Accept: application/json" -H "X-HTTP-Method-Override: PATCH" -X POST -d "{%22schemas%22: [%22urn:scim:schemas:core:1.0%22],%22userName%22: %22bjensen@example.com%22,%22name%22: {%22familyName%22: %22Jensen%22,%22givenName%22: %22Barbara%22},%22displayName%22: %22Babs Jensen%22, %22emails%22: [{%22value%22: %22bjensen@example.com%22,%22type%22: %22work%22,%22primary%22: true}]}" http://localhost:8080/User/erwah-1234-5678

PATCH - Remove email and readd

curl -i -H "Accept: application/json" -H "X-HTTP-Method-Override: PATCH" -X POST -d "%7B%22schemas%22%3A%20%5B%22urn%3Ascim%3Aschemas%3Acore%3A1.0%22%5D%2C%20%22emails%22%3A%20%5B%7B%22value%22%3A%20%22babs%40exmaple.org%22%2C%22type%22%3A%20%22home%22%7D%2C%7B%22value%22%3A%20%22babswork%40exmaple.org%22%2C%22type%22%3A%20%22work%22%7D%5D%2C%22meta%22%3A%20%7B%22attributes%22%3A%20%22emails%22%7D%7D%0A" http://localhost:8080/User/erwah-1234-5678

PATCH - Remove email and displayname


curl -i -H "Accept: application/json" -H "X-HTTP-Method-Override: PATCH" -X POST -d "%7B%22schemas%22%3A%20%5B%22urn%3Ascim%3Aschemas%3Acore%3A1.0%22%5D%2C%20%22emails%22%3A%20%5B%7B%22value%22%3A%20%22babs%40exmaple.org%22%2C%22type%22%3A%20%22home%22%7D%2C%7B%22value%22%3A%20%22babswork%40exmaple.org%22%2C%22type%22%3A%20%22work%22%7D%5D%2C%22meta%22%3A%20%7B%22attributes%22%3A%20%5B%22emails%22%2C%20%22displayName%22%5D%7D%7D" http://localhost:8080/User/erwah-1234-5678


 */

/**
 * To retrieve a known Resource, clients send GET requests to the Resource end point; e.g., /User/{id}.
 * This servlet is the /User end point.
 */

public class ScimUserServlet extends RestServlet {

	/**
	 * Serialize id.
	 */
	private static final long serialVersionUID = -5875059636322733570L;
	
	/**
	 * Gets an user id from a request. /User/myuserid will return myuserid.
	 * @param query A URI, for example /User/myuserid.
	 * @return A scim user id.
	 */
	private String getIdFromUri(String query) {
		// TODO: validate input
		String id = query.substring(6); 
		try {
			id = URLDecoder.decode(id, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			id = "";
		}
		return id;
	}

	/**
	 * Returns a scim user.
	 * @param req Servlet request. 
	 * @param resp Servlet response.
	 * @throws IOException Servlet I/O exception.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String user = getIdFromUri(req.getRequestURI());

		// get a handle to a user storage.
		DummyStorage storage = DummyStorage.getInstance();
		ScimUser scimUser = storage.getUserForId(user);

		if (scimUser != null) {
			resp.setContentType(CONTENT_TYPE_XML);

			// TODO: SPEC: REST: Should the Location header be included in more then POST? Like in this response?
			// TODO: SPEC: REST: Also include the location in meta data for the user?
			// TODO: SPEC: REST: Should ETag be added in more places in spec?
			
			JsonEncoder encoder = new JsonEncoder();
			String json = "";
			try {
				// encode the user as JSON
				json = encoder.encode(scimUser);
			} catch (EncodingFailed e) {
				e.printStackTrace();
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
			}
			
			resp.setStatus(HttpServletResponse.SC_OK); // 200
			resp.getWriter().print(json);
		} else {
			// TODO: SPEC: REST: What to respond when token not there? (4.2.1. Retrieving a known Resource)
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
		}
	}

	/**
	 * Create a new scim user.
	 * @param req Servlet request. 
	 * @param resp Servlet response.
	 * @throws IOException Servlet I/O exception.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String query = "";
		// TODO: SPEC: REST: Shouln't the POST message have a specific name for
		// the value? Right now the name is the value.
		Enumeration<?> paramNames = req.getParameterNames();
		while (paramNames.hasMoreElements()) {
			query = (String) paramNames.nextElement();
		}

		// TODO: SPEC: REST: Should the post message be base64 encoded in spec or not?
		
        ScimUser scimUser = new ScimUser();
		try {
			// decode the json user into an ScimUser object
	        JsonDecoder decoder = new JsonDecoder();
	        decoder.decode(query, scimUser);
	        
	        // store ScimUser into storage
			DummyStorage storage = DummyStorage.getInstance();
			storage.addUser(scimUser);

			// TODO: SPEC: REST: Define what to return in detail.
			String response = "" + "{\n" + "\t\"id\" : \"" + scimUser.getId() + "\",\n" 
										+ "\t\"errors\" : [ ],  //TODO - not in core schema\n"
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
			String contextPath = req.getContextPath();
			String location = scheme + "://" + serverName + ":" + serverPortStr + contextPath + "/User/" + scimUser.getId();

			resp.setHeader("Location", location);

			resp.setStatus(HttpServletResponse.SC_CREATED); // 201
			resp.getWriter().print(response);	        
		} catch (InvalidUser e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
		}
	}

	/**
	 * PUT performs a full update.
	 * @param req Servlet request. 
	 * @param resp Servlet response.
	 * @throws IOException Servlet I/O exception.
	 */
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// TODO: SPEC: REST: Should we keep created time?
		String user = getIdFromUri(req.getRequestURI());
		DummyStorage storage = DummyStorage.getInstance();
		try {
			storage.deleteUser(user);
		} catch (UserNotFoundException e) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}

		String query = "";
		// TODO: SPEC: REST: Shouln't the POST message have a specific name for
		// the value? Right now the name is the value.
		Enumeration<?> paramNames = req.getParameterNames();
		while (paramNames.hasMoreElements()) {
			query = (String) paramNames.nextElement();
		}

		// TODO: SPEC: REST: Should the post message be base64 encoded in spec or not?
		
        ScimUser scimUser = new ScimUser();
		try {
			// decode the json user into an ScimUser object
	        JsonDecoder decoder = new JsonDecoder();
	        decoder.decode(query, scimUser);
	        
	        // store ScimUser into storage
			storage.addUser(scimUser);

			// TODO: SPEC: REST: Define what to return in detail.
			String response = "" + "{\n" + "\t\"id\" : \"" + scimUser.getId() + "\",\n" 
										+ "\t\"errors\" : [ ],  //TODO - not in core schema\n"
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
			String contextPath = req.getContextPath();
			String location = scheme + "://" + serverName + ":" + serverPortStr + contextPath + "/User/" + scimUser.getId();

			resp.setHeader("Location", location);

			resp.setStatus(HttpServletResponse.SC_CREATED); // 201
			resp.getWriter().print(response);	        
		} catch (InvalidUser e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
		}
		
	}

	/**
	 * Delete a scim user.
	 * @param req Servlet request. 
	 * @param resp Servlet response.
	 * @throws IOException Servlet I/O exception.
	 */
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String user = getIdFromUri(req.getRequestURI());
		DummyStorage storage = DummyStorage.getInstance();
		try {
			storage.deleteUser(user);
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (UserNotFoundException e) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	/**
	 * Change an attribute on a scim user.
	 * @param req Servlet request. 
	 * @param resp Servlet response.
	 * @throws IOException Servlet I/O exception.
	 */
	public void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// validate input

		// PRE
		// 		get user from storage
		// 		lock storage
		//		check etag
		
		// delete
		// 		find meta tag, and extract attributes.
		//		clear values

		// add/update 
		//		get changed values

		// singlular
		//		update user
		
		// plural
		// 		find meta tag
		//		remove attributes
		//		get changed value
		//		add attributes
		
		// POST
		//		save user in storage
		//		unlock storage

		
		// adding singular. If the Resource does not already contain a value for the specified attribute the attribute value is added (or said another way “set”).
		// adding plural. If the Resource does not already contain the value the value is added to the attribute
		// Updating attributes (Singular): If the attribute value exists the value is replaced by the value specified in the request.
		// 
		
		String query = "";
		// TODO: SPEC: REST: Shouln't the POST message have a specific name for
		// the value? Right now the name is the value.
		Enumeration<?> paramNames = req.getParameterNames();
		while (paramNames.hasMoreElements()) {
			query = (String) paramNames.nextElement();
		}

		// TODO: SPEC: REST: Should the post message be base64 encoded in spec or not?
		
		String userId = getIdFromUri(req.getRequestURI());

		// get a handle to a user storage.
		DummyStorage storage = DummyStorage.getInstance();
		ScimUser scimUser = storage.getUserForId(userId);
		
		// TODO: check ETag
		try {
			String version = storage.getVersionForUser(scimUser);
		} catch (UserNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

        ScimUser tmpUser = new ScimUser();
		try {
			// decode the json user into an ScimUser object
	        JsonDecoder decoder = new JsonDecoder();
	        decoder.decode(query, tmpUser);

		} catch (InvalidUser e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
		}
		
		if(tmpUser != null) {
			// delete
			JSONArray attr = (JSONArray)tmpUser.getMeta().getAttributes();

			for (int i=0; i<attr.length(); i++) {
				try {
					scimUser.removeAttribute(attr.get(i).toString());
				} catch (JSONException e) {
					// nothing to remove
				}
			}
			
			// update
			scimUser.copyValuesFromUser(tmpUser);

			
		}
		
		JsonEncoder encoder = new JsonEncoder();
		String json = "";
		try {
			// encode the user as JSON
			json = encoder.encode(scimUser);
		} catch (EncodingFailed e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
		}
		
		resp.setStatus(HttpServletResponse.SC_OK); // 200
		resp.getWriter().print(json);

	}

}
