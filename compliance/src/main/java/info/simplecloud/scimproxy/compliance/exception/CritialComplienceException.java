package info.simplecloud.scimproxy.compliance.exception;

import info.simplecloud.scimproxy.compliance.Result;

public class CritialComplienceException extends Exception {

	private Result result = null;

	private static final long serialVersionUID = 1L;
	
	
	public CritialComplienceException(Result result) {
		this.result = result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Result getResult() {
		return result;
	}
	
}
