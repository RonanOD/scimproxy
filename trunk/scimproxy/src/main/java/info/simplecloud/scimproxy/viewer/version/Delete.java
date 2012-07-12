package info.simplecloud.scimproxy.viewer.version;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringEscapeUtils;

@SuppressWarnings("serial")
public class Delete extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    System.out.println("Delete");
        String creds = (String) req.getSession().getAttribute("Creds");
        if (creds == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authenticate.");
            return;
        }
        
        String baseUrl = (String) req.getSession().getAttribute("BaseUrl");
        if (baseUrl == null) {
            System.out.println("Error, missing base url");
            return;
        }

        Map<String, String> indata= Helper.readJsonPostData(req);
        
        HttpClient client = new HttpClient();
        client.getParams().setAuthenticationPreemptive(false);
        
        DeleteMethod method = new DeleteMethod(baseUrl + indata.get("type") + "/" + indata.get("id"));
        
        //PostMethod method = new PostMethod(baseUrl + indata.get("type") + "/" + indata.get("id"));
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));        
        
        //method.setRequestHeader("X-HTTP-Method-Override", "Delete");
        if(indata.get("etag") != null) {
            method.setRequestHeader("If-Match", indata.get("etag"));
        }
        method.setRequestHeader("Authorization", creds);
        method.setRequestHeader("Content-Type", "application/json");

        int responseCode = client.executeMethod(method);
        if(responseCode == 200) {            
            resp.getWriter().print(indata.get("type") + " deleted");
        } else if(responseCode == 404){
            resp.getWriter().print(indata.get("type") + " not found");
        } else {
            resp.getWriter().print("Error, server returned " + responseCode);
            resp.getWriter().print(method.getResponseBodyAsString());
        }
    }
}

