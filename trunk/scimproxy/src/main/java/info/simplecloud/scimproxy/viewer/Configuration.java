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
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class Configuration extends HttpServlet {

    private Log log = LogFactory.getLog(Configuration.class);

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
		        resp.getWriter().println("<li><a href=\"Bulk\">Bulk</a></li>"); 
		        resp.getWriter().println("<li><a href=\"Configuration\">Configuration</a></li>"); 
		        resp.getWriter().println("</ul>"); 
		        resp.getWriter().println("</div"); 

		        resp.getWriter().println("<div id=\"content\">"); 
		        resp.getWriter().println("<div id=\"content\">"); 


		    	// Create an instance of HttpClient.
				HttpClient client = new HttpClient();
	
				// set auth if it's authenticated
				client.getParams().setAuthenticationPreemptive(true);
				Credentials defaultcreds = auth.getAuthUser().getCred();
				client.getState().setCredentials(AuthScope.ANY, defaultcreds);
				
				// Create a method instance.
				GetMethod method = new GetMethod("http://localhost:8080/v1/ServiceProviderConfig");
				method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		        
		        method.setRequestHeader("Accept", "application/json");
		        	
				int responseCode = client.executeMethod(method);
		        resp.getWriter().print("Resource added successfully.<br/><br/>");
		        resp.getWriter().print("<b>Response header:</b>");
		        resp.getWriter().print("<pre class=\"prettyprint\">");
		        
		        for(int i=0; i<method.getResponseHeaders().length; i++) {
			        resp.getWriter().print(method.getResponseHeaders()[i].getName() + ": " + method.getResponseHeaders()[i].getValue() + "<br/>");
		        }
		        
		        resp.getWriter().print("</pre>");
		        resp.getWriter().print("<b>Response body:</b><br/>");
		        resp.getWriter().print("<pre class=\"prettyprint\">");
	        	resp.getWriter().print(method.getResponseBodyAsString());
		        resp.getWriter().print("</pre>");
		        

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
			}
		
			resp.setStatus(HttpServletResponse.SC_OK);
		}
	}

}

