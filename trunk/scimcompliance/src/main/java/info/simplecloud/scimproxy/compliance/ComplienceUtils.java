package info.simplecloud.scimproxy.compliance;

import info.simplecloud.core.User;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.FileUtils;

public class ComplienceUtils {

    /**
     * Get a handle to a down stream HTTP REST server. Adds authentication
     * tokens if needed.
     * 
     * @param csp
     *            The down stream CSP.
     * @param method 
     * @return A http client handle with auth tokens already configured.
     */
    public static HttpClient getHttpClientWithAuth(CSP csp, HttpMethodBase method) {
        // Create an instance of HttpClient.
        HttpClient client = new HttpClient();
        
        if ("basic".equalsIgnoreCase(csp.getAuthentication())) {
            client.getParams().setAuthenticationPreemptive(true);
            Credentials defaultcreds = new UsernamePasswordCredentials(csp.getUsername(), csp.getPassword());
            client.getState().setCredentials(AuthScope.ANY, defaultcreds);
        }

        if ("oauth2".equalsIgnoreCase(csp.getAuthentication())) {
            client.getParams().setAuthenticationPreemptive(false);
            method.setRequestHeader("Authorization", "Bearer " + csp.getAccessToken());
            
        }

        if ("oauth2-v10".equalsIgnoreCase(csp.getAuthentication())) {
            client.getParams().setAuthenticationPreemptive(false);
            method.setRequestHeader("Authorization", "OAuth " + csp.getAccessTokenUserPass());
        }

        return client;
    }	


    /**
     * Shared method for setting config on the HttpMethod. Sets, for example,
     * retry handler.
     * 
     * @param method
     *            Method to add config to.
     */
    public static void configureMethod(HttpMethod method) {
        // Provide custom retry handler is necessary
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
    }
   

    /**
     * Different service providers may support different REST API versions.
     * 
     * @param csp
     *            The current CSP
     * @return A slash prepended string with version number directly from the
     *         config.
     */
    public static String getVersionPath(CSP csp) {
        String versionPath = csp.getVersion();
        if (!"".equals(versionPath)) {
            versionPath = "/" + versionPath;
        }
        return versionPath;
    }
    
    
    public static User getUser() {
        try {
            String fullUser = FileUtils.readFileToString(new File("src/main/resources/user_full.json"));
            return new User(fullUser, User.ENCODING_JSON);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	/**
	 * Gets the raw request, headers and body that's sent to the server.
	 * TODO: Replace this with the output from HttpClients wire logs!
	 * @param method The http method to read data from.
	 * @param body The request body that was sent.
	 * @return
	 */
	public static String getWire(HttpMethodBase method, String body) {
		String r = ">>>>>>";
		
		if(method instanceof PostMethod) {
			r += "POST ";
		}
		if(method instanceof GetMethod) {
			r += "GET ";
		}
		if(method instanceof PutMethod) {
			r += "PUT ";
		}
		if(method instanceof DeleteMethod) {
			r += "DELETE ";
		}
		// TODO, create a new method for PATCH (override open the POST method and change value, check open source code)

		String q = "";
		if(method.getQueryString() != null) {
			q = "?" + method.getQueryString();
		}
		r+= method.getPath() + q + " HTTP/1.1\n";
		
		Header[] headers = method.getRequestHeaders();
		for (Header header : headers) {
			r+= header.getName() + ": " + header.getValue() + "\n";
		}
		
		r += body + "\n\n";

		r += "<<<<<<\n\n";

		r+= method.getStatusLine() + "\n"; 
		Header[] respHeaders = method.getResponseHeaders();
		for (Header header : respHeaders) {
			r+= header.getName() + ": " + header.getValue() + "\n";
		}
		try {
			r += method.getResponseBodyAsString();
		} catch (IOException e) {
			r += "COULD NOT PARSE RESPONSE BODY\n";
			e.printStackTrace();
		}
		
		return r;
	}
		    
}
