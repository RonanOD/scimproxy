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
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

@SuppressWarnings("serial")
public class Batch extends HttpServlet {


	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req, resp);
	}

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
			
			String batch = req.getParameter("batch");
			String encoding = req.getParameter("encoding");


	        resp.getWriter().println("<html>");
	        resp.getWriter().println("<head>");
	        resp.getWriter().println("<script type=\"text/javascript\" src=\"/js/jquery-1.6.2.min.js\"></script>");
	        resp.getWriter().println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/style.css\" />");
	        resp.getWriter().println("</head>");
	        resp.getWriter().println("<body>");

	        resp.getWriter().println("<div id=\"container\">");
	        resp.getWriter().println("<div id=\"header\"><h1>Simple Cloud Identity Management</h1></div>"); 

	        resp.getWriter().println("<div id=\"navigation\">"); 
	        resp.getWriter().println("<ul>"); 
	        resp.getWriter().println("<li><a href=\"List\">List</a></li>"); 
	        resp.getWriter().println("<li><a href=\"Add\">Add</a></li>"); 
	        resp.getWriter().println("<li><a href=\"Batch\">Batch</a></li>"); 
	        resp.getWriter().println("</ul>"); 
	        resp.getWriter().println("</div"); 

	        resp.getWriter().println("<div id=\"content\">"); 
	        resp.getWriter().println("<div id=\"content\">"); 

	        resp.getWriter().println("<h2>Batch</h2>"); 

	        resp.getWriter().print("<p>"); 	
			if(batch!= null && !"".equals(batch)) {
				// Create an instance of HttpClient.
				HttpClient client = new HttpClient();
	
				// set auth if it's authenticated
				client.getParams().setAuthenticationPreemptive(true);
				Credentials defaultcreds = new UsernamePasswordCredentials("usr", "pw");
				client.getState().setCredentials(AuthScope.ANY, defaultcreds);
				
				// Create a method instance.
				PostMethod method = new PostMethod("http://localhost:8080/v1/Batch/User");
				method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		        method.setRequestBody(batch);
		        
		        method.setRequestHeader("Accept", encoding);
		        	
				int responseCode = client.executeMethod(method);
				if(responseCode == 200) {
			        resp.getWriter().print("Batch job processed successfully.<br/><br/>");
			        resp.getWriter().print("<b>Response header:</b>");
			        resp.getWriter().print("<pre>");
			        
			        for(int i=0; i<method.getResponseHeaders().length; i++) {
				        resp.getWriter().print("<br/>" + method.getResponseHeaders()[i].getName() + ": " + method.getResponseHeaders()[i].getValue());
			        }
			        
			        resp.getWriter().print("</pre>");
			        resp.getWriter().print("<b>Response body:</b><br/>");
			        resp.getWriter().print("<pre>");
			        resp.getWriter().print(method.getResponseBodyAsString());
			        resp.getWriter().print("</pre>");
				}
				else {
			        resp.getWriter().print("Failed to add user to storage.<br/><br/>");
				}
			}	
	        resp.getWriter().print("<form method='POST'>");
	        resp.getWriter().print("<input type=\"radio\" name=\"encoding\" value=\"application/json\" checked=\"true\" /> JSON<br/>");
	        resp.getWriter().print("<input type=\"radio\" name=\"encoding\" disabled=\"false\" value=\"application/xml\" /> XML<br/>");
	        resp.getWriter().print("<textarea cols=\"50\" rows=\"15\" name=\"batch\"></textarea><br/>");
	        resp.getWriter().print("<input type=\"submit\" value=\"Start job\"><br/>");
	        resp.getWriter().print("</form>");
	
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

