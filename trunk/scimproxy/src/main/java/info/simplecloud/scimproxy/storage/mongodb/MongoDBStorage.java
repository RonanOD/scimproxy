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
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.QueryBuilder;
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
	public ArrayList<User> getUserList(String sortBy, String sortOrder, int index, int count) {
		ArrayList<User> list = new ArrayList<User>();
		int  order = 1;
		if("descending".equals(sortOrder)) {
			order = -1;
		}
		sortBy = "User." + sortBy;
		
        DBCursor cur = getUserCollection().find().sort(new BasicDBObject( sortBy, order ) ).skip((index-1)*count).limit(count);

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
	public ArrayList<Group> getGroupList(String sortBy, String sortOrder, int index, int count) {
		ArrayList<Group> list = new ArrayList<Group>();
		int order = 1;
		if("descending".equals(sortOrder)) {
			order = -1;
		}
		
		sortBy = "Group." + sortBy;
		
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
	public ArrayList<User> getUserList(String sortBy, String sortOrder, String filterString, int index, int count) {
		ArrayList<User> list = new ArrayList<User>();
		int  order = 1;
		if("descending".equals(sortOrder)) {
			order = -1;
		}
		sortBy = "User." + sortBy;
		
		String attributeName = "User." + filterString.split(" ")[0];
		String operation = filterString.split(" ")[1];
		String filterValue = filterString.split(" ")[2];

		DBObject filter = new BasicDBObject();

		if ("eq".equalsIgnoreCase(operation)) {
			filter = new BasicDBObject(attributeName, filterValue);
		} else if ("co".equalsIgnoreCase(operation)) {
			Pattern pattern = Pattern.compile(".*" + filterValue + ".*");
			filter = QueryBuilder.start(attributeName).regex(pattern).get();
		} else if ("sw".equalsIgnoreCase(operation)) {
			Pattern pattern = Pattern.compile("^" + filterValue);
			filter = QueryBuilder.start(attributeName).regex(pattern).get();
		} else if ("pr".equalsIgnoreCase(operation)) {
			filter = QueryBuilder.start(attributeName).notEquals(null).get();
		} else if ("gr".equalsIgnoreCase(operation)) {
			filter = QueryBuilder.start(attributeName).greaterThan(filterValue).get();
		} else if ("ge".equalsIgnoreCase(operation)) {
			filter = QueryBuilder.start(attributeName).greaterThanEquals(filterValue).get();
		} else if ("lt".equalsIgnoreCase(operation)) {
			filter = QueryBuilder.start(attributeName).lessThan(filterValue).get();
		} else if ("le".equalsIgnoreCase(operation)) {
			filter = QueryBuilder.start(attributeName).lessThanEquals(filterValue).get();
		}
		
        DBCursor cur = getUserCollection().find(filter).sort(new BasicDBObject( sortBy, order ) ).skip((index-1)*count).limit(count);

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
    public ArrayList<Group> getGroupList(String sortBy, String sortOrder, String filter, int index, int count) {
    	
    	log.error("getGroupList is not implemented!");
        return new ArrayList<Group>();
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
