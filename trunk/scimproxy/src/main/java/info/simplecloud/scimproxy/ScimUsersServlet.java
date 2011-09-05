package info.simplecloud.scimproxy;

import info.simplecloud.core.User;
import info.simplecloud.core.coding.encode.JsonEncoder;
import info.simplecloud.core.coding.encode.XmlEncoder;
import info.simplecloud.scimproxy.authentication.AuthenticateUser;
import info.simplecloud.scimproxy.storage.dummy.DummyStorage;
import info.simplecloud.scimproxy.trigger.Trigger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*

 GET:
 curl -i -H "Accept: application/json" http://localhost:8080/Users  

 */

public class ScimUsersServlet extends RestServlet {

	private static final long serialVersionUID = 3404477020945307825L;

	private Log               log              = LogFactory.getLog(ScimUserServlet.class);

    private Trigger trigger = new Trigger();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String attributesString = req.getParameter("attributes") == null ? "" : req.getParameter("attributes");
        AuthenticateUser authUser = (AuthenticateUser) req.getAttribute("AuthUser");
        List<String> attributesList = new ArrayList<String>();
        if (attributesString != null && !"".equals(attributesString)) {
            for (String attribute : attributesString.split(",")) {
                attributesList.add(attribute.trim());
            }
        }

        // TODO: SPEC: REST: what is major
        String sortBy = req.getParameter("sortBy") == null ? "userName" : req.getParameter("sortBy");
        String sortOrder = req.getParameter("sortOrder") == null ? "ascending" : req.getParameter("sortOrder");
        if (!sortOrder.equalsIgnoreCase("ascending") && !sortOrder.equalsIgnoreCase("descending")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print("Sort order must be 'ascending' or 'descending'");
            return;
        }

        DummyStorage storage = DummyStorage.getInstance(authUser.getSessionId());
        List users = null;

        String filterBy = req.getParameter("filterBy");
        String filterValue = req.getParameter("filterValue");
        String filterOp = req.getParameter("filterOp");
        
        if (filterBy != null && !"".equals(filterBy)) {
            users = storage.getList(sortBy, sortOrder, filterBy, filterValue, filterOp);
        } else {
            users = storage.getList(sortBy, sortOrder);
        }

        int index = 0;
        int count = 0;

        String startIndexStr = req.getParameter("startIndex"); // must be
                                                               // absolut and
                                                               // defaults to 0
        String countStr = req.getParameter("count"); // must be absolut and
                                                     // defaults to 0
        if (startIndexStr != null && !"".equals(startIndexStr)) {
            index = Integer.parseInt(startIndexStr);
        }
        if (countStr != null && !"".equals(countStr)) {
            count = Integer.parseInt(countStr);
        }

        int max = index + count;
        if (max > users.size() || max == 0) {
            max = users.size();
        }

        if (index > users.size()) {
            index = users.size();
        }

    	// if we did not find any users in storage, try upstream servers
    	if(users == null || users.size() == 0) {
        	// try finding users in upstream CSP, any communication errors is handled in triggered and ignored here
    		users = trigger.query(sortBy, sortOrder, filterBy, filterValue, filterOp);
        	storage.addList(users);
    	}
        
        try {
            users = users.subList(index, max);
        } catch (IndexOutOfBoundsException e) {
            users = new ArrayList<User>();
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType(HttpGenerator.getContentType(req));

        String response = "";
        if (User.ENCODING_JSON.equalsIgnoreCase(HttpGenerator.getEncoding(req))) {
            response = new JsonEncoder().encode(users, attributesList);
        }
        if (User.ENCODING_XML.equalsIgnoreCase(HttpGenerator.getEncoding(req))) {
            response = new XmlEncoder().encode(users, attributesList);
        }

        resp.getWriter().print(response);

    }
}
