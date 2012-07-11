package info.simplecloud.scimproxy.compliance.test;

import info.simplecloud.scimproxy.compliance.CSP;
import info.simplecloud.scimproxy.compliance.enteties.TestResult;

import java.util.ArrayList;
import java.util.List;

public class PutTest extends Test {

    public PutTest(CSP csp, UserCache cache, GroupCache groupCache) {
        super(csp, cache, groupCache);
    }

    @Override
    public List<TestResult> run() {
        List<TestResult> results = new ArrayList<TestResult>();

        return results;
    }

}
