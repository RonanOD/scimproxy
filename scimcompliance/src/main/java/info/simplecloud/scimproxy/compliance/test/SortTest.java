package info.simplecloud.scimproxy.compliance.test;

import info.simplecloud.scimproxy.compliance.CSP;
import info.simplecloud.scimproxy.compliance.ComplienceUtils;
import info.simplecloud.scimproxy.compliance.ServiceProviderConfig;
import info.simplecloud.scimproxy.compliance.enteties.TestResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

public class SortTest extends Test{

    public SortTest(CSP csp, UserCache cache) {
        super(csp, cache);
    }

    @Override
    public List<TestResult> run() {
        List<TestResult> results = new ArrayList<TestResult>();
        
        ServiceProviderConfig spc = csp.getSpc();
        
        if(spc.hasXmlDataFormat()) {
            results.add(doSort(new Header("Accept", "application/xml"), "ascending", "XML"));
            results.add(doSort(new Header("Accept", "application/xml"), "descending", "XML"));
        }
        
        
        results.add(doSort(new Header("Accept", "application/json"), "ascending", "JSON"));
        results.add(doSort(new Header("Accept", "application/json"), "descending", "JSON"));
        
        return results;
    }
    
    private TestResult doSort(Header accept, String order, String encoding) {
        List<TestResult> results = new ArrayList<TestResult>();

        GetMethod method = new GetMethod();

        ComplienceUtils.configureMethod(method);
        method.setRequestHeader(accept);
        HttpClient client = ComplienceUtils.getHttpClientWithAuth(csp, method);
        
        try {
            int statusCode = client.executeMethod(method);
            
            if(statusCode != 200) {
                
            } else {
                return new TestResult(TestResult.ERROR, String.format("Sort users %s in %s format", order, encoding), "Failed. Server did not respond with 200 OK.", ComplienceUtils.getWire(method, null));
            }
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return new TestResult(TestResult.ERROR,"","",""); 
        
    }

}
