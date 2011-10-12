package info.simplecloud.scimproxy.viewer.version2;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Edit extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String creds = (String) req.getSession().getAttribute("Creds");
        if (creds == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authenticate.");
            return;
        }

        Map<String, String> indata= Helper.readJsonPostData(req);
        
        for(String key : indata.keySet()){
            resp.getWriter().write(key + ": " + indata.get(key) + "\n");
        }

    }

}
