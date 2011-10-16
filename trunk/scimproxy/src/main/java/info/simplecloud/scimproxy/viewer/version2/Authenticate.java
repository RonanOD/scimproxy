package info.simplecloud.scimproxy.viewer.version2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class Authenticate extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // access code from Authorization server

        String code = req.getParameter("code");
        if (code == null) {
            System.out.println("Error, no code");
            resp.sendRedirect(getRequestedURL(req) + "authenticate.html#error1");
            return;
        }

        String authorizationServer = (String) req.getSession().getAttribute("AuthorizationServer");
        
        PostMethod method = new PostMethod(authorizationServer);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
        method.setParameter("grant_type", "authorization_code");
        method.setParameter("code", code);
        method.setParameter("redirect_uri", getRequestedURL(req) + "/Viewer2/Authenticate/");
        

        HttpClient client = new HttpClient();
        int responseCode = client.executeMethod(method);
        if (responseCode != 200) {
            System.out.println("Error, responseCode: " + responseCode);
            resp.sendRedirect(getRequestedURL(req) + "authenticate.html#error2");
            return;
        }
        String responseBody = method.getResponseBodyAsString();
        System.out.println("body: " + responseBody);
        try {
            JSONObject accessResponse = new JSONObject(responseBody);
            req.getSession().setAttribute("Creds", "Bearer " + accessResponse.getString("access_token"));
            System.out.println("Creds: " + req.getSession().getAttribute("Creds"));
            resp.sendRedirect(getRequestedURL(req) + "/viewer2.html");
        } catch (JSONException e) {
            throw new RuntimeException("failed to read responce form authorizationServer", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // basic creds or oauth2 url and

        String baseUrl = req.getParameter("BaseUrl");
        if(!baseUrl.endsWith("/")) {
        	baseUrl += "/";
        }
        req.getSession().setAttribute("BaseUrl", baseUrl);
        System.out.println("baseUrl: " + baseUrl);
        String authSelection = req.getParameter("AuthSelection");
        System.out.println("authSelection: " + authSelection);
        if ("Basic".equals(authSelection)) {
            String creds = req.getParameter("Username") + ":" + req.getParameter("Password");
            creds = "Basic " + new String(Base64.encodeBase64(creds.getBytes()));
            req.getSession().setAttribute("Creds", creds);
            System.out.println("creds:" + creds);
            System.out.println("getRequestedURL(req): " + getRequestedURL(req));
            resp.sendRedirect(getRequestedURL(req) + "/viewer2.html");
        } else {
            String authorizationServer = req.getParameter("AuthorizationServer");
            req.getSession().setAttribute("AuthorizationServer", authorizationServer);
            authorizationServer += "?response_type=code&client_id=scimproxyviewer&redirect_uri=";
            authorizationServer +=  getRequestedURL(req) + "/Viewer2/Authenticate/";
            System.out.println("authorizationServer: " + authorizationServer);
            resp.sendRedirect(authorizationServer);
        }
    }
    
    private String getRequestedURL(HttpServletRequest req) {
        StringBuffer full = req.getRequestURL();
        return full.substring(0, full.indexOf("/Viewer2/Authenticate"));
    }
}
