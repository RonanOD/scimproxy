package info.simplecloud.scimproxy.storage;

import info.simplecloud.core.User;
import info.simplecloud.scimproxy.storage.dummy.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Base interface for all storages that's supported in the system.
 */
public interface IStorage {

	/**
	 * Returns a ScimUser from the storage.
	 * 
	 * @param id
	 *            Unique identifier for the User as defined by the Service
	 *            Provider.
	 * @return If id is found in storage then the fully populated SCIM user
	 *         object is returned. If it's not found or if id is empty then null
	 *         is returned.
	 * @throws UserNotFoundException
	 */
	public User getUserForId(String id) throws UserNotFoundException;

	/**
	 * Adds a user in the user storage.
	 * 
	 * @param user
	 *            A fully populated SCIM user.
	 */
	public void addUser(User user);

	/**
	 * Returns all SCIM users in the storage.
	 * 
	 * @return All users. ArrayList with size 0 if empty.
	 */
	public List<User> getList();

	public List<User> getList(String sortBy, String sortOrder);

	/**
	 * Deletes a SCIM user from storage.
	 * 
	 * @param id
	 *            The id for the user to be deleted from storage.
	 * @throws UserNotFoundException
	 *             Thrown when user was not found in database.
	 */
	public void deleteUser(String id) throws UserNotFoundException;

	
	
	ArrayList<User> getList(String sortBy, String sortOrder, String filterBy, String filterValue, String filterOp);

	void addList(List<User> upstreamUsers);

}
