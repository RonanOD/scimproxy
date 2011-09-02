package info.simplecloud.scimproxy.viewer;

import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.UnknownEncoding;
import info.simplecloud.scimproxy.authentication.Authenticator;
import info.simplecloud.scimproxy.config.Config;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class List extends HttpServlet {

    private Log log = LogFactory.getLog(List.class);

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Config config = null;
		config = Config.getInstance();
		// authenticate
		Authenticator auth = new Authenticator(config);
		if(!auth.authenticate(req, resp)) {
			resp.setHeader("WWW-authenticate", "basic  realm='scimproxy'");
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authenticate.");
		}
		else {
			
			String delete = req.getParameter("delete");
			String etag = req.getParameter("etag");
			
			if(delete != null && !"".equals(delete)) {
				// Create an instance of HttpClient.
				HttpClient client = new HttpClient();

				// set auth if it's authenticated
				client.getParams().setAuthenticationPreemptive(true);
				Credentials creds = auth.getCred();
				if(creds == null)
				{
					creds = new UsernamePasswordCredentials("default", "pw");
				}
				client.getState().setCredentials(AuthScope.ANY, creds);
				
				// Create a method instance.
				DeleteMethod method = new DeleteMethod("http://localhost:8080/v1/User/" + delete);
				method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
				method.setRequestHeader("Content-Type", "text/html; charset=UTF-8");
				method.setRequestHeader("ETag", etag);
				client.executeMethod(method);
			}
		
			// Create an instance of HttpClient.
			HttpClient client = new HttpClient();

			// set auth if it's authenticated
			client.getParams().setAuthenticationPreemptive(true);
			Credentials creds = auth.getCred();
			if(creds == null)
			{
				creds = new UsernamePasswordCredentials("default", "pw");
			}
			client.getState().setCredentials(AuthScope.ANY, creds);
			
			
			// Create a method instance.
			GetMethod method = new GetMethod("http://localhost:8080/v1/Users");
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
			method.setRequestHeader("Content-Type", "text/html; charset=UTF-8");

			try {
				// Execute the method.
				int statusCode = client.executeMethod(method);

				// Read the response body.
				byte[] responseBody = method.getResponseBody();

				log.debug("Response code: " + Integer.toString(statusCode));
				log.debug("Status line: " + method.getStatusLine());
				log.debug("Response: \n" + new String(responseBody));
				java.util.List<User> users = User.getUsers(new String(responseBody), User.ENCODING_JSON);

		        resp.getWriter().print("<html>");
		        resp.getWriter().print("<a href=\"List\">List</a>&nbsp;");
		        resp.getWriter().print("<a href=\"Add\">Add user</a>&nbsp;");
		        resp.getWriter().print("<a href=\"Batch\">Batch</a>");
		        resp.getWriter().print("<br/>");

                resp.getWriter().print("<table border=\"1\">");

				for (User scimUser : users) {
	                resp.getWriter().print("<tr>");
	                resp.getWriter().print("<td>");
	                if(scimUser.getMeta() != null) {
		                resp.getWriter().print("<a href=\"?delete=" + scimUser.getId() + "&etag=" + scimUser.getMeta().getVersion() + "\">delete</a><br/>");
		                resp.getWriter().print("<a href=\"Edit?id=" + scimUser.getId() + "&etag=" + scimUser.getMeta().getVersion() + "\">edit</a>");
	                }
	                else {
	                	resp.getWriter().print("No meta");
	                }
	                resp.getWriter().print("</td>");
	                resp.getWriter().print("<td>");
	                resp.getWriter().print(scimUser.toString() + "<br/>");
	                resp.getWriter().print("</tr>");
	                resp.getWriter().print("</td>");
				}
				if(users.size() == 0) {
	                resp.getWriter().print("<tr><td>No users in storage.</td></tr>");
				}
                resp.getWriter().print("</table>");
                resp.getWriter().print("</html>");
				
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
		
		
			resp.setStatus(HttpServletResponse.SC_OK);
		}
	}

}

