package info.simplecloud.scimproxy;

import info.simplecloud.core.Group;
import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.core.exceptions.UnknownEncoding;
import info.simplecloud.core.types.Meta;
import info.simplecloud.scimproxy.authentication.AuthenticateUser;
import info.simplecloud.scimproxy.config.Config;
import info.simplecloud.scimproxy.exception.PreconditionException;
import info.simplecloud.scimproxy.storage.ResourceNotFoundException;
import info.simplecloud.scimproxy.storage.StorageDelegator;
import info.simplecloud.scimproxy.trigger.Trigger;
import info.simplecloud.scimproxy.util.Util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * To retrieve a known Resource, clients send GET requests to the Resource end
 * point; e.g., /Users/{id}. This servlet is the /Users end point.
 */

public class ScimResourceServlet extends RestServlet {

    private static final long serialVersionUID = -5875059636322733570L;

    protected Trigger trigger = new Trigger();
    
    private Log               log              = LogFactory.getLog(ScimResourceServlet.class);

    
    protected StorageDelegator getUserDelegator(String session) {
    	return StorageDelegator.getInstance(session);
    }

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
		getUserDelegator(authUser.getSessionId()).addUser(scimUser);
        
        // creating user in downstream CSP, any communication errors is handled in triggered and ignored here
        trigger.create(scimUser);

