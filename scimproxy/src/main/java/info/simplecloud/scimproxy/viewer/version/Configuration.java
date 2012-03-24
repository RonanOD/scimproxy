package info.simplecloud.scimproxy.viewer.version;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

@SuppressWarnings("serial")
public class Configuration extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String creds = (String) req.getSession().getAttribute("Creds");
        
        String baseUrl = (String) req.getSession().getAttribute("BaseUrl");
        if (baseUrl == null) {
            System.out.println("Error, missing base url");
            return;
        }

        HttpClient client = new HttpClient();
        client.getParams().setAuthenticationPreemptive(false);

        GetMethod method = new GetMethod(baseUrl + "ServiceProviderConfig");
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
        method.setRequestHeader("Accept", "application/json");
        if(creds != null){            
            method.setRequestHeader("Authorization", creds);
        }
        
        int responseCode = client.executeMethod(method);
        if (responseCode == 200) {
            resp.getWriter().print(method.getResponseBodyAsString());
        } else {
            resp.getWriter().print("Error, server returned " + responseCode);
            resp.getWriter().print(method.getResponseBodyAsString());
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String creds = (String) req.getSession().getAttribute("Creds");
        
        String baseUrl = (String) req.getSession().getAttribute("BaseUrl");
        if (baseUrl == null) {
            System.out.println("Error, missing base url");
            return;
        }

        HttpClient client = new HttpClient();
        client.getParams().setAuthenticationPreemptive(false);

        GetMethod method = new GetMethod(baseUrl + "ServiceProviderConfigs");
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
        method.setRequestHeader("Accept", "application/json");
        if(creds != null){            
            method.setRequestHeader("Authorization", creds);
        }
        
        int responseCode = client.executeMethod(method);
        resp.setContentType("text/javascript");
        if (responseCode == 200) {
            resp.getWriter().print("var config=");
            resp.getWriter().print(method.getResponseBodyAsString());
        } else {
            // TODO return default config
            resp.getWriter().print("Error, server returned " + responseCode);
            resp.getWriter().print(method.getResponseBodyAsString());
        }
    }
}
