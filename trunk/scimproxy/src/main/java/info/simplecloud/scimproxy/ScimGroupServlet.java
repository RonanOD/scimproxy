package info.simplecloud.scimproxy;

import info.simplecloud.core.Group;
import info.simplecloud.core.coding.encode.JsonEncoder;
import info.simplecloud.core.coding.encode.XmlEncoder;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.core.exceptions.UnknownEncoding;
import info.simplecloud.scimproxy.authentication.AuthenticateUser;
import info.simplecloud.scimproxy.exception.PreconditionException;
import info.simplecloud.scimproxy.storage.dummy.DummyStorage;
import info.simplecloud.scimproxy.storage.dummy.ResourceNotFoundException;
import info.simplecloud.scimproxy.user.UserDelegator;
import info.simplecloud.scimproxy.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class ScimGroupServlet extends ScimResourceServlet {
	
    private Log log = LogFactory.getLog(ScimGroupServlet.class);
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String groupId = Util.getGroupIdFromUri(req.getRequestURI());
        AuthenticateUser authUser = (AuthenticateUser) req.getAttribute("AuthUser");

        if(!"".equals(groupId)) {

            try {
                Group scimGroup = UserDelegator.getInstance(authUser.getSessionId()).getGroup(groupId);
                String groupStr = null;

                if (req.getParameter("attributes") != null) {
                	groupStr = scimGroup.getGroup(HttpGenerator.getEncoding(req), Util.getAttributeStringFromRequest(req));
                } else {
                	groupStr = scimGroup.getGroup(HttpGenerator.getEncoding(req));
                }

                if (groupStr == null) {
                    HttpGenerator.badRequest(resp);
                } else {
                    resp.setContentType(HttpGenerator.getContentType(req));
                    resp.setHeader("ETag", scimGroup.getMeta().getVersion());

                    HttpGenerator.ok(resp, groupStr);
                    log.info("Returning group " + scimGroup.getId());
                }

            } catch (UnknownEncoding e) {
                HttpGenerator.serverError(resp);
            } catch (ResourceNotFoundException e) {
                HttpGenerator.notFound(resp);
            }
        }
        else {
            String attributesString = req.getParameter("attributes") == null ? "" : req.getParameter("attributes");
            List<String> attributesList = new ArrayList<String>();
            if (attributesString != null && !"".equals(attributesString)) {
                for (String attribute : attributesString.split(",")) {
                    attributesList.add(attribute.trim());
                }
            }

            // TODO: SPEC: REST: what is major
            String sortBy = req.getParameter("sortBy") == null ? "displayName" : req.getParameter("sortBy");
            String sortOrder = req.getParameter("sortOrder") == null ? "ascending" : req.getParameter("sortOrder");
            if (!sortOrder.equalsIgnoreCase("ascending") && !sortOrder.equalsIgnoreCase("descending")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print("Sort order must be 'ascending' or 'descending'");
                return;
            }

            DummyStorage storage = DummyStorage.getInstance(authUser.getSessionId());
            @SuppressWarnings("rawtypes")
    		List groups = null;

            String filter = req.getParameter("filter");
            
            if (filter != null && !"".equals(filter)) {
            	groups = storage.getList(sortBy, sortOrder, filter);
            } else {
            	groups = storage.getGroupList(sortBy, sortOrder);
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
            if (max > groups.size() || max == 0) {
                max = groups.size();
            }

            if (index > groups.size()) {
                index = groups.size();
            }

            try {
            	groups = groups.subList(index, max);
            } catch (IndexOutOfBoundsException e) {
            	groups = new ArrayList<Group>();
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(HttpGenerator.getContentType(req));

            String response = "";
            if (Group.ENCODING_JSON.equalsIgnoreCase(HttpGenerator.getEncoding(req))) {
                response = new JsonEncoder().encode(groups, attributesList);
            }
            if (Group.ENCODING_XML.equalsIgnoreCase(HttpGenerator.getEncoding(req))) {
                response = new XmlEncoder().encode(groups, attributesList);
            }

            resp.getWriter().print(response);
        }

    }

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String query = Util.getContent(req);

        if (query != null && !"".equals(query)) {
            try {
            	
                String server = HttpGenerator.getServer(req);
                String outEncoding = HttpGenerator.getEncoding(req);
                AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");

                ResourceJob resource = new ResourceJob();
                resource.setData(query);

            	Group scimGroup = internalGroupPost(resource, server, outEncoding, authUser);
            	
                resp.setContentType(HttpGenerator.getContentType(req));
                resp.setHeader("Location", scimGroup.getMeta().getLocation());
                resp.setHeader("ETag", scimGroup.getMeta().getVersion());

                log.info("Creating group " + scimGroup.getId());
                
                HttpGenerator.created(resp, scimGroup.getGroup(HttpGenerator.getEncoding(req)));
            } catch (UnknownEncoding e) {
                HttpGenerator.serverError(resp, "Unknown encoding.");
            } catch (InvalidUser e) {
                HttpGenerator.badRequest(resp, "Malformed user.");
            }
        } else {
            HttpGenerator.badRequest(resp);
        }	
	}

	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String groupId = Util.getGroupIdFromUri(req.getRequestURI());
        String query = Util.getContent(req);
        String etag = req.getHeader("ETag");
        
        if (query != null && !"".equals(query) && groupId != null) {

            try {
                String server = HttpGenerator.getServer(req);
                String outEncoding = HttpGenerator.getEncoding(req);
                AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");

                ResourceJob resource = new ResourceJob();
                resource.setData(query);
                resource.setVersion(etag);
                resource.setId(groupId);

            	Group scimGroup = internalGroupPut(resource, server, outEncoding, authUser);

                resp.setContentType(HttpGenerator.getContentType(req));
                resp.setHeader("Location", scimGroup.getMeta().getLocation());
                resp.setHeader("ETag", scimGroup.getMeta().getVersion());
                HttpGenerator.ok(resp, scimGroup.getGroup(HttpGenerator.getEncoding(req)));

                log.info("Replacing group " + scimGroup.getId());
            } catch (PreconditionException e) {
                HttpGenerator.preconditionFailed(resp, groupId);
            } catch (ResourceNotFoundException e) {
                HttpGenerator.notFound(resp);
            } catch (UnknownEncoding e) {
                HttpGenerator.badRequest(resp, "Unknown encoding.");
            } catch (InvalidUser e) {
                HttpGenerator.badRequest(resp, "Invalid group.");
            }

        } else {
            HttpGenerator.badRequest(resp, "Invalid group or group id.");
        }	
	}

	public void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String groupId = Util.getGroupIdFromUri(req.getRequestURI());
        String query = Util.getContent(req);
        String etag = req.getHeader("ETag");
        
        if (!"".equals(query) && groupId != null && etag != null && !"".equals(etag)) {

        	try {
                String server = HttpGenerator.getServer(req);
                String outEncoding = HttpGenerator.getEncoding(req);
                AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");

                ResourceJob resource = new ResourceJob();
                resource.setData(query);
                resource.setVersion(etag);
                resource.setId(groupId);
        		
				Group scimGroup = internalGroupPatch(resource, server, outEncoding, authUser);
				
                resp.setContentType(HttpGenerator.getContentType(req));
                resp.setHeader("Location", scimGroup.getMeta().getLocation());
                resp.setHeader("ETag", scimGroup.getMeta().getVersion());
                HttpGenerator.ok(resp, scimGroup.getGroup(HttpGenerator.getEncoding(req)));

                log.info("Patching user " + scimGroup.getId());
				
			} catch (UnknownEncoding e) {
                HttpGenerator.badRequest(resp, "Unknown encoding.");
			} catch (InvalidUser e) {
                HttpGenerator.badRequest(resp, "Invalid group.");
			} catch (ResourceNotFoundException e) {
                HttpGenerator.notFound(resp);
			} catch (PreconditionException e) {
                HttpGenerator.preconditionFailed(resp, groupId);
			} catch (UnknownAttribute e) {
                HttpGenerator.badRequest(resp, "Malformed group.");
			}
	    } else {
            HttpGenerator.badRequest(resp, "Missing group id or ETag");
	    }
 	}


	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String groupId = Util.getGroupIdFromUri(req.getRequestURI());
        String etag = req.getHeader("ETag");
        
        log.trace("Trying to deleting group " + groupId + ".");
		
		if (groupId != null) {
		    try {
                String server = HttpGenerator.getServer(req);
                String outEncoding = HttpGenerator.getEncoding(req);
                AuthenticateUser authUser = (AuthenticateUser)req.getAttribute("AuthUser");

                ResourceJob resource = new ResourceJob();
                resource.setVersion(etag);
                resource.setId(groupId);
                
				internalGroupDelete(resource, server, outEncoding, authUser);
		        HttpGenerator.ok(resp);
		        log.info("Deleating group " + groupId + ".");
			} catch (ResourceNotFoundException e) {
			    HttpGenerator.notFound(resp);
			    log.trace("Group " + groupId + " is not found.");
		    }
		    catch (PreconditionException e) {
		    	HttpGenerator.preconditionFailed(resp, groupId);
		    }
		} else {
		    log.trace("Trying to delete a group that can't be found in storage with group id " + groupId + ".");
		    HttpGenerator.badRequest(resp, "Missing or malformed group id.");
		}	
	}
	
}
