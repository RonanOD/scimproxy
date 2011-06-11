package info.simplecloud.scimproxy;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.encoding.JsonEncoder;
import info.simplecloud.core.execeptions.EncodingFailed;
import info.simplecloud.scimproxy.storage.dummy.DummyStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*

GET:
curl -i -H "Accept: application/json" http://localhost:8080/Users  

 */

@SuppressWarnings("serial")
public class ScimUsersServlet extends RestServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		DummyStorage storage = DummyStorage.getInstance();
		ArrayList<ScimUser> users = storage.getList();

		// TODO: SPEC: REST: Really missing an XML representation of two or more
		// nodes in List/Query a response.
		try {
			String response = "";
			response += new JsonEncoder().encode(users);

			resp.setContentType(CONTENT_TYPE_JSON);
			resp.getWriter().print(response);
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (EncodingFailed e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
