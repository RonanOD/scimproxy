package info.simplecloud.scimproxy.authentication;


public interface IAuth {

	public boolean authenticate(String token);
	
}
