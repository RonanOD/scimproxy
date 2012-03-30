package info.simplecloud.scimproxy.viewer.version;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

@SuppressWarnings("serial")
public class Bulk extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        
        Map<String, String> indata = readJsonPostData(req);

        HttpClient client = new HttpClient();
        client.getParams().setAuthenticationPreemptive(false);

        PostMethod method = new PostMethod(baseUrl + "Bulk");
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
        method.setRequestHeader("Content-Type", "application/json");
        method.setRequestHeader("Authorization", creds);
        method.setRequestBody(indata.get("data"));

        int responseCode = client.executeMethod(method);
        if (responseCode == 200) {
            resp.getWriter().print(method.getResponseBodyAsString());
        } else {
            resp.getWriter().print("Error, server returned " + responseCode);
            resp.getWriter().print(method.getResponseBodyAsString());
        }
    }
    
    public static Map<String, String> readJsonPostData(HttpServletRequest req) {
        try {
            BufferedReader br = req.getReader();
            String line;
            String all = "";
            while ((line = br.readLine()) != null) {
                all += line + "\n";
            }

            Map<String, String> result = new HashMap<String, String>();
            String[] parameters = all.split("&");
            for (String parameter : parameters) {
                if(parameter.split("=").length == 2){                    
                    String name = parameter.split("=")[0];
                    String value = parameter.split("=")[1];
                    if (!value.trim().isEmpty()) {
                        result.put(name, URLDecoder.decode(value).trim());
                    }
                }
            }

            return result;
        } catch (IOException e) {
            throw new RuntimeException("failed to read json from post request", e);
        }
    }

}
