package info.simplecloud.scimproxy;

import info.simplecloud.core.Meta;
import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.EncodingFailed;
import info.simplecloud.core.execeptions.InvalidUser;
import info.simplecloud.core.execeptions.UnknownEncoding;
import info.simplecloud.scimproxy.storage.IStorage;
import info.simplecloud.scimproxy.storage.dummy.DummyStorage;
import info.simplecloud.scimproxy.storage.dummy.UserNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * To retrieve a known Resource, clients send GET requests to the Resource end
 * point; e.g., /User/{id}. This servlet is the /User end point.
 */

public class ScimUserServlet extends RestServlet {

    /**
     * Serialize id.
     */
    private static final long serialVersionUID = -5875059636322733570L;
    

    /**
     * Returns a scim user.
     * 
     * @param req
     *            Servlet request.
     * @param resp
     *            Servlet response.
     * @throws IOException
     *             Servlet I/O exception.
     */
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String user = getIdFromUri(req.getRequestURI());

        // get a handle to a user storage.
        DummyStorage storage = DummyStorage.getInstance();
        ScimUser scimUser = storage.getUserForId(user);

        if (scimUser != null) {

            String json = "";
            try {
            	// TODO: get encoding from request
				json = scimUser.getUser("JSON");
            } catch (UnknownEncoding e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
                resp.getWriter().print("Error: An internal error. Using unknown encoding.");
                return;
            } catch (EncodingFailed e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
                resp.getWriter().print("Error: An internal error. Failed to encode user.");
                return;
            }

            // TODO: SPEC: REST: Should the Location header be included in more then POST? 
            // TODO: SPEC: REST: Also include the location in meta data for the user?
            resp.setContentType(CONTENT_TYPE_JSON);
            resp.setHeader("ETag", scimUser.getMeta().getVersion());
            resp.setStatus(HttpServletResponse.SC_OK); // 200
            resp.getWriter().print(json);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
            resp.getWriter().print("Specified User does not exist.");
        }
    }

    
    /**
     * Create a new scim user.
     * 
     * @param req
     *            Servlet request.
     * @param resp
     *            Servlet response.
     * @throws IOException
     *             Servlet I/O exception.
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String query = getContent(req);
        // TODO: SPEC: REST: Should the post message be base64 encoded in spec or not?

        if(query == null || "".equals(query)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            resp.getWriter().print("Error: Request is unparseable, syntactically incorrect, or violates schema.");
            return;
        }

        try {
            ScimUser scimUser = new ScimUser(query, "JSON");
            Meta meta = scimUser.getMeta();
            if(meta == null) {
            	meta = new Meta();
            }
            meta.setVersion(generateVersionString());
            scimUser.setMeta(meta);

            // store ScimUser into storage
            DummyStorage storage = DummyStorage.getInstance();
            storage.addUser(scimUser);
/*
            // TODO: REST: SPEC: Why not return scim user instead? Easier to parse.
            // TODO: SPEC: REST: Define what to return in detail.
            String response = "" + "{\n" + "\t\"id\" : \"" + scimUser.getId() + "\",\n"
                    + "\t\"errors\" : [ ],  //TODO - not in core schema\n" + "\t\"success\" : true //TODO - not in core schema\n" + "}\n";
*/
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
            resp.setHeader("ETag", scimUser.getMeta().getVersion());

            resp.setStatus(HttpServletResponse.SC_CREATED); // 201
            resp.getWriter().print(scimUser.getUser("JSON"));
        } catch (UnknownEncoding e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            resp.getWriter().print("Error: An internal error. Using unknown encoding.");
		} catch (InvalidUser e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            resp.getWriter().print("Error: Request is unparseable, syntactically incorrect, or violates schema. Unknown encoding.");
        } catch (EncodingFailed e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            resp.getWriter().print("Error: An internal error. Using unknown encoding.");
		} 
    }

    
    /**
     * PUT performs a full update.
     * 
     * @param req
     *            Servlet request.
     * @param resp
     *            Servlet response.
     * @throws IOException
     *             Servlet I/O exception.
     */
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // TODO: SPEC: REST: Should ETag be verified in PUT?

        // TODO: SPEC: REST: Should we keep created time?
        String userId = getIdFromUri(req.getRequestURI());
        String query = getContent(req);
        // TODO: SPEC: REST: Should the post message be base64 encoded in spec or not?

        if(query == null || "".equals(query) || userId == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            resp.getWriter().print("Error: Request is unparseable, syntactically incorrect, or violates schema.");
            return;
        }
        
        DummyStorage storage = DummyStorage.getInstance();
        try {
            storage.deleteUser(userId);
        } catch (UserNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
            resp.getWriter().print("Specified User does not exist.");
            return;
        }

        // decode the json user into an ScimUser object
        ScimUser scimUser;
		try {
			scimUser = new ScimUser(query, "JSON");
        } catch (UnknownEncoding e1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 (right now it's actually a 500 as long as it's hardcoded)
            resp.getWriter().print("Error: Request is unparseable, syntactically incorrect, or violates schema. Unknown encoding.");
            return;
        } catch (InvalidUser e1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            resp.getWriter().print("Error: Request is unparseable, syntactically incorrect, or violates schema. Failed to decode patch.");
            return;
        }

        // store ScimUser into storage
        Meta meta = scimUser.getMeta();
        if(meta == null) {
        	meta = new Meta();
        }
        meta.setVersion(generateVersionString());
        scimUser.setMeta(meta);
        storage.addUser(scimUser);


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
        resp.setContentType(CONTENT_TYPE_JSON);
        resp.setHeader("ETag", scimUser.getMeta().getVersion());

        resp.setStatus(HttpServletResponse.SC_CREATED); // 201
        try {
			resp.getWriter().print(scimUser.getUser("JSON"));
        } catch (UnknownEncoding e1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 (right now it's actually a 500 as long as it's hardcoded)
            resp.getWriter().print("Error: Request is unparseable, syntactically incorrect, or violates schema. Unknown encoding.");
        } catch (EncodingFailed e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            resp.getWriter().print("Error: An internal error. Could not encode user that was added.");
		}
    }
    

    /**
     * Delete a scim user.
     * 
     * @param req
     *            Servlet request.
     * @param resp
     *            Servlet response.
     * @throws IOException
     *             Servlet I/O exception.
     */
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = getIdFromUri(req.getRequestURI());
        
        if(userId == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            resp.getWriter().print("Error: Request is unparseable, syntactically incorrect, or violates schema. Missing or malformed user id.");
            return;
        }

        DummyStorage storage = DummyStorage.getInstance();
        try {
            storage.deleteUser(userId);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (UserNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    

    /**
     * Change or remove attribute on a scim user.
     * 
     * @param req
     *            Servlet request.
     * @param resp
     *            Servlet response.
     * @throws IOException
     *             Servlet I/O exception.
     */
    public void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String query = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String message = null;
		while ((message = reader.readLine()) != null) {
			query += message;
		}

        
        // TODO: SPEC: REST: Should the post message be base64 encoded in spec
        // or not?

        String userId = getIdFromUri(req.getRequestURI());
        String etag = req.getHeader("ETag");
        
        if("".equals(query) || userId == null || etag == null || "".equals(etag)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            resp.getWriter().print("Error: Request is unparseable, syntactically incorrect, or violates schema. Missing user id or ETag.");
            return;
        }
        
        // get a handle to a user storage.
        // TODO change from singleton to servlet context thingi
        IStorage storage = DummyStorage.getInstance();

        // get user
        // TODO make sure user to lock user
        ScimUser scimUser = storage.getUserForId(userId);
        if (scimUser == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
            resp.getWriter().print("Specified User does not exist.");
            return;
        }

        // check that version haven't changed since loaded from server
        String version = scimUser.getMeta().getVersion();
        if(!etag.equals(version)) {
            resp.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED); // 412
            resp.getWriter().print("Error: Failed to update as Resource " + scimUser.getId() + " changed on the server since you last retrieved it.");
            return;
        }

        try {
            // TODO Read encoding from request
            scimUser.patch(query, "JSON");
            scimUser.getMeta().setVersion(generateVersionString());
        } catch (UnknownEncoding e1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            resp.getWriter().print("Error: Request is unparseable, syntactically incorrect, or violates schema. Unknown encoding.");
            return;
        } catch (InvalidUser e1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            resp.getWriter().print("Error: Request is unparseable, syntactically incorrect, or violates schema. Failed to decode patch.");
            return;
        }

        try {
            // TODO read encoding from request
            resp.getWriter().print(scimUser.getUser("JSON"));
        } catch (UnknownEncoding e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            resp.getWriter().print("Error: An internal error. Using unknown encoding.");
            return;
        } catch (EncodingFailed e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            resp.getWriter().print("Error: An internal error. Failed to encode user.");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK); // 200
    }
    
    
    /**
     * Gets the content from a request by looping though all lines.
     * @param req The request to parse.
     * @return The content of the request or null if an error occurred while parsing request.
     */
    private String getContent(HttpServletRequest req) {
        String query = "";
        BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
			String message = null;
			while ((message = reader.readLine()) != null) {
				query += message;
			}
		} catch (IOException e) {
			query = null;
		}
		return query;
    }
    
    
    /**
     * Calculate a version number for a scim user using MD5, don't use value as real MD5 for matching. The calculation is done without the meta node with version it self that's added later.
     * @param s A serialized scim user.
     * @return A version number of a scim user. Null is returned if an error occurred.
     */
	private SecureRandom random = new SecureRandom();
    private String generateVersionString() {
		return new BigInteger(130, random).toString(32) + Long.toString(System.currentTimeMillis());
    }
    
    
    /**
     * Gets an user id from a request. /User/myuserid will return myuserid.
     * 
     * @param query
     *            A URI, for example /User/myuserid.
     * @return A scim user id.
     */
    private String getIdFromUri(String query) {
        // TODO: validate input
        String id = query.substring(6);
        try {
            id = URLDecoder.decode(id, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            id = null;
        }
        return id;
    }
}
