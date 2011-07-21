package info.simplecloud.scimproxy.viewer;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.UnknownEncoding;
import info.simplecloud.core.types.Meta;
import info.simplecloud.scimproxy.authentication.Authenticator;
import info.simplecloud.scimproxy.config.Config;
import info.simplecloud.scimproxy.storage.dummy.UserNotFoundException;
import info.simplecloud.scimproxy.user.User;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class Edit extends HttpServlet {

    private Log log = LogFactory.getLog(Edit.class);

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Config config = null;
		config = Config.getInstance();
		// authenticate
		Authenticator auth = new Authenticator(config);
		if(!auth.authenticate(req, resp)) {
			resp.setHeader("WWW-authenticate", "basic  realm='scimproxy'");
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authenticate.");
		}
		else {

			String save = req.getParameter("save");
			String id = req.getParameter("id");
			String userName = req.getParameter("userName");
			String etag = req.getParameter("etag");
				
	        resp.getWriter().print("<html>");
	        resp.getWriter().print("<a href=\"List\">List</a>&nbsp;");
	        resp.getWriter().print("<a href=\"Add\">Add user</a>");
	        resp.getWriter().print("<br/>");

	        if("true".equals(save)) {

				try {
					ScimUser user = User.getInstance().getUser(id);
					
					user.setId(id);
					user.setUserName(userName);
					if(user.getMeta() == null) {
						user.setMeta(new Meta());
					}
					user.getMeta().setVersion(etag);
					
					// Create an instance of HttpClient.
					HttpClient client = new HttpClient();
		
					// set auth if it's authenticated
					client.getParams().setAuthenticationPreemptive(true);
					Credentials defaultcreds = new UsernamePasswordCredentials("usr", "pw");
					client.getState().setCredentials(AuthScope.ANY, defaultcreds);
					
					String scimMethod = req.getParameter("scimMethod");
					
					if("put".equalsIgnoreCase(scimMethod)) {
						// Create a method instance.
						PutMethod method = new PutMethod("http://localhost:8080/User/" + user.getId());
						method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
				        method.setRequestHeader("etag", etag);
				        method.setRequestBody(user.getUser("JSON"));
				        
						int responseCode = client.executeMethod(method);
						if(responseCode == 200) {
					        resp.getWriter().print("User edited successfully.<br/><br/>");
						}
						else {
					        resp.getWriter().print("Failed to edit user.<br/><br/>");
						}					
					}
					else {
						// Create a method instance.
						PostMethod method = new PostMethod("http://localhost:8080/User/" + user.getId());
						method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
				        method.setRequestHeader("X-HTTP-Method-Override", "PATCH");
				        method.setRequestHeader("etag", etag);
				        method.setRequestBody(user.getUser("JSON"));
				        
						int responseCode = client.executeMethod(method);
						if(responseCode == 200) {
					        resp.getWriter().print("User edited successfully.<br/><br/>");
						}
						else {
					        resp.getWriter().print("Failed to edit user.<br/><br/>");
						}					
					}

				} catch (UserNotFoundException e) {
			        resp.getWriter().print("Can't find user.<br/>");
				} catch (UnknownEncoding e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        else {

		        if(id != null && !"".equals(id)) {
					try {
						ScimUser user = User.getInstance().getUser(id);
						resp.getWriter().print("<form method=\"POST\">");
		        	
						resp.getWriter().print("<input type=\"text\" name=\"id\" value=\"" + user.getId() +"\"/><br/>");
						resp.getWriter().print("<input type=\"text\" name=\"userName\" value=\"" + user.getUserName() +"\"/><br/>");
						resp.getWriter().print("<input type=\"text\" name=\"etag\" value=\"" + etag +"\"/><br/>");
						resp.getWriter().print("Put: <input type=\"radio\" name=\"scimMethod\" value=\"put\"/><br/>");
						resp.getWriter().print("Patch: <input type=\"radio\" name=\"scimMethod\" value=\"patch\"/><br/>");
						resp.getWriter().print("<input type=\"hidden\" name=\"save\" value=\"true\"/>");
						resp.getWriter().print("<input type=\"submit\" value=\"Edit\"/><br/>");
						resp.getWriter().print("</form>");
					} catch (UserNotFoundException e) {
				        resp.getWriter().print("Can't find user.<br/>");
					}
				}
		        else {
			        resp.getWriter().print("No id.<br/>");
		        }
	        }

	        resp.getWriter().print("</html>");
		
			resp.setStatus(HttpServletResponse.SC_OK);
		}
	}

}

