package info.simplecloud.scimproxy;

import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.core.exceptions.UnknownEncoding;
import info.simplecloud.scimproxy.authentication.AuthenticateUser;
import info.simplecloud.scimproxy.exception.PreconditionException;
import info.simplecloud.scimproxy.storage.dummy.ResourceNotFoundException;
import info.simplecloud.scimproxy.user.UserDelegator;
import info.simplecloud.scimproxy.util.Util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * To retrieve a known Resource, clients send GET requests to the Resource end
 * point; e.g., /User/{id}. This servlet is the /User end point.
 */

public class ScimUserServlet extends ScimUserUpdatesServlet {

    /**
     * Serialize id.
     */
    private static final long serialVersionUID = -5875059636322733570L;

    private Log               log              = LogFactory.getLog(ScimUserServlet.class);

    
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
        String userId = Util.getUserIdFromUri(req.getRequestURI());
        AuthenticateUser authUser = (AuthenticateUser) req.getAttribute("AuthUser");

        try {
        	// TODO: should CSP trigger GET call be made before trying locally?

        	User scimUser = UserDelegator.getInstance(authUser.getSessionId()).getUser(userId);

            String userStr = null;

            if (req.getParameter("attributes") != null) {
                userStr = scimUser.getUser(HttpGenerator.getEncoding(req), Util.getAttributeStringFromRequest(req));
            } else {
                userStr = scimUser.getUser(HttpGenerator.getEncoding(req));
            }

            if (userStr == null) {
                HttpGenerator.badRequest(resp);
            } else {
                resp.setContentType(HttpGenerator.getContentType(req));
                resp.setHeader("ETag", scimUser.getMeta().getVersion());

                HttpGenerator.ok(resp, userStr);
                log.info("Returning user " + scimUser.getId());
            }

        } catch (UnknownEncoding e) {
            HttpGenerator.serverError(resp);
        } catch (ResourceNotFoundException e) {
            HttpGenerator.notFound(resp);
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

        String query = Util.getContent(req);
        // TODO: SPEC: REST: Should the post message be base64 encoded in spec
        // or not?

        if (query != null && !"".equals(query)) {
            try {
            	
            	User scimUser = internalPost(query, req);
            	
                resp.setContentType(HttpGenerator.getContentType(req));
                resp.setHeader("Location", scimUser.getMeta().getLocation());
                resp.setHeader("ETag", scimUser.getMeta().getVersion());

                log.info("Creating user " + scimUser.getId());
                
                // creating user in downstream CSP, any communication errors is handled in triggered and ignored here
                trigger.create(scimUser);				

                HttpGenerator.created(resp, scimUser.getUser(HttpGenerator.getEncoding(req)));
            } catch (UnknownEncoding e) {
                HttpGenerator.serverError(resp, "Unknown encoding.");
            } catch (InvalidUser e) {
                HttpGenerator.badRequest(resp, "Malformed user.");
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

        String userId = Util.getUserIdFromUri(req.getRequestURI());
        String query = Util.getContent(req);
        String etag = req.getHeader("ETag");
        
        if (query != null && !"".equals(query) && userId != null) {

            try {
            	User scimUser = internalPut(userId, etag, query, req);

                resp.setContentType(HttpGenerator.getContentType(req));
                resp.setHeader("Location", scimUser.getMeta().getLocation());
                resp.setHeader("ETag", scimUser.getMeta().getVersion());
                HttpGenerator.ok(resp, scimUser.getUser(HttpGenerator.getEncoding(req)));

                log.info("Replacing user " + scimUser.getId());
            } catch (PreconditionException e) {
                HttpGenerator.preconditionFailed(resp, userId);
            } catch (ResourceNotFoundException e) {
                HttpGenerator.notFound(resp);
            } catch (UnknownEncoding e) {
                HttpGenerator.badRequest(resp, "Unknown encoding.");
            } catch (InvalidUser e) {
                HttpGenerator.badRequest(resp, "Invalid user.");
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
        String userId = Util.getUserIdFromUri(req.getRequestURI());
        log.trace("Trying to deleting user " + userId + ".");

        if (userId != null) {
            try {
					internalDelete(userId, req.getHeader("ETag"), req);
                    HttpGenerator.ok(resp);
                    log.info("Deleating user " + userId + ".");

            } catch (ResourceNotFoundException e) {
                HttpGenerator.notFound(resp);
                log.trace("User " + userId + " is not found.");
            }
            catch (PreconditionException e) {
            	HttpGenerator.preconditionFailed(resp, userId);
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
        String userId = Util.getUserIdFromUri(req.getRequestURI());
        String query = Util.getContent(req);
        String etag = req.getHeader("ETag");
        

        if (!"".equals(query) && userId != null && etag != null && !"".equals(etag)) {

        	try {
				User scimUser = internalPatch(userId, etag, query, req);
				
                resp.setContentType(HttpGenerator.getContentType(req));
                resp.setHeader("Location", scimUser.getMeta().getLocation());
                resp.setHeader("ETag", scimUser.getMeta().getVersion());
                HttpGenerator.ok(resp, scimUser.getUser(HttpGenerator.getEncoding(req)));

                log.info("Patching user " + scimUser.getId());
				
			} catch (UnknownEncoding e) {
                HttpGenerator.badRequest(resp, "Unknown encoding.");
			} catch (InvalidUser e) {
                HttpGenerator.badRequest(resp, "Invalid user.");
			} catch (ResourceNotFoundException e) {
                HttpGenerator.notFound(resp);
			} catch (PreconditionException e) {
                HttpGenerator.preconditionFailed(resp, userId);
			} catch (UnknownAttribute e) {
                HttpGenerator.badRequest(resp, "Malformed user.");
			}
	    } else {
            HttpGenerator.badRequest(resp, "Missing user id or ETag");
	    }
    }
    
}
