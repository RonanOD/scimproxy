package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.EncodingFailed;
import info.simplecloud.core.execeptions.FailedToGetValue;
import info.simplecloud.core.execeptions.FailedToSetValue;
import info.simplecloud.core.execeptions.InvalidUser;
import info.simplecloud.core.execeptions.UnhandledAttributeType;
import info.simplecloud.core.execeptions.UnknowExtension;
import info.simplecloud.core.execeptions.UnknownEncoding;
import info.simplecloud.core.execeptions.UnknownType;
import info.simplecloud.core.types.Meta;
import info.simplecloud.scimproxy.storage.dummy.UserNotFoundException;
import info.simplecloud.scimproxy.user.User;
import info.simplecloud.scimproxy.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * To retrieve a known Resource, clients send GET requests to the Resource end
 * point; e.g., /User/{id}. This servlet is the /User end point.
 */

public class ScimUserServlet extends RestServlet {

	/**
	 * Serialize id.
	 */
	private static final long serialVersionUID = -5875059636322733570L;

	private Log log = LogFactory.getLog(ScimUserServlet.class);

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
		String userId = getIdFromUri(req.getRequestURI());

		try {

			// TODO: SPEC: REST: Should the Location header be included in more
			// then POST?
			// TODO: SPEC: REST: Also include the location in meta data for the
			// user?
			ScimUser scimUser = User.getUser(userId);
			String userStr = scimUser.getUser(HttpGenerator.getEncoding(req));

			resp.setContentType(HttpGenerator.getContentType(req));
			resp.setHeader("ETag", scimUser.getMeta().getVersion());

			HttpGenerator.ok(resp, userStr);

			log.info("Returning user " + scimUser.getId());
		} catch (UnknownEncoding e) {
			HttpGenerator.serverError(resp);
		} catch (EncodingFailed e) {
			HttpGenerator.serverError(resp);
		} catch (UserNotFoundException e) {
			HttpGenerator.notFound(resp);
		} catch (FailedToGetValue e) {
            HttpGenerator.serverError(resp);
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
		// TODO: SPEC: REST: Should the post message be base64 encoded in spec
		// or not?

		if (query != null && !"".equals(query)) {
			try {
				ScimUser scimUser = new ScimUser(query, HttpGenerator.getEncoding(req));
				Meta meta = scimUser.getMeta();
				if (meta == null) {
					meta = new Meta();
				}
				meta.setVersion(Util.generateVersionString());
				scimUser.setMeta(meta);

				User.addUser(scimUser);

				/*
				 * // TODO: REST: SPEC: Why not return scim user instead? Easier
				 * to parse. // TODO: SPEC: REST: Define what to return in
				 * detail. String response = "" + "{\n" + "\t\"id\" : \"" +
				 * scimUser.getId() + "\",\n" +
				 * "\t\"errors\" : [ ],  //TODO - not in core schema\n" +
				 * "\t\"success\" : true //TODO - not in core schema\n" + "}\n";
				 */
				resp.setContentType(HttpGenerator.getContentType(req));
				resp.setHeader("Location", HttpGenerator.getLocation(scimUser, req));
				resp.setHeader("ETag", scimUser.getMeta().getVersion());

				log.info("Creating user " + scimUser.getId());

				HttpGenerator.created(resp, scimUser.getUser(HttpGenerator.getEncoding(req)));
			} catch (UnknownEncoding e) {
				HttpGenerator.serverError(resp, "Unknown encoding.");
			} catch (InvalidUser e) {
				HttpGenerator.badRequest(resp, "Malformed user.");
			} catch (EncodingFailed e) {
				HttpGenerator.badRequest(resp, "Failed to encode user.");
			} catch (UnhandledAttributeType e) {
	            HttpGenerator.serverError(resp);
            } catch (FailedToSetValue e) {
                HttpGenerator.serverError(resp);
            } catch (UnknownType e) {
                HttpGenerator.serverError(resp);
            } catch (InstantiationException e) {
                HttpGenerator.serverError(resp);
            } catch (IllegalAccessException e) {
                HttpGenerator.serverError(resp);
            } catch (FailedToGetValue e) {
                HttpGenerator.serverError(resp);
            }
		} else {
			HttpGenerator.badRequest(resp);
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

		String userId = getIdFromUri(req.getRequestURI());
		String query = getContent(req);

		if (query != null && !"".equals(query) && userId != null) {

			try {

				// TODO: SPEC: REST: Should the post message be base64 encoded
				// in spec or not?
				// TODO: SPEC: REST: Should ETag be verified in PUT?
				// TODO: SPEC: REST: Should we keep created time?
				ScimUser scimUser = new ScimUser(query, HttpGenerator.getEncoding(req));
				Meta meta = scimUser.getMeta();
				if (meta == null) {
					meta = new Meta();
				}
				meta.setVersion(Util.generateVersionString());
				scimUser.setMeta(meta);

				// delete old user
				User.deletetUser(userId);

				// add new user
				User.addUser(scimUser);

				resp.setHeader("Location", HttpGenerator.getLocation(scimUser, req));
				resp.setContentType(HttpGenerator.getContentType(req));
				resp.setHeader("ETag", scimUser.getMeta().getVersion());
				HttpGenerator.ok(resp, scimUser.getUser(HttpGenerator.getEncoding(req)));

				log.info("Replacing user " + scimUser.getId());
			} catch (UserNotFoundException e) {
				HttpGenerator.notFound(resp);
			} catch (UnknownEncoding e) {
				HttpGenerator.badRequest(resp, "Unknown encoding.");
			} catch (InvalidUser e) {
				HttpGenerator.badRequest(resp, "Invalid user.");
			} catch (EncodingFailed e) {
				HttpGenerator.serverError(resp, "Failed to decode changed user.");
			} catch (UnhandledAttributeType e) {
	            HttpGenerator.serverError(resp);
            } catch (FailedToSetValue e) {
                HttpGenerator.serverError(resp);
            } catch (UnknownType e) {
                HttpGenerator.serverError(resp);
            } catch (InstantiationException e) {
                HttpGenerator.serverError(resp);
            } catch (IllegalAccessException e) {
                HttpGenerator.serverError(resp);
            } catch (FailedToGetValue e) {
                HttpGenerator.serverError(resp);
            }

		} else {
			HttpGenerator.badRequest(resp, "Invalid user or user id.");
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
		log.trace("Trying to deleting user " + userId + ".");
		if (userId != null) {
			try {
				User.deletetUser(userId);
				HttpGenerator.ok(resp);
				log.info("Deleating user " + userId + ".");
			} catch (UserNotFoundException e) {
				HttpGenerator.notFound(resp);
				log.trace("User " + userId + " is not found.");
			}
		} else {
			log.trace("Trying to delete a user that can't be found in storage with user id " + userId + ".");
			HttpGenerator.badRequest(resp, "Missing or malformed user id.");
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

		String query = getContent(req);
		String userId = getIdFromUri(req.getRequestURI());
		String etag = req.getHeader("ETag");

		if (!"".equals(query) && userId != null && etag != null && !"".equals(etag)) {

			// TODO: SPEC: REST: Should the post message be base64 encoded in
			// spec or not?

			try {
				ScimUser scimUser = User.getUser(userId);
				// check that version haven't changed since loaded from server
				String version = scimUser.getMeta().getVersion();
				if (etag.equals(version)) {
					// patch user
					scimUser.patch(query, HttpGenerator.getEncoding(req));
					// generate new version number
					User.updateVersionNumber(scimUser);

					resp.getWriter().print(scimUser.getUser(HttpGenerator.getEncoding(req)));
					resp.setStatus(HttpServletResponse.SC_OK); // 200
					log.info("Patching user " + scimUser.getId());
				} else {
					HttpGenerator.preconditionFailed(resp, scimUser);
				}

			} catch (UserNotFoundException e) {
				HttpGenerator.notFound(resp);
			} catch (UnknownEncoding e) {
				HttpGenerator.badRequest(resp, "Unknown encoding.");
			} catch (EncodingFailed e) {
				HttpGenerator.serverError(resp, "Failed to encode user.");
			} catch (InvalidUser e) {
				HttpGenerator.badRequest(resp, "Malformed user.");
			} catch (UnhandledAttributeType e) {
	            HttpGenerator.serverError(resp);
            } catch (FailedToSetValue e) {
                HttpGenerator.serverError(resp);
            } catch (UnknownType e) {
                HttpGenerator.serverError(resp);
            } catch (InstantiationException e) {
                HttpGenerator.serverError(resp);
            } catch (IllegalAccessException e) {
                HttpGenerator.serverError(resp);
            } catch (FailedToGetValue e) {
                HttpGenerator.serverError(resp);
            } catch (UnknowExtension e) {
                HttpGenerator.serverError(resp);
            }

		} else {
			HttpGenerator.badRequest(resp, "Missing user id or ETag");
		}

	}

	/**
	 * Gets the content from a request by looping though all lines.
	 * 
	 * @param req
	 *            The request to parse.
	 * @return The content of the request or null if an error occurred while
	 *         parsing request.
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
	 * Gets an user id from a request. /User/myuserid will return myuserid.
	 * 
	 * @param query
	 *            A URI, for example /User/myuserid.
	 * @return A scim user id.
	 */
	public static String getIdFromUri(String query) {
		String id = "";
		// TODO: add more validation of input
		String s = "/User/";
		if(query != null && query.length() > 0) {
			int indexOfUserId = query.indexOf(s) + s.length();
			
			id = query.substring(indexOfUserId);
			try {
				id = URLDecoder.decode(id, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// just return empty id
			}
		}
		return id;
	}

}
