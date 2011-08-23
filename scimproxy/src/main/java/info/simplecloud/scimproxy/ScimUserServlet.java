package info.simplecloud.scimproxy;

import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.UnknownEncoding;
import info.simplecloud.core.types.Meta;
import info.simplecloud.scimproxy.exception.PreconditionException;
import info.simplecloud.scimproxy.storage.dummy.UserNotFoundException;
import info.simplecloud.scimproxy.user.User;
import info.simplecloud.scimproxy.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        String userId = getIdFromUri(req.getRequestURI());

        try {
        	// TODO: should CSP trigger GET call be made before trying locally?

            info.simplecloud.core.User scimUser = User.getInstance().getUser(userId);
            String userStr = null;

            if (req.getParameter("attributes") != null) {
                userStr = scimUser.getUser(HttpGenerator.getEncoding(req), getAttributeStringFromRequest(req));
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
        } catch (UserNotFoundException e) {
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

        String query = getContent(req);
        // TODO: SPEC: REST: Should the post message be base64 encoded in spec
        // or not?

        if (query != null && !"".equals(query)) {
            try {
            	
            	info.simplecloud.core.User scimUser = internalPost(query, req);
            	
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

        String userId = getIdFromUri(req.getRequestURI());
        String query = getContent(req);
        String etag = req.getHeader("ETag");
        
        if (query != null && !"".equals(query) && userId != null) {

            try {
            	info.simplecloud.core.User scimUser = internalPut(userId, etag, query, req);

                resp.setContentType(HttpGenerator.getContentType(req));
                resp.setHeader("Location", scimUser.getMeta().getLocation());
                resp.setHeader("ETag", scimUser.getMeta().getVersion());
                HttpGenerator.ok(resp, scimUser.getUser(HttpGenerator.getEncoding(req)));

                log.info("Replacing user " + scimUser.getId());
            } catch (PreconditionException e) {
                HttpGenerator.preconditionFailed(resp, userId);
            } catch (UserNotFoundException e) {
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
    	delete(req, resp);
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
    	patch(req, resp);
    }
    
    private List<String> getAttributeStringFromRequest(HttpServletRequest req) {
        String attributesString = req.getParameter("attributes") == null ? "" : req.getParameter("attributes");
        List<String> attributesList = new ArrayList<String>();
        if (attributesString != null && !"".equals(attributesString)) {
            for (String attribute : attributesString.split(",")) {
                attributesList.add(attribute.trim());
            }
        }
        return attributesList;
    }
    
}
