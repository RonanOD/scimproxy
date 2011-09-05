package info.simplecloud.scimproxy;

import info.simplecloud.core.Group;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.UnknownEncoding;
import info.simplecloud.scimproxy.authentication.AuthenticateUser;
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
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}

	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}

	public void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}

}
