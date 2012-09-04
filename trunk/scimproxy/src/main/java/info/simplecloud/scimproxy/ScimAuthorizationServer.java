package info.simplecloud.scimproxy;

import info.simplecloud.scimproxy.authentication.AuthenticateUser;
import info.simplecloud.scimproxy.authentication.AuthenticationUsers;
import info.simplecloud.scimproxy.authentication.Basic;
import info.simplecloud.scimproxy.config.Config;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class ScimAuthorizationServer extends HttpServlet {
    private static Random random = new SecureRandom();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Authorization Request
        // authorize?response_type=code&client_id=s6BhdRkqt3&state=xyz&redirect_uri=https%3A%2F%2Fclient%2Eexample%2Ecom%2Fcb

        String basicAuth = req.getHeader("Authorization");
        if (basicAuth == null) {
            resp.setHeader("WWW-authenticate", "basic  realm='scimproxy'");
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authenticate.");
            return;
        }
        Config.getInstance();
        Basic basic = new Basic();
        if (!basic.authenticate(basicAuth)) {
            resp.setHeader("WWW-authenticate", "basic  realm='scimproxy'");
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authenticate.");
            return;
        }

        AuthenticateUser user = basic.getAuthUser();
        String redirect_uri = req.getParameter("redirect_uri");
        String state = req.getParameter("state");

        // Authorization Response
        // 302 Found
        // location:
        // https://client.example.com/cb?code=SplxlOBeZQQYbYS6WxSbIA&state=xyz
        byte[] codeBytes = new byte[20];
        random.nextBytes(codeBytes);
        String accessCode = new String(Hex.encodeHex(codeBytes));
        user.setCode(accessCode);

        if (state != null) {
            redirect_uri += "?state=" + state + "&code=" + accessCode;
        } else {
            redirect_uri += "?code=" + accessCode;
        }

        resp.sendRedirect(redirect_uri);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String code = req.getParameter("code");
        String grant_type = req.getParameter("grant_type");
        //String redirect_uri = req.getParameter("redirect_uri");

        // Access Token Request
        // grant_type=authorization_code&code=SplxlOBeZQQYbYS6WxSbIA&redirect_uri=https%3A%2F%2Fclient%2Eexample%2Ecom%2Fcb
        Config.getInstance();

        AuthenticateUser user = null;
        if("code".equalsIgnoreCase(grant_type)) {
            if (code != null) {
                user = AuthenticationUsers.getInstance().getUserWithCode(code);
            }
        } else if ("password".equalsIgnoreCase(grant_type)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            
            // for the future
            String client_creds_header = req.getHeader("Authorization");
            String client_id = req.getParameter("client_id");
            String client_secret = req.getParameter("client_secret");
            
            String basicAuth = "Basic " + Base64.encodeBase64String((username + ":" + password).getBytes());

            System.out.println("password basicAuth: " + basicAuth);
            Basic basic = new Basic();
            if (!basic.authenticate(basicAuth)) {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            user = basic.getAuthUser();
        } else if ( "client_credentials".equalsIgnoreCase(grant_type)) {
            String client_creds_header = req.getHeader("Authorization");
            if(client_creds_header == null) {
                String client_id = req.getParameter("client_id");
                String client_secret = req.getParameter("client_secret");
                client_creds_header = "Basic " + Base64.encodeBase64String((client_id + ":" + client_secret).getBytes());                
            }

            System.out.println("client_credentials basicAuth: " + client_creds_header);
            Basic basic = new Basic();
            if (!basic.authenticate(client_creds_header)) {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            user = basic.getAuthUser();
        }
        

        if (user == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Access Token Response
        // 200 OK
        // {
        // "access_token":"2YotnFZFEjr1zCsicMWpAA",
        // }

        user.setCode(null);
        byte[] tokenBytes = new byte[20];
        random.nextBytes(tokenBytes);

        String accessToken = new String(Hex.encodeHex(tokenBytes));
        user.setAccessToken(accessToken);
        resp.setHeader("Content-Type", "application/json");
        try {
            JSONObject responce = new JSONObject();
            responce.put("access_token", accessToken);
            responce.put("token_type", "dev");
            resp.getWriter().write(responce.toString(2));
        } catch (JSONException e) {
            throw new RuntimeException("access token error", e);
        }
    }
}
