package info.simplecloud.scimproxy;

import info.simplecloud.core.Group;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * To retrieve a known Resource, clients send GET requests to the Resource end
 * point; e.g., /User/{id}. This servlet is the /User end point.
 */

public class ScimResourceServlet extends RestServlet {

    private static final long serialVersionUID = -5875059636322733570L;

    protected Trigger trigger = new Trigger();
    
    private Log               log              = LogFactory.getLog(ScimResourceServlet.class);

    protected User internalUserPost(ResourceJob resource, String server, String encoding, AuthenticateUser authUser) throws UnknownEncoding, InvalidUser {
        User scimUser = new User(resource.getData(), encoding);
        
        if (scimUser.getMeta() == null) {
        	scimUser.setMeta(new Meta());
        }
        if(scimUser.getMeta().getVersion() == null || "".equals(scimUser.getMeta().getVersion())) {
        	scimUser.getMeta().setVersion(Util.generateVersionString());
        }
        scimUser.getMeta().setLocation(HttpGenerator.getLocation(scimUser, server));

        // add user to set ID
		UserDelegator.getInstance(authUser.getSessionId()).addUser(scimUser);

        // set location to object
        scimUser.getMeta().setLocation(HttpGenerator.getLocation(scimUser, server));

        // TODO: this is really not a nice way to get the Location into the meta data
        try {
        	UserDelegator.getInstance(authUser.getSessionId()).deletetUser(scimUser.getId());
		} catch (ResourceNotFoundException e) {
			// do nothing
		}
		
		UserDelegator.getInstance(authUser.getSessionId()).addUser(scimUser);
        
        // creating user in downstream CSP, any communication errors is handled in triggered and ignored here
        trigger.create(scimUser);

        return scimUser;
    }

