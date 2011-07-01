package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.coding.encode.JsonEncoder;
import info.simplecloud.core.execeptions.EncodingFailed;
import info.simplecloud.core.execeptions.FailedToGetValue;
import info.simplecloud.core.execeptions.UnhandledAttributeType;
import info.simplecloud.core.tools.ScimUserComparator;
import info.simplecloud.scimproxy.storage.dummy.DummyStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*

 GET:
 curl -i -H "Accept: application/json" http://localhost:8080/Users  

 */

@SuppressWarnings("serial")
public class ScimUsersServlet extends RestServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String attributesString = req.getParameter("attributes") == null ? "" : req.getParameter("attributes");
        List<String> attributesList = new ArrayList<String>();
        if(attributesString != null && !"".equals(attributesString)) {
            for(String attribute: attributesString.split(",")) {
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

        DummyStorage storage = DummyStorage.getInstance();
        List<ScimUser> users = null;

        String filterBy = req.getParameter("filterBy");
        String filterValue = req.getParameter("filterValue");
        String filterOp = req.getParameter("filterOp");

        if(filterBy != null && !"".equals(filterBy)) {
        	users =  storage.getList(filterBy, filterValue, filterOp);
        }
        else {
        	users =  storage.getList();
        }
        
        
        Collections.sort(users, new ScimUserComparator<String>(sortBy, sortOrder.equalsIgnoreCase("ascending")));
        
        try {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(HttpGenerator.getContentType(req));

            String response = new JsonEncoder().encode(users, attributesList);
            resp.getWriter().print(response);
        } catch (EncodingFailed e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().print("Error: failed to build user set");
        } catch (FailedToGetValue e) {
            HttpGenerator.serverError(resp);
        } catch (UnhandledAttributeType e) {
            HttpGenerator.serverError(resp);
        }
    }
}
