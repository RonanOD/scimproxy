package info.simplecloud.scimproxy.storage.dummy;

import info.simplecloud.core.ComplexTypeComparator;
import info.simplecloud.core.Group;
import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.scimproxy.storage.IStorage;
import info.simplecloud.scimproxy.storage.ResourceNotFoundException;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A dummy storage. Holds users in an in memory, non persistent, database. When
 * initiated first time it populates it self with two users.
 */
public class DummyStorage implements IStorage {

    private Log                                        log               = LogFactory.getLog(DummyStorage.class);

    private static final HashMap<String, DummyStorage> STORAGE_INSTANCES = new HashMap<String, DummyStorage>();

    // TODO: synchronize users object
    private ArrayList<User>                            users             = new ArrayList<User>();

    private ArrayList<Group>                           groups            = new ArrayList<Group>();

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
    public static DummyStorage getInstance(String sessionId) {
        if (STORAGE_INSTANCES.get(sessionId) == null) {
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
        // service provider always defines the id
    	if(user.getId() == null || "".equals(user.getId().trim())) {
            user.setId(generateId());
    		user.getMeta().setLocation(user.getMeta().getLocation() + user.getId());
    	}
        
        users.add(user);
    }

    @Override
    public void replaceUser(String id, User user) throws ResourceNotFoundException {
        // service provider always defines the id
        deleteUser(id);
        users.add(user);
    }

    @Override
    public void addList(List<User> upstreamUsers) {
        users.addAll(upstreamUsers);
    }

    @Override
    public ArrayList<User> getUserList() {
        ArrayList<User> list = new ArrayList<User>();
        for (User user : users) {
            list.add(user);
        }
        return list;
    }

    @Override
    public ArrayList<User> getUserList(String sortBy, String sortOrder, int index, int count) {
        ArrayList<User> list = new ArrayList<User>();
        for (User user : users) {
            list.add(user);
        }
        Collections.sort(list, new ComplexTypeComparator(sortBy, sortOrder.equalsIgnoreCase("ascending")));
        
        int max = index + count;
        if (max > list.size() || max == 0) {
            max = list.size();
        }

        if (index > list.size()) {
            index = list.size();
        }

        try {
        	list = new ArrayList<User>(list.subList(index, max));
        } catch (IndexOutOfBoundsException e) {
        	list = new ArrayList<User>();
        }
        
        return list;
    }

    @Override
    public ArrayList<Group> getGroupList() {
        ArrayList<Group> list = new ArrayList<Group>();
        for (Group group : groups) {
            list.add(group);
        }

        return list;
    }

    @Override
    public ArrayList<Group> getGroupList(String sortBy, String sortOrder, int index, int count) {
        ArrayList<Group> list = new ArrayList<Group>();
        for (Group group : groups) {
            list.add(group);
        }
        Collections.sort(list, new ComplexTypeComparator(sortBy, sortOrder.equalsIgnoreCase("ascending")));
        
        int max = index + count;
        if (max > list.size() || max == 0) {
            max = list.size();
        }

        if (index > list.size()) {
            index = list.size();
        }

        try {
        	list = new ArrayList<Group>(list.subList(index, max));
        } catch (IndexOutOfBoundsException e) {
        	list = new ArrayList<Group>();
        }
        
        return list;
    }

    @Override
    public ArrayList<User> getUserList(String sortBy, String sortOrder, String filter, int index, int count) {
        try {
            ArrayList<User> list = new ArrayList<User>();
            for (User user : users) {
                // TODO make it more error safe
                // TODO handle more then one operation
                // TODO handle other types than string

                String attributeName = filter.split(" ")[0];
                String operation = filter.split(" ")[1];
                String filterValue = filter.split(" ")[2];
                String actualValue = user.getAttribute(attributeName);

                actualValue = (actualValue == null ? "" : actualValue);

                if ("eq".equalsIgnoreCase(operation) && filterValue.equalsIgnoreCase(actualValue)) {
                    list.add(user);
                } else if ("co".equalsIgnoreCase(operation) && actualValue.contains(filterValue)) {
                    list.add(user);
                } else if ("sw".equalsIgnoreCase(operation) && actualValue.startsWith(filterValue)) {
                    list.add(user);
                } else if ("pr".equalsIgnoreCase(operation) && user.getAttribute(filterValue) != null) {
                    list.add(user);
                } else if ("gt".equalsIgnoreCase(operation) && actualValue.compareToIgnoreCase(filterValue) < 0) {
                    list.add(user);
                } else if ("ge".equalsIgnoreCase(operation) && actualValue.compareToIgnoreCase(filterValue) <= 0) {
                    list.add(user);
                } else if ("lt".equalsIgnoreCase(operation) && actualValue.compareToIgnoreCase(filterValue) > 0) {
                    list.add(user);
                } else if ("le".equalsIgnoreCase(operation) && actualValue.compareToIgnoreCase(filterValue) >= 0) {
                    list.add(user);
                }
            }

            Collections.sort(list, new ComplexTypeComparator(sortBy, sortOrder.equalsIgnoreCase("ascending")));


            int max = index + count;
            if (max > list.size() || max == 0) {
                max = list.size();
            }

            if (index > list.size()) {
                index = list.size();
            }

            try {
            	list = new ArrayList<User>(list.subList(index, max));
            } catch (IndexOutOfBoundsException e) {
            	list = new ArrayList<User>();
            }

            return list;
        } catch (UnknownAttribute e) {
            return new ArrayList<User>();
            //throw new RuntimeException("filter failed", e);
        }
    }

    @Override
    public ArrayList<Group> getGroupList(String sortBy, String sortOrder, String filter, int index, int count) {
    	
    	log.error("getGroupList is not implemented!");
        return new ArrayList<Group>();
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
        Group scimGroup = null;
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
        if (group.getId() == null || "".equals(group.getId().trim())) {
            group.setId(generateId());
    		group.getMeta().setLocation(group.getMeta().getLocation() + group.getId());
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

    @Override
    public void setPassword(String clearTextPassword, User user) {
        // TODO: Implement change password!
        log.error("Implement change password! Set " + clearTextPassword + " on user " + user);
    }

    @Override
    public void replaceGroup(String id, Group scimGroup) throws ResourceNotFoundException {
        deleteGroup(id);
        groups.add(scimGroup);
    }

}
