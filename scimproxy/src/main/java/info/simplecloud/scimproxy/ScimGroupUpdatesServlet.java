package info.simplecloud.scimproxy;

import info.simplecloud.core.Group;
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


public class ScimGroupUpdatesServlet extends RestServlet {

	private static final long serialVersionUID = -5868027899663275730L;

    protected Trigger trigger = new Trigger();
    
    protected Group internalPost(String query, HttpServletRequest req) throws UnknownEncoding, InvalidUser {
        AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");

        Group scimGroup = new Group(query, HttpGenerator.getEncoding(req));
        if (scimGroup.getMeta() == null) {
        	scimGroup.setMeta(new Meta());
        }
        if(scimGroup.getMeta().getVersion() == null || "".equals(scimGroup.getMeta().getVersion())) {
        	scimGroup.getMeta().setVersion(Util.generateVersionString());
        }
        scimGroup.getMeta().setLocation(HttpGenerator.getGroupLocation(scimGroup, req));

        // add group to set ID
        UserDelegator.getInstance(authUser.getSessionId()).addGroup(scimGroup);

        // set location to object
        scimGroup.getMeta().setLocation(HttpGenerator.getGroupLocation(scimGroup, req));

        // TODO: this is really not a nice way to get the Location into the meta data
        try {
			UserDelegator.getInstance(authUser.getSessionId()).deletetGroup(scimGroup);
			
		} catch (ResourceNotFoundException e) {
			// do nothing
		}
        UserDelegator.getInstance(authUser.getSessionId()).addGroup(scimGroup);

        // TODO:   trigger.post(...);				

        return scimGroup;
    }

    protected Group internalPut(String groupId, String etag, String query, HttpServletRequest req) throws UnknownEncoding, InvalidUser, ResourceNotFoundException, PreconditionException {
        AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");

        Group scimGroup = new Group(query, HttpGenerator.getEncoding(req));

        // verify that group have not been changed since latest get and this operation
        Group oldGroup = UserDelegator.getInstance(authUser.getSessionId()).getGroup(groupId);
        
        // TODO: should return precondition exception if oldUser is not found or don't have a version.
        if(oldGroup != null) {
        	if(oldGroup.getMeta() != null) {
        		if(!etag.equals(oldGroup.getMeta().getVersion())) {
        			throw new PreconditionException();
        		}
        	}
        }

        // set a new version number on the user that we are about to change
        Meta meta = scimGroup.getMeta();
        if (meta == null) {
            meta = new Meta();
        }
        meta.setVersion(Util.generateVersionString());
    	meta.setLocation(HttpGenerator.getGroupLocation(scimGroup, req));

    	scimGroup.setMeta(meta);
        
        // delete old user
        UserDelegator.getInstance(authUser.getSessionId()).deletetGroup(groupId);

        // add new user
        UserDelegator.getInstance(authUser.getSessionId()).addGroup(scimGroup);
        
        // creating user in downstream CSP, any communication errors is handled in triggered and ignored here
        // TODO:   trigger.put(query, userId, etag);				

        return scimGroup;
    }

    
    protected Group internalPatch(String groupId, String etag, String query, HttpServletRequest req) throws UnknownEncoding, InvalidUser, ResourceNotFoundException, PreconditionException, UnknownAttribute {
        AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");

        Group scimGroup = new Group(query, HttpGenerator.getEncoding(req));

        // verify that group have not been changed since latest get and this operation
        Group oldGroup = UserDelegator.getInstance(authUser.getSessionId()).getGroup(groupId);
        
        // TODO: should return precondition exception if oldUser is not found or don't have a version.
        if(oldGroup != null) {
        	if(oldGroup.getMeta() != null) {
        		if(!etag.equals(oldGroup.getMeta().getVersion())) {
        			throw new PreconditionException();
        		}
        	}
        }

        // patch group
        scimGroup.patch(query, HttpGenerator.getEncoding(req));
        // generate new version number
        UserDelegator.getInstance(authUser.getSessionId()).updateVersionNumber(scimGroup);

        
        // set a new version number on the user that we are about to change
        Meta meta = scimGroup.getMeta();
        if (meta == null) {
            meta = new Meta();
        }
        meta.setVersion(Util.generateVersionString());
    	meta.setLocation(HttpGenerator.getGroupLocation(scimGroup, req));

    	scimGroup.setMeta(meta);
        
        // delete old user
        UserDelegator.getInstance(authUser.getSessionId()).deletetGroup(groupId);

        // add new user
        UserDelegator.getInstance(authUser.getSessionId()).addGroup(scimGroup);
        
        // creating user in downstream CSP, any communication errors is handled in triggered and ignored here
        // TODO:   trigger.put(query, userId, etag);				

        return scimGroup;
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
    public void internalDelete(String groupId, String etag, HttpServletRequest req) throws ResourceNotFoundException, PreconditionException {
        AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");

    	Group scimGroup = UserDelegator.getInstance(authUser.getSessionId()).getGroup(groupId);
        String version = scimGroup.getMeta().getVersion();
        if (etag != null && !"".equals(etag) && etag.equals(version)) {
        	UserDelegator.getInstance(authUser.getSessionId()).deletetGroup(groupId);
            // creating user in downstream CSP, any communication errors is handled in triggered and ignored here
        	// TODO: trigger
        	//trigger.delete(scimUser);				
        }
        else {
        	throw new PreconditionException();
        }
    }


}
