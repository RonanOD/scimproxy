package info.simplecloud.scimproxy.viewer.version2;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringEscapeUtils;

@SuppressWarnings("serial")
public class Edit extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String creds = (String) req.getSession().getAttribute("Creds");
        if (creds == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authenticate.");
            return;
        }

        String baseUrl = (String) req.getSession().getAttribute("BaseUrl");
        if (baseUrl == null) {
            System.out.println("Error, missing base url");
            return;
        }

        Map<String, String> indata = Helper.readJsonPostData(req);

        HttpClient client = new HttpClient();
        client.getParams().setAuthenticationPreemptive(false);

        EntityEnclosingMethod method;
        String url = baseUrl + "v1/" + indata.get("type") + "/" + indata.get("id");
        if ("PATCH".equals(indata.get("operation"))) {
            method = new PostMethod(url);
            method.setRequestHeader("X-HTTP-Method-Override", "PATCH");
        } else {
            method = new PutMethod(url);
        }

        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
        method.setRequestHeader("Accept", indata.get("encoding"));
        method.setRequestHeader("Authorization", creds);
        method.setRequestBody(indata.get("data"));

        client.executeMethod(method);

        if ("application/json".equals(indata.get("encoding"))) {
            resp.getWriter().print(method.getResponseBodyAsString());
        } else {
            resp.getWriter().print(StringEscapeUtils.escapeHtml(method.getResponseBodyAsString()));
        }

    }

}
