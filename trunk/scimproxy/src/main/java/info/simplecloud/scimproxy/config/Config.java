package info.simplecloud.scimproxy.config;

import info.simplecloud.scimproxy.ScimUserServlet;
import info.simplecloud.scimproxy.authentication.AuthenticateUser;
import info.simplecloud.scimproxy.authentication.AuthenticationUsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

public class Config {

    /*
     * 
     * An example config file. Create it under /opt/scimproxy/config.xml and
     * save it as UTF-8.
     * 
     * 
     * <?xml version="1.0" encoding="UTF-8" ?> <config> <auth type="basic">
     * <user> <username>usr</username> <password>pw</password> </user> <user>
     * <username>usr3</username> <password>pw</password> </user> </auth> <bulk>
     * <maxOperations>100</maxOperations>
     * <maxPayloadSize>1000000</maxPayloadSize> </bulk> <storages> <storage>
     * <type>dummy</type> </storage> </storages> <down-stream> <csp>
     * <url>http://192.168.41.137:8080</url>
     * <preferedEncoding>JSON</preferedEncoding> <version>1.0</version> <auth
     * type="basic"> <username>usr</username> <password>pw</password> </auth>
     * </csp> </down-stream> </config>
     */
	
	public static final String	AUTH_BASIC = "BASIC";
	public static final String	AUTH_OAUTH2 = "OAUTH2";
	public static final String	AUTH_OAUTH2_V10 = "OAUTH2V10";

    // TODO: read config file location from somewhere in system (property?)
    private static String       CONFIG_FILE        = System.getProperty("info.simplecloud.scimproxy.config.Config.CONFIG_FILE",
                                                           "src/main/resources/config.xml");

    // lets keep it a singleton at the moment
    private static Config       INSTANCE           = null;

    private Log                 log                = LogFactory.getLog(ScimUserServlet.class);
    
    private AuthenticationUsers authUsers          = AuthenticationUsers.getInstance();

    private ArrayList<String> 	authenticationMethods = new ArrayList<String>(); 

	private int                 bulkMaxOperations  = 0;
    private int                 bulkMaxPayloadSize = 0;

    private List<CSP>           downStreamCSP      = new ArrayList<CSP>();

    // only have support for dummy storage at the moment so it's just a string
    // at the moment
    private String              storageType        = "";

    /**
     * Private constructor that creates the singleton instance.
     */
    private Config() {
        readConfig("");
    }

    /**
     * Private constructor that creates the singleton instance.
     */
    private Config(String config) {
        readConfig(config);
    }

    /**
     * Returns singleton value for the config.
     * 
     * @return
     */
    public static Config getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Config();
        }
        return INSTANCE;
    }

    public static Config getInstance(String config) {
        if (INSTANCE == null) {
            INSTANCE = new Config(config);
        }
        return INSTANCE;
    }

    private void readConfig(String confStr) {
        try {
            XMLConfiguration config = null;
            if ("".equals(confStr)) {
                config = new XMLConfiguration(CONFIG_FILE);
            } else {

                config = new XMLConfiguration();
                try {
                    config.getDocumentBuilder().parse(confStr);
                } catch (SAXException e) {
                    // implementation error
                    e.printStackTrace();
                } catch (IOException e) {
                    // implementation error
                    e.printStackTrace();
                }
            }


            // add all configured users in the system to the list of potential user to authenticate with
            String[] userNames = config.getStringArray("users.user.username");
            String[] passwords = config.getStringArray("users.user.password");
            if (userNames.length == passwords.length) {
                for (int i = 0; i < userNames.length; i++) {
                    authUsers.addUser(userNames[i], passwords[i]);
                }
            }

            List<String> methodList = config.getList("auth.method.type");
            if (methodList != null) {
                for (int i = 0; i < methodList.size(); i++) {
                	String type = config.getString("auth.method(" + i + ").type");
	                if ("basic".equalsIgnoreCase(type)) {
	                	authenticationMethods.add(AUTH_BASIC);
	                }
	                if ("oauth2".equalsIgnoreCase(type)) {
	                	authenticationMethods.add(AUTH_OAUTH2);
	                }
	                if ("oauth2v10".equalsIgnoreCase(type)) {
	                	authenticationMethods.add(AUTH_OAUTH2_V10);
	                }
                }
            }


            List<String> storagesList = config.getList("storages.storage.type");
            if (storagesList != null) {
                for (int i = 0; i < storagesList.size(); i++) {
                    // only have dummy support at the moment
                    setStorageType(config.getString("storages.storage(" + i + ").type"));
                }
            }

            setBulkMaxOperations(config.getInt("bulk.maxOperations"));
            setBulkMaxPayloadSize(config.getInt("bulk.maxPayloadSize"));

            List<String> downProp = config.getList("down-stream.csp.url");
            if (downProp != null) {
                for (int i = 0; i < downProp.size(); i++) {
                    CSP csp = new CSP();
                    csp.setUrl(config.getString("down-stream.csp(" + i + ").url"));
                    csp.setPreferedEncoding(config.getString("down-stream.csp(" + i + ").preferedEncoding"));
                    csp.setVersion(config.getString("down-stream.csp(" + i + ").version"));

                    csp.setAuthentication(config.getString("down-stream.csp(" + i + ").auth[@type]"));
                    csp.setUsername(config.getString("down-stream.csp(" + i + ").auth.username"));
                    csp.setPassword(config.getString("down-stream.csp(" + i + ").auth.password"));
                    csp.setOAuth2AccessToken(config.getString("down-stream.csp(" + i + ").auth.accessToken"));
                    csp.setOAuth2AuthorizationServer(config.getString("down-stream.csp(" + i + ").auth.authorizationServer"));
                    csp.setoAuth2ClientId(config.getString("down-stream.csp(" + i + ").auth.clientId"));
                    csp.setoAuth2ClientSecret(config.getString("down-stream.csp(" + i + ").auth.clientSecret"));

                    downStreamCSP.add(csp);

                    log.info("All resources will be sent downstreams to: " + csp);
                }
            }

            log.info("Configuration loaded successfully.");

        } catch (ConfigurationException cex) {
            log.error("Could not find configuration file. " + cex.toString());
        }
    }


    public void setDownStreamCSP(List<CSP> downStreamCSP) {
        this.downStreamCSP = downStreamCSP;
    }

    public List<CSP> getDownStreamCSP() {
        return downStreamCSP;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getStorageType() {
        return storageType;
    }

    public int getBulkMaxOperations() {
        return bulkMaxOperations;
    }

    public void setBulkMaxOperations(int bulkMaxOperations) {
        this.bulkMaxOperations = bulkMaxOperations;
    }

    public void setBulkMaxPayloadSize(int bulkMaxPayloadSize) {
        this.bulkMaxPayloadSize = bulkMaxPayloadSize;
    }

    public int getBulkMaxPayloadSize() {
        return bulkMaxPayloadSize;
    }

    public ArrayList<String> getAuthenticationMethods() {
		return authenticationMethods;
	}

	public void setAuthenticationMethods(ArrayList<String> authenticationMethods) {
		this.authenticationMethods = authenticationMethods;
	}

}
