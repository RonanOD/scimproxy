package info.simplecloud.scimproxy.authentication;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;


public class Basic implements IAuth {
	
	private Credentials cred = null;

	@Override
	public boolean authenticate(String token) {
		
		boolean authStatus = false;
		try {
			
			UsernamePasswordCredentials cred = parseBasicString(token);
	        String correctPassword = AuthenticationUsers.getInstance().getPasswordForUser(cred.getUserName());

	        if(correctPassword != null && correctPassword.equals(cred.getPassword())) {
	        	this.cred = cred;
	        	authStatus = true;
	        }
		} catch (Exception e) {
			// do nothing
		}

		return authStatus;
	}
	
	public UsernamePasswordCredentials parseBasicString(String token)
	{
		String basicIdentifier = "Basic ";
		String encoded = token.substring(basicIdentifier.length());
        String decoded = new String(Base64.decodeBase64(encoded));
        
        String user = decoded.substring(0, decoded.indexOf(":"));
        String pass = decoded.substring(decoded.indexOf(":")+1);
        return new UsernamePasswordCredentials(user, pass);
	}

	public void setCred(Credentials cred) {
		this.cred = cred;
	}

	public Credentials getCred() {
		return cred;
	}

}
