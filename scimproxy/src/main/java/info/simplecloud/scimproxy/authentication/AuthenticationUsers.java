package info.simplecloud.scimproxy.authentication;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;

public class AuthenticationUsers {

    private static HashMap<String, AuthenticateUser> users;
    private static AuthenticationUsers               instance;
    private SecureRandom random = new SecureRandom();

    private AuthenticationUsers() {
        users = new HashMap<String, AuthenticateUser>();
    }

    public void addUser(String userName, String password) {
        AuthenticateUser user = new AuthenticateUser(userName, password);

        user.setSessionId(new BigInteger(130, random).toString(32));
        users.put(userName, user);
    }

    public String getPasswordForUser(String userName) {
        for (String key : users.keySet()) {
            AuthenticateUser user = users.get(key);
        }
        if (users.get(userName) == null) {
            return null;
        }
        return users.get(userName).getPassword();
    }

    public AuthenticateUser getAuthenticateUserWithUserName(String userName) {
        return users.get(userName);
    }

    public String getUserSessionForUserName(String userName) {
        if (users.get(userName) == null) {
            return null;
        }
        return users.get(userName).getSessionId();
    }

    public void setUserSesionForUserName(String userName, String sessionId) {
        if (users.get(userName) == null) {
            return;
        }
        users.get(userName).setSessionId(sessionId);
    }

    public void removeUser(String userName) {
        users.remove(userName);
    }

    public static AuthenticationUsers getInstance() {
        if (instance == null) {
            instance = new AuthenticationUsers();
        }
        return instance;
    }

    public AuthenticateUser getUserFromToken(String token) {
        for (String key : users.keySet()) {
            AuthenticateUser user = users.get(key);
            if (user.hasAccessToken(token)) {
                return user;
            }
        }
        return null;
    }

    public AuthenticateUser getUserWithCode(String code) {
        for (String key : users.keySet()) {
            AuthenticateUser user = users.get(key);
            if (user.hasCode(code)) {
                return user;
            }
        }
        return null;
    }
}
