package info.simplecloud.scimproxy.compliance.test;

import info.simplecloud.scimproxy.compliance.CSP;
import info.simplecloud.scimproxy.compliance.ServiceProviderConfig;
import info.simplecloud.scimproxy.compliance.enteties.TestResult;

import java.util.ArrayList;
import java.util.List;

public class PatchTest extends Test{

    public PatchTest(CSP csp, UserCache cache) {
        super(csp, cache);
    }

    @Override
    public List<TestResult> run() {
        List<TestResult> results = new ArrayList<TestResult>();
        ServiceProviderConfig spc = csp.getSpc();
        if(spc.hasPatch()){
            return results;
        }
        
        
        return results;
    }

}
