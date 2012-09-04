package info.simplecloud.scimproxy;

import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.UnknownEncoding;
import info.simplecloud.scimproxy.storage.ResourceNotFoundException;
import info.simplecloud.scimproxy.storage.StorageDelegator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.UsernamePasswordCredentials;

public class ScimUserBind extends HttpServlet {

    /** */
    private static final long serialVersionUID = 9010968278311889465L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String basicAuth = req.getHeader("Authorization");
        if (basicAuth == null) {
            resp.setHeader("WWW-authenticate", "basic  realm='scimproxy-userbind'");
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authenticate.");
            return;
        }

        UsernamePasswordCredentials creds = parseBasicString(basicAuth);
        if (creds == null) {
            resp.setHeader("WWW-authenticate", "basic  realm='scimproxy-userbind'");
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authenticate.");
            return;
        }

        try {
            List<User> users = new ArrayList<User>();
            try {
                User user = StorageDelegator.getInstance("1").getUser(creds.getUserName());
                if (user != null) {
                    users.add(user);
                }
            } catch (ResourceNotFoundException e) {
                // Ignore and it will go away
            }

            ArrayList<User> allUsers = StorageDelegator.getInstance("1").getUserList();

            for (User currentUser : allUsers) {
                if (currentUser.getUserName() != null && currentUser.getUserName().equalsIgnoreCase(creds.getUserName())) {
                    users.add(currentUser);
                }
            }
            
            User foundUser = null;
            for(User user : users) {               
                String password = user.getPassword();
                if (password != null && password.equals(creds.getPassword())) {
                    foundUser = user;
                    break;
                }
            }
            
            if(foundUser == null) {                
                resp.setHeader("WWW-authenticate", "basic  realm='scimproxy-userbind'");
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authenticate.");
                return;
            }

            // Success!!!
            resp.setStatus(HttpServletResponse.SC_OK);

            PrintWriter writer = resp.getWriter();
            writer.write("<html>");
            writer.write("<head> ");
            writer.write("<title>ScimProxy User Bind</title>");
            writer.write("</head>");
            writer.write("<body>");
            writer.write("<h1>Welcome " + foundUser.getUserName() + "</h1>");
            writer.write("<pre>" + foundUser.getUser("json") + "</pre>");
            writer.write("</body>");
            writer.write("</html>");

        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            resp.setHeader("WWW-authenticate", "basic  realm='scimproxy-userbind'");
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authenticate.");
            return;
        } catch (UnknownEncoding e) {
            e.printStackTrace();
            resp.setHeader("WWW-authenticate", "basic  realm='scimproxy-userbind'");
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authenticate.");
            return;
        }
    }

    public UsernamePasswordCredentials parseBasicString(String token) {
        String basicIdentifier = "Basic ";
        String encoded = token.substring(basicIdentifier.length());
        String decoded = new String(Base64.decodeBase64(encoded));

        String user = decoded.substring(0, decoded.indexOf(":"));
        String pass = decoded.substring(decoded.indexOf(":") + 1);
        return new UsernamePasswordCredentials(user, pass);
    }
}
