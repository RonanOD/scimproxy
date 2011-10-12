package info.simplecloud.scimproxy;

import info.simplecloud.scimproxy.authentication.AuthenticateUser;
import info.simplecloud.scimproxy.authentication.AuthenticationUsers;
import info.simplecloud.scimproxy.authentication.Basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
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
        System.out.println("==== GET ====");
        System.out.println(req);
        // Authorization Request
        // authorize?response_type=code&client_id=s6BhdRkqt3&state=xyz&redirect_uri=https%3A%2F%2Fclient%2Eexample%2Ecom%2Fcb

        String basicAuth = req.getHeader("Authorization");
        System.out.println("basicAuth: " + basicAuth);
        if (basicAuth == null) {
            resp.setHeader("WWW-authenticate", "basic  realm='scimproxy'");
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authenticate.");
            return;
        }

        Basic basic = new Basic();
        if (!basic.authenticate(basicAuth)) {
            resp.setHeader("WWW-authenticate", "basic  realm='scimproxy'");
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authenticate.");
            return;
        }

        AuthenticateUser user = basic.getAuthUser();
        String redirect_uri = req.getParameter("redirect_uri");
        String state = req.getParameter("state");
        System.out.println("redirect_uri: " + redirect_uri);

        // Authorization Response
        // 302 Found
        // location:
        // https://client.example.com/cb?code=SplxlOBeZQQYbYS6WxSbIA&state=xyz
        byte[] codeBytes = new byte[20];
        random.nextBytes(codeBytes);
        String accessCode = new String(Hex.encodeHex(codeBytes));
        System.out.println("accessCode: " + accessCode);
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
        System.out.println("==== POST ====");
        System.out.println(req);
        BufferedReader reader = req.getReader();
        String line;
        String all = "";
        while ((line = reader.readLine()) != null) {
            all += line;
            System.out.println(line);
        }
        String[] parameters = all.split("&");
        String code = null;
        String grant_type = null;
        String redirect_uri = null;
        for (String parameter : parameters) {
            if (parameter.startsWith("code")) {
                code = URLDecoder.decode(parameter.split("=")[1]);
            } else if (parameter.startsWith("grant_type")) {
                grant_type = URLDecoder.decode(parameter.split("=")[1]);
            } else if (parameter.startsWith("redirect_uri")) {
                redirect_uri = URLDecoder.decode(parameter.split("=")[1]);
            }
        }
        System.out.println("grant_type: " + grant_type);
        System.out.println("redirect_uri: " + redirect_uri);
        System.out.println("code: " + code);
        
        // Access Token Request
        // grant_type=authorization_code&code=SplxlOBeZQQYbYS6WxSbIA&redirect_uri=https%3A%2F%2Fclient%2Eexample%2Ecom%2Fcb

        if (code == null) {
            System.out.println("Missing code");
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        AuthenticateUser user = AuthenticationUsers.getInstance().getUserWithCode(code);
        if (user == null) {
            System.out.println("Missing user");
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

        try {
            JSONObject responce = new JSONObject();
            responce.put("access_token", accessToken);
            resp.getWriter().write(responce.toString(2));
        } catch (JSONException e) {
            throw new RuntimeException("access token error", e);
        }
    }
}
