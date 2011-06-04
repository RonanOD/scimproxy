package info.simplecloud.scimproxy.storage.dummy;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.InvalidUser;
import info.simplecloud.core.execeptions.UnknownEncoding;
import info.simplecloud.scimproxy.ScimUserServlet;
import info.simplecloud.scimproxy.storage.IStorage;

import java.util.ArrayList;
import java.util.logging.Logger;

public class DummyStorage implements IStorage {

	private static final DummyStorage INSTANCE = new DummyStorage();

	private ArrayList<ScimUser> users = new ArrayList<ScimUser>();

	private static final Logger log = Logger.getLogger(DummyStorage.class.getName());

	private DummyStorage() {
		ScimUser u = null;
		try {
			u = new ScimUser("first", "json");
			u.setUserName("Alice");
		} catch (UnknownEncoding e) {
			e.printStackTrace();
		} catch (InvalidUser e) {
			e.printStackTrace();
		}
		users.add(u);
	}

	public static DummyStorage getInstance() {
		return INSTANCE;
	}

	@Override
	public ScimUser getUserForId(String id) {
		if (users == null || "".equals(id) || users.size() > 0) {
			return null;
		}

		for (ScimUser user : users) {
			log.info(user.toString());
			if (id.equals(user.getAttribute("id"))) {
				return user;
			}
		}
		
		return null;
	}

	@Override
	public void addUser(ScimUser user) {
		users.add(user);
	}

}
