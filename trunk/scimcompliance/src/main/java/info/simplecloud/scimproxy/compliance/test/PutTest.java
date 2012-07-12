package info.simplecloud.scimproxy.compliance.test;

import info.simplecloud.core.Group;
import info.simplecloud.core.Resource;
import info.simplecloud.core.User;
import info.simplecloud.core.types.Meta;
import info.simplecloud.scimproxy.compliance.CSP;
import info.simplecloud.scimproxy.compliance.ComplienceUtils;
import info.simplecloud.scimproxy.compliance.enteties.TestResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.exception.ExceptionUtils;

public class PutTest extends Test {

	public PutTest(CSP csp, ResourceCache<CachedUser> cache, ResourceCache<CachedGroup> groupCache) {
		super(csp, cache, groupCache);
	}

	@Override
	public List<TestResult> run() {
		List<TestResult> results = new ArrayList<TestResult>();
		User user = ComplienceUtils.getUser();
		CachedUser cachedUser = this.userCache.borrowCachedResource();
		user.setId(cachedUser.getId());
		user.setMeta(new Meta());
		user.getMeta().setVersion(cachedUser.getEtag());

		results.add(putUser(user, cachedUser, "PUT User JSON", "/Users/", User.ENCODING_JSON));
		user.setDisplayName("Bobert");
		user.getMeta().setVersion(cachedUser.getEtag());
		results.add(putUser(user, cachedUser, "PUT User XML", "/Users/", User.ENCODING_XML));

		// Groups
		CachedGroup cachedGroup = this.groupCache.borrowCachedResource();
		Group group = new Group();
		group.setDisplayName("TheTeam");
		group.setId(cachedGroup.getId());
		group.setMeta(new Meta());
		group.getMeta().setVersion(cachedGroup.getEtag());

		results.add(putGroup(group, cachedGroup, "PUT Group JSON", "/Groups/", User.ENCODING_JSON));
		group.setDisplayName("2ndTeam");
		group.getMeta().setVersion(cachedGroup.getEtag());
		results.add(putGroup(group, cachedGroup, "PUT Group XML", "/Groups/", User.ENCODING_XML));
		return results;
	}

	private TestResult putGroup(Group group, CachedResource cachedResource, String test, String path, String encoding) {
		PutMethod method = getMethod(group, path, encoding);

		StringRequestEntity body = null;
		try {
			body = new StringRequestEntity(group.getGroup(encoding), "application/" + encoding, CharEncoding.UTF_8);
		} catch (Exception e) {
			return new TestResult(TestResult.ERROR, test, "Failed. " + e.getMessage(),
					ExceptionUtils.getFullStackTrace(e));
		}

		String responseBody;
		try {
			responseBody = doPut(test, method, body);
		} catch (Exception e) {
			return new TestResult(TestResult.ERROR, test, "Failed. " + e.getMessage(), ComplienceUtils.getWire(method,
					body.getContent()));
		}

		Group responsegroup;
		try {
			responsegroup = new Group(responseBody, encoding);
			cachedResource.setEtag(responsegroup.getMeta().getVersion());
		} catch (Exception e) {
			return new TestResult(TestResult.ERROR, test, "Failed. Could not parse group. " + e.getMessage(),
					ComplienceUtils.getWire(method, body.getContent()));
		}
		if (!group.getDisplayName().equals(responsegroup.getDisplayName())) {
			return new TestResult(TestResult.ERROR, test, "Failed. Server responded with a different display name.",
					ComplienceUtils.getWire(method, body.getContent()));
		}

		return new TestResult(TestResult.SUCCESS, test, "", ComplienceUtils.getWire(method, body.getContent()));
	}

	private TestResult putUser(User user, CachedResource cachedResource, String test, String path, String encoding) {

		PutMethod method = getMethod(user, path, encoding);

		StringRequestEntity body = null;
		try {
			body = new StringRequestEntity(user.getUser(encoding), "application/" + encoding, CharEncoding.UTF_8);
		} catch (Exception e) {
			return new TestResult(TestResult.ERROR, test, "Failed. " + e.getMessage(),
					ExceptionUtils.getFullStackTrace(e));
		}

		String responseBody;
		try {
			responseBody = doPut(test, method, body);
		} catch (Exception e) {
			return new TestResult(TestResult.ERROR, test, "Failed. " + e.getMessage(), ComplienceUtils.getWire(method,
					body.getContent()));
		}

		User responseUser;
		try {
			responseUser = new User(responseBody, encoding);
			cachedResource.setEtag(responseUser.getMeta().getVersion());
		} catch (Exception e) {
			return new TestResult(TestResult.ERROR, test, "Failed. Could not parse user. " + e.getMessage(),
					ComplienceUtils.getWire(method, body.getContent()));
		}
		if (!user.getDisplayName().equals(responseUser.getDisplayName())) {
			return new TestResult(TestResult.ERROR, test, "Failed. Server responded with a different display name.",
					ComplienceUtils.getWire(method, body.getContent()));
		}

		return new TestResult(TestResult.SUCCESS, test, "", ComplienceUtils.getWire(method, body.getContent()));

	}

	private String doPut(String test, PutMethod method, StringRequestEntity body) throws Exception {
		method.setRequestEntity(body);

		HttpClient client = ComplienceUtils.getHttpClientWithAuth(this.csp, method);

		int code;
		try {
			code = client.executeMethod(method);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
		String responseBody = null;
		try {
			responseBody = method.getResponseBodyAsString();
		} catch (IOException e) {
			responseBody = "";
		}
		if (code != 200) {
			throw new Exception(responseBody);
		}
		return responseBody;
	}

	private PutMethod getMethod(Resource resource, String path, String encoding) {
		PutMethod method = new PutMethod(this.csp.getUrl() + this.csp.getVersion() + path + resource.getId());

		ComplienceUtils.configureMethod(method);
		method.setRequestHeader(new Header("Accept", "application/" + encoding));
		if (resource.getMeta() != null && !resource.getMeta().getVersion().isEmpty()) {
			method.setRequestHeader(new Header("If-Match", resource.getMeta().getVersion()));
		}
		return method;
	}
}
