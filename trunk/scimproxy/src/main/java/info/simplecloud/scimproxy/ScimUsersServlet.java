package info.simplecloud.scimproxy;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*

GET:
curl -i -H "Accept: application/json" http://localhost:8080/Users  

 */

@SuppressWarnings("serial")
public class ScimUsersServlet extends Authenticated {
	
	private static final Logger log = Logger.getLogger(ScimUsersServlet.class.getName());

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		log.warning("Hardcoded response!");
		
		resp.setContentType(CONTENT_TYPE_JSON);
		resp.getWriter().println(
			"{\n" +
				"\t\"totalResults\": 3,\n" +
				"\t\"entry\": [\n" +
					"\t\t{\n" +
						"\t\t\t\"schemas\": [\"urn:scim:schemas:core:1.0\"],\n" +
						"\t\t\t\"id\": \"3f62ce30-dcd6-4dd7-abfe-2352a76f9978\",\n" +
						"\t\t\t\"displayName\": \"Babs Jensen\",\n" +
						"\t\t\t\"userName\": \"bjensen\",\n" +
						"\t\t\t\"meta\": {\n" +
							"\t\t\t\t\"location\":\"http://example.com/User/3f62ce30-...\"\n" +
						"\t\t\t}\n" +
					"\t\t},\n" +
					"\t\t{\n" +
						"\t\t\t\"schemas\": [\"urn:scim:schemas:core:1.0\"],\n" +
						"\t\t\t\"id\": \"4f62ce30-dcd6-4dd7-abfe-2352a76f9978\",\n" +
						"\t\t\t\"displayName\": \"Mandy Pepperidge\",\n" +
						"\t\t\t\"userName\": \"mPepperidge\",\n" +
						"\t\t\t\"meta\": {\n" +
							"\t\t\t\t\"location\":\"http://example.com/User/4f62ce30-...\"\n" +
						"\t\t\t}\n" +
					"\t\t},\n" +
					"\t\t{\n" +
						"\t\t\t\"schemas\": [\"urn:scim:schemas:core:1.0\"],\n" +
						"\t\t\t\"id\": \"5f62ce30-dcd6-4dd7-abfe-2352a76f9978\",\n" +
						"\t\t\t\"displayName\": \"Noel Smith\",\n" +
						"\t\t\t\"userName\": \"nsmith\",\n" +
						"\t\t\t\"meta\": {\n" +
							"\t\t\t\t\"location\":\"http://example.com/User/5f62ce30-...\"\n" +
						"\t\t\t}\n" +
					"\t\t}\n" +
				"\t]\n" +
			"}\n");
		resp.setStatus(HttpServletResponse.SC_OK);
	}
}
