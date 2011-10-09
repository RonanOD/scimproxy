package info.simplecloud.scimproxy;

import info.simplecloud.scimproxy.config.Config;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

public class ScimServiceProviderConfigServletGetTest {

    private static HttpTester     request  = new HttpTester();
    private static HttpTester     response = new HttpTester();
    private static ServletTester  tester   = null;

    @BeforeClass
    public static void setUp() throws Exception {
        tester = new ServletTester();
        tester.addServlet(ScimServiceProviderConfigServlet.class, "/v1/ServiceProviderConfig");
        tester.addServlet(DefaultServlet.class, "/");
        tester.start();
    }

    @Test
    public void getConf() throws Exception {
        request.setMethod("GET");
        request.setVersion("HTTP/1.0");
        request.setHeader("Authorization", "Basic dXNyOnB3");

        request.setURI("/v1/ServiceProviderConfig");
        response.parse(tester.getResponses(request.generate()));

        Assert.assertEquals(200, response.getStatus());
        String conf = response.getContent();
        
		JSONObject jsonObj = new JSONObject(conf);
		JSONObject bulkObj = jsonObj.getJSONObject("bulk");

        Assert.assertEquals(true, bulkObj.getBoolean("supported"));
        Assert.assertEquals(Config.getInstance().getBulkMaxPayloadSize(), bulkObj.getInt("maxPayloadSize"));
        Assert.assertEquals(Config.getInstance().getBulkMaxOperations(), bulkObj.getInt("maxOperations"));
    }

}
