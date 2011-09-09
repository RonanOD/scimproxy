package info.simplecloud.scimproxy.viewer;

import info.simplecloud.core.Group; 
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
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringEscapeUtils;
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
			String type = req.getParameter("type");
			
			if(delete != null && !"".equals(delete)) {
				// Create an instance of HttpClient.
				HttpClient client = new HttpClient();

				// set auth if it's authenticated
				client.getParams().setAuthenticationPreemptive(true);
				Credentials creds = auth.getAuthUser().getCred();
				client.getState().setCredentials(AuthScope.ANY, creds);
				
				// Create a method instance.
				DeleteMethod method = new DeleteMethod("http://localhost:8080/v1/" + type + "/" + delete);
				method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
				method.setRequestHeader("Content-Type", "text/html; charset=UTF-8");
				method.setRequestHeader("ETag", etag);
				client.executeMethod(method);
			}
		
			// Create an instance of HttpClient.
			HttpClient client = new HttpClient();

			// set auth if it's authenticated
			client.getParams().setAuthenticationPreemptive(true);
			Credentials creds = auth.getAuthUser().getCred();
			client.getState().setCredentials(AuthScope.ANY, creds);
			
			GetMethod methodGroups = null;
			GetMethod methodUsers = null;
			
			try {
				
		        resp.getWriter().println("<html>");
		        resp.getWriter().println("<head>");
		        resp.getWriter().println("<link rel=\"stylesheet\" type=\"text/css\" href=\"http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.1/themes/base/jquery-ui.css\">");
		        resp.getWriter().println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/prettify.css\" />");
		        resp.getWriter().println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/style.css\" />");
		        resp.getWriter().println("<script type=\"text/javascript\" src=\"/js/prettify/prettify.js\"></script>");
		        resp.getWriter().println("<script type=\"text/javascript\" src=\"http://code.jquery.com/jquery-latest.js\"></script>");

		        
		        resp.getWriter().println("</head>");
		        resp.getWriter().println("<body onload=\"prettyPrint()\">");

		        resp.getWriter().println("<div id=\"container\">");
		        resp.getWriter().println("<div id=\"header\"><h1>Simple Cloud Identity Management</h1></div>"); 

		        resp.getWriter().println("<div id=\"navigation\">"); 
		        resp.getWriter().println("<ul>"); 
		        resp.getWriter().println("<li><a href=\"List?type=user\">Users</a></li>"); 
		        resp.getWriter().println("<li><a href=\"List?type=group\">Groups</a></li>"); 
		        resp.getWriter().println("<li><a href=\"Add\">Add</a></li>"); 
		        resp.getWriter().println("<li><a href=\"Batch\">Batch</a></li>"); 
		        resp.getWriter().println("</ul>"); 
		        resp.getWriter().println("</div"); 

		        resp.getWriter().println("<div id=\"content\">"); 
		        resp.getWriter().println("<div id=\"content\">"); 

		        resp.getWriter().println("<p><button>Change format</button><br/>"); 

		        
				if("group".equalsIgnoreCase(type)) {

					
					// Create a method instance.
					methodGroups = new GetMethod("http://localhost:8080/v1/Groups");
					methodGroups.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
					methodGroups.setRequestHeader("Content-Type", "text/html; charset=UTF-8");
	
					// Execute the method.
					int statusCodeGroup = client.executeMethod(methodGroups);
	
					// Read the response body.
					byte[] responseBodyGroup = methodGroups.getResponseBody();
	
					log.debug("Response code: " + Integer.toString(statusCodeGroup));
					log.debug("Status line: " + methodGroups.getStatusLine());
					log.debug("Response: \n" + new String(responseBodyGroup));
	
					String groupsStr = new String(responseBodyGroup);
					
					java.util.List<Group> groups = Group.getGroups(groupsStr, Group.ENCODING_JSON);
	
	
			        resp.getWriter().println("<h2>Groups</h2>"); 
	
			        resp.getWriter().println("<p>"); 
			        
	                resp.getWriter().println("<table class=\"list\">");
	
	                resp.getWriter().println("<tr>");
	                resp.getWriter().println("<th>&nbsp;</th>");
	                resp.getWriter().println("<th>id</th>");
	                resp.getWriter().println("<th>display name</th>");
	                resp.getWriter().println("<th><div class=\"resource\">JSON</div><div class=\"resource\" style=\"display: none\">XML</div></th>");
	                resp.getWriter().println("</tr>");
	
					for (Group scimGroup : groups) {
		                resp.getWriter().println("<tr>");
		                resp.getWriter().println("<td>");
		                if(scimGroup.getMeta() != null) {
			                resp.getWriter().println("<a href=\"?type=Group&delete=" + scimGroup.getId() + "&etag=" + scimGroup.getMeta().getVersion() + "\">delete</a><br/>");
			                resp.getWriter().println("<a href=\"Edit?type=Group&id=" + scimGroup.getId() + "&etag=" + scimGroup.getMeta().getVersion() + "\">edit</a>");
		                }
		                else {
		                	resp.getWriter().println("No meta");
		                }
		                resp.getWriter().println("</td>");
		                resp.getWriter().println("<td>");
		                resp.getWriter().println(scimGroup.getId());
		                resp.getWriter().println("</td>");
		                resp.getWriter().println("<td>");
		                resp.getWriter().println(scimGroup.getDisplayName());
		                resp.getWriter().println("</td>");
		                resp.getWriter().println("<td>");
		                resp.getWriter().println("<div class=\"resource\"><pre class=\"prettyprint\" id=\"javascript\">" + scimGroup.getGroup(Group.ENCODING_JSON) + "</pre></div>");
		                resp.getWriter().println("<div class=\"resource\" style=\"display: none\"><pre class=\"prettyprint\">" + StringEscapeUtils.escapeHtml(scimGroup.getGroup(Group.ENCODING_XML)) + "</pre></div>");
		                resp.getWriter().println("</td>");
		                resp.getWriter().println("</tr>");
					}
					if(groups.size() == 0) {
		                resp.getWriter().println("<tr><td colspan=\"5\">No groups in storage.</td></tr>");
					}
	                resp.getWriter().println("</table>");
	               
			        resp.getWriter().println("</p>"); 
		        
				}
				else {
					// Create a method instance.
					methodUsers = new GetMethod("http://localhost:8080/v1/Users");
					methodUsers.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
					methodUsers.setRequestHeader("Content-Type", "text/html; charset=UTF-8");
	
					// Execute the method.
					int statusCodeUser = client.executeMethod(methodUsers);
	
					// Read the response body.
					byte[] responseBodyUser = methodUsers.getResponseBody();
	
					log.debug("Response code: " + Integer.toString(statusCodeUser));
					log.debug("Status line: " + methodUsers.getStatusLine());
					log.debug("Response: \n" + new String(responseBodyUser));
					java.util.List<User> users = User.getUsers(new String(responseBodyUser), User.ENCODING_JSON);
	
	
			        resp.getWriter().println("<h2>Users</h2>"); 
	
	                resp.getWriter().println("<table class=\"list\">");
	
	                resp.getWriter().println("<tr>");
	                resp.getWriter().println("<th>&nbsp;</th>");
	                resp.getWriter().println("<th>id</th>");
	                resp.getWriter().println("<th>user name</th>");
	                resp.getWriter().println("<th><div class=\"resource\">JSON</div><div class=\"resource\" style=\"display: none\">XML</div></th>");
	                resp.getWriter().println("</tr>");
	
					for (User scimUser : users) {
		                resp.getWriter().println("<tr>");
		                resp.getWriter().println("<td>");
		                if(scimUser.getMeta() != null) {
			                resp.getWriter().println("<a href=\"?type=User&delete=" + scimUser.getId() + "&etag=" + scimUser.getMeta().getVersion() + "\">delete</a><br/>");
			                resp.getWriter().println("<a href=\"Edit?type=User&id=" + scimUser.getId() + "&etag=" + scimUser.getMeta().getVersion() + "\">edit</a>");
		                }
		                else {
		                	resp.getWriter().println("No meta");
		                }
		                resp.getWriter().println("</td>");
		                resp.getWriter().println("<td>");
		                resp.getWriter().println(scimUser.getId());
		                resp.getWriter().println("</td>");
		                resp.getWriter().println("<td>");
		                resp.getWriter().println(scimUser.getUserName());
		                resp.getWriter().println("</td>");
		                resp.getWriter().println("<td>");
		                resp.getWriter().println("<div class=\"resource\"><pre class=\"prettyprint\" id=\"javascript\">" + scimUser.getUser(User.ENCODING_JSON) + "</pre></div>");
		                resp.getWriter().println("<div class=\"resource\" style=\"display: none\"><pre class=\"prettyprint\">" + StringEscapeUtils.escapeHtml(scimUser.getUser(User.ENCODING_XML)) + "</pre></div>");
		                resp.getWriter().println("</td>");
		                resp.getWriter().println("</tr>");
					}
					if(users.size() == 0) {
		                resp.getWriter().println("<tr><td colspan=\"5\">No users in storage.</td></tr>");
					}
	                resp.getWriter().println("</table>");
	               
			        resp.getWriter().println("</p>"); 
			    
				}
		        

		        resp.getWriter().println("</div>"); 


                resp.getWriter().println("<div id=\"footer\">");
                resp.getWriter().println("SCIM Viewer");
                resp.getWriter().println("</div>");
                
                resp.getWriter().println("</div>");
                
		        resp.getWriter().println("<script>");
		        resp.getWriter().println("$(\"button\").click(function () {");
		        resp.getWriter().println("$(\"div.resource\").toggle();");
		        resp.getWriter().println("});");
		        resp.getWriter().println("</script>");
		        resp.getWriter().println("</body>");
                resp.getWriter().println("</html>");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// Release the connection.
				if(methodGroups != null) {
					methodGroups.releaseConnection();
				}
				if(methodUsers != null) {
					methodUsers.releaseConnection();
				}
			}
		
			resp.setStatus(HttpServletResponse.SC_OK);
		}
	}

}

