package info.simplecloud.scimproxy.authentication;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;

public class AuthenticateUser {
	
	private UsernamePasswordCredentials userCredentials;
	private String sessionId;
	
	public AuthenticateUser(String userName, String password)
	{
		userCredentials = new UsernamePasswordCredentials(userName, password);
	}
	
	public String getUserName() {
		return userCredentials.getUserName();
	}
	public String getPassword() {
		return userCredentials.getPassword();
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getSessionId() {
		return sessionId;
	}
	
	public Credentials getCred()
	{
		return userCredentials;
	}

}
