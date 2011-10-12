package info.simplecloud.scimproxy.authentication;

public class OAuth2 implements IAuth {

    private AuthenticateUser user;

    @Override
    public boolean authenticate(String token) {
        String basicIdentifier = "Bearer ";
        String encoded = token.substring(basicIdentifier.length());
        this.user = AuthenticationUsers.getInstance().getUserFromToken(encoded);
        return this.user != null;
    }

    public AuthenticateUser getAuthUser() {
        return this.user;
    }

}
