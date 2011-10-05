package info.simplecloud.scimproxy.viewer;

import info.simplecloud.scimproxy.authentication.Authenticator;
import info.simplecloud.scimproxy.config.Config;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringEscapeUtils;
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
			String etag = req.getParameter("etag");
			String encoding = req.getParameter("encoding");
			String resource = req.getParameter("resource");
			String type = req.getParameter("type");
				

	        resp.getWriter().println("<html>");
	        resp.getWriter().println("<head>");
	        resp.getWriter().println("<script type=\"text/javascript\" src=\"/js/jquery-1.6.2.min.js\"></script>");
	        resp.getWriter().println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/style.css\" />");
	        resp.getWriter().println("<link href=\"/css/prettify.css\" type=\"text/css\" rel=\"stylesheet\" />");
	        resp.getWriter().println("<script type=\"text/javascript\" src=\"/js/prettify/prettify.js\"></script>");
	        resp.getWriter().println("</head>");
	        resp.getWriter().println("<body onload=\"prettyPrint()\">");

	        resp.getWriter().println("<div id=\"container\">");
	        resp.getWriter().println("<div id=\"header\"><h1>Simple Cloud Identity Management</h1></div>"); 

	        resp.getWriter().println("<div id=\"navigation\">"); 
	        resp.getWriter().println("<ul>"); 
	        resp.getWriter().println("<li><a href=\"List?type=user\">Users</a></li>"); 
	        resp.getWriter().println("<li><a href=\"List?type=group\">Groups</a></li>"); 
	        resp.getWriter().println("<li><a href=\"Add\">Add</a></li>"); 
	        resp.getWriter().println("<li><a href=\"Bulk\">Bulk</a></li>"); 
	        resp.getWriter().println("<li><a href=\"Configuration\">Configuration</a></li>"); 
	        resp.getWriter().println("</ul>"); 
	        resp.getWriter().println("</div"); 

	        resp.getWriter().println("<div id=\"content\">"); 
	        resp.getWriter().println("<div id=\"content\">"); 

	        resp.getWriter().println("<h2>Edit resource " + id + "</h2>");
	        
	        if("true".equals(save)) {
				
				// Create an instance of HttpClient.
				HttpClient client = new HttpClient();
	
				// set auth if it's authenticated
				client.getParams().setAuthenticationPreemptive(true);
				Credentials defaultcreds = auth.getAuthUser().getCred();
				client.getState().setCredentials(AuthScope.ANY, defaultcreds);
				
				String scimMethod = req.getParameter("scimMethod");
				
				if("put".equalsIgnoreCase(scimMethod)) {
					// Create a method instance.
					PutMethod method = new PutMethod("http://localhost:8080/v1/" + type + "/" + id);
					method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
			        method.setRequestHeader("etag", etag);
			        method.setRequestHeader("Accept", encoding);
			        method.setRequestBody(resource);
			        
					int responseCode = client.executeMethod(method);
					if(responseCode == 200) {
				        resp.getWriter().print("Resource replaced successfully.<br/><br/>");
				        resp.getWriter().print("<b>Response header:</b>");
				        resp.getWriter().print("<pre class=\"prettyprint\">");
				        
				        for(int i=0; i<method.getResponseHeaders().length; i++) {
					        resp.getWriter().print("<br/>" + method.getResponseHeaders()[i].getName() + ": " + method.getResponseHeaders()[i].getValue());
				        }
				        
				        resp.getWriter().print("</pre>");
				        resp.getWriter().print("<b>Response body:</b><br/>");
				        resp.getWriter().print("<pre class=\"prettyprint\">");
				        if("application/json".equals(encoding)) {
				        	resp.getWriter().print(method.getResponseBodyAsString());
				        }
				        else {
				        	resp.getWriter().print(StringEscapeUtils.escapeHtml(method.getResponseBodyAsString()));
				        }
				        resp.getWriter().print("</pre>");
					}
					else {
				        resp.getWriter().print("Failed to replace resource.<br/><br/>");
					}					
				}
				else {
					// Create a method instance.
					PostMethod method = new PostMethod("http://localhost:8080/v1/" + type + "/" + id);
					method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
			        method.setRequestHeader("X-HTTP-Method-Override", "PATCH");
			        method.setRequestHeader("etag", etag);
			        method.setRequestHeader("Accept", encoding);
			        method.setRequestBody(resource);
			        
					int responseCode = client.executeMethod(method);
					if(responseCode == 200) {
				        resp.getWriter().print("Resource edited successfully.<br/><br/>");
					}
					else {
				        resp.getWriter().print("Failed to replace resource.<br/><br/>");
					}					
				}

	        }
	        else {

		        if(id != null && !"".equals(id)) {
					resp.getWriter().print("<form method=\"POST\">");
	        	
			        resp.getWriter().print("<table>");
			        resp.getWriter().print("<input type=\"radio\" name=\"encoding\" value=\"application/json\" checked=\"true\" /> JSON<br/>");
			        resp.getWriter().print("<input type=\"radio\" name=\"encoding\" value=\"application/xml\" /> XML<br/>");
			        resp.getWriter().print("<textarea cols=\"50\" rows=\"15\" name=\"resource\"></textarea><br/>");
					resp.getWriter().print("Put: <input type=\"radio\" name=\"scimMethod\" value=\"put\"/><br/>");
					resp.getWriter().print("Patch: <input type=\"radio\" name=\"scimMethod\" value=\"patch\"/><br/>");
					resp.getWriter().print("<input type=\"hidden\" name=\"save\" value=\"true\"/>");
					resp.getWriter().print("<input type=\"hidden\" name=\"etag\" value=\"" + etag + "\"/>");
					resp.getWriter().print("<input type=\"hidden\" name=\"type\" value=\"" + type + "\"/>");
					resp.getWriter().print("<input type=\"hidden\" name=\"id\" value=\"" + id + "\"/>");
					resp.getWriter().print("<input type=\"submit\" value=\"Edit\"/><br/>");
					resp.getWriter().print("</form>");
				}
		        else {
			        resp.getWriter().print("No id.<br/>");
		        }
	        }

	        resp.getWriter().println("</p>"); 
	        resp.getWriter().println("</div>"); 


            resp.getWriter().println("<div id=\"footer\">");
            resp.getWriter().println("SCIM Viewer");
            resp.getWriter().println("</div>");
            
            resp.getWriter().println("</div>");
            
	        resp.getWriter().println("</body>");
            resp.getWriter().println("</html>");
		
			resp.setStatus(HttpServletResponse.SC_OK);
		}
	}

}

