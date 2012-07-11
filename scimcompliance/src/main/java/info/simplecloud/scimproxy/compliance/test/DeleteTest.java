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

	public DeleteTest(CSP csp, ResourceCache<CachedUser> cache, ResourceCache<CachedGroup> groupCache) {
		super(csp, cache, groupCache);
	}

	@Override
	public List<TestResult> run() {
		List<TestResult> results = new ArrayList<TestResult>();

		CachedUser cachedUser;
		while(this.userCache.size() > 0) {
			cachedUser = (CachedUser) this.userCache.removeCachedResource();
			results.add(delete(cachedUser.getId(), cachedUser.getEtag(), "/Users/", "Delete user", 200));
			results.add(delete(cachedUser.getId(), cachedUser.getEtag(), "/Users/", "Delete non-existing user", 404));
		}
		CachedResource group;
		while(this.groupCache.size() > 0) {
			group = this.groupCache.removeCachedResource();
			results.add(delete(group.getId(), group.getEtag(), "/Groups/", "Delete group", 200));
			results.add(delete(group.getId(), group.getEtag(), "/Groups/", "Delete non-existing group", 404));
		}
		return results;
	}

	private TestResult delete(String id, String etag, String path, String test, int expectedCode) {
		DeleteMethod method = new DeleteMethod(csp.getUrl() + csp.getVersion() + path + id);
		ComplienceUtils.configureMethod(method);
		method.setRequestHeader(new Header("Accept", "application/json"));
		method.setRequestHeader(new Header("If-Match", etag));
		HttpClient client = ComplienceUtils.getHttpClientWithAuth(csp, method);
		String resourcesString = "<no resource>";
		
		try {
            int statusCode = client.executeMethod(method);

            if (statusCode != expectedCode) {
                return new TestResult(TestResult.ERROR, test , "Failed. Server did not respond with " + expectedCode + ".", ComplienceUtils.getWire(
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
