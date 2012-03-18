package info.simplecloud.scimproxy;

import info.simplecloud.core.Resource;
import info.simplecloud.scimproxy.util.Util;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ScimSchemaServlet extends HttpServlet {

	/**
	 * Schema only supports GET method.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String uri = req.getRequestURI();
		String response = "";
		String encoding = HttpGenerator.getEncoding(req);

		if(uri.endsWith("User")) {

			if(Resource.ENCODING_XML.equalsIgnoreCase(encoding)) {
		        resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			}
			else {
				response = "{" +
				  "\"id\":\"urn:scim:schemas:core:1.0:User\"," +
				  "\"name\":\"User\"," +
				  "\"description\":\"Core User\"," +
				  "\"schema\":\"urn:scim:schemas:core:1.0\"," +
				  "\"queryEndpoint\":\"/Users\"," +
				  "\"attributes\":[" +
				    "{" +
				      "\"name\":\"id\"," +
				      "\"type\":\"string\"," +
				      "\"plural\":false," +
				      "\"description\":\"Unique identifier for the SCIM resource as defined by the Service Provider. Each representation of the resource MUST include a non-empty id value. This identifier MUST be unique across the Service Provider's entire set of resources. It MUST be a stable, non-reassignable identifier that does not change when the same resource is returned in subsequent requests. The value of the id attribute is always issued by the Service Provider and MUST never be specified by the Service Consumer. REQUIRED.\"," +
				      "\"schema\":\"urn:scim:schemas:core:1.0\"," +
				      "\"readOnly\":true," +
				      "\"required\":true," +
				      "\"caseExact\":false" +
				    "}," +
				    "{" +
				      "\"name\":\"name\"," +
				      "\"type\":\"complex\"," +
				      "\"plural\":false," +
				      "\"description\":\"The components of the user's real name. Providers MAY return just the full name as a single string in the formatted sub-attribute, or they MAY return just the individual component attributes using the other sub-attributes, or they MAY return both. If both variants are returned, they SHOULD be describing the same name, with the formatted name indicating how the component attributes should be combined. OPTIONAL.\"," +
				      "\"schema\":\"urn:scim:schemas:core:1.0\"," +
				      "\"readOnly\":false," +
				      "\"required\":false," +
				      "\"caseExact\":false," +
				      "\"subAttributes\":[" +
				        "{" +
				          "\"name\":\"formatted\"," +
				          "\"type\":\"string\"," +
				          "\"plural\":false," +
				          "\"description\":\"The full name, including all middle names, titles, and suffixes as appropriate, formatted for display (e.g. Ms. Barbara J Jensen, III.). OPTIONAL.\" ," +
				          "\"readOnly\":false," +
				          "\"required\":false," +
				          "\"caseExact\":false" +
				        "}," +
				        "{" +
				          "\"name\":\"familyName\"," +
				          "\"type\":\"string\"," +
				          "\"plural\":false," +
				          "\"description\":\"The family name of the User, or Last Name in most Western languages (e.g. Jensen given the full name Ms. Barbara J Jensen, III.). OPTIONAL\"," +
				          "\"readOnly\":false," +
				          "\"required\":false," +
				          "\"caseExact\":false" +
				        "}," +
				        "{" +
				          "\"name\":\"givenName\"," +
				          "\"type\":\"string\"," +
				          "\"plural\":false," +
				          "\"description\":\"The given name of the User, or First Name in most Western languages (e.g. Barbara given the full name Ms. Barbara J Jensen, III.). OPTIONAL.\"," +
				          "\"readOnly\":false," +
				          "\"required\":false," +
				          "\"caseExact\":false" +
				        "}," +
				        "{" +
				          "\"name\":\"middleName\"," +
				          "\"type\":\"string\"," +
				          "\"plural\":false," +
				          "\"description\":\"The middle name(s) of the User (e.g. Robert given the full name Ms. Barbara J Jensen, III.). OPTIONAL.\"," +
				          "\"readOnly\":false," +
				          "\"required\":false," +
				          "\"caseExact\":false" +
				        "}," +
				        "{" +
				          "\"name\":\"honorificPrefix\"," +
				          "\"type\":\"string\"," +
				          "\"plural\":false," +
				          "\"description\":\"The honorific prefix(es) of the User, or Title in most Western languages (e.g. Ms. given the full name Ms. Barbara J Jensen, III.). OPTIONAL.\"," +
				          "\"readOnly\":false," +
				          "\"required\":false," +
				          "\"caseExact\":false" +
				        "}," +
				        "{" +
				          "\"name\":\"honorificSuffix\"," +
				          "\"type\":\"string\"," +
				          "\"plural\":false," +
				          "\"description\":\"The honorific suffix(es) of the User, or Suffix in most Western languages (e.g. III. given the full name Ms. Barbara J Jensen, III.). OPTIONAL.\"," +
				          "\"readOnly\":false," +
				          "\"required\":false," +
				          "\"caseExact\":false" +
				        "}" +
				      "]" +
				     "}," +
				     "{" +
				       "\"name\":\"emails\"," +
				       "\"type\":\"string\"," +
				       "\"plural\":true," +
				       "\"description\":\"E-mail addresses for the user. The value SHOULD be canonicalized by the Service Provider, e.g. bjensen@example.com instead of bjensen@EXAMPLE.COM. Canonical Type values of work, home, and other. OPTIONAL.\"," +
				       "\"schema\":\"urn:scim:schemas:core:1.0\"," +
				       "\"readOnly\":false," +
				       "\"required\":false," +
				       "\"caseExact\":false," +
				       "\"pluralTypes\":[\"work\",\"home\",\"other\"]" +
				     "}," +
				     "{" +
				       "\"name\":\"addresses\"," +
				       "\"type\":\"complex\"," +
				       "\"plural\":true," +
				       "\"description\":\"A physical mailing address for this User, as described in (address Element). Canonical Type Values of work, home, and other. The value attribute is a complex type with the following sub-attributes. OPTIONAL.\"," +
				       "\"schema\":\"urn:scim:schemas:core:1.0\"," +
				       "\"readOnly\":false," +
				       "\"required\":false," +
				       "\"caseExact\":false," +
				       "\"pluralTypes\":[\"work\",\"home\",\"other\"]," +
				       "\"subAttributes\":[" +
				         "{" +
				           "\"name\":\"formatted\"," +
				           "\"type\":\"string\"," +
				           "\"plural\":false," +
				           "\"description\":\"The full mailing address, formatted for display or use with a mailing label. This attribute MAY contain newlines. OPTIONAL.\"," +
				           "\"readOnly\":false," +
				           "\"required\":false," +
				           "\"caseExact\":false" +
				         "}," +
				         "{" +
				           "\"name\":\"streetAddress\"," +
				           "\"type\":\"string\"," +
				           "\"plural\":false," +
				           "\"description\":\"The full street address component, which may include house number, street name, PO BOX, and multi-line extended street address information. This attribute MAY contain newlines. OPTIONAL.\"," +
				           "\"readOnly\":false," +
				           "\"required\":false," +
				           "\"caseExact\":false" +
				         "}," +
				         "{" +
				           "\"name\":\"locality\"," +
				           "\"type\":\"string\"," +
				           "\"plural\":false," +
				           "\"description\":\"The city or locality component. OPTIONAL.\"," +
				           "\"readOnly\":false," +
				           "\"required\":false," +
				           "\"caseExact\":false" +
				         "}," +
				         "{" +
				           "\"name\":\"region\"," +
				           "\"type\":\"string\"," +
				           "\"plural\":false," +
				           "\"description\":\"The state or region component. OPTIONAL.\"," +
				           "\"readOnly\":false," +
				           "\"required\":false," +
				           "\"caseExact\":false" +
				         "}," +
				         "{" +
				           "\"name\":\"postalCode\"," +
				           "\"type\":\"string\"," +
				           "\"plural\":false," +
				           "\"description\":\"The zipcode or postal code component. OPTIONAL.\"," +
				           "\"readOnly\":false," +
				           "\"required\":false," +
				           "\"caseExact\":false" +
				         "}," +
				         "{" +
				           "\"name\":\"country\"," +
				           "\"type\":\"string\"," +
				           "\"plural\":false," +
				           "\"description\":\"The country name component. OPTIONAL.\"," +
				           "\"readOnly\":false," +
				           "\"required\":false," +
				           "\"caseExact\":false" +
				         "}" +
				       "]" +
				     "}," +
				     "{" +
				       "\"name\":\"employeeNumber\"," +
				       "\"type\":\"string\"," +
				       "\"plural\":false," +
				       "\"description\":\"Numeric or alphanumeric identifier assigned to a person, typically based on order of hire or association with an organization. Single valued. OPTIONAL.\"," +
				       "\"schema\":\"urn:scim:schemas:extension:enterprise:1.0\"," +
				       "\"readOnly\":false," +
				       "\"required\":false," +
				       "\"caseExact\":false" +
				     "}" +
				   "]" +
				"}";

		        resp.setHeader("Location", HttpGenerator.getServer(req) + "/v1/Schema/Users");
		        resp.setStatus(HttpServletResponse.SC_OK);
		        resp.setContentType(HttpGenerator.getContentType(req));
				response = Util.formatJsonPretty(response);
				resp.getWriter().print(response);	
			}
		}
		else if(uri.endsWith("Group")) {

			if(Resource.ENCODING_XML.equalsIgnoreCase(encoding)) {
		        resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
			}
			else {
				response = "{" +
					  "\"id\":\"urn:scim:schemas:core:1.0:Group\"," +
					  "\"name\":\"Group\"," +
					  "\"description\":\"Core Group\"," +
					  "\"schema\":\"urn:scim:schemas:core:1.0\"," +
					  "\"queryEndpoint\":\"/Groups\"," +
					  "\"attributes\":[" +
					    "{" +
					      "\"name\":\"id\"," +
					      "\"type\":\"string\"," +
					      "\"plural\":false," +
					      "\"description\":\"Unique identifier for the SCIM resource as defined by the Service Provider. Each representation of the resource MUST include a non-empty id value. This identifier MUST be unique across the Service Provider's entire set of resources. It MUST be a stable, non-reassignable identifier that does not change when the same resource is returned in subsequent requests. The value of the id attribute is always issued by the Service Provider and MUST never be specified by the Service Consumer. REQUIRED.\"," +
					      "\"schema\":\"urn:scim:schemas:core:1.0\"," +
					      "\"readOnly\":true," +
					      "\"required\":true," +
					      "\"caseExact\":false" +
					    "}," +
					  "]" +
					"}";
		        resp.setHeader("Location", HttpGenerator.getServer(req) + "/v1/Schema/Groups");
		        resp.setStatus(HttpServletResponse.SC_OK);
		        resp.setContentType(HttpGenerator.getContentType(req));
				response = Util.formatJsonPretty(response);
				resp.getWriter().print(response);	
			}
			
		}
		
	}
	
}
