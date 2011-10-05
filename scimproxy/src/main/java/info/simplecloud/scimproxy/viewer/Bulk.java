package info.simplecloud.scimproxy.viewer;

import info.simplecloud.scimproxy.authentication.Authenticator;
import info.simplecloud.scimproxy.config.Config;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringEscapeUtils;

@SuppressWarnings("serial")
public class Bulk extends HttpServlet {

	
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

			String bulk = "";
			String encoding = "";
			
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			boolean isMultipart = ServletFileUpload.isMultipartContent(req);

			if(isMultipart) {
				try {
					java.util.List items = upload.parseRequest(req);
					// Process the uploaded items
					Iterator iter = items.iterator();
					while (iter.hasNext()) {
					    FileItem item = (FileItem) iter.next();

					    if (!item.isFormField()) {
					    	byte[] data = item.get();
					    	bulk = new String(data);
					    }
					    
						if (item.isFormField()) {
						    String name = item.getFieldName();
						    String value = item.getString();
						    if("bulk".equalsIgnoreCase(name)) {
								bulk = value;
						    }
						    if("encoding".equalsIgnoreCase(name)) {
						    	encoding = value;
						    }
						}

					}
				} catch (FileUploadException e) {
					e.printStackTrace();
				}
			}


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

	        resp.getWriter().println("<h2>Bulk</h2>"); 

	        resp.getWriter().print("<p>"); 	
			if(bulk!= null && !"".equals(bulk)) {
				// Create an instance of HttpClient.
				HttpClient client = new HttpClient();
				
				// set auth if it's authenticated
				client.getParams().setAuthenticationPreemptive(true);
				Credentials defaultcreds = auth.getAuthUser().getCred();
				client.getState().setCredentials(AuthScope.ANY, defaultcreds);
				
				// Create a method instance.
				PostMethod method = new PostMethod("http://localhost:8080/v1/Bulk");
				method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
				
		        method.setRequestBody(bulk);
		        
		        method.setRequestHeader("Accept", encoding);
		        	
				int responseCode = client.executeMethod(method);
				if(responseCode == 200) {
			        resp.getWriter().print("Bulk job processed successfully.<br/><br/>");
				}
				else {
			        resp.getWriter().print("Failed to add resources to storage. Response code: <b>" + Integer.toString(responseCode) + "</b><br/><br/>");
				}

		        resp.getWriter().print("<b>Response header:</b>");
		        resp.getWriter().print("<pre class=\"prettyprint\">");
		        
		        for(int i=0; i<method.getResponseHeaders().length; i++) {
			        resp.getWriter().print(method.getResponseHeaders()[i].getName() + ": " + method.getResponseHeaders()[i].getValue() + "<br/>");
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
	        resp.getWriter().print("<form id=\"bulkform\" method=\"POST\" enctype=\"multipart/form-data\">");
	        resp.getWriter().print("<input type=\"radio\" id=\"encoding\" value=\"application/json\" checked=\"true\" /> JSON<br/>");
	        resp.getWriter().print("<input type=\"radio\" id=\"encoding\" disabled=\"false\" value=\"application/xml\" /> XML<br/>");
	        resp.getWriter().print("<input type=\"file\" name=\"bulkFile\" /><br/>");
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

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doPost(req, resp);
	}

}

