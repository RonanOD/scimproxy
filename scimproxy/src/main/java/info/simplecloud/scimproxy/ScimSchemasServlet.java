package info.simplecloud.scimproxy;

import info.simplecloud.core.Resource;
import info.simplecloud.scimproxy.util.Util;

import java.io.IOException;
import javax.servlet.http.*;


@SuppressWarnings("serial")
public class ScimSchemasServlet extends RestServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String encoding = HttpGenerator.getEncoding(req);
        resp.setHeader("Location", HttpGenerator.getServer(req) + "/v1/Schemas");
		
		if(Resource.ENCODING_XML.equalsIgnoreCase(encoding)) {
	        resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
		}
		else {
	        String response = "" + 
			"{" + 
				"\"resources\":[" + 
					"{" + 
        				"\"name\":\"User\"," + 
        				"\"description\":\"TBD\"," + 
        				"\"schema\":\"urn:scim:schemas:core:1.0\"" + 
					"}," + 
					"{" + 
						"\"name\":\"Group\"," + 
						"\"description\":\"TBD\"," + 
						"\"schema\":\"urn:scim:schemas:core:1.0\"" + 
					"}" + 
				"]" + 
			"}";

	        resp.setStatus(HttpServletResponse.SC_OK);
	        resp.setContentType(HttpGenerator.getContentType(req));
			response = Util.formatJsonPretty(response);
			resp.getWriter().print(response);	
		}
        
	}

}
