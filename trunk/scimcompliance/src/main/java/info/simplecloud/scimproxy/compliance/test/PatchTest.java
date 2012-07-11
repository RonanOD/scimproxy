package info.simplecloud.scimproxy.compliance.test;

import info.simplecloud.core.User;
import info.simplecloud.scimproxy.compliance.CSP;
import info.simplecloud.scimproxy.compliance.ComplienceUtils;
import info.simplecloud.scimproxy.compliance.ServiceProviderConfig;
import info.simplecloud.scimproxy.compliance.enteties.TestResult;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PatchMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.exception.ExceptionUtils;

public class PatchTest extends Test{

    public PatchTest(CSP csp, ResourceCache<CachedUser> cache, ResourceCache<CachedGroup> groupCache) {
        super(csp, cache, groupCache);
    }

    @Override
    public List<TestResult> run() {
        List<TestResult> results = new ArrayList<TestResult>();
        ServiceProviderConfig spc = csp.getSpc();
        if(spc.hasPatch()){
        	CachedUser user = this.userCache.borrowCachedResource();
        	results.add(patch(user, "Alice", "PATCH User JSON", "/Users/", User.ENCODING_JSON));
        	results.add(patch(user, "Bob", "PATCH User XML", "/Users/", User.ENCODING_XML));
        	CachedGroup group = this.groupCache.borrowCachedResource();
        	results.add(patch(group, "SuperGroup", "PATCH Group JSON", "/Groups/", User.ENCODING_JSON));
        	results.add(patch(group, "DuperGroup", "PATCH Group XML", "/Groups/", User.ENCODING_XML));
        } else {
        	results.add(new TestResult(TestResult.WARNING, "Test PATCH" , "Failed. ServiceProvider does not support PATCH.", "<empty>"));
        }
        return results;
    }
    
    public TestResult patch(CachedResource resource, String displayName, String test, String path, String encoding) {

    	PatchMethod method = new PatchMethod(csp.getUrl() + csp.getVersion() + path + resource.getId());
		User scimUser = new User(resource.getId());
		scimUser.setDisplayName(displayName);
		
		ComplienceUtils.configureMethod(method);
		method.setRequestHeader(new Header("Accept", "application/json"));
		method.setRequestHeader(new Header("If-Match", resource.getEtag()));
		StringRequestEntity body = null;
		try {
			body = new StringRequestEntity(scimUser.getUser(encoding), encoding, "UTF-8");
		} catch (Exception e) {
			return new TestResult(TestResult.ERROR, test , "Failed. " + e.getMessage(), ExceptionUtils.getFullStackTrace(e));
		}
		method.setRequestEntity(body);

		HttpClient client = ComplienceUtils.getHttpClientWithAuth(csp, method);
		int code;
		try {
			code = client.executeMethod(method);
		} catch (Exception e) {
			return new TestResult(TestResult.ERROR, test , "Failed. " + e.getMessage(), ComplienceUtils.getWire(method, body.getContent()));
		}
		if (code != 200) {
			return new TestResult(TestResult.ERROR, test , "Failed. Server did not respond with 200 OK.", ComplienceUtils.getWire(
					method, body.getContent()));
		}
		User responseUser;
		try {
			responseUser = new User(method.getResponseBodyAsString(),
					User.ENCODING_JSON);
		} catch (Exception e) {
			return new TestResult(TestResult.ERROR, test , "Failed. Could not parse user. " + e.getMessage(), ComplienceUtils.getWire(
					method, body.getContent()));
		}
		if (!displayName.equals(responseUser.getDisplayName())) {
			return new TestResult(TestResult.ERROR, test , "Failed. Server responded with a different display name.", ComplienceUtils.getWire(
					method, body.getContent()));
		}
		resource.setEtag(responseUser.getMeta().getVersion());
		return new TestResult(TestResult.SUCCESS, test, "", ComplienceUtils.getWire(method, body.getContent()));

    }
}

