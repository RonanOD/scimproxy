package info.simplecloud.scimproxy.compliance.test;

import info.simplecloud.scimproxy.compliance.CSP;
import info.simplecloud.scimproxy.compliance.enteties.TestResult;

import java.util.List;

public abstract class Test {

	CSP csp = new CSP();
    UserCache userCache;
    GroupCache groupCache;

	public Test(CSP csp, UserCache cache, GroupCache groupCache) {
		this.csp = csp;
		this.userCache = cache;
		this.groupCache = groupCache;
	}
	
	public abstract List<TestResult> run();
	
}
