package info.simplecloud.scimproxy.trigger;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.InvalidUser;
import info.simplecloud.core.execeptions.UnknownEncoding;
import info.simplecloud.scimproxy.config.CSP;
import info.simplecloud.scimproxy.config.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Trigger handles all communication to other server, usually down stream
 * servers that listen to all changes done to the storage.
 */
public class Trigger {

	private Log log = LogFactory.getLog(Trigger.class);

	/**
	 * Sends the newly created user down streams to all configured servers. Only
	 * logs to log when communication errors.
	 */
	public void create(ScimUser user) {
		ArrayList<CSP> servers = (ArrayList<CSP>) Config.getInstance().getDownStreamCSP();
		for (CSP csp : servers) {

			HttpClient client = getHttpClientWithAuth(csp);

			// Create a method instance.
			PostMethod method = new PostMethod(csp.getUrl() + getVersionPath(csp) + "/User");
			configureMethod(method);

			try {
				method.setRequestBody(user.getUser(csp.getPreferedEncoding()));

				// Execute the method.
				int statusCode = client.executeMethod(method);

				// Read the response body.
				byte[] responseBody = method.getResponseBody();

				log.debug("Response code: " + Integer.toString(statusCode));
				log.debug("Status line: " + method.getStatusLine());
				log.debug("Response: \n" + new String(responseBody));

			} catch (HttpException e) {
				log.error("Fatal protocol violation: " + e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				log.error("Fatal transport violation: " + e.getMessage());
				e.printStackTrace();
			} catch (UnknownEncoding e) {
				log.error("Unknown encoding. XML and JSON is supported.");
				e.printStackTrace();
			} finally {
				// Release the connection.
				method.releaseConnection();
				log.info("Created user " + user.getId() + " downstreams at: " + csp.getUrl());
			}
		}
	}

	/**
	 * Deletes the user to all configured servers down stream to this server.
	 * Only logs to log when communication errors.
	 */
	public void delete(ScimUser user) {
		ArrayList<CSP> servers = (ArrayList<CSP>) Config.getInstance().getDownStreamCSP();
		for (CSP csp : servers) {

			HttpClient client = getHttpClientWithAuth(csp);

			// Create a method instance.
			DeleteMethod method = new DeleteMethod(csp.getUrl() + getVersionPath(csp) + "/User/" + user.getId());
			configureMethod(method);
			method.setRequestHeader(new Header("ETag", user.getMeta().getVersion()));

			try {
				// Execute the method.
				int statusCode = client.executeMethod(method);

				// Read the response body.
				byte[] responseBody = method.getResponseBody();

				if (statusCode != 200) {
					log.error("Failed to delete user downstreams at " + csp.getUrl());
				}
				log.debug("Response code: " + Integer.toString(statusCode));
				log.debug("Status line: " + method.getStatusLine());
				log.debug("Response: \n" + new String(responseBody));

			} catch (HttpException e) {
				log.error("Fatal protocol violation: " + e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				log.error("Fatal transport violation: " + e.getMessage());
				e.printStackTrace();
			} finally {
				// Release the connection.
				method.releaseConnection();
				log.info("Deleted user " + user.getId() + " downstreams at: " + csp.getUrl());
			}
		}
	}

	/**
	 * Patch a user in all configured down stream server.
	 * 
	 * @param query
	 *            Patch SCIM user.
	 * @param userId
	 *            User to patch.
	 * @param etag
	 *            Current ETag for this user.
	 */
	public void patch(String query, String userId, String etag) {

		log.info("Patch user " + userId + " downstreams. IMPLEMENT!");

	}

	/**
	 * Put a user in all configured down stream server.
	 * 
	 * @param query
	 *            Patch SCIM user.
	 * @param userId
	 *            User to patch.
	 * @param etag
	 *            Current ETag for this user.
	 */
	public void put(String query, String userId, String etag) {
		log.info("Patch user " + userId + " downstreams. IMPLEMENT!");
	}

	public ScimUser get(String userId) {
		log.info("Patch user " + userId + " downstreams. IMPLEMENT!");
		return null;
	}

	public List<ScimUser> query(String sortBy, String sortOrder, String filterBy, String filterValue, String filterOp) {

		String query = "";
		// TODO: make use of the variables sent to this method!

		List<ScimUser> users = new ArrayList<ScimUser>();
		ArrayList<CSP> servers = (ArrayList<CSP>) Config.getInstance().getUpStreamCSP();
		for (CSP csp : servers) {

			HttpClient client = getHttpClientWithAuth(csp);

			// Create a method instance.
			GetMethod method = new GetMethod(csp.getUrl() + getVersionPath(csp) + "/Users?" + query);
			configureMethod(method);

			try {
				// Execute the method.
				int statusCode = client.executeMethod(method);

				// Read the response body.
				String responseBody = method.getResponseBodyAsString();

				if (statusCode != 200) {
					log.error("Failed to delete user downstreams at " + csp.getUrl());
				}
				log.debug("Response code: " + Integer.toString(statusCode));
				log.debug("Status line: " + method.getStatusLine());
				log.debug("Response: \n" + responseBody);

				users.addAll(ScimUser.getScimUsers(responseBody, "JSON"));

			} catch (HttpException e) {
				log.error("Fatal protocol violation: " + e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				log.error("Fatal transport violation: " + e.getMessage());
				e.printStackTrace();
			} catch (UnknownEncoding e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidUser e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// Release the connection.
				method.releaseConnection();
			}
		}

		return users;
	}

	/**
	 * Get a handle to a down stream HTTP REST server. Adds authentication
	 * tokens if needed.
	 * 
	 * @param csp
	 *            The down stream CSP.
	 * @return A http client handle with auth tokens already configured.
	 */
	private HttpClient getHttpClientWithAuth(CSP csp) {
		// Create an instance of HttpClient.
		HttpClient client = new HttpClient();

		// set auth if it's authenticated
		if ("basic".equalsIgnoreCase(csp.getAuthentication())) {
			client.getParams().setAuthenticationPreemptive(true);
			Credentials defaultcreds = new UsernamePasswordCredentials(csp.getBasicUsername(), csp.getBasicPassword());
			client.getState().setCredentials(AuthScope.ANY, defaultcreds);
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
	private void configureMethod(HttpMethod method) {
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
	private String getVersionPath(CSP csp) {
		String versionPath = csp.getVersion();
		if (!"".equals(versionPath)) {
			versionPath = "/" + versionPath;
		}
		return versionPath;
	}

}
