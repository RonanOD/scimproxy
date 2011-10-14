package info.simplecloud.scimproxy.config;

import java.util.HashMap;

public class CSP {

	public static String AUTH_BASIC = "basic";
	
	private String url = "";
	private String authentication ="";
	private String basicUsername = "";
	private String basicPassword = "";
	private String preferedEncoding = "JSON";
	private String version = "";
	
	private HashMap<String, String> resourceIdMapping = new HashMap<String, String>();
	private HashMap<String, String> versionMapping = new HashMap<String, String>();
	
	public CSP() {
		
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	public String getAuthentication() {
		return authentication;
	}

	public void setBasicUsername(String basicUsername) {
		this.basicUsername = basicUsername;
	}

	public String getBasicUsername() {
		return basicUsername;
	}

	public void setBasicPassword(String basicPassword) {
		this.basicPassword = basicPassword;
	}

	public String getBasicPassword() {
		return basicPassword;
	}
	
	public String toString() {
		// don't print password
		return "url=" + url + ", auth=" + authentication + ", basicUsername=" + basicUsername;
	}

	public void setPreferedEncoding(String preferedEncoding) {
		this.preferedEncoding = preferedEncoding;
	}

	public String getPreferedEncoding() {
		return preferedEncoding;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return version;
	}
	
	public String getExternalIdForId(String resourceId) {
		return (String)resourceIdMapping.get(resourceId);
	}

	public void setExternalIdForId(String resourceId, String externalId) {
		resourceIdMapping.put(resourceId, externalId);
	}

	public String getVersionForId(String resourceId) {
		return (String)versionMapping.get(resourceId);
	}

	public void setVersionForId(String resourceId, String version) {
		versionMapping.put(resourceId, version);
	}

}
