package info.simplecloud.scimproxy.viewer;

import info.simplecloud.scimproxy.authentication.Authenticator;
import info.simplecloud.scimproxy.config.Config;
import info.simplecloud.scimproxy.trigger.Trigger;

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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class Batch extends HttpServlet {

    private Log log = LogFactory.getLog(Batch.class);

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
	        resp.getWriter().print("<html>");
	        resp.getWriter().print("<a href=\"List\">List</a>&nbsp;");
	        resp.getWriter().print("<a href=\"Add\">Add user</a>");
	        resp.getWriter().print("<a href=\"Batch\">Batch</a>");
	        resp.getWriter().print("<br/>");
	
			if(batch!= null && !"".equals(batch)) {
		        resp.getWriter().print("Not implemented.<br/><br/>");
			}
	
	        resp.getWriter().print("<br/><br/><form method='POST'>");
	        resp.getWriter().print("<input type=\"radio\" name=\"encoding\" value=\"JSON\" checked=\"true\" /> JSON<br/>");
	        resp.getWriter().print("<input type=\"radio\" name=\"encoding\" disabled=\"true\" value=\"XML\" /> XML<br/>");
	        resp.getWriter().print("<textarea cols=\"50\" rows=\"15\" name=\"batch\"></textarea><br/>");
	        resp.getWriter().print("<input type=\"submit\" value=\"Start job\"><br/>");
	        resp.getWriter().print("</form></html>");
	
			resp.setStatus(HttpServletResponse.SC_OK);
		}
	}

}

