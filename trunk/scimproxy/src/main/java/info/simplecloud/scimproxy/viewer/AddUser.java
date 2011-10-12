package info.simplecloud.scimproxy.viewer;

import info.simplecloud.scimproxy.authentication.AuthenticationUsers;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUser extends HttpServlet {

    /**
	 * 
	 */
    private static final long serialVersionUID = 3486573754295877383L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String userName = req.getParameter("Username");
        String password = req.getParameter("Password");
        try {
            if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
                resp.sendRedirect("/authenticate.html#adduser");
                return;
            }

            AuthenticationUsers.getInstance().addUser(userName, password);
            resp.sendRedirect("/authenticate.html");
        } catch (IOException e) {
            throw new RuntimeException("failed to redirect", e);
        }
    }

}
