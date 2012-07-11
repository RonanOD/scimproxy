package info.simplecloud.scimproxy.compliance.test;

import info.simplecloud.scimproxy.compliance.CSP;
import info.simplecloud.scimproxy.compliance.ComplienceUtils;
import info.simplecloud.scimproxy.compliance.enteties.TestResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.DeleteMethod;

public class DeleteTest extends Test {

	public DeleteTest(CSP csp, UserCache cache, GroupCache groupCache) {
		super(csp, cache, groupCache);
	}

	@Override
	public List<TestResult> run() {
		List<TestResult> results = new ArrayList<TestResult>();

		CachedUser cachedUser;
		while(this.userCache.size() > 0) {
			cachedUser = this.userCache.removeCashedUser();
			results.add(delete(cachedUser.getId(), cachedUser.getEtag(), "Delete User Test"));
		}
		return results;
	}

	private TestResult delete(String id, String etag, String test) {
		DeleteMethod method = new DeleteMethod(csp.getUrl() + csp.getVersion() + "/Users/" + id);
		ComplienceUtils.configureMethod(method);
		method.setRequestHeader(new Header("Accept", "application/json"));
		method.setRequestHeader(new Header("If-Match", etag));
		HttpClient client = ComplienceUtils.getHttpClientWithAuth(csp, method);
		String resourcesString = "<no resource>";
		
		// First, for real
		try {
            int statusCode = client.executeMethod(method);

            if (statusCode != 200) {
                return new TestResult(TestResult.ERROR, test , "Failed. Server did not respond with 200 OK.", ComplienceUtils.getWire(
                        method, resourcesString));
            } else {
                resourcesString = method.getResponseBodyAsString();
            }
        } catch (HttpException e) {
            return new TestResult(TestResult.ERROR, test, "Failed, http error: " + e.getMessage(), ComplienceUtils.getWire(method,
                    resourcesString));
        } catch (IOException e) {
            return new TestResult(TestResult.ERROR, test, "Failed, io error: " + e.getMessage(), ComplienceUtils.getWire(method,
                    resourcesString));
        }

		// Expect a 404
		try {
            int statusCode = client.executeMethod(method);

            if (statusCode != 404) {
                return new TestResult(TestResult.ERROR, test , "Failed. Server did not respond with 404 OK.", ComplienceUtils.getWire(
                        method, resourcesString));
            } else {
                resourcesString = method.getResponseBodyAsString();
            }
        } catch (HttpException e) {
            return new TestResult(TestResult.ERROR, test, "Failed, http error: " + e.getMessage(), ComplienceUtils.getWire(method,
                    resourcesString));
        } catch (IOException e) {
            return new TestResult(TestResult.ERROR, test, "Failed, io error: " + e.getMessage(), ComplienceUtils.getWire(method,
                    resourcesString));
        }

        return new TestResult(TestResult.SUCCESS, test, "", ComplienceUtils.getWire(method, resourcesString));

	}
}