    protected User internalUserPut(ResourceJob resource, String server, String encoding, AuthenticateUser authUser) throws UnknownEncoding, InvalidUser, ResourceNotFoundException, PreconditionException {

        User scimUser = new User(resource.getData().toString(), encoding);
        // verify that user have not been changed since latest get and this operation
        User oldUser = UserDelegator.getInstance(authUser.getSessionId()).getUser(resource.getId());
        
        // TODO: should return precondition exception if oldUser is not found or don't have a version.
        if(oldUser != null) {
        	if(oldUser.getMeta() != null) {
        		if(resource.getVersion() == null || !resource.getVersion().equals(oldUser.getMeta().getVersion())) {
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
    	meta.setLocation(HttpGenerator.getLocation(scimUser, server));

        scimUser.setMeta(meta);
        
        UserDelegator.getInstance(authUser.getSessionId()).replaceUser(resource.getId(), scimUser);
        
        // replacing user in downstream CSP, any communication errors is handled in triggered and ignored here
        trigger.put(scimUser);

        return scimUser;
    }

    
    protected User internalUserPatch(ResourceJob resource, String server, String encoding, AuthenticateUser authUser) throws UnknownEncoding, InvalidUser, ResourceNotFoundException, PreconditionException, UnknownAttribute {

        User scimUser = new User(resource.getData(), encoding);
        
        // verify that user have not been changed since latest get and this operation
        User oldUser = UserDelegator.getInstance(authUser.getSessionId()).getUser(resource.getId());
        
        // TODO: should return precondition exception if oldUser is not found or don't have a version.
        if(oldUser != null) {
        	if(oldUser.getMeta() != null) {
        		if(!resource.getVersion().equals(oldUser.getMeta().getVersion())) {
        			throw new PreconditionException();
        		}
        	}
        }

        // patch user
        scimUser.patch(resource.getData(), encoding);
        
        // set a new version number on the user that we are about to change
        Meta meta = scimUser.getMeta();
        if (meta == null) {
            meta = new Meta();
        }

        meta.setVersion(Util.generateVersionString());
    	meta.setLocation(HttpGenerator.getLocation(scimUser, server));

        scimUser.setMeta(meta);
        
        UserDelegator.getInstance(authUser.getSessionId()).replaceUser(resource.getId(), scimUser);
        
        // editing user in downstream CSP, any communication errors is handled in triggered and ignored here
        trigger.patch(new User(resource.getData(), encoding));

        return scimUser;
	}


    public void internalUserDelete(ResourceJob resource, String server, String encoding, AuthenticateUser authUser) throws ResourceNotFoundException, PreconditionException {
    	User scimUser = UserDelegator.getInstance(authUser.getSessionId()).getUser(resource.getId());

        String version = scimUser.getMeta().getVersion();
        if (resource.getVersion() != null && !"".equals(resource.getVersion()) && resource.getVersion().equals(version)) {
        	UserDelegator.getInstance(authUser.getSessionId()).deletetUser(resource.getId());
            // deleting user in downstream CSP, any communication errors is handled in triggered and ignored here
        	trigger.delete(scimUser);
        }
        else {
        	throw new PreconditionException();
        }
    }


    protected Group internalGroupPost(ResourceJob resource, String server, String encoding, AuthenticateUser authUser) throws UnknownEncoding, InvalidUser {

        Group scimGroup = new Group(resource.getData(), encoding);
        if (scimGroup.getMeta() == null) {
        	scimGroup.setMeta(new Meta());
        }
        if(scimGroup.getMeta().getVersion() == null || "".equals(scimGroup.getMeta().getVersion())) {
        	scimGroup.getMeta().setVersion(Util.generateVersionString());
        }
        scimGroup.getMeta().setLocation(HttpGenerator.getLocation(scimGroup, server));

        // add group to set ID
        UserDelegator.getInstance(authUser.getSessionId()).addGroup(scimGroup);

        // set location to object
        scimGroup.getMeta().setLocation(HttpGenerator.getLocation(scimGroup, server));

        // TODO: this is really not a nice way to get the Location into the meta data
        try {
			UserDelegator.getInstance(authUser.getSessionId()).deletetGroup(scimGroup);
			
		} catch (ResourceNotFoundException e) {
			// do nothing
		}
        UserDelegator.getInstance(authUser.getSessionId()).addGroup(scimGroup);

        // creating group in downstream CSP, any communication errors is handled in triggered and ignored here
        trigger.create(scimGroup);

        return scimGroup;
    }

    protected Group internalGroupPut(ResourceJob resource, String server, String encoding, AuthenticateUser authUser) throws UnknownEncoding, InvalidUser, ResourceNotFoundException, PreconditionException {

        Group scimGroup = new Group(resource.getData().toString(), encoding);

        // verify that group have not been changed since latest get and this operation
        Group oldGroup = UserDelegator.getInstance(authUser.getSessionId()).getGroup(resource.getId());
        
        // TODO: should return precondition exception if oldUser is not found or don't have a version.
        if(oldGroup != null) {
        	if(oldGroup.getMeta() != null) {
        		if(!resource.getVersion().equals(oldGroup.getMeta().getVersion())) {
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
    	meta.setLocation(HttpGenerator.getLocation(scimGroup, server));

    	scimGroup.setMeta(meta);
        
        UserDelegator.getInstance(authUser.getSessionId()).replaceGroup(resource.getId(), scimGroup);

        // replacing group in downstream CSP, any communication errors is handled in triggered and ignored here
        trigger.put(scimGroup);

        return scimGroup;
    }

    
    protected Group internalGroupPatch(ResourceJob resource, String server, String encoding, AuthenticateUser authUser) throws UnknownEncoding, InvalidUser, ResourceNotFoundException, PreconditionException, UnknownAttribute {
        Group scimGroup = new Group(resource.getData(), encoding);

        // verify that group have not been changed since latest get and this operation
        Group oldGroup = UserDelegator.getInstance(authUser.getSessionId()).getGroup(resource.getId());
        
        // TODO: should return precondition exception if oldUser is not found or don't have a version.
        if(oldGroup != null) {
        	if(oldGroup.getMeta() != null) {
        		if(!resource.getVersion().equals(oldGroup.getMeta().getVersion())) {
        			throw new PreconditionException();
        		}
        	}
        }

        // patch group
        scimGroup.patch(resource.getData(), encoding);
        // generate new version number
        UserDelegator.getInstance(authUser.getSessionId()).updateVersionNumber(scimGroup);

        
        // set a new version number on the user that we are about to change
        Meta meta = scimGroup.getMeta();
        if (meta == null) {
            meta = new Meta();
        }
        meta.setVersion(Util.generateVersionString());
    	meta.setLocation(HttpGenerator.getLocation(scimGroup, server));

    	scimGroup.setMeta(meta);

        UserDelegator.getInstance(authUser.getSessionId()).replaceGroup(resource.getId(), scimGroup);

        
        // editing group in downstream CSP, any communication errors is handled in triggered and ignored here
        trigger.patch(new Group(resource.getData(), encoding));

        return scimGroup;
	}

    public void internalGroupDelete(ResourceJob resource, String server, String encoding, AuthenticateUser authUser) throws ResourceNotFoundException, PreconditionException {

    	Group scimGroup = UserDelegator.getInstance(authUser.getSessionId()).getGroup(resource.getId());
        String version = scimGroup.getMeta().getVersion();
        if (resource.getVersion() != null && !"".equals(resource.getVersion()) && resource.getVersion().equals(version)) {
        	UserDelegator.getInstance(authUser.getSessionId()).deletetGroup(resource.getId());
            // deleting group in downstream CSP, any communication errors is handled in triggered and ignored here
        	trigger.delete(scimGroup);
        }
        else {
        	throw new PreconditionException();
        }
    }

    public void internalChangePasswordPatch(ResourceJob resource, String password, AuthenticateUser authUser) throws ResourceNotFoundException {
    	User scimUser = UserDelegator.getInstance(authUser.getSessionId()).getUser(resource.getId());
    	UserDelegator.getInstance(authUser.getSessionId()).setPassword(password, scimUser);
        // deleting group in downstream CSP, any communication errors is handled in triggered and ignored here
    	trigger.changePassword(scimUser, password);
    }
    
}
