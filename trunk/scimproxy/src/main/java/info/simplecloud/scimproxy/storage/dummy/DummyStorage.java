package info.simplecloud.scimproxy.storage.dummy;

import info.simplecloud.core.Address;
import info.simplecloud.core.ComplexType;
import info.simplecloud.core.Name;
import info.simplecloud.core.PluralType;
import info.simplecloud.core.ScimUser;
import info.simplecloud.scimproxy.storage.IStorage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * A dummy storage. Holds users in an in memory, non persistent, database.
 * When initiated first time it populates it self with two users.
 */
public class DummyStorage implements IStorage {

	private static final DummyStorage INSTANCE = new DummyStorage();

	// TODO: synchronize users object
	private ArrayList<DummyUser> users = new ArrayList<DummyUser>();

	private static final Logger log = Logger.getLogger(DummyStorage.class.getName());

	/**
	 * Constructor. Adds two users to storage.
	 */
	private DummyStorage() {
        ScimUser scim1 = new ScimUser();
        ScimUser scim2 = new ScimUser();

        // create user 1
        scim1.setAttribute(ScimUser.ATTRIBUTE_ID, "yhgty-ujhyu-iolki");
        scim1.setAttribute(ScimUser.ATTRIBUTE_NAME,
                new Name().setAttribute(Name.ATTRIBUTE_GIVEN_NAME, "Samuel").setAttribute(Name.ATTRIBUTE_HONORIFIC_PREFIX, "mr."));

        List<PluralType<String>> emails = new LinkedList<PluralType<String>>();
        emails.add(new PluralType<String>("samuel@erdtman.se", "private", true));
        emails.add(new PluralType<String>("samuel.erdtman@nexussafe.com", "work", false));
        scim1.setAttribute(ScimUser.ATTRIBUTE_EMAILS, emails);

        List<PluralType<ComplexType>> addresses = new LinkedList<PluralType<ComplexType>>();
        addresses.add(new PluralType<ComplexType>(new Address().setAttribute(Address.ATTRIBUTE_CONTRY, "Sweeden").setAttribute(
                Address.ATTRIBUTE_POSTAL_CODE, "12 345"), "home", true));
        addresses.add(new PluralType<ComplexType>(new Address().setAttribute(Address.ATTRIBUTE_CONTRY, "England").setAttribute(
                Address.ATTRIBUTE_POSTAL_CODE, "67-890"), "work", false));
        
        scim1.setAttribute(ScimUser.ATTRIBUTE_ADDRESSES, addresses);

        // create user 2
        scim2.setAttribute(ScimUser.ATTRIBUTE_ID, "erwah-1234-5678");
        scim2.setAttribute(ScimUser.ATTRIBUTE_NAME,
                new Name().setAttribute(Name.ATTRIBUTE_GIVEN_NAME, "Erik").setAttribute(Name.ATTRIBUTE_HONORIFIC_PREFIX, "mr."));

        List<PluralType<String>> emails2 = new LinkedList<PluralType<String>>();
        emails2.add(new PluralType<String>("erik@wahlstromstekniska.se", "private", false));
        emails2.add(new PluralType<String>("erik.wahlstrom@nexussafe.com", "work", true));
        scim2.setAttribute(ScimUser.ATTRIBUTE_EMAILS, emails2);

        List<PluralType<ComplexType>> addresses2 = new LinkedList<PluralType<ComplexType>>();
        addresses2.add(new PluralType<ComplexType>(new Address().setAttribute(Address.ATTRIBUTE_CONTRY, "Sweden").setAttribute(
                Address.ATTRIBUTE_POSTAL_CODE, "112 50"), "home", true));
        scim2.setAttribute(ScimUser.ATTRIBUTE_ADDRESSES, addresses2);

        
        users.add(new DummyUser(scim1, "1"));
		users.add(new DummyUser(scim2, "1"));
	}
	

	/**
	 * Returns singleton value for the storage.
	 * @return
	 */
	public static DummyStorage getInstance() {
		return INSTANCE;
	}

	@Override
	public ScimUser getUserForId(String id) {
		if (users == null || "".equals(id) || users.size() == 0) {
			return null;
		}

		for (DummyUser user : users) {
			log.info(user.getScimUser().toString());
			if (id.equals(user.getScimUser().getAttribute("id"))) {
				return user.getScimUser();
			}
		}
		
		return null;
	}

	@Override
	public void addUser(ScimUser user) {
		users.add(new DummyUser(user, "1"));
	}
	
	@Override
	public ArrayList<ScimUser> getList() {
		ArrayList<ScimUser> list = new ArrayList<ScimUser>();
		for (DummyUser user : users) {
			list.add(user.getScimUser());
		}
		return list;
	}

	@Override
	public void deleteUser(String id) throws UserNotFoundException {
		boolean found = false;
		if(id != null && !"".equals(id.trim())) {
			for(int i=0; i<users.size(); i++) {
				if(id.equals(users.get(i).getScimUser().getId())) {
					users.remove(i);
					found = true;
					break;
				}
			}
		}
		if(!found) {
			throw new UserNotFoundException();
		}
	}


	@Override
	public String getVersionForUser(ScimUser user) throws UserNotFoundException {

		String version = "";
		for(int i=0; i<users.size(); i++) {
			if(user.getId().equals(users.get(i).getScimUser().getId())) {
				version = users.get(i).getVersion();
				break;
			}
		}
		
		if("".equals(version)) {
			throw new UserNotFoundException();
		}
		return version;
	}
	

}

class DummyUser {
	private ScimUser user;
	private String version;
	
	public DummyUser(ScimUser u, String v) {
		this.user = u;
		this.version = v;
	}
	
	public ScimUser getScimUser() {
		return this.user;
	}
	
	public String getVersion() {
		return this.version;
	}
	
}