        log.trace("Created user " + scimUser);
        return scimUser;
    }

    protected User internalUserPut(ResourceJob resource, String server, String encoding, AuthenticateUser authUser) throws UnknownEncoding, InvalidUser, ResourceNotFoundException, PreconditionException {

        User scimUser = new User(resource.getData().toString(), encoding);
        // verify that user have not been changed since latest get and this operation

        if(Config.getInstance().isForceEtags()) {
            User oldUser = null;
    		try {
    			oldUser = getUserDelegator(authUser.getSessionId()).getUser(resource.getId());
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            
            // TODO: should return precondition exception if oldUser is not found or don't have a version.
            if(oldUser != null) {
            	if(oldUser.getMeta() != null) {
            		if(resource.getVersion() == null || !resource.getVersion().equals(oldUser.getMeta().getVersion())) {
            			throw new PreconditionException();
            		}
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
        
        getUserDelegator(authUser.getSessionId()).replaceUser(resource.getId(), scimUser);
        
        // replacing user in downstream CSP, any communication errors is handled in triggered and ignored here
        trigger.put(scimUser);

        log.trace("Replaced user " + scimUser);

        return scimUser;
    }

    
    protected User internalUserPatch(ResourceJob resource, String server, String encoding, AuthenticateUser authUser) throws UnknownEncoding, InvalidUser, ResourceNotFoundException, PreconditionException, UnknownAttribute {

        // verify that user have not been changed since latest get and this operation
    	User oldUser = null;
		try {
			oldUser = getUserDelegator(authUser.getSessionId()).getUser(resource.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(Config.getInstance().isForceEtags()) {
	        // TODO: should return precondition exception if oldUser is not found or don't have a version.
	        if(oldUser != null) {
	        	if(oldUser.getMeta() != null) {
	        		if(!resource.getVersion().equals(oldUser.getMeta().getVersion())) {
	        			throw new PreconditionException();
	        		}
	        	}
	        }
	   }

        // patch user
        oldUser.patch(resource.getData(), encoding);
        
        // set a new version number on the user that we are about to change
        Meta meta = oldUser.getMeta();
        if (meta == null) {
            meta = new Meta();
        }

        meta.setVersion(Util.generateVersionString());
    	meta.setLocation(HttpGenerator.getLocation(oldUser, server));

    	oldUser.setMeta(meta);
        
        getUserDelegator(authUser.getSessionId()).replaceUser(resource.getId(), oldUser);
        
        // editing user in downstream CSP, any communication errors is handled in triggered and ignored here
        trigger.patch(new User(resource.getData(), encoding));

        log.trace("Updated user " + oldUser);

        return oldUser;
	}


    public void internalUserDelete(ResourceJob resource, String server, String encoding, AuthenticateUser authUser) throws ResourceNotFoundException, PreconditionException {
    	User scimUser = null;
		try {
			scimUser = getUserDelegator(authUser.getSessionId()).getUser(resource.getId());
		} catch (Exception e) {
			throw new ResourceNotFoundException();
		}

		if(Config.getInstance().isForceEtags()) {
	        String version = scimUser.getMeta().getVersion();
	        if (resource.getVersion() != null && !"".equals(resource.getVersion()) && resource.getVersion().equals(version)) {
	        	getUserDelegator(authUser.getSessionId()).deletetUser(resource.getId());
	            // deleting user in downstream CSP, any communication errors is handled in triggered and ignored here
	        	trigger.delete(scimUser);
	        }
	        else {
	        	throw new PreconditionException();
	        }
		}
		else {
        	getUserDelegator(authUser.getSessionId()).deletetUser(resource.getId());
            // deleting user in downstream CSP, any communication errors is handled in triggered and ignored here
        	trigger.delete(scimUser);
		}
		
        log.trace("Deleted user " + scimUser);
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
        getUserDelegator(authUser.getSessionId()).addGroup(scimGroup);

        // creating group in downstream CSP, any communication errors is handled in triggered and ignored here
        trigger.create(scimGroup);

        log.trace("Created group " + scimGroup);

        return scimGroup;
    }

    protected Group internalGroupPut(ResourceJob resource, String server, String encoding, AuthenticateUser authUser) throws UnknownEncoding, InvalidUser, ResourceNotFoundException, PreconditionException {

        Group scimGroup = new Group(resource.getData().toString(), encoding);

		if(Config.getInstance().isForceEtags()) {
	        // verify that group have not been changed since latest get and this operation
	        Group oldGroup = getUserDelegator(authUser.getSessionId()).getGroup(resource.getId());
	        
	        // TODO: should return precondition exception if oldUser is not found or don't have a version.
	        if(oldGroup != null) {
	        	if(oldGroup.getMeta() != null || resource.getVersion() != null) {
	        		if(resource.getVersion() == null || !resource.getVersion().equals(oldGroup.getMeta().getVersion())) {
	        			throw new PreconditionException();
	        		}
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
        
    	getUserDelegator(authUser.getSessionId()).replaceGroup(resource.getId(), scimGroup);

        // replacing group in downstream CSP, any communication errors is handled in triggered and ignored here
        trigger.put(scimGroup);

        log.trace("Replace group " + scimGroup);

        return scimGroup;
    }

    
    protected Group internalGroupPatch(ResourceJob resource, String server, String encoding, AuthenticateUser authUser) throws UnknownEncoding, InvalidUser, ResourceNotFoundException, PreconditionException, UnknownAttribute {
        
        // verify that group have not been changed since latest get and this operation
        Group oldGroup = getUserDelegator(authUser.getSessionId()).getGroup(resource.getId());

        if(Config.getInstance().isForceEtags()) {
	        // TODO: should return precondition exception if oldUser is not found or don't have a version.
	        if(oldGroup != null) {
	        	if(oldGroup.getMeta() != null) {
	        		if(!resource.getVersion().equals(oldGroup.getMeta().getVersion())) {
	        			throw new PreconditionException();
	        		}
	        	}
	        }
		}
		
        // patch group
        oldGroup.patch(resource.getData(), encoding);
        // generate new version number
        getUserDelegator(authUser.getSessionId()).updateVersionNumber(oldGroup);

        
        // set a new version number on the user that we are about to change
        Meta meta = oldGroup.getMeta();
        if (meta == null) {
            meta = new Meta();
        }
        meta.setVersion(Util.generateVersionString());
    	meta.setLocation(HttpGenerator.getLocation(oldGroup, server));

    	oldGroup.setMeta(meta);

    	getUserDelegator(authUser.getSessionId()).replaceGroup(resource.getId(), oldGroup);

        
        // editing group in downstream CSP, any communication errors is handled in triggered and ignored here
        trigger.patch(new Group(resource.getData(), encoding));

        log.trace("Update group " + oldGroup);

        return oldGroup;
	}

    public void internalGroupDelete(ResourceJob resource, String server, String encoding, AuthenticateUser authUser) throws ResourceNotFoundException, PreconditionException {

    	Group scimGroup = getUserDelegator(authUser.getSessionId()).getGroup(resource.getId());
    	
		if(Config.getInstance().isForceEtags()) {
	        String version = scimGroup.getMeta().getVersion();
	        if (resource.getVersion() != null && !"".equals(resource.getVersion()) && resource.getVersion().equals(version)) {
	        	getUserDelegator(authUser.getSessionId()).deletetGroup(resource.getId());
	            // deleting group in downstream CSP, any communication errors is handled in triggered and ignored here
	        	trigger.delete(scimGroup);
	        }
	        else {
	        	throw new PreconditionException();
	        }
		}
		else {
        	getUserDelegator(authUser.getSessionId()).deletetGroup(resource.getId());
            // deleting group in downstream CSP, any communication errors is handled in triggered and ignored here
        	trigger.delete(scimGroup);
		}
        
        log.trace("Removed group " + scimGroup);
    }

    public void internalChangePasswordPatch(ResourceJob resource, String password, AuthenticateUser authUser) throws ResourceNotFoundException {
    	User scimUser = null;
		try {
			scimUser = getUserDelegator(authUser.getSessionId()).getUser(resource.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getUserDelegator(authUser.getSessionId()).setPassword(password, scimUser);
        // deleting group in downstream CSP, any communication errors is handled in triggered and ignored here
    	trigger.changePassword(scimUser, password);
    	
        log.trace("Changed password for user " + scimUser);

    }
    
}
