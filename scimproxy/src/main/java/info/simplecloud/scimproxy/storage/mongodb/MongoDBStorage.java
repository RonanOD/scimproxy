package info.simplecloud.scimproxy.storage.mongodb;

import info.simplecloud.core.Group;
import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.UnknownEncoding;
import info.simplecloud.scimproxy.storage.IStorage;
import info.simplecloud.scimproxy.storage.ResourceNotFoundException;
import info.simplecloud.scimproxy.storage.dummy.DummyStorage;

import java.math.BigInteger;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

/**
 * A MongoDB storage.
 */

public class MongoDBStorage implements IStorage {

	private Log log = LogFactory.getLog(DummyStorage.class);

	private static final HashMap<String, MongoDBStorage> STORAGE_INSTANCES = new HashMap<String, MongoDBStorage>();

	Mongo mongo = null;

	/**
	 * Constructor. Adds two users to storage.
	 */
	private MongoDBStorage(String sessionId) {
		try {
			mongo = new Mongo();
			// TODO: initiate unique database per user
			//DB db = mongo.getDB("scimproxy_" + find_out_username());
			DB db = mongo.getDB("scimproxy");
			db.getCollection("user").createIndex(new BasicDBObject("User.id", 1));
			db.getCollection("group").createIndex(new BasicDBObject("Group.id", 1));

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns singleton value for the storage.
	 * 
	 * @return
	 */
	public static MongoDBStorage getInstance(String sessionId) {
		if (STORAGE_INSTANCES.get(sessionId) == null) {
			STORAGE_INSTANCES.put(sessionId, new MongoDBStorage(sessionId));
		}
		return STORAGE_INSTANCES.get(sessionId);
	}

	@Override
	public User getUserForId(String id) throws ResourceNotFoundException {
		User scimUser = null;
		
        BasicDBObject query = new BasicDBObject();
        query.put("User.id", id);
        DBCursor cur = getUserCollection().find(query);

        if(cur.hasNext()) {
        	String json = JSON.serialize(cur.next().get("User"));
            try {
				scimUser = new User(json, User.ENCODING_JSON);
			} catch (Exception e) {
				log.error("Could not decode user from database.");
				throw new ResourceNotFoundException();
			}
        }
        else {
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
				
		try {
			// create a document to store key and value
			BasicDBObject document = new BasicDBObject();
			DBObject dbObject = (DBObject)JSON.parse(user.getUser(User.ENCODING_JSON));

			document.put("User", dbObject);
			// save in database
			getUserCollection().insert(document);
		} catch (UnknownEncoding e) {
			// TODO, THROW THIS!
			e.printStackTrace();
		}

	}

	@Override
	public void replaceUser(String id, User user)
			throws ResourceNotFoundException {
		// service provider always defines the id
		deleteUser(id);
		addUser(user);
	}

	@Override
	public void addList(List<User> upstreamUsers) {
		for(User user: upstreamUsers) {
			addUser(user);
		}
	}

	@Override
	public ArrayList<User> getUserList(){
		ArrayList<User> list = new ArrayList<User>();
		
        DBCursor cur = getUserCollection().find();

        while(cur.hasNext()) {
        	String json = JSON.serialize(cur.next().get("User"));
            User scimUser = null;
			try {
				scimUser = new User(json, User.ENCODING_JSON);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            list.add(scimUser);
        }

		return list;
	}

	@Override
	public ArrayList<User> getUserList(String sortBy, String sortOrder) {
		ArrayList<User> list = new ArrayList<User>();
		int order = 1;
		if("ascending".equals(sortOrder)) {
			order = -1;
		}
        DBCursor cur = getUserCollection().find().sort(new BasicDBObject( sortBy, order ) );

        while(cur.hasNext()) {
        	String json = JSON.serialize(cur.next().get("User"));
            User scimUser = null;
			try {
				scimUser = new User(json, User.ENCODING_JSON);
			} catch (Exception e) {
				e.printStackTrace();
			}
            list.add(scimUser);
        }

        return list;
	}

	@Override
	public ArrayList<Group> getGroupList() {
		ArrayList<Group> list = new ArrayList<Group>();
		
        DBCursor cur = getGroupCollection().find();

        while(cur.hasNext()) {
        	String json = JSON.serialize(cur.next().get("Group"));
            Group scimGroup = null;
			try {
				scimGroup = new Group(json, Group.ENCODING_JSON);
			} catch (Exception e) {
				e.printStackTrace();
			}
            list.add(scimGroup);
        }

		return list;
	}

	@Override
	public ArrayList<Group> getGroupList(String sortBy, String sortOrder) {
		ArrayList<Group> list = new ArrayList<Group>();
		int order = 1;
		if("ascending".equals(sortOrder)) {
			order = -1;
		}
        DBCursor cur = getGroupCollection().find().sort(new BasicDBObject( sortBy, order ) );

        while(cur.hasNext()) {
        	String json = JSON.serialize(cur.next().get("Group"));
            Group scimGroup = null;
			try {
				scimGroup = new Group(json, Group.ENCODING_JSON);
			} catch (Exception e) {
				e.printStackTrace();
			}
            list.add(scimGroup);
        }

        return list;
	}

	@Override
	public ArrayList<User> getList(String sortBy, String sortOrder, String filter) {
		return new ArrayList<User>();
/*		try {
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

				if ("eq".equalsIgnoreCase(operation)
						&& filterValue.equalsIgnoreCase(actualValue)) {
					list.add(user);
				} else if ("co".equalsIgnoreCase(operation)
						&& actualValue.contains(filterValue)) {
					list.add(user);
				} else if ("sw".equalsIgnoreCase(operation)
						&& actualValue.startsWith(filterValue)) {
					list.add(user);
				} else if ("pr".equalsIgnoreCase(operation)
						&& user.getAttribute(filterValue) != null) {
					list.add(user);
				} else if ("gt".equalsIgnoreCase(operation)
						&& actualValue.compareToIgnoreCase(filterValue) < 0) {
					list.add(user);
				} else if ("ge".equalsIgnoreCase(operation)
						&& actualValue.compareToIgnoreCase(filterValue) <= 0) {
					list.add(user);
				} else if ("lt".equalsIgnoreCase(operation)
						&& actualValue.compareToIgnoreCase(filterValue) > 0) {
					list.add(user);
				} else if ("le".equalsIgnoreCase(operation)
						&& actualValue.compareToIgnoreCase(filterValue) >= 0) {
					list.add(user);
				}
			}

			Collections.sort(
					list,
					new ComplexTypeComparator(sortBy, sortOrder
							.equalsIgnoreCase("ascending")));

			return list;
		} catch (UnknownAttribute e) {
			return new ArrayList<User>();
			// throw new RuntimeException("filter failed", e);
		}
*/
	}

	@Override
	public void deleteUser(String id) throws ResourceNotFoundException {
		boolean found = false;
		if (id != null && !"".equals(id.trim())) {
			
	        BasicDBObject query = new BasicDBObject();
	        query.put("User.id", id);
	        DBCursor cur = getUserCollection().find(query);

	        if(cur.hasNext()) {
	            getUserCollection().remove(cur.next());
	            found = true;
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
		
        BasicDBObject query = new BasicDBObject();
        query.put("Group.id", groupId);
        DBCursor cur = getGroupCollection().find(query);

        if(cur.hasNext()) {
        	String json = JSON.serialize(cur.next().get("Group"));
            try {
				scimGroup = new Group(json, Group.ENCODING_JSON);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        else {
			throw new ResourceNotFoundException();
        }

		return scimGroup;
	}

	@Override
	public void addGroup(Group group) {
		// service provider always defines the id
		if(group.getId() == null || "".equals(group.getId().trim())) {
			group.setId(generateId());
			group.getMeta().setLocation(group.getMeta().getLocation() + group.getId());
		}

		try {
			// create a document to store key and value
			BasicDBObject document = new BasicDBObject();
			DBObject dbObject = (DBObject)JSON.parse(group.getGroup(Group.ENCODING_JSON));

			//document.put(group.getId(), dbObject);
			document.put("Group", dbObject);
			// save in database
			getGroupCollection().insert(document);
		} catch (UnknownEncoding e) {
			// TODO, THROW THIS!
			e.printStackTrace();
		}
	}

	@Override
	public void deleteGroup(String id) throws ResourceNotFoundException {
		boolean found = false;
		if (id != null && !"".equals(id.trim())) {
			
	        BasicDBObject query = new BasicDBObject();
	        query.put("Group.id", id);
	        DBCursor cur = getGroupCollection().find(query);

	        if(cur.hasNext()) {
	            getGroupCollection().remove(cur.next());
	            found = true;
	        }
		}
		if (!found) {
			throw new ResourceNotFoundException();
		}
	}

	@Override
	public void setPassword(String clearTextPassword, User user) {
		// TODO: Implement change password!
		log.error("Implement change password! Set " + clearTextPassword
				+ " on user " + user);
	}

	@Override
	public void replaceGroup(String id, Group scimGroup)
			throws ResourceNotFoundException {
		deleteGroup(id);
		addGroup(scimGroup);
	}

	private DBCollection getUserCollection() {
		DB db = mongo.getDB("scimproxy");
		DBCollection collection = db.getCollection("user");
		return collection;
	}

	private DBCollection getGroupCollection() {
		DB db = mongo.getDB("scimproxy");
		DBCollection collection = db.getCollection("group");
		return collection;
	}

}
