package info.simplecloud.scimproxy.storage.dummy;

import info.simplecloud.core.Address;
import info.simplecloud.core.ComplexType;
import info.simplecloud.core.Meta;
import info.simplecloud.core.Name;
import info.simplecloud.core.PluralType;
import info.simplecloud.core.ScimUser;
import info.simplecloud.scimproxy.storage.IStorage;

import java.math.BigInteger;
import java.security.SecureRandom;
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
	private ArrayList<ScimUser> users = new ArrayList<ScimUser>();

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

        Meta meta1 = new Meta();
        meta1.setVersion("1");
        scim1.setMeta(meta1);
        
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
        
        scim2.setDisplayName("Erik Wahlström");

        Meta meta2 = new Meta();
        meta2.setVersion("1");
        scim2.setMeta(meta2);

        
        users.add(scim1);
        users.add(scim2);
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
		if(user.getId() == null) {
			user.setId(generateId());
		}
		users.add(user);
	}
	
	@Override
	public ArrayList<ScimUser> getList() {
		ArrayList<ScimUser> list = new ArrayList<ScimUser>();
		for (ScimUser user : users) {
			list.add(user);
		}
		return list;
	}

	@Override
	public void deleteUser(String id) throws UserNotFoundException {
		boolean found = false;
		if(id != null && !"".equals(id.trim())) {
			for(int i=0; i<users.size(); i++) {
				if(id.equals(users.get(i).getId())) {
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
	

	private SecureRandom random = new SecureRandom();
	private String generateId()
	{
		return new BigInteger(130, random).toString(32);
	}


}

