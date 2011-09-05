package info.simplecloud.scimproxy.authentication;

import java.rmi.server.ObjID;
import java.util.HashMap;

public class AuthenticationUsers {
	
	private static HashMap<String, AuthenticateUser> users;
	private static AuthenticationUsers instance;
	
	private AuthenticationUsers ()
	{
		users = new HashMap<String, AuthenticateUser>();
	}
	
	public void addUser(String userName, String password)
	{
		AuthenticateUser user = new AuthenticateUser(userName, password);
		user.setSessionId(new ObjID().toString());
		users.put(userName, user);
	}
	
	public String getPasswordForUser(String userName)
	{
		if(users.get(userName) == null)
		{
			return null;
		}
		return users.get(userName).getPassword();
	}
	
	public AuthenticateUser getAuthenticateUserWithUserName(String userName)
	{
		return users.get(userName);
	}
	
	public String getUserSessionForUserName(String userName)
	{
		if(users.get(userName) == null)
		{
			return null;
		}
		return users.get(userName).getSessionId();
	}
	
	public void setUserSesionForUserName(String userName, String sessionId)
	{
		if(users.get(userName) == null)
		{
			return;
		}
		users.get(userName).setSessionId(sessionId);
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
