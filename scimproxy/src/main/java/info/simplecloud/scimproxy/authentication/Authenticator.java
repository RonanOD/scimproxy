package info.simplecloud.scimproxy.authentication;

import info.simplecloud.scimproxy.config.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Authenticator {

    private Config           config   = null;
    private AuthenticateUser authUser = null;

    public Authenticator(Config config) {
        this.config = config;
    }

    public boolean authenticate(HttpServletRequest req, HttpServletResponse resp) {
        boolean authStatus = false;
        // check if BASIC authentication is configured
        if (config.getAuthenticationMethods().contains(Config.AUTH_BASIC)) {
            String basicAuth = req.getHeader("Authorization");
            if (basicAuth != null && basicAuth.indexOf("Basic ") == 0) {
                Basic basic = new Basic();
                authStatus = basic.authenticate(basicAuth);
                this.authUser = basic.getAuthUser();
            }
        }

        if (!authStatus && config.getAuthenticationMethods().contains(Config.AUTH_OAUTH2)
                || config.getAuthenticationMethods().contains(Config.AUTH_OAUTH2_V10)) {
            String oauthAuth = req.getHeader("Authorization");

            if (oauthAuth != null && oauthAuth.indexOf("Bearer ") == 0) {
                String token = oauthAuth.substring("Bearer ".length());
                OAuth2 oauth2 = new OAuth2();
                authStatus = oauth2.authenticate(token);
                this.authUser = oauth2.getAuthUser();
            }
            if (oauthAuth != null && oauthAuth.indexOf("OAuth ") == 0) {
                String token = oauthAuth.substring("OAuth ".length());
                OAuth2 oauth2 = new OAuth2();
                authStatus = oauth2.authenticate(token);
                this.authUser = oauth2.getAuthUser();
            }
        }

        return authStatus;
    }

    public void setAuthUser(AuthenticateUser cred) {
        this.authUser = cred;
    }

    public AuthenticateUser getAuthUser() {
        return authUser;
    }
}
