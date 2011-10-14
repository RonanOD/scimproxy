package info.simplecloud.scimproxy.viewer.version2;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class Helper {
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

    public static String getRequestedURL(HttpServletRequest req) {
        StringBuffer full = req.getRequestURL();
        return full.substring(0, full.indexOf("/Viewer/"));
    }
}
