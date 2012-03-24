package info.simplecloud.scimproxy;

import info.simplecloud.core.User;
import info.simplecloud.core.coding.encode.JsonEncoder;
import info.simplecloud.core.coding.encode.XmlEncoder;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.core.exceptions.UnknownEncoding;
import info.simplecloud.scimproxy.authentication.AuthenticateUser;
import info.simplecloud.scimproxy.exception.PreconditionException;
import info.simplecloud.scimproxy.storage.ResourceNotFoundException;
import info.simplecloud.scimproxy.storage.StorageDelegator;
import info.simplecloud.scimproxy.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * To retrieve a known Resource, clients send GET requests to the Resource end
 * point; e.g., /Users/{id}. This servlet is the /Users end point.
 */

public class ScimUserServlet extends ScimResourceServlet {

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

        if(!"".equals(userId)) {
            try {
            	User scimUser = getUserDelegator(authUser.getSessionId()).getUser(userId);
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
            } catch (Exception e) {
                HttpGenerator.notFound(resp);
            }
        }
        else {

            String attributesString = req.getParameter("attributes") == null ? "" : req.getParameter("attributes");
            List<String> attributesList = new ArrayList<String>();
            if (attributesString != null && !"".equals(attributesString)) {
                for (String attribute : attributesString.split(",")) {
                    attributesList.add(attribute.trim());
                }
            }

            // TODO: SPEC: REST: what is major
            String sortBy = req.getParameter("sortBy") == null ? "userName" : req.getParameter("sortBy");
            String sortOrder = req.getParameter("sortOrder") == null ? "ascending" : req.getParameter("sortOrder");
            if (!sortOrder.equalsIgnoreCase("ascending") && !sortOrder.equalsIgnoreCase("descending")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print("Sort order must be 'ascending' or 'descending'");
                return;
            }

            int index = 0;
            int count = 0;

            String startIndexStr = req.getParameter("startIndex"); // must be
                                                                   // absolut and
                                                                   // defaults to 0
            String countStr = req.getParameter("count"); // must be absolut and
                                                         // defaults to 0


            if (startIndexStr != null && !"".equals(startIndexStr)) {
                index = Integer.parseInt(startIndexStr);
            }
            if (countStr != null && !"".equals(countStr)) {
                count = Integer.parseInt(countStr);
            }
            
            List users = null;

            String filter = req.getParameter("filter");

            
            if (filter != null && !filter.isEmpty()) {
            	// TODO: Same is used for Group and User!
                try {
					users = getUserDelegator(authUser.getSessionId()).getUserList(sortBy, sortOrder, filter, index, count);
				} catch (ResourceNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } else {
                try {
					users = getUserDelegator(authUser.getSessionId()).getUserList(sortBy, sortOrder, index, count);
				} catch (ResourceNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(HttpGenerator.getContentType(req));

            String response = "";
            if (User.ENCODING_JSON.equalsIgnoreCase(HttpGenerator.getEncoding(req))) {
                response = new JsonEncoder().encode(users, attributesList);
            }
            if (User.ENCODING_XML.equalsIgnoreCase(HttpGenerator.getEncoding(req))) {
                response = new XmlEncoder().encode(users, attributesList);
            }

            resp.getWriter().print(response);
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

        if (query != null && !"".equals(query)) {
            try {
                String server = HttpGenerator.getServer(req);
                String outEncoding = HttpGenerator.getEncoding(req);
                AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");

                ResourceJob resource = new ResourceJob();
                resource.setData(query);
                
            	User scimUser = internalUserPost(resource, server, outEncoding, authUser);
                resp.setContentType(HttpGenerator.getContentType(req));
                resp.setHeader("Location", scimUser.getMeta().getLocation());
                resp.setHeader("ETag", scimUser.getMeta().getVersion());

                log.info("Creating user " + scimUser.getId());
                
                HttpGenerator.created(resp, scimUser.getUser(HttpGenerator.getEncoding(req)));
            } catch (UnknownEncoding e) {
                e.printStackTrace();
                HttpGenerator.serverError(resp, "Unknown encoding.");
            } catch (InvalidUser e) {
                e.printStackTrace();
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
                String server = HttpGenerator.getServer(req);
                String outEncoding = HttpGenerator.getEncoding(req);
                AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");

                ResourceJob resource = new ResourceJob();
                resource.setData(query);
                resource.setVersion(etag);
                resource.setId(userId);

            	User scimUser = internalUserPut(resource, server, outEncoding, authUser);

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

        boolean isPassword = Util.isChangePassword(req.getRequestURI());

        if(isPassword) {
            AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");
            ResourceJob resource = new ResourceJob();
            resource.setId(userId);
            resource.setData(query);
            
			try {
				JSONObject jsonObj = new JSONObject(resource.getData());
				String password = jsonObj.getString("password");
				
				// TODO: Add support for XML!

				internalChangePasswordPatch(resource, password, authUser);
                HttpGenerator.noContent(resp);
			} catch (ResourceNotFoundException e) {
                HttpGenerator.notFound(resp);
			} catch (JSONException e) {
                HttpGenerator.badRequest(resp, "Malformed request.");
			}
        }
        else {
            if (!"".equals(query) && userId != null && etag != null && !"".equals(etag)) {

            	try {
                    String server = HttpGenerator.getServer(req);
                    String outEncoding = HttpGenerator.getEncoding(req);
                    AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");

                    ResourceJob resource = new ResourceJob();
                    resource.setData(query);
                    resource.setVersion(etag);
                    resource.setId(userId);

    				User scimUser = internalUserPatch(resource, server, outEncoding, authUser);
    				
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
        String etag = req.getHeader("ETag");

        log.trace("Trying to deleting user " + userId + ".");

        if (userId != null) {
            try {
                String server = HttpGenerator.getServer(req);
                String outEncoding = HttpGenerator.getEncoding(req);
                AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");

                ResourceJob resource = new ResourceJob();
                resource.setVersion(etag);
                resource.setId(userId);

				internalUserDelete(resource, server, outEncoding, authUser);
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
    
}
