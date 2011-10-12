package info.simplecloud.scimproxy.authentication;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;

public class AuthenticateUser {
	
	private UsernamePasswordCredentials userCredentials;
	private String sessionId;
    private Object accessToken;
    private String code;
	
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

    public boolean hasAccessToken(String token) {
        return token.equals(this.accessToken);
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setCode(String code) {
        this.code = code;
        
    }

    public boolean hasCode(String code) {
        System.out.println("this.code: " + this.code);
        return code.equals(this.code);
    }

}
