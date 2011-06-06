package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.decoding.JsonDecoder;
import info.simplecloud.core.encoding.JsonEncoder;
import info.simplecloud.core.execeptions.EncodingFailed;
import info.simplecloud.core.execeptions.InvalidUser;
import info.simplecloud.core.execeptions.UnknownEncoding;
import info.simplecloud.scimproxy.storage.dummy.DummyStorage;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.impl.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;

/*

GET:
curl -i -H "Accept: application/json" http://localhost:8080/User/erwah-1234-5678

POST:
curl -i -H "Accept: application/json" -X POST -d "{%22schemas%22: [%22urn:scim:schemas:core:1.0%22],%22userName%22: %22bjensen@example.com%22,%22name%22: {%22familyName%22: %22Jensen%22,%22givenName%22: %22Barbara%22},%22displayName%22: %22Babs Jensen%22, %22emails%22: [{%22value%22: %22bjensen@example.com%22,%22type%22: %22work%22,%22primary%22: true}]}" http://localhost:8080/User
BASE64 encoded POST
curl -i -H "Accept: application/json" -X POST -d "ewoJICAgICJzY2hlbWFzIjogWyJ1cm46c2NpbTpzY2hlbWFzOmNvcmU6MS4wIl0sCgkgICAgImlkIjogIjAwNUQwMDAwMDAxQXoxdSIsCgkgICAgImV4dGVybmFsSWQiOiAiNzAxOTg0IiwKCSAgICAidXNlck5hbWUiOiAiYmplbnNlbkBleGFtcGxlLmNvbSIsCgkgICAgIm5hbWUiOiB7CgkgICAgCSJmb3JtYXR0ZWQiOiAiTXMuIEJhcmJhcmEgSiBKZW5zZW4gSUlJIiwKCSAgICAgICAgImZhbWlseU5hbWUiOiAiSmVuc2VuIiwKCSAgICAgICAgImdpdmVuTmFtZSI6ICJCYXJiYXJhIiwKCSAgICAgICAgIm1pZGRsZU5hbWUiOiAiSmFuZSIsCgkgICAgICAgICJob25vcmlmaWNQcmVmaXgiOiAiTXMuIiwKCSAgICAgICAgImhvbm9yaWZpY1N1ZmZpeCI6ICJJSUkiCgkgICAgfSwKCSAgICAiZGlzcGxheU5hbWUiOiAiQmFicyBKZW5zZW4iLAoJICAgICJuaWNrTmFtZSI6ICJCYWJzIiwKCSAgICAicHJvZmlsZVVybCI6ICJodHRwczovL2xvZ2luLmV4YW1wbGUuY29tL2JqZW5zZW4iLAoJICAgICJlbWFpbHMiOiBbCgkgICAgICAgIHsKCSAgICAgICAgICAidmFsdWUiOiAiYmplbnNlbkBleGFtcGxlLmNvbSIsCgkgICAgICAgICAgInR5cGUiOiAid29yayIsCgkgICAgICAgICAgInByaW1hcnkiOiB0cnVlCgkgICAgICAgIH0sCgkgICAgICAgIHsKCSAgICAgICAgICAidmFsdWUiOiAiYmFic0BqZW5zZW4ub3JnIiwKCSAgICAgICAgICAidHlwZSI6ICJob21lIgoJICAgICAgICB9CgkgICAgXSwKCSAgICAiYWRkcmVzc2VzIjogWwoJICAgICAgICB7CgkgICAgICAgICAgInR5cGUiOiAid29yayIsCgkgICAgICAgICAgInN0cmVldEFkZHJlc3MiOiAiMTAwIFVuaXZlcnNhbCBDaXR5IFBsYXphIiwKCSAgICAgICAgICAibG9jYWxpdHkiOiAiSG9sbHl3b29kIiwKCSAgICAgICAgICAicmVnaW9uIjogIkNBIiwKCSAgICAgICAgICAicG9zdGFsQ29kZSI6ICI5MTYwOCIsCgkgICAgICAgICAgImNvdW50cnkiOiAiVVNBIiwKCSAgICAgICAgICAiZm9ybWF0dGVkIjogIjEwMCBVbml2ZXJzYWwgQ2l0eSBQbGF6YVxuSG9sbHl3b29kLCBDQSA5MTYwOCBVU0EiLAoJICAgICAgICAgICJwcmltYXJ5IjogdHJ1ZQoJICAgICAgICB9LAoJICAgICAgICB7CgkgICAgICAgICAgInR5cGUiOiAiaG9tZSIsCgkgICAgICAgICAgInN0cmVldEFkZHJlc3MiOiAiNDU2IEhvbGx5d29vZCBCbHZkIiwKCSAgICAgICAgICAibG9jYWxpdHkiOiAiSG9sbHl3b29kIiwKCSAgICAgICAgICAicmVnaW9uIjogIkNBIiwKCSAgICAgICAgICAicG9zdGFsQ29kZSI6ICI5MTYwOCIsCgkgICAgICAgICAgImNvdW50cnkiOiAiVVNBIiwKCSAgICAgICAgICAiZm9ybWF0dGVkIjogIjQ1NiBIb2xseXdvb2QgQmx2ZFxuSG9sbHl3b29kLCBDQSA5MTYwOCBVU0EiCgkgICAgICAgIH0KCSAgICBdLAoJICAgICJwaG9uZU51bWJlcnMiOiBbCgkgICAgICAgIHsKCSAgICAgICAgICAidmFsdWUiOiAiODAwLTg2NC04Mzc3IiwKCSAgICAgICAgICAidHlwZSI6ICJ3b3JrIgoJICAgICAgICB9LAoJICAgICAgICB7CgkgICAgICAgICAgInZhbHVlIjogIjgxOC0xMjMtNDU2NyIsCgkgICAgICAgICAgInR5cGUiOiAibW9iaWxlIgoJICAgICAgICB9CgkgICAgXSwKCSAgICAiaW1zIjogWwoJICAgICAgICB7CgkgICAgICAgICAgInZhbHVlIjogInNvbWVhaW1oYW5kbGUiLAoJICAgICAgICAgICJ0eXBlIjogImFpbSIKCSAgICAgICAgfQoJICAgIF0sCgkgICAgInBob3RvcyI6IFsKCSAgICAgICAgewoJICAgICAgICAgICJ2YWx1ZSI6ICJodHRwczovL3Bob3Rvcy5leGFtcGxlLmNvbS9wcm9maWxlcGhvdG8vNzI5MzAwMDAwMDBDY25lL0YiLAoJICAgICAgICAgICJ0eXBlIjogInBob3RvIgoJICAgICAgICB9LAoJICAgICAgICB7CgkgICAgICAgICAgInZhbHVlIjogImh0dHBzOi8vcGhvdG9zLmV4YW1wbGUuY29tL3Byb2ZpbGVwaG90by83MjkzMDAwMDAwMENjbmUvVCIsCgkgICAgICAgICAgInR5cGUiOiAidGh1bWJuYWlsIgoJICAgICAgICB9CgkgICAgXSwKCSAgICAiZW1wbG95ZWVOdW1iZXIiOiAiNzAxOTg0IiwKCSAgICAidXNlclR5cGUiOiAiRW1wbG95ZWUiLAoJICAgICJ0aXRsZSI6ICJUb3VyIEd1aWRlIiwKCSAgICAibWFuYWdlciI6ICJNYW5keSBQZXBwZXJpZGdlIiwKCSAgICAicHJlZmVycmVkTGFuZ3VhZ2UiOiAiZW5fVVMiLAoJICAgICJsb2NhbGUiOiAiZW5fVVMiLAoJICAgICJ1dGNPZmZzZXQiOiAiLTA4OjAwIiwKCSAgICAiY29zdENlbnRlciI6ICI0MTMwIiwKCSAgICAib3JnYW5pemF0aW9uIjogIlVuaXZlcnNhbCBTdHVkaW9zIiwKCSAgICAiZGl2aXNpb24iOiAiVGhlbWUgUGFyayIsCgkgICAgImRlcGFydG1lbnQiOiAiVG91ciBPcGVyYXRpb25zIiwKCSAgICAiZ3JvdXBzIjogWwoJICAgICAgICB7CgkgICAgICAgICAgInZhbHVlIjogIlRvdXIgR3VpZGVzIiwKCSAgICAgICAgICAicHJpbWFyeSI6IHRydWUKCSAgICAgICAgfSwKCSAgICAgICAgewoJICAgICAgICAgICJ2YWx1ZSI6ICJFbXBsb3llZXMiLAoJICAgICAgICB9LAoJICAgICAgICB7CgkgICAgICAgICAgInZhbHVlIjogIlVTIEVtcGxveWVlcyIsCgkgICAgICAgIH0KCSAgICBdLAoJICAgICJtZXRhIjogewoJICAgICAgICAiY3JlYXRlZCI6ICIyMDEwLTAxLTIzVDA0OjU2OjIyWiIsCgkgICAgICAgICJsYXN0TW9kaWZpZWQiOiAiMjAxMS0wNS0xM1QwNDo0MjozNFoiCgkgICAgfQoJfQ==" http://localhost:8080/User

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

	String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";
	String CONTENT_TYPE_XML = "text/xml; charset=UTF-8";

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// TODO: get user id from request, validate input
		String user = req.getRequestURI();
		user = user.substring(6);

		DummyStorage storage = DummyStorage.getInstance();
		ScimUser scimUser = storage.getUserForId(user);

		if (scimUser != null) {
			resp.setContentType(CONTENT_TYPE_XML);

			// TODO: SPEC: REST: Should the Location header be included in more then POST? Like in this response?
			// TODO: SPEC: REST: Should ETag be added in more places in spec?
			
			JsonEncoder encoder = new JsonEncoder();
			String json = "";
			try {
				json = encoder.encode(scimUser);
			} catch (EncodingFailed e) {
				e.printStackTrace();
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
				// TODO: Include error message.
			}
			
			resp.setStatus(HttpServletResponse.SC_OK); // 200
			resp.getWriter().print(json);
		} else {
			// TODO: SPEC: REST: What to respond when token not there? (4.2.1. Retrieving a known Resource)
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
		}
	}

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
//			query = new String(Base64.decode(query.getBytes()));

	        JsonDecoder decoder = new JsonDecoder();
	        decoder.decode(query, scimUser);
	        
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
