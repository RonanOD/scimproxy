package info.simplecloud.scimproxy;

import java.io.IOException;
import javax.servlet.http.*;

/*

GET:
curl -i -H "Accept: application/json" http://localhost:8080/Group

POST:
curl -i -H "Accept: application/json" -X POST -d "firstName=james" http://localhost:8080/Group

PUT:
curl -i -H "Accept: application/json" -X PUT -d "phone=1-800-999-9999" http://localhost:8080/Group

DELETE:
curl -i -H "Accept: application/json" -X DELETE http://localhost:8080/Group

PUT OVER POST:
curl -i -H "Accept: application/json" -H "X-HTTP-Method-Override: PUT" -X POST -d "phone=1-800-999-9999" http://localhost:8080/Group

DELETE OVER POST:
curl -i -H "Accept: application/json" -H "X-HTTP-Method-Override: DELETE" -X GET  http://localhost:8080/Group

 */

@SuppressWarnings("serial")
public class ScimGroupServlet extends RestServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}

	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}

	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// TODO: SPEC: REST: Should X-HTTP-Method-Override: DELETE be sent over GET instead of POST?
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}

	public void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// TODO: SPEC: REST: Should we really use the PATCH method? It's not part of HTTP 1.1.
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}

}
