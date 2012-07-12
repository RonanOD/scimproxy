package info.simplecloud.scimproxy.compliance.enteties;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AuthMetod {
    public static String                  AUTH_BASIC      = "basicAuth";
    public static String                  AUTH_OAUTH      = "oauthAuth";
    private static String                 NAME_AUTH_BASIC = "Basic";
    private static String                 NAME_AUTH_OAUTH = "OAuth 2";
    private static Map<String, AuthMetod> authMetods      = new HashMap<String, AuthMetod>();
    static {
        authMetods.put("oauth", new AuthMetod(NAME_AUTH_OAUTH, AUTH_OAUTH));
        authMetods.put("oauth2", new AuthMetod(NAME_AUTH_OAUTH, AUTH_OAUTH));
        authMetods.put("oauth 2", new AuthMetod(NAME_AUTH_OAUTH, AUTH_OAUTH));
        authMetods.put("oauth2.0", new AuthMetod(NAME_AUTH_OAUTH, AUTH_OAUTH));
        authMetods.put("oauth 2.0", new AuthMetod(NAME_AUTH_OAUTH, AUTH_OAUTH));
        authMetods.put("oauth2-v10", new AuthMetod(NAME_AUTH_OAUTH, AUTH_OAUTH));
        
        authMetods.put("basic", new AuthMetod(NAME_AUTH_BASIC, AUTH_BASIC));
        authMetods.put("http basic", new AuthMetod(NAME_AUTH_BASIC, AUTH_BASIC));
    }

    public static AuthMetod getMetod(String method) {
        return authMetods.get(method.toLowerCase());
    }

    @XmlElement(name = "name")
    String name;

    @XmlElement(name = "value")
    String value;

    private AuthMetod() {

    }

    private AuthMetod(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
