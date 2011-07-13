package info.simplecloud.scimproxy.trigger;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.EncodingFailed;
import info.simplecloud.core.execeptions.FailedToGetValue;
import info.simplecloud.core.execeptions.UnhandledAttributeType;
import info.simplecloud.core.execeptions.UnknownEncoding;
import info.simplecloud.scimproxy.config.CSP;
import info.simplecloud.scimproxy.config.Config;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class Trigger {

	// create
	public void create(ScimUser user) {
		ArrayList<CSP> servers = (ArrayList<CSP>) Config.getInstance().getDownStreamCSP();
		for (CSP csp : servers) {

			// Create an instance of HttpClient.
			HttpClient client = new HttpClient();
			client.getParams().setAuthenticationPreemptive(true);
			Credentials defaultcreds = new UsernamePasswordCredentials(csp.getBasicUsername(), csp.getBasicPassword());
			client.getState().setCredentials(AuthScope.ANY, defaultcreds);

			// Create a method instance.
			PostMethod method = new PostMethod(csp.getUrl() + "/User");

			// Provide custom retry handler is necessary
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

			try {

				method.setRequestBody(user.getUser(csp.getPreferedEncoding()));

				// Execute the method.
				int statusCode = client.executeMethod(method);

				if (statusCode != HttpStatus.SC_CREATED) {
					System.err.println("Method failed: " + method.getStatusLine());
				}

				// Read the response body.
				byte[] responseBody = method.getResponseBody();

				// Deal with the response.
				// Use caution: ensure correct character encoding and is not
				// binary data
				System.out.println(new String(responseBody));

			} catch (HttpException e) {
				System.err.println("Fatal protocol violation: " + e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Fatal transport error: " + e.getMessage());
				e.printStackTrace();
			} catch (UnknownEncoding e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EncodingFailed e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnhandledAttributeType e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// Release the connection.
				method.releaseConnection();
			}

		}
	}

	// edit

	// replace

	// delete

}
