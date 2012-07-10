package info.simplecloud.scimproxy.compliance;

import info.simplecloud.scimproxy.compliance.enteties.Result;
import info.simplecloud.scimproxy.compliance.enteties.Statistics;
import info.simplecloud.scimproxy.compliance.enteties.TestResult;
import info.simplecloud.scimproxy.compliance.exception.CritialComplienceException;
import info.simplecloud.scimproxy.compliance.test.ConfigTest;
import info.simplecloud.scimproxy.compliance.test.DeleteTest;
import info.simplecloud.scimproxy.compliance.test.FilterTest;
import info.simplecloud.scimproxy.compliance.test.PatchTest;
import info.simplecloud.scimproxy.compliance.test.PostTest;
import info.simplecloud.scimproxy.compliance.test.PutTest;
import info.simplecloud.scimproxy.compliance.test.SortTest;
import info.simplecloud.scimproxy.compliance.test.UserCache;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
            return new Result();
        }

        // create a CSP to use to connect to the server
        CSP csp = new CSP();
        csp.setUrl(url);
        csp.setVersion("/v1");

        // if basic
        csp.setAuthentication("basic");
        csp.setUsername("usr");
        csp.setPassword("pw");

        /*
         * // if oauth2, grant_type client_credentials
         * csp.setAuthentication("oauth2"); csp.setUsername("usr");
         * csp.setPassword("pw");
         * csp.setOAuth2AuthorizationServer("oAuthAuthorizationServer");
         * 
         * // if oauth2, other grant_type csp.setAuthentication("oauth2-v10");
         * csp.setUsername("usr"); csp.setPassword("pw");
         * csp.setOAuth2AuthorizationServer("oAuthAuthorizationServer");
         * csp.setoAuth2GrantType("grantType");
         * csp.setoAuth2ClientId("clientId");
         * csp.setoAuth2ClientSecret("clientSecret");
         */

        ArrayList<TestResult> results = new ArrayList<TestResult>();
        
        // get the configuration
        try {

            // start with the critical tests (will throw exception and test will
            // stop if fails)
            ConfigTest configTest = new ConfigTest();
            results.add(configTest.getConfiguration(csp));
            results.add(configTest.getSchema("User", csp));
            results.add(configTest.getSchema("Group", csp));

            // TODO: add the required attributes in userSchema and groupSchema
            // that server wanted

            // start mandatory test suite (JSON)

            UserCache cache = new UserCache();

            results.addAll(new PostTest(csp, cache).run());
            results.addAll(new DeleteTest(csp, cache).run());
            results.addAll(new FilterTest(csp, cache).run());
            results.addAll(new PatchTest(csp, cache).run());
            results.addAll(new PutTest(csp, cache).run());
            results.addAll(new SortTest(csp, cache).run());

        } catch (Exception e) {
        	if (e instanceof CritialComplienceException) {
        		results.add(((CritialComplienceException) e).getResult());
        	} else {
        		throw new ServletException("Unexpected exception. ", e );
        	}
        }
        int success = 0;
        int fail = 0;

        for (TestResult result : results) {
        	if (result.getStatus() != TestResult.SUCCESS) {
        		fail++;
        	} else {
        		success++;
        	}
        }
        return new Result(new Statistics(success, fail), results, false, "Basic");


        // System.out.println(json);
        // required features in JSON
        // define test suite

        // create group

        // create simple user
        // create full user

        // update user
        // replace group
        // replace simple user
        // replace full user

        // delete users
        // delete group

        // json (both accept and .json)
        // xml (both accept and .json)

        // bulk 10 users

        // patch simple attribute (both using PATCH and POST with x-headers)
        // patch complex attribute
        // patch multiValued simple
        // patch multiValued complex
        // add user to group

        // get user
        // get partial user
        // partial complex
        // partial multiValued

        // list users
        // sort
        // ascending
        // descending
        // filter
        // all different filter types
        // nested filters
        // multiValued
        // pagination
        // combine (sort, filter attributes)
    }
}
