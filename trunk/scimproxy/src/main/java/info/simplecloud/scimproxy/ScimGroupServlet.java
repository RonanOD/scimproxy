package info.simplecloud.scimproxy;

import info.simplecloud.core.Group;
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

@SuppressWarnings("serial")
public class ScimGroupServlet extends ScimGroupUpdatesServlet {
	
    private Log log = LogFactory.getLog(ScimGroupServlet.class);
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String groupId = Util.getGroupIdFromUri(req.getRequestURI());
        AuthenticateUser authUser = (AuthenticateUser) req.getAttribute("AuthUser");


        try {
            Group scimGroup = UserDelegator.getInstance(authUser.getSessionId()).getGroup(groupId);
            String groupStr = null;

            if (req.getParameter("attributes") != null) {
            	groupStr = scimGroup.getGroup(HttpGenerator.getEncoding(req), Util.getAttributeStringFromRequest(req));
            } else {
            	groupStr = scimGroup.getGroup(HttpGenerator.getEncoding(req));
            }

            if (groupStr == null) {
                HttpGenerator.badRequest(resp);
            } else {
                resp.setContentType(HttpGenerator.getContentType(req));
                resp.setHeader("ETag", scimGroup.getMeta().getVersion());

                HttpGenerator.ok(resp, groupStr);
                log.info("Returning group " + scimGroup.getId());
            }

        } catch (UnknownEncoding e) {
            HttpGenerator.serverError(resp);
        } catch (ResourceNotFoundException e) {
            HttpGenerator.notFound(resp);
        }
    }

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String query = Util.getContent(req);

        if (query != null && !"".equals(query)) {
            try {
            	Group scimGroup = internalPost(query, req);
            	
                resp.setContentType(HttpGenerator.getContentType(req));
                resp.setHeader("Location", scimGroup.getMeta().getLocation());
                resp.setHeader("ETag", scimGroup.getMeta().getVersion());

                log.info("Creating group " + scimGroup.getId());
                
                // TODO: creating user in downstream CSP, any communication errors is handled in triggered and ignored here
//                trigger.create(scimUser);				

                HttpGenerator.created(resp, scimGroup.getGroup(HttpGenerator.getEncoding(req)));
            } catch (UnknownEncoding e) {
                HttpGenerator.serverError(resp, "Unknown encoding.");
            } catch (InvalidUser e) {
                HttpGenerator.badRequest(resp, "Malformed user.");
            }
        } else {
            HttpGenerator.badRequest(resp);
        }	
	}

	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String groupId = Util.getGroupIdFromUri(req.getRequestURI());
        String query = Util.getContent(req);
        String etag = req.getHeader("ETag");
        
        if (query != null && !"".equals(query) && groupId != null) {

            try {
            	Group scimGroup = internalPut(groupId, etag, query, req);

                resp.setContentType(HttpGenerator.getContentType(req));
                resp.setHeader("Location", scimGroup.getMeta().getLocation());
                resp.setHeader("ETag", scimGroup.getMeta().getVersion());
                HttpGenerator.ok(resp, scimGroup.getGroup(HttpGenerator.getEncoding(req)));

                log.info("Replacing group " + scimGroup.getId());
            } catch (PreconditionException e) {
                HttpGenerator.preconditionFailed(resp, groupId);
            } catch (ResourceNotFoundException e) {
                HttpGenerator.notFound(resp);
            } catch (UnknownEncoding e) {
                HttpGenerator.badRequest(resp, "Unknown encoding.");
            } catch (InvalidUser e) {
                HttpGenerator.badRequest(resp, "Invalid group.");
            }

        } else {
            HttpGenerator.badRequest(resp, "Invalid group or group id.");
        }	
	}

	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String groupId = Util.getGroupIdFromUri(req.getRequestURI());
		log.trace("Trying to deleting group " + groupId + ".");
		
		if (groupId != null) {
		    try {
				internalDelete(groupId, req.getHeader("ETag"), req);
		        HttpGenerator.ok(resp);
		        log.info("Deleating group " + groupId + ".");
			} catch (ResourceNotFoundException e) {
			    HttpGenerator.notFound(resp);
			    log.trace("Group " + groupId + " is not found.");
		    }
		    catch (PreconditionException e) {
		    	HttpGenerator.preconditionFailed(resp, groupId);
		    }
		} else {
		    log.trace("Trying to delete a group that can't be found in storage with group id " + groupId + ".");
		    HttpGenerator.badRequest(resp, "Missing or malformed group id.");
		}	
	}

	public void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String groupId = Util.getGroupIdFromUri(req.getRequestURI());
        String query = Util.getContent(req);
        String etag = req.getHeader("ETag");
        
        if (!"".equals(query) && groupId != null && etag != null && !"".equals(etag)) {

        	try {
				Group scimGroup = internalPatch(groupId, etag, query, req);
				
                resp.setContentType(HttpGenerator.getContentType(req));
                resp.setHeader("Location", scimGroup.getMeta().getLocation());
                resp.setHeader("ETag", scimGroup.getMeta().getVersion());
                HttpGenerator.ok(resp, scimGroup.getGroup(HttpGenerator.getEncoding(req)));

                log.info("Patching user " + scimGroup.getId());
				
			} catch (UnknownEncoding e) {
                HttpGenerator.badRequest(resp, "Unknown encoding.");
			} catch (InvalidUser e) {
                HttpGenerator.badRequest(resp, "Invalid group.");
			} catch (ResourceNotFoundException e) {
                HttpGenerator.notFound(resp);
			} catch (PreconditionException e) {
                HttpGenerator.preconditionFailed(resp, groupId);
			} catch (UnknownAttribute e) {
                HttpGenerator.badRequest(resp, "Malformed group.");
			}
	    } else {
            HttpGenerator.badRequest(resp, "Missing group id or ETag");
	    }
 	}

}
