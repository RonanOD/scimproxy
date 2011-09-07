package info.simplecloud.core;

import info.simplecloud.core.exceptions.UnknownExtension;
import info.simplecloud.core.extensions.EnterpriseAttributes;
import info.simplecloud.core.extensions.types.Manager;
import info.simplecloud.core.types.Address;
import info.simplecloud.core.types.Name;
import info.simplecloud.core.types.PluralType;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
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
    
    @Test
    public void equals() throws UnknownExtension {
        User user1 = new User("id-123");
        User user1eq1 = new User("id-123");
        User user1noteq1 = new User("not equal");
        User user1noteq2 = new User("id-123");
        User user1noteq3 = new User("id-123");
        User user1noteq4 = new User("id-123");
        User user1noteq5 = new User("id-123");


        Assert.assertEquals(user1eq1, user1);
        Assert.assertFalse(user1.equals(user1noteq1));
        Assert.assertFalse(user1noteq1.equals(user1));
        Assert.assertEquals(user1noteq2, user1);
        Assert.assertEquals(user1noteq3, user1);
        Assert.assertEquals(user1noteq4, user1);
        Assert.assertEquals(user1noteq5, user1);

        user1.setName(new Name("Mr. Frans Friskus", "Friskus", "Frans", null, "mr.", null));
        user1eq1.setName(new Name("Mr. Frans Friskus", "Friskus", "Frans", null, "mr.", null));
        user1noteq2.setName(new Name("Mr. Frans 1 Friskus", "Friskus", "Frans", "1", "mr.", null));
        user1noteq3.setName(new Name("Mr. Frans Friskus", "Friskus", "Frans", null, "mr.", null));
        user1noteq4.setName(new Name("Mr. Frans Friskus", "Friskus", "Frans", null, "mr.", null));
        user1noteq5.setName(new Name("Mr. Frans Friskus", "Friskus", "Frans", null, "mr.", null));

        Assert.assertEquals(user1eq1, user1);
        Assert.assertFalse(user1.equals(user1noteq1));
        Assert.assertFalse(user1noteq1.equals(user1));
        Assert.assertFalse(user1.equals(user1noteq2));
        Assert.assertFalse(user1noteq2.equals(user1));
        Assert.assertEquals(user1noteq3, user1);
        Assert.assertEquals(user1noteq4, user1);
        Assert.assertEquals(user1noteq5, user1);

        EnterpriseAttributes ea1 = user1.getExtension(EnterpriseAttributes.class);
        ea1.setEmployeeNumber("abc123");
        EnterpriseAttributes ea2 = user1eq1.getExtension(EnterpriseAttributes.class);
        ea2.setEmployeeNumber("abc123");
        EnterpriseAttributes ea3 = user1noteq3.getExtension(EnterpriseAttributes.class);
        ea3.setEmployeeNumber("not equal");
        EnterpriseAttributes ea4 = user1noteq4.getExtension(EnterpriseAttributes.class);
        ea4.setEmployeeNumber("abc123");
        EnterpriseAttributes ea5 = user1noteq5.getExtension(EnterpriseAttributes.class);
        ea5.setEmployeeNumber("abc123");

        Assert.assertEquals(user1eq1, user1);
        Assert.assertFalse(user1.equals(user1noteq1));
        Assert.assertFalse(user1noteq1.equals(user1));
        Assert.assertFalse(user1.equals(user1noteq2));
        Assert.assertFalse(user1noteq2.equals(user1));
        Assert.assertFalse(user1.equals(user1noteq3));
        Assert.assertFalse(user1noteq3.equals(user1));
        Assert.assertEquals(user1noteq4, user1);
        Assert.assertEquals(user1noteq5, user1);

        List<PluralType<String>> l1 = new ArrayList<PluralType<String>>();
        l1.add(new PluralType<String>("String1", "type1", true, false));
        user1.setMemberOf(l1);
        List<PluralType<String>> l2 = new ArrayList<PluralType<String>>();
        l2.add(new PluralType<String>("String1", "type1", true, false));
        user1eq1.setMemberOf(l2);
        List<PluralType<String>> l3 = new ArrayList<PluralType<String>>();
        l3.add(new PluralType<String>("not equal", "type1", true, false));
        user1noteq4.setMemberOf(l3);
        List<PluralType<String>> l4 = new ArrayList<PluralType<String>>();
        l4.add(new PluralType<String>("String1", "type1", true, false));
        user1noteq5.setMemberOf(l4);

        Assert.assertEquals(user1eq1, user1);
        Assert.assertFalse(user1.equals(user1noteq1));
        Assert.assertFalse(user1noteq1.equals(user1));
        Assert.assertFalse(user1.equals(user1noteq2));
        Assert.assertFalse(user1noteq2.equals(user1));
        Assert.assertFalse(user1.equals(user1noteq3));
        Assert.assertFalse(user1noteq3.equals(user1));
        Assert.assertFalse(user1.equals(user1noteq4));
        Assert.assertFalse(user1noteq4.equals(user1));
        Assert.assertEquals(user1noteq5, user1);

        List<PluralType<Address>> plural1 = new ArrayList<PluralType<Address>>();
        Address workAddress = new Address();
        workAddress.setCountry("Sweden");
        workAddress.setRegion("Stockholm");
        plural1.add(new PluralType<Address>(workAddress, "work", true, false));
        user1.setAddresses(plural1);

        List<PluralType<Address>> plural2 = new ArrayList<PluralType<Address>>();
        Address workAddress2 = new Address();
        workAddress2.setCountry("Sweden");
        workAddress2.setRegion("Stockholm");
        plural2.add(new PluralType<Address>(workAddress2, "work", true, false));
        user1eq1.setAddresses(plural2);

        List<PluralType<Address>> plural3 = new ArrayList<PluralType<Address>>();
        Address workAddress3 = new Address();
        workAddress3.setCountry("Sweden");
        workAddress3.setRegion("not equal");
        plural3.add(new PluralType<Address>(workAddress3, "work", true, false));
        user1noteq1.setAddresses(plural3);

        Assert.assertEquals(user1eq1, user1);
        Assert.assertFalse(user1.equals(user1noteq1));
        Assert.assertFalse(user1noteq1.equals(user1));
        Assert.assertFalse(user1.equals(user1noteq2));
        Assert.assertFalse(user1noteq2.equals(user1));
        Assert.assertFalse(user1.equals(user1noteq3));
        Assert.assertFalse(user1noteq3.equals(user1));
        Assert.assertFalse(user1.equals(user1noteq4));
        Assert.assertFalse(user1noteq4.equals(user1));
        Assert.assertFalse(user1.equals(user1noteq5));
        Assert.assertFalse(user1noteq5.equals(user1));

    }

}
