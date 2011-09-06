package info.simplecloud.core;

import info.simplecloud.core.extensions.EnterpriseAttributes;
import info.simplecloud.core.extensions.types.Manager;
import info.simplecloud.core.types.Name;
import info.simplecloud.core.types.PluralType;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class UserTest {

    @BeforeClass
    public static void setUp() {
        User.registerExtension(EnterpriseAttributes.class);
    }

    @Test
    public void create() throws Exception {

        User user = new User("ABCDE-12345-EFGHI-78910");
        // TODO use all setters, all getters, setAttribute and getAttribute
        // TODO check that all attributes matches specification
        user.setAttribute("userName", "kaan");
        user.setAttribute("externalId", "djfkhasdjkfha");
        user.setAttribute("name", new Name());
        user.setAttribute("name.givenName", "Karl");
        user.setAttribute("name.familyName", "Andersson");
        user.setAttribute("displayName", "Kalle");
        user.setAttribute("nickName", "Kalle Anka");
        user.setAttribute("profileUrl", "https://example.com");
        user.setAttribute("title", "master");
        user.setAttribute("userType", "super");
        user.setAttribute("preferredLanguage", "swedish");
        user.setAttribute("locale", "sv");
        user.setAttribute("password", "kan123!");

        List<String> schemas = new ArrayList<String>();
        schemas.add("urn:scim:schemas:core:1.0");
        user.setAttribute("schemas", schemas);

        user.toString();

        // TODO validate result
    }

    @Test
    public void encode() throws Exception {
        User user = new User("ABCDE-12345-EFGHI-78910");

        user.setAttribute("userName", "kaan");
        user.setAttribute("externalId", "djfkhasdjkfha");
        user.setAttribute("name.givenName", "Karl");
        user.setAttribute("name.familyName", "Andersson");
        user.setAttribute("displayName", "Kalle");
        user.setAttribute("nickName", "Kalle Anka");
        user.setAttribute("profileUrl", "https://example.com");
        user.setAttribute("title", "master");
        user.setAttribute("userType", "super");
        user.setAttribute("preferredLanguage", "swedish");
        user.setAttribute("locale", "sv");
        user.setAttribute("password", "kan123!");

        EnterpriseAttributes ea = user.getExtension(EnterpriseAttributes.class);
        ea.setDivision("Test division");
        ea.setManager(new Manager("MI-123ABC", "Hugo Boss"));

        List<PluralType<String>> emails = new ArrayList<PluralType<String>>();
        emails.add(new PluralType<String>("karl@andersson.se", "home", false, false));
        emails.add(new PluralType<String>("karl.andersson@work.com", "work", true, false));
        user.setAttribute("emails", emails);

        user.getUser(Resource.ENCODING_JSON);
        user.getUser(Resource.ENCODING_XML);

        // TODO validate result

    }

    @Test
    public void encodePartial() throws Exception {
        User user = new User("ABCDE-12345-EFGHI-78910");

        user.setAttribute("userName", "kaan");
        user.setAttribute("externalId", "djfkhasdjkfha");
        user.setAttribute("name", new Name());
        user.setAttribute("name.givenName", "Karl");
        user.setAttribute("name.familyName", "Andersson");
        user.setAttribute("displayName", "Kalle");
        user.setAttribute("nickName", "Kalle Anka");
        user.setAttribute("profileUrl", "https://example.com");
        user.setAttribute("title", "master");
        user.setAttribute("userType", "super");
        user.setAttribute("preferredLanguage", "swedish");
        user.setAttribute("locale", "sv");
        user.setAttribute("password", "kan123!");

        List<PluralType<String>> emails = new ArrayList<PluralType<String>>();
        emails.add(new PluralType<String>("karl@andersson.se", "home", false, false));
        emails.add(new PluralType<String>("karl.andersson@work.com", "work", true, false));
        user.setAttribute("emails", emails);

        List<String> includeAttributes = new ArrayList<String>();
        includeAttributes.add("userName");
        includeAttributes.add("title");

        user.getUser(Resource.ENCODING_JSON, includeAttributes);
        user.getUser(Resource.ENCODING_XML, includeAttributes);

        // TODO validate result
    }

    @Test
    public void decode() throws Exception {
        User user = new User("ABCDE-12345-EFGHI-78910");

        user.setAttribute("userName", "kaan");
        user.setAttribute("externalId", "djfkhasdjkfha");
        user.setAttribute("name", new Name());
        user.setAttribute("name.givenName", "Karl");
        user.setAttribute("name.familyName", "Andersson");
        user.setAttribute("displayName", "Kalle");
        user.setAttribute("nickName", "Kalle Anka");
        user.setAttribute("profileUrl", "https://example.com");
        user.setAttribute("title", "master");
        user.setAttribute("userType", "super");
        user.setAttribute("preferredLanguage", "swedish");
        user.setAttribute("locale", "sv");
        user.setAttribute("password", "kan123!");

        List<PluralType<String>> emails = new ArrayList<PluralType<String>>();
        emails.add(new PluralType<String>("karl@andersson.se", "home", false, false));
        emails.add(new PluralType<String>("karl.andersson@work.com", "work", true, false));
        user.setAttribute("emails", emails);

        List<String> includeAttributes = new ArrayList<String>();
        includeAttributes.add("userName");
        includeAttributes.add("title");
        String stringUser = user.getUser(Resource.ENCODING_JSON);

        User user2 = new User(stringUser, Resource.ENCODING_JSON);
        stringUser = user2.getUser(Resource.ENCODING_XML);

        User user3 = new User(stringUser, Resource.ENCODING_XML);
        stringUser = user3.getUser(Resource.ENCODING_XML);

        // TODO validate result
    }

    @Test
    public void jsonPatch() {
        // TODO implement test
    }

    @Test
    public void xmlPatch() {
        // TODO implement test
    }

    @Test
    public void sort() {
        // TODO implement test
    }

}
