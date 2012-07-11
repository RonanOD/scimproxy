package info.simplecloud.scimproxy.compliance;

import info.simplecloud.scimproxy.compliance.enteties.AuthMetod;
import info.simplecloud.scimproxy.compliance.enteties.Result;
import info.simplecloud.scimproxy.compliance.enteties.Statistics;
import info.simplecloud.scimproxy.compliance.enteties.TestResult;
import info.simplecloud.scimproxy.compliance.exception.CritialComplienceException;
import info.simplecloud.scimproxy.compliance.test.ConfigTest;
import info.simplecloud.scimproxy.compliance.test.FilterTest;
import info.simplecloud.scimproxy.compliance.test.PatchTest;
import info.simplecloud.scimproxy.compliance.test.PostTest;
import info.simplecloud.scimproxy.compliance.test.PutTest;
import info.simplecloud.scimproxy.compliance.test.SortTest;
import info.simplecloud.scimproxy.compliance.test.UserCache;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.validator.routines.UrlValidator;

@Path("/test")
public class Compliance extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // TODO: l18n and remove hardcoded text strings in code

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Result runTests(@FormParam("url") String url, @FormParam("username") String username, @FormParam("password") String password,
            @FormParam("clientId") String clientId, @FormParam("clientSecret") String clientSecret,
            @FormParam("authorizationServer") String authorizationServer, @FormParam("authMethod") String authMethod)
            throws InterruptedException, ServletException {

        // TODO: remove when done coding!
        if (url == null || url.isEmpty()) {
            url = "http://127.0.0.1:8080";
        }

        String[] schemes = { "http", "https" };
        UrlValidator urlValidator = new UrlValidator(schemes, UrlValidator.ALLOW_LOCAL_URLS);
        if (!urlValidator.isValid(url)) {
            return new Result("Invalid service provider URL");
        }

        // create a CSP to use to connect to the server
        CSP csp = new CSP();
        csp.setUrl(url);
        csp.setVersion("/v1");
        csp.setAuthentication(authMethod);
        csp.setUsername(username);
        csp.setPassword(password);
        csp.setOAuth2AuthorizationServer(authorizationServer);
        csp.setoAuth2ClientId(clientId);
        csp.setoAuth2ClientSecret(clientSecret);
        csp.setoAuth2GrantType("password");

        ArrayList<TestResult> results = new ArrayList<TestResult>();

        // get the configuration
        try {
            // start with the critical tests (will throw exception and test will
            // stop if fails)
            ConfigTest configTest = new ConfigTest();
            results.add(configTest.getConfiguration(csp));
            results.add(configTest.getSchema("User", csp));
            results.add(configTest.getSchema("Group", csp));

            if ((AuthMetod.AUTH_BASIC.equalsIgnoreCase(authMethod) && StringUtils.isEmpty(username) && StringUtils.isEmpty(password))
                    || (AuthMetod.AUTH_OAUTH.equalsIgnoreCase(authMethod) && StringUtils.isEmpty(username) && StringUtils.isEmpty(password)
                            && StringUtils.isEmpty(authorizationServer) && StringUtils.isEmpty(clientId) && StringUtils
                            .isEmpty(clientSecret))) {
                ServiceProviderConfig spc = csp.getSpc();
                return new Result(spc.getAuthenticationSchemes());
            }

            // TODO: add the required attributes in userSchema and groupSchema
            // that server wanted

            UserCache cache = new UserCache();

            results.addAll(new PostTest(csp, cache).run());
            // results.addAll(new DeleteTest(csp, cache).run());
            results.addAll(new FilterTest(csp, cache).run());
            results.addAll(new PatchTest(csp, cache).run());
            results.addAll(new PutTest(csp, cache).run());
            results.addAll(new SortTest(csp, cache).run());

        } catch (Throwable e) {
            if (e instanceof CritialComplienceException) {
                results.add(((CritialComplienceException) e).getResult());
            } else {
                results.add(new TestResult(TestResult.ERROR, "Unknown Test", e.getMessage(), ExceptionUtils.getFullStackTrace(e)));
            }
        }
        int success = 0;
        int fail = 0;

        for (TestResult result : results) {
            if (result.isSuccess()) {
                success++;
            } else {
                fail++;
            }
        }
        return new Result(new Statistics(success, fail), results);
    }
}
