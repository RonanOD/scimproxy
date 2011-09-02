package info.simplecloud.scimproxy.authentication;

import java.util.HashMap;

public class AuthenticationUsers {
	
	private static HashMap<String, String> users;
	private static AuthenticationUsers instance;
	
	private AuthenticationUsers ()
	{
		users = new HashMap<String, String>();
	}
	
	public void addUser(String userName, String password)
	{
		users.put(userName, password);
	}
	
	public String getPasswordForUser(String userName)
	{
		return users.get(userName);
	}
	
	public void removeUser(String userName)
	{
		users.remove(userName);
	}
	
	public static AuthenticationUsers getInstance()
	{
		if(instance == null)
		{
			instance = new AuthenticationUsers();
		}
		return instance;
	}

}
