package info.simplecloud.scimproxy.storage.dummy;

import info.simplecloud.core.ComplexTypeComparator;
import info.simplecloud.core.Group;
import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.scimproxy.storage.IStorage;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * A dummy storage. Holds users in an in memory, non persistent, database. When
 * initiated first time it populates it self with two users.
 */
public class DummyStorage implements IStorage {

    private static final HashMap<String, DummyStorage> STORAGE_INSTANCES = new HashMap<String, DummyStorage>();

    // TODO: synchronize users object
    private ArrayList<User>           users    = new ArrayList<User>();

    private ArrayList<Group>           groups    = new ArrayList<Group>();

    /**
     * Constructor. Adds two users to storage.
     */
    private DummyStorage() {
   
    }

    /**
     * Returns singleton value for the storage.
     * 
     * @return
     */
    public static DummyStorage getInstance( String sessionId) {
        if(STORAGE_INSTANCES.get(sessionId) == null)
        {
        	STORAGE_INSTANCES.put(sessionId, new DummyStorage());
        }
        return STORAGE_INSTANCES.get(sessionId);
    }

    @Override
    public User getUserForId(String id) throws ResourceNotFoundException {
        User scimUser = null;
        if (users != null && !"".equals(id)) {
            for (User user : users) {
                try {
                    if (id.equals(user.getAttribute("id"))) {
                        scimUser = user;
                        break;
                    }
                } catch (UnknownAttribute e) {
                    throw new RuntimeException("Internal error", e);
                }
            }
        }
        if (scimUser == null) {
            throw new ResourceNotFoundException();
        }
        return scimUser;

    }

    @Override
    public void addUser(User user) {
		// TODO: Verify that id is not already there.
        if (user.getId() == null) {
            user.setId(generateId());
        }
        users.add(user);
    }

    @Override
    public void addList(List<User> upstreamUsers) {
        users.addAll(upstreamUsers);
    }

    @Override
    public ArrayList<User> getList() {
        ArrayList<User> list = new ArrayList<User>();
        for (User user : users) {
            list.add(user);
        }
        return list;
    }

    @Override
    public ArrayList<User> getList(String sortBy, String sortOrder) {
        ArrayList<User> list = new ArrayList<User>();
        for (User user : users) {
            list.add(user);
        }
        Collections.sort(list, new ComplexTypeComparator(sortBy, sortOrder.equalsIgnoreCase("ascending")));
        return list;
    }

    @Override
    public ArrayList<User> getList(String sortBy, String sortOrder, String filterBy, String filterValue, String filterOp) {
        ArrayList<User> list = new ArrayList<User>();
        for (User user : users) {

            // TODO: Do a way better and nicer looking filtering function.
            /*
             * equals: the two values must be identical equalsIgnoreCase: the
             * two values must be identical contains: the entire filterValue
             * must be a substring of the attribute value. startswith: the
             * entire filterValue must be a substring of the attribute value,
             * starting at the beginning of the attribute value. This criterion
             * is satisfied if the two strings are equal. present: if the
             * attribute specified by filterBy has a non-empty value, or if it
             * contains a non-empty node for complex attributes there is a
             * match.
             */
            boolean add = false;
            String value = null;
            // TODO: add support for complex and plural types
            String methodName = "get" + filterBy.substring(0, 1).toUpperCase() + filterBy.substring(1);
            try {
                // TODO this is not necessary use the getAttribute method
                // instead
                Method method = User.class.getMethod(methodName);
                value = (String) method.invoke(user);
            } catch (Exception e) {
                break;
            }

            if (value == null) {
                value = "";
            }

            if ("equals".equals(filterOp)) {
                if (value.equals(filterValue)) {
                    add = true;
                }
            }

            if ("equalsIgnoreCase".equals(filterOp)) {
                if (value.equalsIgnoreCase(filterValue)) {
                    add = true;
                }
            }

            if ("contains".equals(filterOp)) {
                if (value.indexOf(filterValue) > 0) {
                    add = true;
                }
            }

            if ("startsWith".equals(filterOp)) {
                if (value.indexOf(filterValue) > -1) {
                    add = true;
                }
            }

            if ("present".equals(filterOp)) {
                if (!"".equals(value)) {
                    add = true;
                }
            }

            if (add) {
                list.add(user);
            }
        }

        Collections.sort(list, new ComplexTypeComparator(sortBy, sortOrder.equalsIgnoreCase("ascending")));

        return list;
    }

    @Override
    public void deleteUser(String id) throws ResourceNotFoundException {
        boolean found = false;
        if (id != null && !"".equals(id.trim())) {
            for (int i = 0; i < users.size(); i++) {
                if (id.equals(users.get(i).getId())) {
                    users.remove(i);
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            throw new ResourceNotFoundException();
        }
    }

    private SecureRandom random = new SecureRandom();

    private String generateId() {
        return new BigInteger(130, random).toString(32);
    }

	@Override
	public Group getGroupForId(String groupId) throws ResourceNotFoundException {
		Group scimGroup= null;
        if (groups != null && !"".equals(groupId)) {
            for (Group group : groups) {
                try {
                    if (groupId.equals(group.getAttribute("id"))) {
                        scimGroup = group;
                        break;
                    }
                } catch (UnknownAttribute e) {
                    throw new RuntimeException("Internal error", e);
                }
            }
        }
        if (scimGroup == null) {
            throw new ResourceNotFoundException();
        }
        return scimGroup;

	}

	@Override
	public void addGroup(Group group) {
		// TODO: Verify that id is not already there.
        if (group.getId() == null) {
        	group.setId(generateId());
        }
        groups.add(group);
	}

    @Override
    public void deleteGroup(String id) throws ResourceNotFoundException {
        boolean found = false;
        if (id != null && !"".equals(id.trim())) {
            for (int i = 0; i < groups.size(); i++) {
                if (id.equals(groups.get(i).getId())) {
                    groups.remove(i);
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            throw new ResourceNotFoundException();
        }
    }
	
}
