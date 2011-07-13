package info.simplecloud.scimproxy.config;

import info.simplecloud.scimproxy.ScimUserServlet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	<down-stream>
		<csp>
			<url>https://downstream/1/</url>
			<preferedEncoding>JSON</preferedEncoding>
			<auth>basic</auth>
			<basic-auth>
				<username>usr</username>
				<password>pw</password>
			</basic-auth>
		</csp>
	</down-stream>
</config>



 */
	
	
	// lets keep it a singleton at the moment, config is loaded on startup.
	private static final Config INSTANCE = new Config();

	private Log log = LogFactory.getLog(ScimUserServlet.class);

	
	private boolean basicAuth = false;
	private boolean noneAuth = false; // Public to all internet users in the world!
	private String basicAuthUsername = "";
	private String basicAuthPassword = "";
	
	private List<CSP> downStreamCSP = new ArrayList<CSP>();
	
	private Config() {
		try
		{
		    XMLConfiguration config = new XMLConfiguration("/opt/scimproxy/config.xml");

		    if("basic".equals(config.getString("auth"))) {
		    	setBasicAuth(true);
		    }
		    if("none".equals(config.getString("auth"))) {
		    	setNoneAuth(true);
		    }
		    setBasicAuthUsername(config.getString("basic-auth.username"));
		    setBasicAuthPassword(config.getString("basic-auth.password"));

		    List<String>prop = config.getList("down-stream.csp.url");
		    if(prop != null)
		    {
		    	for(int i=0; i<prop.size(); i++) {
		    		CSP csp = new CSP();
		    		csp.setUrl(config.getString("down-stream.csp(" + i + ").url"));
		    		csp.setPreferedEncoding(config.getString("down-stream.csp(" + i + ").preferedEncoding"));

		    		if(CSP.AUTH_BASIC.equals(config.getString("down-stream.csp(" + i + ").auth"))) {
		    			csp.setAuthentication(CSP.AUTH_BASIC);
		    			csp.setBasicUsername(config.getString("down-stream.csp(" + i + ").basic-auth.username"));
		    			csp.setBasicPassword(config.getString("down-stream.csp(" + i + ").basic-auth.password"));
		    		}
		    		downStreamCSP.add(csp);
		    	}
		    }
		    
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

	
}


