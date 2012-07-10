package info.simplecloud.scimproxy.compliance.test;

import info.simplecloud.scimproxy.compliance.CSP;
import info.simplecloud.scimproxy.compliance.enteties.TestResult;

import java.util.List;

public abstract class Test {

	CSP csp = new CSP();
    UserCache cache;

	public Test(CSP csp, UserCache cache) {
		this.csp = csp;
		this.cache = cache;
	}
	
	public abstract List<TestResult> run();
	
}
