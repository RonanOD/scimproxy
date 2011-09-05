package info.simplecloud.scimproxy.user;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import info.simplecloud.scimproxy.ScimUserServlet;
import info.simplecloud.scimproxy.config.Config;
import info.simplecloud.scimproxy.storage.IStorage;
import info.simplecloud.scimproxy.storage.dummy.DummyStorage;
import info.simplecloud.scimproxy.storage.dummy.UserNotFoundException;
import info.simplecloud.scimproxy.util.Util;

/**
 * Manages user storages and expose the storage with CRUD functions. Will be
 * extended in the future with plugin based and configurable storage selector.
 * This class can also in the future include caching, load balancing, and
 * pooling against different storages.
 */
public class User {
	
	private static final HashMap<String, User> USER_INSTANCES = new HashMap<String, User>();

	private static IStorage storage = null;
	
    private Log log = LogFactory.getLog(ScimUserServlet.class);

	private User(String sessionId) {
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
	public static User getInstance(String sessionId) {
		if(USER_INSTANCES.get(sessionId) == null) {
			USER_INSTANCES.put(sessionId, new User(sessionId));
		}
		return USER_INSTANCES.get(sessionId);
	}

	
	/**
	 * Deletes a user from the configured user storage.
	 * 
	 * @param user
	 *            User to delete.
	 * @throws UserNotFoundException
	 *             Thrown when user was not found in storage.
	 */
	public void deletetUser(info.simplecloud.core.User user) throws UserNotFoundException {
		deletetUser(user.getId());
	}

	/**
	 * Deletes a user using an id from the configured user storage.
	 * 
	 * @param userid
	 *            A unique and primary id of the user from the configured user
	 *            storage.
	 * @throws UserNotFoundException
	 *             Thrown when user was not found in storage.
	 */
	public void deletetUser(String userid) throws UserNotFoundException {
		storage.deleteUser(userid);
	}

	/**
	 * Returns a user from the configured user storage.
	 * 
	 * @param userid
	 *            A unique and primary id of the user from the configured user
	 *            storage.
	 * @return The found user.
	 * @throws UserNotFoundException
	 *             Thrown when user was not found in storage.
	 */
	public info.simplecloud.core.User getUser(String userId) throws UserNotFoundException {
		return storage.getUserForId(userId);
	}

	/**
	 * Creates a new user in the configured user storage.
	 * 
	 * @param user
	 *            User to be added.
	 */
	public void addUser(info.simplecloud.core.User user) {
		storage.addUser(user);
	}

	/**
	 * Updates a version number on the user in the configured user storage.
	 * 
	 * @param user
	 *            User to update version number on.
	 */
	public void updateVersionNumber(info.simplecloud.core.User user) {
		user.getMeta().setVersion(Util.generateVersionString());
	}

}