/*
2012-05-03 15:08:52 3 INFO - - "AUTHORIZATION ACCESS_GRANTED: Request URI: mvpnas://mvpnas:0/wa/client.ewa [ Resource URI:mvpnas://mvpnas:0/ (cache 2250 sec. recursive) ] "
2012-05-03 15:08:58 0 192.176.242.136 "Access Client" 0adc707064451337 - - - login.moderat.se https - - GET /wa/tunnel - "Access Client/4,9,11,5088" - - 130 HTTP/1.0 - - 171 101
2012-05-03 15:08:58 WARNING 1032092 "Client disconnected unexpectedly, winerr=0" - - - -
2012-05-03 15:08:52 3 INFO - - "AUTHORIZATION ACCESS_DENIED: Request URI: mvpnas://mvpnas:0/wa/webclient/hhioqg8w4jk0 [ Resource URI:wa/webclient/VPN MODvStrExtPub ] #SG-VPN-MODvStrExtPub-Users evaluated false "

2012-05-03 15:09:04 3 INFO - - "AUTHORIZATION ACCESS_GRANTED: Request URI: mvpnas://mvpnas:0/wa/client.ewa [ Resource URI:mvpnas://mvpnas:0/ (cache 2250 sec. recursive) ] "
2012-05-03 15:09:04 3 INFO - - "AUTHORIZATION ACCESS_DENIED: Request URI: mvpnas://mvpnas:0/wa/webclient/hhioqg8w4jk0 [ Resource URI:wa/webclient/VPN MODvStrExtPub ] #SG-VPN-MODvStrExtPub-Users evaluated false "
2012-05-03 15:09:09 WARNING 1032092 "Client disconnected unexpectedly, winerr=0" - - - -
2012-05-03 15:09:09 0 192.176.242.136 "Access Client" fde4500d2142c301 - - - login.moderat.se https - - GET /wa/tunnel - "Access Client/4,9,11,5088" - - 130 HTTP/1.0 - - 171 101

2012-05-03 15:09:30 3 INFO - - "AUTHORIZATION ACCESS_GRANTED: Request URI: mvpnas://mvpnas:0/wa/client.ewa [ Resource URI:mvpnas://mvpnas:0/ (cache 2250 sec. recursive) ] "
2012-05-03 15:09:30 3 INFO - - "AUTHORIZATION ACCESS_DENIED: Request URI: mvpnas://mvpnas:0/wa/webclient/hhioqg8w4jk0 [ Resource URI:wa/webclient/VPN MODvStrExtPub ] #SG-VPN-MODvStrExtPub-Users evaluated false "
2012-05-03 15:09:35 0 192.176.242.136 "Access Client" 6795970757baa16d - - - login.moderat.se https - - GET /wa/tunnel - "Access Client/4,9,11,5088" - - 130 HTTP/1.0 - - 171 101
2012-05-03 15:09:35 WARNING 1032092 "Client disconnected unexpectedly, winerr=0" - - - -
2012-05-03 15:09:49 3 INFO - - "AUTHORIZATION ACCESS_GRANTED: Request URI: mvpnas://mvpnas:0/wa/client.ewa [ Resource URI:mvpnas://mvpnas:0/ (cache 2250 sec. recursive) ] "

2012-05-03 15:09:55 WARNING 1032092 "Client disconnected unexpectedly, winerr=0" - - - -
2012-05-03 15:09:55 0 192.176.242.136 "Access Client" f8f55b737a20d17f - - - login.moderat.se https - - GET /wa/tunnel - "Access Client/4,9,11,5088" - - 130 HTTP/1.0 - - 171 101
2012-05-03 15:09:58 3 INFO - - "AUTHORIZATION ACCESS_GRANTED: Request URI: mvpnas://mvpnas:0/wa/client.ewa [ Resource URI:mvpnas://mvpnas:0/ (cache 2250 sec. recursive) ] "
2012-05-03 15:10:03 0 192.176.242.136 "Access Client" 8578cd04e86ab8db - - - login.moderat.se https - - GET /wa/tunnel - "Access Client/4,9,11,5088" - - 130 HTTP/1.0 - - 171 101
2012-05-03 15:11:30 3 INFO - - "AUTHORIZATION ACCESS_DENIED: Request URI: mvpnas://mvpnas:0/wa/webclient/hhioqg8w4jk0 [ Resource URI:wa/webclient/VPN MODvStrExtPub ] #SG-VPN-MODvStrExtPub-Users evaluated false "
2012-05-03 15:11:30 3 INFO - - "AUTHORIZATION ACCESS_GRANTED: Request URI: mvpnas://mvpnas:0/wa/client.ewa [ Resource URI:mvpnas://mvpnas:0/ (cache 2250 sec. recursive) ] "
2012-05-03 15:11:32 WARNING 1032092 "Client disconnected unexpectedly, winerr=0" - - - -
2012-05-03 15:11:35 WARNING 1032092 "Client disconnected unexpectedly, winerr=0" - - - -
2012-05-03 15:11:35 3 INFO - - "AUTHORIZATION ACCESS_DENIED: Request URI: mvpnas://mvpnas:0/wa/webclient/hhioqg8w4jk0 [ Resource URI:wa/webclient/VPN MODvStrExtPub ] #SG-VPN-MODvStrExtPub-Users evaluated false "
2012-05-03 15:11:35 0 192.176.242.136 "Access Client" f2bf5df30514dc07 - - - login.moderat.se https - - GET /wa/tunnel - "Access Client/4,9,11,5088" - - 130 HTTP/1.0 - - 171 101

*/


