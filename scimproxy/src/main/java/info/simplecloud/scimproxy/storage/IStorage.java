package info.simplecloud.scimproxy.storage;

import info.simplecloud.core.Group;
import info.simplecloud.core.User;

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
	 * @throws ResourceNotFoundException
	 */
	public User getUserForId(String id) throws ResourceNotFoundException;

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
	public ArrayList<User> getUserList();

	public ArrayList<User> getUserList(String sortBy, String sortOrder, int index, int count);

	/**
	 * Deletes a SCIM user from storage.
	 * 
	 * @param id
	 *            The id for the user to be deleted from storage.
	 * @throws ResourceNotFoundException
	 *             Thrown when user was not found in database.
	 */
	public void deleteUser(String id) throws ResourceNotFoundException;

	
	
	ArrayList<User> getUserList(String sortBy, String sortOrder, String filter, int index, int count);
	ArrayList<Group> getGroupList(String sortBy, String sortOrder, String filter, int index, int count);

	void addList(List<User> upstreamUsers);

	public Group getGroupForId(String groupId) throws ResourceNotFoundException;

	public void addGroup(Group group);

	public void deleteGroup(String id) throws ResourceNotFoundException;

	ArrayList<Group> getGroupList();

	ArrayList<Group> getGroupList(String sortBy, String sortOrder, int index, int count);

	public void setPassword(String clearTextPassword, User user);

	public void replaceUser(String id, User scimUser) throws ResourceNotFoundException;

	public void replaceGroup(String id, Group scimGroup) throws ResourceNotFoundException;

}
