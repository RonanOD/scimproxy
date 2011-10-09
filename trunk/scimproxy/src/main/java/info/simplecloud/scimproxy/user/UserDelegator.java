package info.simplecloud.scimproxy.user;

import info.simplecloud.core.Group;
import info.simplecloud.core.Resource;
import info.simplecloud.core.User;
import info.simplecloud.scimproxy.config.Config;
import info.simplecloud.scimproxy.storage.IStorage;
import info.simplecloud.scimproxy.storage.dummy.DummyStorage;
import info.simplecloud.scimproxy.storage.dummy.ResourceNotFoundException;
import info.simplecloud.scimproxy.util.Util;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Manages user storages and expose the storage with CRUD functions. Will be
 * extended in the future with plugin based and configurable storage selector.
 * This class can also in the future include caching, load balancing, and
 * pooling against different storages.
 */
public class UserDelegator {

    private static final HashMap<String, UserDelegator> USER_INSTANCES = new HashMap<String, UserDelegator>();

	private static IStorage storage = null;
	
    private Log log = LogFactory.getLog(UserDelegator.class);

    
	private UserDelegator(String sessionId) {
		// read storage type from config
		if("dummy".equalsIgnoreCase(Config.getInstance().getStorageType())) {
			storage = DummyStorage.getInstance(sessionId);
			log.info("Dummy storage configured.");
		}
		else {
			log.fatal("No storage configured.");
		}
	}
	
	/**
	 * Returns singleton value for the config.
	 * 
	 * @return
	 */
	public static UserDelegator getInstance(String sessionId) {
		if(USER_INSTANCES.get(sessionId) == null) {
			USER_INSTANCES.put(sessionId, new UserDelegator(sessionId));
        }
        return USER_INSTANCES.get(sessionId);
	}

	
	/**
	 * Deletes a user from the configured user storage.
	 * 
	 * @param user
	 *            User to delete.
	 * @throws ResourceNotFoundException
	 *             Thrown when user was not found in storage.
	 */
	public void deletetUser(User user) throws ResourceNotFoundException {
		deletetUser(user.getId());
	}

	/**
	 * Deletes a user using an id from the configured user storage.
	 * 
	 * @param userid
	 *            A unique and primary id of the user from the configured user
	 *            storage.
	 * @throws ResourceNotFoundException
	 *             Thrown when user was not found in storage.
	 */
	public void deletetUser(String userid) throws ResourceNotFoundException {
		storage.deleteUser(userid);
	}

	/**
	 * Returns a user from the configured user storage.
	 * 
	 * @param userid
	 *            A unique and primary id of the user from the configured user
	 *            storage.
	 * @return The found user.
	 * @throws ResourceNotFoundException
	 *             Thrown when user was not found in storage.
	 */
	public User getUser(String userId) throws ResourceNotFoundException {
		return storage.getUserForId(userId);
	}

	/**
	 * Creates a new user in the configured user storage.
	 * 
	 * @param user
	 *            User to be added.
	 */
	public void addUser(User user) {
		storage.addUser(user);
	}

	/**
	 * Creates a new user in the configured user storage.
	 * 
	 * @param user
	 *            User to be added.
	 */
	public void setPassword(String clearTextPassword, User user) {
		storage.setPassword(clearTextPassword, user);
	}

	/**
	 * Updates a version number on the user in the configured user storage.
	 * 
	 * @param user
	 *            User to update version number on.
	 */
	public void updateVersionNumber(Resource resource) {
		resource.getMeta().setVersion(Util.generateVersionString());
	}

	/**
	 * Returns a group from the configured user storage.
	 * 
	 * @param groupId
	 *            A unique and primary id of the group from the configured user
	 *            storage.
	 * @return The found group.
	 * @throws ResourceNotFoundException
	 *             Thrown when group was not found in storage.
	 */
	public Group getGroup(String groupId) throws ResourceNotFoundException {
		return storage.getGroupForId(groupId);
	}

	/**
	 * Creates a new group in the configured user storage.
	 * 
	 * @param group
	 *            Group to be added.
	 */
	public void addGroup(Group group) {
		storage.addGroup(group);
	}	
	
	
	/**
	 * Deletes a group from the configured user storage.
	 * 
	 * @param group
	 *            Group to delete.
	 * @throws ResourceNotFoundException
	 *             Thrown when user was not found in storage.
	 */
	public void deletetGroup(Group group) throws ResourceNotFoundException {
		deletetGroup(group.getId());
	}

	/**
	 * Deletes a group using an id from the configured user storage.
	 * 
	 * @param groupId
	 *            A unique and primary id of the group from the configured user
	 *            storage.
	 * @throws ResourceNotFoundException
	 *             Thrown when group was not found in storage.
	 */
	public void deletetGroup(String groupId) throws ResourceNotFoundException {
		storage.deleteGroup(groupId);
	}
	
	
}
