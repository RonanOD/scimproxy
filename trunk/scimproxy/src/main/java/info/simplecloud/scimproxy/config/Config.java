package info.simplecloud.scimproxy.config;

import info.simplecloud.scimproxy.ScimUserServlet;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Config {

/*

	An example config file. Create it under /opt/scimproxy/config.xml and save it as UTF-8.
	
	
<?xml version="1.0" encoding="UTF-8" ?>
<config>
	<auth>basic</auth>
	<basic-auth>
		<username>usr</username>
		<password>pw</password>
	</basic-auth>
</config>


 */
	
	
	// lets keep it a singleton at the moment, config is loaded on startup.
	private static final Config INSTANCE = new Config();

	private Log log = LogFactory.getLog(ScimUserServlet.class);

	
	private boolean basicAuth = false;
	private String basicAuthUsername = "";
	private String basicAuthPassword = "";
	
	private Config() {
		try
		{
		    XMLConfiguration config = new XMLConfiguration("/opt/scimproxy/config.xml");

		    if("basic".equals(config.getString("auth"))) {
		    	setBasicAuth(true);
		    }
		    setBasicAuthUsername(config.getString("basic-auth.username"));
		    setBasicAuthPassword(config.getString("basic-auth.password"));
		}
		catch(ConfigurationException cex)
		{
			log.error("Could not find configuration file. " + cex.toString() );
		}
	}
	
	
	
	/**
	 * Returns singleton value for the config.
	 * 
	 * @return
	 */
	public static Config getInstance() {
		return INSTANCE;
	}



	public void setBasicAuthUsername(String basicAuthUsername) {
		this.basicAuthUsername = basicAuthUsername;
	}



	public String getBasicAuthUsername() {
		return basicAuthUsername;
	}



	public void setBasicAuthPassword(String basicAuthPassword) {
		this.basicAuthPassword = basicAuthPassword;
	}



	public String getBasicAuthPassword() {
		return basicAuthPassword;
	}



	public void setBasicAuth(boolean basicAuth) {
		this.basicAuth = basicAuth;
	}



	public boolean isBasicAuth() {
		return basicAuth;
	}

	
}
