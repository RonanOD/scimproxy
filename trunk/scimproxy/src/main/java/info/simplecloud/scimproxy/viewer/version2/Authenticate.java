package info.simplecloud.scimproxy.viewer.version2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
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
            resp.sendRedirect(getRequestedURL(req) + "authenticate.html#error1");
            return;
        }

        String authorizationServer = (String) req.getSession().getAttribute("AuthorizationServer");

        PostMethod method = new PostMethod(authorizationServer);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
        method.setParameter("grant_type", "authorization_code");
        method.setParameter("code", code);
        method.setParameter("redirect_uri", getRequestedURL(req) + "/Viewer2/Authenticate/");
        String credsPrefix = "Bearer ";
        System.out.println("authSelection step2: " + req.getSession().getAttribute("authSelection"));
        if ("OAuth2-v10".equalsIgnoreCase((String) req.getSession().getAttribute("authSelection"))) {
            method.setParameter("client_secret", (String) req.getSession().getAttribute("ClientSecret"));
            credsPrefix = "OAuth ";
        }

        HttpClient client = new HttpClient();
        int responseCode = client.executeMethod(method);
        if (responseCode != 200) {
            resp.sendRedirect(getRequestedURL(req) + "authenticate.html#error2");
            return;
        }
        String responseBody = method.getResponseBodyAsString();
        try {
            JSONObject accessResponse = new JSONObject(responseBody);
            req.getSession().setAttribute("Creds", credsPrefix + accessResponse.getString("access_token"));
            System.out.println("credsPrefix: " + credsPrefix);
            resp.sendRedirect(getRequestedURL(req) + "/viewer2.html");
        } catch (JSONException e) {
            throw new RuntimeException("failed to read responce form authorizationServer", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // basic creds or oauth2 url and
System.out.println("==== POST");
        String baseUrl = req.getParameter("BaseUrl");
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        req.getSession().setAttribute("BaseUrl", baseUrl);
        String authSelection = req.getParameter("AuthSelection");
        System.out.println(authSelection);
        if ("Basic".equals(authSelection)) {
            String creds = req.getParameter("Username") + ":" + req.getParameter("Password");
            creds = "Basic " + new String(Base64.encodeBase64(creds.getBytes()));
            req.getSession().setAttribute("Creds", creds);
            resp.sendRedirect(getRequestedURL(req) + "/viewer2.html");
        } else if ("OAuth2-v10".equals(authSelection)) {
            try {
                HttpClient client = new HttpClient();
                client.getParams().setAuthenticationPreemptive(false);
                PostMethod method = new PostMethod(req.getParameter("AuthorizationServer"));
                
                NameValuePair[] body = new NameValuePair[] { 
                        new NameValuePair("username", req.getParameter("Username")),
                        new NameValuePair("password", req.getParameter("Password")), 
                        new NameValuePair("client_id", req.getParameter("ClientId")),
                        new NameValuePair("client_secret", req.getParameter("ClientSecret")),
                        new NameValuePair("grant_type", "password")};
                method.setRequestBody(body);
                
                method.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                int responseCode = client.executeMethod(method);

                String responseBody = method.getResponseBodyAsString();
                System.out.println(responseBody);
                if (responseCode != 200) {
                    throw new RuntimeException("Failed to fetch access token form authorization server, , got response code " + responseCode);
                }
                JSONObject accessResponse = new JSONObject(responseBody);

                req.getSession().setAttribute("Creds", "OAuth " + accessResponse.getString("access_token"));
                resp.sendRedirect(getRequestedURL(req) + "/viewer2.html");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to read response from authorizationServer at", e);
            }
        } else {
            StringBuilder authorizationServer = new StringBuilder(req.getParameter("AuthorizationServer"));
            req.getSession().setAttribute("AuthorizationServer", authorizationServer.toString());
            req.getSession().setAttribute("ClientId", req.getParameter("ClientId"));
            req.getSession().setAttribute("ClientSecret", req.getParameter("ClientSecret"));
            req.getSession().setAttribute("authSelection", authSelection);
            authorizationServer.append("?");
            authorizationServer.append("response_type=").append("code").append("&");
            authorizationServer.append("client_id=").append(req.getParameter("ClientId")).append("&");
            authorizationServer.append("redirect_uri=").append(getRequestedURL(req)).append("/Viewer2/Authenticate/");
            resp.sendRedirect(authorizationServer.toString());
        }
    }

    private String getRequestedURL(HttpServletRequest req) {
        StringBuffer full = req.getRequestURL();
        return full.substring(0, full.indexOf("/Viewer2/Authenticate"));
    }
}
