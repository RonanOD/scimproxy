package info.simplecloud.scimproxy.trigger;

import info.simplecloud.core.Group;
import info.simplecloud.core.Resource;
import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.UnknownEncoding;
import info.simplecloud.scimproxy.HttpGenerator;
import info.simplecloud.scimproxy.config.CSP;
import info.simplecloud.scimproxy.config.Config;
import info.simplecloud.scimproxy.viewer.version2.Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mortbay.util.UrlEncoded;

/**
 * Trigger handles all communication to other server, usually down stream
 * servers that listen to all changes done to the storage.
 */
public class Trigger {

    private Log log = LogFactory.getLog(Trigger.class);

    /**
     * Sends the newly created resource down streams to all configured servers. Only
     * logs to log in case of communication errors.
     */
    @SuppressWarnings("deprecation")
	public void create(Resource resource) {
        ArrayList<CSP> servers = (ArrayList<CSP>) Config.getInstance().getDownStreamCSP();
        for (CSP csp : servers) {

            String resourceString = null;
            String id = null;
            String endpoint = null;
            String createdResourceEndpoint = "";
            PostMethod method = null;
            try {
            	if(resource instanceof User) {
            		User tmp = (User)resource;
            		String tmpUsr = tmp.getUser(Resource.ENCODING_JSON);
	        		User user = new User(tmpUsr, Resource.ENCODING_JSON);
	        		id = user.getId();
	        		user.setActive(true);
	        		user.setId(null); // reset id for salesforce
					resourceString = user.getUser(csp.getPreferedEncoding());
	                endpoint = csp.getUrl() + getVersionPath(csp) + "/User";
	        	}
	        	else if(resource instanceof Group) {
	        		Group tmp = (Group)resource;
            		String tmpUsr = tmp.getGroup(Resource.ENCODING_JSON);
	        		Group group = new Group(tmpUsr, Resource.ENCODING_JSON);
	        		id = group.getId();
	        		group.setId(null); // reset id for salesforce
	        		resourceString = group.getGroup(csp.getPreferedEncoding());
	                endpoint = csp.getUrl() + getVersionPath(csp) + "/Group";
	        	}

                method = new PostMethod(endpoint);
                HttpClient client = getHttpClientWithAuth(csp, method);

	            // Create a method instance.
	            configureMethod(method);


                if("xml".equalsIgnoreCase(csp.getPreferedEncoding())) {
                    method.setRequestHeader(new Header("Accept", "application/xml"));
                    method.setRequestHeader(new Header("Content-Type", "application/xml"));
                }
                else {
                    method.setRequestHeader(new Header("Accept", "application/json"));
                    method.setRequestHeader(new Header("Content-Type", "application/json"));
                }

                method.setRequestBody(resourceString);

            	// Execute the method.
                int statusCode = client.executeMethod(method);

                // Read the response body.
                byte[] responseBody = method.getResponseBody();

                String serverResp = new String(responseBody);

                if(statusCode == 201) {
                    String externalId = null;
                    String version = "";
                	if(resource instanceof User) {
                        User cspUser = new User(serverResp, csp.getPreferedEncoding());
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

                    log.info("Created resource " + resource.getId() + " downstreams at " + createdResourceEndpoint);
                }
                else {
                    log.error("Failed to send resource " + id + " downstreams to " + csp.getUrl());
                    log.error(serverResp);
                }


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
            }
        }
    }

    /**
     * Deletes the Resource to all configured servers down stream to this server.
     * Only logs to log when communication errors.
     */
    public void delete(Resource resource) {
        ArrayList<CSP> servers = (ArrayList<CSP>) Config.getInstance().getDownStreamCSP();
        for (CSP csp : servers) {

            String endpoint = "";
            String id = "";
            DeleteMethod method = null;

            try {
	
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
	            method = new DeleteMethod(endpoint);
                HttpClient client = getHttpClientWithAuth(csp, method);

                configureMethod(method);
	            method.setRequestHeader(new Header("ETag", version));
	
	            if("xml".equalsIgnoreCase(csp.getPreferedEncoding())) {
	//                method.setRequestHeader(new Header("Accept", "application/xml"));
                    method.setRequestHeader(new Header("Content-Type", "application/xml"));
	            }
	            else {
//	                method.setRequestHeader(new Header("Accept", "application/json"));
                    method.setRequestHeader(new Header("Content-Type", "application/json"));
	            }

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
                log.info("Deleted resource " + resource.getId() + " downstreams at " + endpoint);
            }
        }
    }

    /**
     * Patch a Resource in all configured down stream server.
     * 
     * @param resource
     *            Resource to patch.
     */
    @SuppressWarnings("deprecation")
	public void patch(Resource resource) {
        ArrayList<CSP> servers = (ArrayList<CSP>) Config.getInstance().getDownStreamCSP();
        for (CSP csp : servers) {

            String endpoint = "";
            String id = "";
            PostMethod method = null;
            String resourceString = "";
            String createdResourceEndpoint = "";

            try {

	        	if(resource instanceof User) {
	        		User user = (User)resource;
	        		id = user.getId();
	                endpoint = csp.getUrl() + getVersionPath(csp) + "/User/";
					resourceString = user.getUser(csp.getPreferedEncoding());
	        	}
	        	else if(resource instanceof Group) {
	        		Group group = (Group)resource;
	        		id = group.getId();
	                endpoint = csp.getUrl() + getVersionPath(csp) + "/Group/";
					resourceString = group.getGroup(csp.getPreferedEncoding());
	        	}
	        	
	        	String externalId = csp.getExternalIdForId(id);
	        	String version = csp.getVersionForId(id);
	        	endpoint = endpoint + externalId;
	
	            // Create a method instance.
	            method = new PostMethod(endpoint);
                HttpClient client = getHttpClientWithAuth(csp, method);

	            configureMethod(method);
	            method.setRequestHeader(new Header("ETag", version));
	            method.setRequestHeader(new Header("X-HTTP-Method-Override", "PATCH"));
	            
	            if("xml".equalsIgnoreCase(csp.getPreferedEncoding())) {
	                method.setRequestHeader(new Header("Accept", "application/xml"));
                    method.setRequestHeader(new Header("Content-Type", "application/xml"));
	            }
	            else {
	                method.setRequestHeader(new Header("Accept", "application/json"));
                    method.setRequestHeader(new Header("Content-Type", "application/json"));
	            }
            	
	            method.setRequestBody(resourceString);

                // Execute the method.
                int statusCode = client.executeMethod(method);
                
                // Read the response body.
                byte[] responseBody = method.getResponseBody();
                String respBody = new String(responseBody);

                if (statusCode != 200) {
                    log.error("Failed to patch resource " + id + " downstreams at " + csp.getUrl());
                }
                else {
                    externalId = null;
                    version = "";
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
                log.info("Paching resource " + resource.getId() + " downstreams at " + createdResourceEndpoint);
            }
        }
    }

    /**
     * Replaces a Resorce in all configured down stream server.
     * 
     * @param resource Resource to replace. Id is included and can't be changed.
     */
    public void put(Resource resource) {
        ArrayList<CSP> servers = (ArrayList<CSP>) Config.getInstance().getDownStreamCSP();
        for (CSP csp : servers) {

            String endpoint = "";
            String id = "";
            PutMethod method = null;
            String resourceString = "";
            String createdResourceEndpoint = "";

            try {

	        	if(resource instanceof User) {
	        		User user = (User)resource;
	        		id = user.getId();
	                endpoint = csp.getUrl() + getVersionPath(csp) + "/User/";
					resourceString = user.getUser(csp.getPreferedEncoding());
	        	}
	        	else if(resource instanceof Group) {
	        		Group group = (Group)resource;
	        		id = group.getId();
	                endpoint = csp.getUrl() + getVersionPath(csp) + "/Group/";
					resourceString = group.getGroup(csp.getPreferedEncoding());
	        	}
	        	
	        	String externalId = csp.getExternalIdForId(id);
	        	String version = csp.getVersionForId(id);
	        	endpoint = endpoint + externalId;
	
	            // Create a method instance.
	            method = new PutMethod(endpoint);
                HttpClient client = getHttpClientWithAuth(csp, method);

	            configureMethod(method);
	            method.setRequestHeader(new Header("ETag", version));
	            
	            if("xml".equalsIgnoreCase(csp.getPreferedEncoding())) {
	                method.setRequestHeader(new Header("Accept", "application/xml"));
                    method.setRequestHeader(new Header("Content-Type", "application/xml"));
	            }
	            else {
	                method.setRequestHeader(new Header("Accept", "application/json"));
                    method.setRequestHeader(new Header("Content-Type", "application/json"));
	            }
            	
	            method.setRequestBody(resourceString);

                // Execute the method.
                int statusCode = client.executeMethod(method);

                // Read the response body.
                byte[] responseBody = method.getResponseBody();
                String respBody = new String(responseBody);
                
                if (statusCode != 200) {
                    log.error("Failed to replace resource " + id + " downstreams at " + csp.getUrl());
                } 
                else {
                    externalId = null;
                    version = "";
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
                log.info("Replacing resource " + resource.getId() + " downstreams at " + createdResourceEndpoint);
            }
        }
    }

	public void changePassword(User scimUser, String password) {
        log.info("Change password on User " + scimUser.getId() + " downstream not implemented.");
	}



    /**
     * Get a handle to a down stream HTTP REST server. Adds authentication
     * tokens if needed.
     * 
     * @param csp
     *            The down stream CSP.
     * @param method 
     * @return A http client handle with auth tokens already configured.
     */
    private HttpClient getHttpClientWithAuth(CSP csp, HttpMethodBase method) {
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
