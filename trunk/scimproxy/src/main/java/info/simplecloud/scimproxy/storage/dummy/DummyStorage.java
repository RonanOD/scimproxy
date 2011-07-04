package info.simplecloud.scimproxy.storage.dummy;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.tools.ScimUserComparator;
import info.simplecloud.core.types.Address;
import info.simplecloud.core.types.ComplexType;
import info.simplecloud.core.types.Name;
import info.simplecloud.core.types.PluralType;
import info.simplecloud.scimproxy.storage.IStorage;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A dummy storage. Holds users in an in memory, non persistent, database. When
 * initiated first time it populates it self with two users.
 */
public class DummyStorage implements IStorage {

	private static final DummyStorage INSTANCE = new DummyStorage();

	// TODO: synchronize users object
	private ArrayList<ScimUser> users = new ArrayList<ScimUser>();

	/**
	 * Constructor. Adds two users to storage.
	 */
	private DummyStorage() {
		ScimUser carol = new ScimUser();
		ScimUser dave = new ScimUser();

		// create carol
		carol.setUserName("Carol");
		carol.setAttribute(ScimUser.ATTRIBUTE_ID, generateId());
		carol.setAttribute(ScimUser.ATTRIBUTE_NAME, new Name().setAttribute(Name.ATTRIBUTE_GIVEN_NAME, "Carol").setAttribute(Name.ATTRIBUTE_HONORIFIC_PREFIX, "ms."));

		List<PluralType<String>> carolEmail = new LinkedList<PluralType<String>>();
		carolEmail.add(new PluralType<String>("carol@foo.bar", "private", true));
		carolEmail.add(new PluralType<String>("carol@bar.foo", "work", false));
		carol.setAttribute(ScimUser.ATTRIBUTE_EMAILS, carolEmail);

		List<PluralType<ComplexType>> carolAddress = new LinkedList<PluralType<ComplexType>>();
		carolAddress.add(new PluralType<ComplexType>(new Address().setAttribute(Address.ATTRIBUTE_CONTRY, "Sweden").setAttribute(Address.ATTRIBUTE_POSTAL_CODE, "12 345"), "home", true));
		carolAddress.add(new PluralType<ComplexType>(new Address().setAttribute(Address.ATTRIBUTE_CONTRY, "USA").setAttribute(Address.ATTRIBUTE_POSTAL_CODE, "67-890"), "work", false));

		carol.setAttribute(ScimUser.ATTRIBUTE_ADDRESSES, carolAddress);

		// create dave
		dave.setUserName("Dave");
		dave.setAttribute(ScimUser.ATTRIBUTE_ID, generateId());
		dave.setAttribute(ScimUser.ATTRIBUTE_NAME, new Name().setAttribute(Name.ATTRIBUTE_GIVEN_NAME, "Dave").setAttribute(Name.ATTRIBUTE_HONORIFIC_PREFIX, "mr."));

		List<PluralType<String>> daveEmail = new LinkedList<PluralType<String>>();
		daveEmail.add(new PluralType<String>("dave@foo.bar", "private", false));
		daveEmail.add(new PluralType<String>("dave@bar.foo", "work", true));
		dave.setAttribute(ScimUser.ATTRIBUTE_EMAILS, daveEmail);

		List<PluralType<ComplexType>> daveAddress = new LinkedList<PluralType<ComplexType>>();
		daveAddress.add(new PluralType<ComplexType>(new Address().setAttribute(Address.ATTRIBUTE_CONTRY, "Sweden").setAttribute(Address.ATTRIBUTE_POSTAL_CODE, "112 50"), "home", true));
		dave.setAttribute(ScimUser.ATTRIBUTE_ADDRESSES, daveAddress);

		dave.setDisplayName("Dave");

		users.add(carol);
		users.add(dave);
	}

	/**
	 * Returns singleton value for the storage.
	 * 
	 * @return
	 */
	public static DummyStorage getInstance() {
		return INSTANCE;
	}

	@Override
	public ScimUser getUserForId(String id) throws UserNotFoundException {
		ScimUser scimUser = null;
		if (users != null && !"".equals(id)) {
			for (ScimUser user : users) {
				if (id.equals(user.getAttribute("id"))) {
					scimUser = user;
					break;
				}
			}
		}
		if (scimUser == null) {
			throw new UserNotFoundException();
		}
		return scimUser;

	}

	@Override
	public void addUser(ScimUser user) {
		if (user.getId() == null) {
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
	public ArrayList<ScimUser> getList(String sortBy, String sortOrder) {
		ArrayList<ScimUser> list = new ArrayList<ScimUser>();
		for (ScimUser user : users) {
			list.add(user);
		}
        Collections.sort(list, new ScimUserComparator<String>(sortBy, sortOrder.equalsIgnoreCase("ascending")));
        
		return list;
	}

	@Override
	public ArrayList<ScimUser> getList(String sortBy, String sortOrder, String filterBy, String filterValue, String filterOp) {
		ArrayList<ScimUser> list = new ArrayList<ScimUser>();
		for (ScimUser user : users) {
			
			// TODO: Do a way better and nicer looking filtering function.
			/*
				equals:	the two values must be identical
				equalsIgnoreCase: the two values must be identical
				contains: the entire filterValue must be a substring of the attribute value.
				startswith: the entire filterValue must be a substring of the attribute value, starting at the beginning of the attribute value. This criterion is satisfied if the two strings are equal.
				present: if the attribute specified by filterBy has a non-empty value, or if it contains a non-empty node for complex attributes there is a match.

			 */
			boolean add = false;
			String value = null;
			// TODO: add support for complex and plural types
			String methodName = "get" + filterBy.substring(0, 1).toUpperCase() + filterBy.substring(1);
			try {
				Method method = new ScimUser().getClass().getMethod(methodName);
				value = (String)method.invoke(user);
			} catch (Exception e) {
				break;
			}
			
			if(value == null) {
				value = "";
			}
			
			if("equals".equals(filterOp)) {
				if(value.equals(filterValue)) {
					add = true;
				}
			} 
				
			if("equalsIgnoreCase".equals(filterOp)) {
				if(value.equalsIgnoreCase(filterValue)) {
					add = true;
				}
			} 

			if("contains".equals(filterOp)) {
				if(value.indexOf(filterValue) > 0) {
					add = true;
				}
			} 

			if("startsWith".equals(filterOp)) {
				if(value.indexOf(filterValue) > -1) {
					add = true;
				}
			} 
			
			if("present".equals(filterOp)) {
				if(!"".equals(value)) {
					add = true;
				}
			} 

			if(add) {
				list.add(user);
			}
		}
		
        Collections.sort(list, new ScimUserComparator<String>(sortBy, sortOrder.equalsIgnoreCase("ascending")));

		return list;
	}

	@Override
	public void deleteUser(String id) throws UserNotFoundException {
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
			throw new UserNotFoundException();
		}
	}

	private SecureRandom random = new SecureRandom();

	private String generateId() {
		return new BigInteger(130, random).toString(32);
	}

}
