package info.simplecloud.scimproxy.config;

import info.simplecloud.scimproxy.ScimUserServlet;
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

	An example config file. Create it under /opt/scimproxy/config.xml and save it as UTF-8.
	
	
<?xml version="1.0" encoding="UTF-8" ?>
<config>
	<auth type="basic">
		<user>
			<username>usr</username>
			<password>pw</password>
		</user>
		<user>
			<username>usr3</username>
			<password>pw</password>
		</user>
	</auth>
	<storages>
		<storage>
			<type>dummy</type>
		</storage>
	</storages>
	<up-stream>
		<csp>
			<url>http://192.168.41.137:8080</url>
			<preferedEncoding>JSON</preferedEncoding>
			<version>1.0</version>
			<auth type="basic">
				<username>usr</username>
				<password>pw</password>
			</auth>
		</csp>
	</up-stream>
	<down-stream>
		<csp>
			<url>http://192.168.41.137:8080</url>
			<preferedEncoding>JSON</preferedEncoding>
			<version>1.0</version>
			<auth type="basic">
				<username>usr</username>
				<password>pw</password>
			</auth>
		</csp>
	</down-stream>
</config>



 */
	
	// TODO: read config file location from somewhere in system (property?)
	private static String CONFIG_FILE = System.getProperty("info.simplecloud.scimproxy.config.Config.CONFIG_FILE", "src/main/resources/config.xml");
	
	// lets keep it a singleton at the moment
	private static Config INSTANCE = null;

	private Log log = LogFactory.getLog(ScimUserServlet.class);
	private AuthenticationUsers authUsers = AuthenticationUsers.getInstance();

	
	private boolean basicAuth = false;
	private boolean noneAuth = false; // Public to all Internet users in the world!
	private String basicAuthUsername = "";
	private String basicAuthPassword = "";
	
	private List<CSP> downStreamCSP = new ArrayList<CSP>();
	private List<CSP> upStreamCSP = new ArrayList<CSP>();

	// only have support for dummy storage at the moment so it's just a string at the moment
	private String storageType = "";

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
		if(INSTANCE == null) {
			INSTANCE = new Config();
		}
		return INSTANCE;
	}

	
	public static Config getInstance(String config) {
		if(INSTANCE == null) {
			INSTANCE = new Config(config);
		}
		return INSTANCE;
	}



	private void readConfig(String confStr) {
		try
		{
			XMLConfiguration config = null;
			if("".equals(confStr)) {
				config = new XMLConfiguration(CONFIG_FILE);
			}
			else {
				
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


		    if("basic".equals(config.getString("auth[@type]"))) {
		    	setBasicAuth(true);
		    	String[] userNames = config.getStringArray("auth.user.username");
		    	String[] passwords = config.getStringArray("auth.user.password");
		    	if(userNames.length != passwords.length)
		    	{
		    		
		    	}
		    	else
		    	{
		    		for(int i = 0; i < userNames.length; i++)
		    		{
		    			authUsers.addUser(userNames[i], passwords[i]);
		    		}
		    	}
		    	
		    }
		    if("none".equals(config.getString("auth[@type]"))) {
		    	setNoneAuth(true);
		    }
		    
		    List<String> storagesList = config.getList("storages.storage.type");
		    if(storagesList != null)
		    {
		    	for(int i=0; i<storagesList.size(); i++) {
		    		// only have dummy support at the moment
		    		setStorageType(config.getString("storages.storage(" + i + ").type"));
		    	}
		    }


		    List<String> downProp = config.getList("down-stream.csp.url");
		    if(downProp != null)
		    {
		    	for(int i=0; i<downProp.size(); i++) {
		    		CSP csp = new CSP();
		    		csp.setUrl(config.getString("down-stream.csp(" + i + ").url"));
		    		csp.setPreferedEncoding(config.getString("down-stream.csp(" + i + ").preferedEncoding"));
		    		csp.setVersion(config.getString("down-stream.csp(" + i + ").version"));

		    		if(CSP.AUTH_BASIC.equals(config.getString("down-stream.csp(" + i + ").auth[@type]"))) {
		    			csp.setAuthentication(CSP.AUTH_BASIC);
		    			csp.setBasicUsername(config.getString("down-stream.csp(" + i + ").auth.username"));
		    			csp.setBasicPassword(config.getString("down-stream.csp(" + i + ").auth.password"));
		    		}
		    		downStreamCSP.add(csp);
		    	}
		    }

		    List<String> upProp = config.getList("up-stream.csp.url");
		    if(upProp != null)
		    {
		    	for(int i=0; i<upProp.size(); i++) {
		    		CSP csp = new CSP();
		    		csp.setUrl(config.getString("up-stream.csp(" + i + ").url"));
		    		csp.setPreferedEncoding(config.getString("up-stream.csp(" + i + ").preferedEncoding"));

		    		if(CSP.AUTH_BASIC.equals(config.getString("up-stream.csp(" + i + ").auth[@type]"))) {
		    			csp.setAuthentication(CSP.AUTH_BASIC);
		    			csp.setBasicUsername(config.getString("up-stream.csp(" + i + ").auth.username"));
		    			csp.setBasicPassword(config.getString("up-stream.csp(" + i + ").auth.password"));
		    		}
		    		upStreamCSP.add(csp);
		    	}
		    }
		    
		}
		catch(ConfigurationException cex)
		{
			log.error("Could not find configuration file. " + cex.toString() );
		}
	}
	
	
	
	
	public void setBasicAuth(boolean b) {
		this.basicAuth = true;
		
	}

	public boolean isBasicAuth() {
		return basicAuth;
	}



	public void setNoneAuth(boolean noneAuth) {
		this.noneAuth = noneAuth;
	}



	public boolean isNoneAuth() {
		return noneAuth;
	}



	public void setDownStreamCSP(List<CSP> downStreamCSP) {
		this.downStreamCSP = downStreamCSP;
	}



	public List<CSP> getDownStreamCSP() {
		return downStreamCSP;
	}



	public void setUpStreamCSP(List<CSP> upStreamCSP) {
		this.upStreamCSP = upStreamCSP;
	}



	public List<CSP> getUpStreamCSP() {
		return upStreamCSP;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getStorageType() {
		return storageType;
	}

	
}


