package info.simplecloud.scimproxy.trigger;

import info.simplecloud.core.Group;
import info.simplecloud.core.Resource;
import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.UnknownEncoding;
import info.simplecloud.scimproxy.config.CSP;
import info.simplecloud.scimproxy.config.Config;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
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
     * logs to log in case of communication errors.
     */
    @SuppressWarnings("deprecation")
	public void create(Resource resource) {
        ArrayList<CSP> servers = (ArrayList<CSP>) Config.getInstance().getDownStreamCSP();
        for (CSP csp : servers) {

            HttpClient client = getHttpClientWithAuth(csp);

            String resourceString = null;
            String id = null;
            String endpoint = null;
            String createdResourceEndpoint = "";
            PostMethod method = null;
            try {
            	if(resource instanceof User) {
	        		User user = (User)resource;
	        		id = user.getId();
					resourceString = user.getUser(csp.getPreferedEncoding());
	                endpoint = csp.getUrl() + getVersionPath(csp) + "/User";
	        	}
	        	else if(resource instanceof Group) {
	        		Group group = (Group)resource;
	        		id = null;
	        		// TODO: Implement externalId on Resource!
	        		resourceString = group.getGroup(csp.getPreferedEncoding());
	                endpoint = csp.getUrl() + getVersionPath(csp) + "/Group";
	        	}

                method = new PostMethod(endpoint);

	            // Create a method instance.
	            configureMethod(method);

                method.setRequestBody(resourceString);

            	// Execute the method.
                int statusCode = client.executeMethod(method);

                // Read the response body.
                byte[] responseBody = method.getResponseBody();

                String externalId = null;
                String version = "";
            	if(resource instanceof User) {
                    User cspUser = new User(new String(responseBody), csp.getPreferedEncoding());
                    externalId = cspUser.getId();
                    version = cspUser.getMeta().getVersion();
                    createdResourceEndpoint = cspUser.getMeta().getLocation();
            	}
            	else if(resource instanceof Group) {
                    Group cspGroup = new Group(new String(responseBody), csp.getPreferedEncoding());
                    externalId = cspGroup.getId();
                    version = cspGroup.getMeta().getVersion();
                    createdResourceEndpoint = cspGroup.getMeta().getLocation();
            	}

                csp.setExternalIdForId(id, externalId);
                csp.setVersionForId(id, version);

                log.debug("Response from " + csp.getUrl());
                log.debug("- Code : " + Integer.toString(statusCode) + " - " + method.getStatusLine());
                log.debug("- Content: \n" + new String(responseBody));

            } catch (HttpException e) {
                log.error("Fatal protocol violation: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                log.error("Fatal transport violation: " + e.getMessage());
                e.printStackTrace();
            } catch (UnknownEncoding e) {
                log.error("Unknown encoding. XML and JSON is supported.");
                e.printStackTrace();
            } catch (InvalidUser e) {
                log.error("Invalid user returned from server.");
				e.printStackTrace();
            } catch (Exception e) {
                log.error("Unknown error.");
				e.printStackTrace();
			} finally {
                // Release the connection.
				if(method != null) {
	                method.releaseConnection();
				}
                log.info("Created resource " + resource.getId() + " downstreams at " + createdResourceEndpoint);
            }
        }
    }

    /**
     * Deletes the user to all configured servers down stream to this server.
     * Only logs to log when communication errors.
     */
    public void delete(Resource resource) {
        ArrayList<CSP> servers = (ArrayList<CSP>) Config.getInstance().getDownStreamCSP();
        for (CSP csp : servers) {

            HttpClient client = getHttpClientWithAuth(csp);

            String endpoint = "";
            String id = "";

        	if(resource instanceof User) {
        		User user = (User)resource;
        		id = user.getId();
                endpoint = csp.getUrl() + getVersionPath(csp) + "/User/";
        	}
        	else if(resource instanceof Group) {
        		Group group = (Group)resource;
        		id = null;
        		// TODO: Implement externalId on Resource!
                endpoint = csp.getUrl() + getVersionPath(csp) + "/Group/";
        	}
        	
        	String externalId = csp.getExternalIdForId(id);
        	String version = csp.getVersionForId(id);
        	endpoint = endpoint + externalId;

            // Create a method instance.
            DeleteMethod method = new DeleteMethod(endpoint);
            configureMethod(method);
            method.setRequestHeader(new Header("ETag", version));

            try {
                // Execute the method.
                int statusCode = client.executeMethod(method);

                // Read the response body.
                byte[] responseBody = method.getResponseBody();

                if (statusCode != 200) {
                    log.error("Failed to delete resource " + id + " downstreams at " + csp.getUrl());
                }
                log.debug("Response from " + csp.getUrl());
                log.debug("- Code : " + Integer.toString(statusCode) + " - " + method.getStatusLine());
                log.debug("- Content: \n" + new String(responseBody));

            } catch (HttpException e) {
                log.error("Fatal protocol violation: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                log.error("Fatal transport violation: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                log.error("Unknown error.");
				e.printStackTrace();
			} finally {
                // Release the connection.
				if(method != null) {
	                method.releaseConnection();
				}
                log.info("Deleted user " + resource.getId() + " downstreams at " + endpoint);
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
