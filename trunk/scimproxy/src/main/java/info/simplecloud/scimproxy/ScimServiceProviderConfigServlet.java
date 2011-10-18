package info.simplecloud.scimproxy;

import info.simplecloud.core.User;
import info.simplecloud.scimproxy.config.Config;
import info.simplecloud.scimproxy.util.Util;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ScimServiceProviderConfigServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType(HttpGenerator.getContentType(req));

        String response = "";
        if (User.ENCODING_JSON.equalsIgnoreCase(HttpGenerator.getEncoding(req))) {
        	response = "" + 
        	"{" +
        		  "\"schemas\": [\"urn:scim:schemas:core:1.0\"]," +
        		  "\"documentationUrl\":\"" + HttpGenerator.getServer(req) + "\"," +
        		  "\"patch\": {" +
        		    "\"supported\":true" +
        		  "}," +
        		  "\"bulk\": {" +
        		    "\"supported\":true," +
        		    "\"maxOperations\":" + Config.getInstance().getBulkMaxOperations() + "," +
        		    "\"maxPayloadSize\":" + Config.getInstance().getBulkMaxPayloadSize() +
        		  "}," +
        		  "\"filter\": {" +
        		    "\"supported\":false," +
        		    "\"maxResults\": 200" +
        		  "}," +
        		  "\"changePassword\" : {" +
        		    "\"supported\":true" +
        		  "}," +
        		  "\"sort\": {" +
        		    "\"supported\":true" +
        		  "}," +
        		  "\"etag\": {" +
        		    "\"supported\":true" +
        		  "}," +
        		  "\"authenticationSchemes\": [" +
        		    "{" +
        		      "\"name\": \"HTTP Basic\"," +
        		      "\"description\": \"Authentication Scheme using the Http Basic Standard\"," +
        		      "\"specUrl\":\"http://www.ietf.org/rfc/rfc2617.txt\"," +
        		      "\"documentationUrl\":\"" + HttpGenerator.getServer(req) + "\"," +
        		      "\"type\":\"HttpBasic\"" +
        		     "}," +
         		    "{" +
         		    	"\"name\": \"OAuth2\"," +
         		    	"\"description\": \"OAuth2\"," +
         		    	"\"specUrl\":\"http://tools.ietf.org/html/draft-ietf-oauth-v2-22\"," +
         		    	"\"documentationUrl\":\"" + HttpGenerator.getServer(req) + "\"," +
         		    	"\"type\":\"OAuth2\"" +
         		    "}," +
         		    "{" +
     		    	"\"name\": \"OAuth2-v10\"," +
     		    	"\"description\": \"OAuth2 v10 \"," +
     		    	"\"specUrl\":\"http://tools.ietf.org/html/draft-ietf-oauth-v2-10\"," +
     		    	"\"documentationUrl\":\"" + HttpGenerator.getServer(req) + "\"," +
     		    	"\"type\":\"OAuth2-v10\"" +
     		    "}" +
        		  "]" +
        		"}";
        	
			response = Util.formatJsonPretty(response);

        }
        if (User.ENCODING_XML.equalsIgnoreCase(HttpGenerator.getEncoding(req))) {
            response = "NOT IMPLEMENTED";
        }

        resp.getWriter().print(response);	
	}

}
