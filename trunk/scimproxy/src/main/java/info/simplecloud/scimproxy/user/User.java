package info.simplecloud.scimproxy.user;

import info.simplecloud.core.ScimUser;
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

	/**
	 * Holds just a static dummy user at the moment.
	 */
	private static DummyStorage storage = DummyStorage.getInstance();

	/**
	 * Deletes a user from the configured user storage.
	 * 
	 * @param user
	 *            User to delete.
	 * @throws UserNotFoundException
	 *             Thrown when user was not found in storage.
	 */
	public static void deletetUser(ScimUser user) throws UserNotFoundException {
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
	public static void deletetUser(String userid) throws UserNotFoundException {
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
	public static ScimUser getUser(String userId) throws UserNotFoundException {
		return storage.getUserForId(userId);
	}

	/**
	 * Creates a new user in the configured user storage.
	 * 
	 * @param user
	 *            User to be added.
	 */
	public static void addUser(ScimUser user) {
		storage.addUser(user);
	}

	/**
	 * Updates a version number on the user in the configured user storage.
	 * 
	 * @param user
	 *            User to update version number on.
	 */
	public static void updateVersionNumber(ScimUser user) {
		user.getMeta().setVersion(Util.generateVersionString());
	}

}
