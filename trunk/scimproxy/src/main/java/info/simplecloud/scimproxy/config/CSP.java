package info.simplecloud.scimproxy.config;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;

public class CSP {

	public static final String AUTH_OAUTH2 = "OAuth2";
	public static String AUTH_BASIC = "basic";
	
	private String url = "";
	private String authentication ="";
	private String username = "";
	private String password = "";
	private String oAuth2AccessToken = "";
	private String oAuth2AuthorizationServer = "";
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

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
	
	public void setOAuth2AccessToken(String oAuth2AccessToken) {
		this.oAuth2AccessToken = oAuth2AccessToken;
	}

	public String getOAuth2AccessToken() {
		return oAuth2AccessToken;
	}

	public void setOAuth2AuthorizationServer(String oAuthAuthorizationServer) {
		this.oAuth2AuthorizationServer = oAuthAuthorizationServer;
	}
	
	public String getOAuthAuthorizationServer() {
		return this.oAuth2AuthorizationServer;
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
	
	// methods used for mapping aginst ids and versions
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

	public String toString() {
		// don't print password
		return "url=" + url + ", auth=" + authentication;
	}

    public String getAccessToken() {
        if (this.oAuth2AccessToken != null) {
            return this.oAuth2AccessToken;
        }

        try {
            HttpClient client = new HttpClient();
            client.getParams().setAuthenticationPreemptive(true);
            Credentials defaultcreds = new UsernamePasswordCredentials(this.getUsername(), this.getPassword());
            client.getState().setCredentials(AuthScope.ANY, defaultcreds);

            PostMethod method = new PostMethod(this.getOAuthAuthorizationServer());
            method.setRequestBody("grant_type=client_credentials");
            int responseCode = client.executeMethod(method);
            if (responseCode != 200) {
            	
                throw new RuntimeException("Failed to fetch access token form authorization server, " + this.getOAuthAuthorizationServer() + ", got response code "
                        + responseCode);
            }
            String responseBody = method.getResponseBodyAsString();
            JSONObject accessResponse = new JSONObject(responseBody);
            accessResponse.getString("access_token");
            return (this.oAuth2AccessToken = accessResponse.getString("access_token"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read response from authorizationServer at " + this.getOAuthAuthorizationServer(), e);
		}
    }


}
