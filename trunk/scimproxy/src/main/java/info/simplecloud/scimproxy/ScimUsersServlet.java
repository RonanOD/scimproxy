package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.coding.encode.JsonEncoder;
import info.simplecloud.core.coding.encode.XmlEncoder;
import info.simplecloud.core.execeptions.EncodingFailed;
import info.simplecloud.core.execeptions.UnhandledAttributeType;
import info.simplecloud.scimproxy.storage.dummy.DummyStorage;

import java.io.IOException;
import java.util.ArrayList;
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
        	users =  storage.getList(sortBy, sortOrder, filterBy, filterValue, filterOp);
        }
        else {
        	users =  storage.getList(sortBy, sortOrder);
        }
        
        try {
        	int index = 0;
        	int count = 0;
        	
            String startIndexStr = req.getParameter("startIndex"); // must be absolut and defaults to 0
            String countStr = req.getParameter("count"); // must be absolut and defaults to 0
            if(startIndexStr != null && !"".equals(startIndexStr)) {
            	index = Integer.parseInt(startIndexStr);
            }
            if(countStr != null && !"".equals(countStr)) {
            	count = Integer.parseInt(countStr);
            }
            
            int max = index+count;
            if(max > users.size() || max == 0) {
            	max = users.size();
            }
            
            if(index > users.size()) {
            	index = users.size();
            }
            
            try {
                users = users.subList(index, max);
            } catch (IndexOutOfBoundsException e) {
            	users = new ArrayList<ScimUser>();
            }

        	resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(HttpGenerator.getContentType(req));

            String response = "";
            if("JSON".equals(HttpGenerator.getEncoding(req))) {
            	response = new JsonEncoder().encode(users, attributesList);
            }
            if("XML".equals(HttpGenerator.getEncoding(req))) {
            	response = new XmlEncoder().encode(users, attributesList);
            }
            
            resp.getWriter().print(response);
        } catch (EncodingFailed e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().print("Error: failed to build user set");
        } catch (UnhandledAttributeType e) {
            HttpGenerator.serverError(resp);
        }
    }
}
