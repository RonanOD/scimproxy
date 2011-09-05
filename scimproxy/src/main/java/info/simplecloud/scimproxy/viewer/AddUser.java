package info.simplecloud.scimproxy.viewer;

import info.simplecloud.scimproxy.authentication.AuthenticationUsers;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.HttpStatus;

public class AddUser extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3486573754295877383L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	{
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		try {
			resp.getWriter().println("<html>");
			resp.getWriter().println("<head>");
			resp.getWriter().println("<title>Add User - Scim proxy</title>");
			resp.getWriter().println("</head>");

			if(userName == null || userName.isEmpty() || password == null || password.isEmpty())
			{
				resp.setStatus(HttpStatus.ORDINAL_404_Not_Found);
				resp.getWriter().println("<body>");
				resp.getWriter().println("No user name or password was provided. Please try <a href=\"/adduser.jsp\">again</a>");
				resp.getWriter().println("</body>");
				resp.getWriter().println("</html>");
				return;
			}
			
			AuthenticationUsers.getInstance().addUser(userName, password);
			resp.getWriter().println("<body>");
			resp.getWriter().println("User created!. Please klick <a href=\"/index.jsp\">here</a> to login.");
			resp.getWriter().println("</body>");
			resp.getWriter().println("</html>");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
