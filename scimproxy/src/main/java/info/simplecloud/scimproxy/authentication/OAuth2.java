package info.simplecloud.scimproxy.authentication;

public class OAuth2 implements IAuth {

    private AuthenticateUser user;

    @Override
    public boolean authenticate(String token) {
        this.user = AuthenticationUsers.getInstance().getUserFromToken(token);
        return this.user != null;
    }

    public AuthenticateUser getAuthUser() {
        return this.user;
    }

}
