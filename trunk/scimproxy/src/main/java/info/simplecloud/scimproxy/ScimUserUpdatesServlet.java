package info.simplecloud.scimproxy;

import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.core.exceptions.UnknownEncoding;
import info.simplecloud.core.types.Meta;
import info.simplecloud.scimproxy.authentication.AuthenticateUser;
import info.simplecloud.scimproxy.exception.PreconditionException;
import info.simplecloud.scimproxy.storage.dummy.ResourceNotFoundException;
import info.simplecloud.scimproxy.trigger.Trigger;
import info.simplecloud.scimproxy.user.UserDelegator;
import info.simplecloud.scimproxy.util.Util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

/**
 * To retrieve a known Resource, clients send GET requests to the Resource end
 * point; e.g., /User/{id}. This servlet is the /User end point.
 */

public class ScimUserUpdatesServlet extends RestServlet {

    private static final long serialVersionUID = -5875059636322733570L;

    protected Trigger trigger = new Trigger();
    
    protected User internalPost(String query, HttpServletRequest req) throws UnknownEncoding, InvalidUser {
        User scimUser = new User(query, HttpGenerator.getEncoding(req));
        AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");
        
        if (scimUser.getMeta() == null) {
        	scimUser.setMeta(new Meta());
        }
        if(scimUser.getMeta().getVersion() == null || "".equals(scimUser.getMeta().getVersion())) {
        	scimUser.getMeta().setVersion(Util.generateVersionString());
        }
        scimUser.getMeta().setLocation(HttpGenerator.getUserLocation(scimUser, req));

        // add user to set ID
		UserDelegator.getInstance(authUser.getSessionId()).addUser(scimUser);

        // set location to object
        scimUser.getMeta().setLocation(HttpGenerator.getUserLocation(scimUser, req));

        // TODO: this is really not a nice way to get the Location into the meta data
        try {
        	UserDelegator.getInstance(authUser.getSessionId()).deletetUser(scimUser.getId());
		} catch (ResourceNotFoundException e) {
			// do nothing
		}
		
		UserDelegator.getInstance(authUser.getSessionId()).addUser(scimUser);
        
        // TODO:   trigger.post(...);				

        return scimUser;
    }

    protected User internalPut(String userId, String etag, String query, HttpServletRequest req) throws UnknownEncoding, InvalidUser, ResourceNotFoundException, PreconditionException {

        User scimUser = new User(query, HttpGenerator.getEncoding(req));
        AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");
        
        // verify that user have not been changed since latest get and this operation
        User oldUser = UserDelegator.getInstance(authUser.getSessionId()).getUser(userId);
        
        // TODO: should return precondition exception if oldUser is not found or don't have a version.
        if(oldUser != null) {
        	if(oldUser.getMeta() != null) {
        		if(!etag.equals(oldUser.getMeta().getVersion())) {
        			throw new PreconditionException();
        		}
        	}
        }

        // set a new version number on the user that we are about to change
        Meta meta = scimUser.getMeta();
        if (meta == null) {
            meta = new Meta();
        }
        meta.setVersion(Util.generateVersionString());
    	meta.setLocation(HttpGenerator.getUserLocation(scimUser, req));

        scimUser.setMeta(meta);
        
        // delete old user
        UserDelegator.getInstance(authUser.getSessionId()).deletetUser(userId);

        // add new user
        UserDelegator.getInstance(authUser.getSessionId()).addUser(scimUser);
        
        // creating user in downstream CSP, any communication errors is handled in triggered and ignored here
        // TODO:   trigger.put(query, userId, etag);				

        return scimUser;
    }

    
    protected User internalPatch(String userId, String etag, String query, HttpServletRequest req) throws UnknownEncoding, InvalidUser, ResourceNotFoundException, PreconditionException, UnknownAttribute {

        User scimUser = new User(query, HttpGenerator.getEncoding(req));
        AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");
        
        // verify that user have not been changed since latest get and this operation
        User oldUser = UserDelegator.getInstance(authUser.getSessionId()).getUser(userId);
        
        // TODO: should return precondition exception if oldUser is not found or don't have a version.
        if(oldUser != null) {
        	if(oldUser.getMeta() != null) {
        		if(!etag.equals(oldUser.getMeta().getVersion())) {
        			throw new PreconditionException();
        		}
        	}
        }

        // patch user
        scimUser.patch(query, HttpGenerator.getEncoding(req));
        // generate new version number
        UserDelegator.getInstance(authUser.getSessionId()).updateVersionNumber(scimUser);
        
        // set a new version number on the user that we are about to change
        Meta meta = scimUser.getMeta();
        if (meta == null) {
            meta = new Meta();
        }
        meta.setVersion(Util.generateVersionString());
    	meta.setLocation(HttpGenerator.getUserLocation(scimUser, req));

        scimUser.setMeta(meta);
        
        // delete old user
        UserDelegator.getInstance(authUser.getSessionId()).deletetUser(userId);

        // add new user
        UserDelegator.getInstance(authUser.getSessionId()).addUser(scimUser);
        
        // creating user in downstream CSP, any communication errors is handled in triggered and ignored here
        // TODO:   trigger.put(query, userId, etag);				

        return scimUser;
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
     * @throws ResourceNotFoundException 
     * @throws PreconditionException 
     */
    public void internalDelete(String userId, String etag, HttpServletRequest req) throws ResourceNotFoundException, PreconditionException {

    	AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");
    	User scimUser = UserDelegator.getInstance(authUser.getSessionId()).getUser(userId);

        String version = scimUser.getMeta().getVersion();
        if (etag != null && !"".equals(etag) && etag.equals(version)) {
        	UserDelegator.getInstance(authUser.getSessionId()).deletetUser(userId);
            // creating user in downstream CSP, any communication errors is handled in triggered and ignored here
        	// TODO: trigger
        	//trigger.delete(scimUser);				
        }
        else {
        	throw new PreconditionException();
        }
    }


}
