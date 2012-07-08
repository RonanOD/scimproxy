package info.simplecloud.scimproxy.compliance.test;

import info.simplecloud.scimproxy.compliance.CSP;
import info.simplecloud.scimproxy.compliance.enteties.TestResult;

import java.util.ArrayList;

public abstract class Test {

	CSP csp = new CSP();

	public Test(CSP csp) {
		this.csp = csp;
	}
	
	public abstract ArrayList<TestResult> run();
	
}
