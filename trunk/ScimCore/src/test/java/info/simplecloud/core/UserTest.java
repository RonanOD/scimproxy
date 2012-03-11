package info.simplecloud.core;

import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.core.exceptions.UnknownEncoding;
import info.simplecloud.core.exceptions.UnknownExtension;
import info.simplecloud.core.extensions.EnterpriseAttributes;
import info.simplecloud.core.types.Address;
import info.simplecloud.core.types.MultiValuedType;
import info.simplecloud.core.types.Name;

import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.impl.util.Base64;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserTest {

    @BeforeClass
    public static void setUp() {
        User.registerExtension(EnterpriseAttributes.class);
    }

    @Test
    public void parse() throws UnknownEncoding, InvalidUser {
        String base64User = "ew0KCSJzY2hlbWFzIjogWyJ1cm46c2NpbTpzY2hlbWFzOmNvcmU6MS4wIl0sDQoJImlkIjogIjAwNUQwMDAwMDAxQXoxdSIsDQoJImV4dGVybmFsSWQiOiAiNzAxOTg0IiwNCgkidXNlck5hbWUiOiAiYmplbnNlbkBleGFtcGxlLmNvbSIsDQoJIm5hbWUiOiB7DQoJCSJmb3JtYXR0ZWQiOiAiTXMuIEJhcmJhcmEgSiBKZW5zZW4gSUlJIiwNCgkJImZhbWlseU5hbWUiOiAiSmVuc2VuIiwNCgkJImdpdmVuTmFtZSI6ICJCYXJiYXJhIiwNCgkJIm1pZGRsZU5hbWUiOiAiSmFuZSIsDQoJCSJob25vcmlmaWNQcmVmaXgiOiAiTXMuIiwNCgkJImhvbm9yaWZpY1N1ZmZpeCI6ICJJSUkiDQoJfSwNCgkiZGlzcGxheU5hbWUiOiAiQmFicyBKZW5zZW4iLA0KCSJuaWNrTmFtZSI6ICJCYWJzIiwNCgkicHJvZmlsZVVybCI6ICJodHRwczovL2xvZ2luLmV4YW1wbGUuY29tL2JqZW5zZW4iLA0KCSJlbWFpbHMiOiBbDQoJCXsNCgkJICAidmFsdWUiOiAiYmplbnNlbkBleGFtcGxlLmNvbSIsDQoJCSAgInR5cGUiOiAid29yayIsDQoJCSAgInByaW1hcnkiOiB0cnVlDQoJCX0sDQoJCXsNCgkJICAidmFsdWUiOiAiYmFic0BqZW5zZW4ub3JnIiwNCgkJICAidHlwZSI6ICJob21lIg0KCQl9DQoJXSwNCgkiYWRkcmVzc2VzIjogWw0KCQl7DQoJCSAgInR5cGUiOiAid29yayIsDQoJCSAgInN0cmVldEFkZHJlc3MiOiAiMTAwIFVuaXZlcnNhbCBDaXR5IFBsYXphIiwNCgkJICAibG9jYWxpdHkiOiAiSG9sbHl3b29kIiwNCgkJICAicmVnaW9uIjogIkNBIiwNCgkJICAicG9zdGFsQ29kZSI6ICI5MTYwOCIsDQoJCSAgImNvdW50cnkiOiAiVVNBIiwNCgkJICAiZm9ybWF0dGVkIjogIjEwMCBVbml2ZXJzYWwgQ2l0eSBQbGF6YVxuSG9sbHl3b29kLCBDQSA5MTYwOCBVU0EiLA0KCQkgICJwcmltYXJ5IjogdHJ1ZQ0KCQl9LA0KCQl7DQoJCSAgInR5cGUiOiAiaG9tZSIsDQoJCSAgInN0cmVldEFkZHJlc3MiOiAiNDU2IEhvbGx5d29vZCBCbHZkIiwNCgkJICAibG9jYWxpdHkiOiAiSG9sbHl3b29kIiwNCgkJICAicmVnaW9uIjogIkNBIiwNCgkJICAicG9zdGFsQ29kZSI6ICI5MTYwOCIsDQoJCSAgImNvdW50cnkiOiAiVVNBIiwNCgkJICAiZm9ybWF0dGVkIjogIjQ1NiBIb2xseXdvb2QgQmx2ZFxuSG9sbHl3b29kLCBDQSA5MTYwOCBVU0EiDQoJCX0NCgldLA0KCSJwaG9uZU51bWJlcnMiOiBbDQoJCXsNCgkJICAidmFsdWUiOiAiODAwLTg2NC04Mzc3IiwNCgkJICAidHlwZSI6ICJ3b3JrIg0KCQl9LA0KCQl7DQoJCSAgInZhbHVlIjogIjgxOC0xMjMtNDU2NyIsDQoJCSAgInR5cGUiOiAibW9iaWxlIg0KCQl9DQoJXSwNCgkiaW1zIjogWw0KCQl7DQoJCSAgInZhbHVlIjogInNvbWVhaW1oYW5kbGUiLA0KCQkgICJ0eXBlIjogImFpbSINCgkJfQ0KCV0sDQoJInBob3RvcyI6IFsNCgkJew0KCQkgICJ2YWx1ZSI6ICJodHRwczovL3Bob3Rvcy5leGFtcGxlLmNvbS9wcm9maWxlcGhvdG8vNzI5MzAwMDAwMDBDY25lL0YiLA0KCQkgICJ0eXBlIjogInBob3RvIg0KCQl9LA0KCQl7DQoJCSAgInZhbHVlIjogImh0dHBzOi8vcGhvdG9zLmV4YW1wbGUuY29tL3Byb2ZpbGVwaG90by83MjkzMDAwMDAwMENjbmUvVCIsDQoJCSAgInR5cGUiOiAidGh1bWJuYWlsIg0KCQl9DQoJXSwNCgkidXNlclR5cGUiOiAiRW1wbG95ZWUiLA0KCSJ0aXRsZSI6ICJUb3VyIEd1aWRlIiwNCgkicHJlZmVycmVkTGFuZ3VhZ2UiOiAiZW5fVVMiLA0KCSJsb2NhbGUiOiAiZW5fVVMiLA0KCSJ0aW1lem9uZSI6ICJBbWVyaWNhL0RlbnZlciIsDQoJImdyb3VwcyI6IFsNCgkJew0KCQkgICJkaXNwbGF5IjogIlRvdXIgR3VpZGVzIiwNCgkJICAidmFsdWUiOiAiMDAzMDAwMDAwMDVOMlk2QUEiLA0KCQkgICJwcmltYXJ5IjogdHJ1ZQ0KCQl9LA0KCQl7DQoJCSAgImRpc3BsYXkiOiAiRW1wbG95ZWVzIiwNCgkJICAidmFsdWUiOiAiMDAzMDAwMDAwMDVOMzRINzgiDQoJCX0sDQoJCXsNCgkJICAiZGlzcGxheSI6ICJVUyBFbXBsb3llZXMiLA0KCQkgICJ2YWx1ZSI6ICIwMDMwMDAwMDAwNU45OFlUMSINCgkJfQ0KCV0sDQoJIm1ldGEiOiB7DQoJCSJjcmVhdGVkIjogIjIwMTAtMDEtMjNUMDQ6NTY6MjJaIiwNCgkJImxhc3RNb2RpZmllZCI6ICIyMDExLTA1LTEzVDA0OjQyOjM0WiINCgl9DQp9";
        String stringUser = new String(Base64.decode(base64User.getBytes()));

        User user = new User(stringUser, Resource.ENCODING_JSON);

        validateCoreUser(user);
    }

    @Test
    public void parseEnterpriseUser() throws UnknownEncoding, InvalidUser, UnknownExtension, UnknownAttribute {
        String base64User = "ew0KCSJzY2hlbWFzIjogWyJ1cm46c2NpbTpzY2hlbWFzOmNvcmU6MS4wIiwgInVybjpzY2ltOnNjaGVtYXM6ZXh0ZW5zaW9uOmVudGVycHJpc2U6MS4wIl0sDQoJImlkIjogIjAwNUQwMDAwMDAxQXoxdSIsDQoJImV4dGVybmFsSWQiOiAiNzAxOTg0IiwNCgkidXNlck5hbWUiOiAiYmplbnNlbkBleGFtcGxlLmNvbSIsDQoJIm5hbWUiOiB7DQoJCSJmb3JtYXR0ZWQiOiAiTXMuIEJhcmJhcmEgSiBKZW5zZW4gSUlJIiwNCgkJImZhbWlseU5hbWUiOiAiSmVuc2VuIiwNCgkJImdpdmVuTmFtZSI6ICJCYXJiYXJhIiwNCgkJIm1pZGRsZU5hbWUiOiAiSmFuZSIsDQoJCSJob25vcmlmaWNQcmVmaXgiOiAiTXMuIiwNCgkJImhvbm9yaWZpY1N1ZmZpeCI6ICJJSUkiDQoJfSwNCgkiZGlzcGxheU5hbWUiOiAiQmFicyBKZW5zZW4iLA0KCSJuaWNrTmFtZSI6ICJCYWJzIiwNCgkicHJvZmlsZVVybCI6ICJodHRwczovL2xvZ2luLmV4YW1wbGUuY29tL2JqZW5zZW4iLA0KCSJlbWFpbHMiOiBbDQoJCXsNCgkJICAidmFsdWUiOiAiYmplbnNlbkBleGFtcGxlLmNvbSIsDQoJCSAgInR5cGUiOiAid29yayIsDQoJCSAgInByaW1hcnkiOiB0cnVlDQoJCX0sDQoJCXsNCgkJICAidmFsdWUiOiAiYmFic0BqZW5zZW4ub3JnIiwNCgkJICAidHlwZSI6ICJob21lIg0KCQl9DQoJXSwNCgkiYWRkcmVzc2VzIjogWw0KCQl7DQoJCSAgInR5cGUiOiAid29yayIsDQoJCSAgInN0cmVldEFkZHJlc3MiOiAiMTAwIFVuaXZlcnNhbCBDaXR5IFBsYXphIiwNCgkJICAibG9jYWxpdHkiOiAiSG9sbHl3b29kIiwNCgkJICAicmVnaW9uIjogIkNBIiwNCgkJICAicG9zdGFsQ29kZSI6ICI5MTYwOCIsDQoJCSAgImNvdW50cnkiOiAiVVNBIiwNCgkJICAiZm9ybWF0dGVkIjogIjEwMCBVbml2ZXJzYWwgQ2l0eSBQbGF6YVxuSG9sbHl3b29kLCBDQSA5MTYwOCBVU0EiLA0KCQkgICJwcmltYXJ5IjogdHJ1ZQ0KCQl9LA0KCQl7DQoJCSAgInR5cGUiOiAiaG9tZSIsDQoJCSAgInN0cmVldEFkZHJlc3MiOiAiNDU2IEhvbGx5d29vZCBCbHZkIiwNCgkJICAibG9jYWxpdHkiOiAiSG9sbHl3b29kIiwNCgkJICAicmVnaW9uIjogIkNBIiwNCgkJICAicG9zdGFsQ29kZSI6ICI5MTYwOCIsDQoJCSAgImNvdW50cnkiOiAiVVNBIiwNCgkJICAiZm9ybWF0dGVkIjogIjQ1NiBIb2xseXdvb2QgQmx2ZFxuSG9sbHl3b29kLCBDQSA5MTYwOCBVU0EiDQoJCX0NCgldLA0KCSJwaG9uZU51bWJlcnMiOiBbDQoJCXsNCgkJICAidmFsdWUiOiAiODAwLTg2NC04Mzc3IiwNCgkJICAidHlwZSI6ICJ3b3JrIg0KCQl9LA0KCQl7DQoJCSAgInZhbHVlIjogIjgxOC0xMjMtNDU2NyIsDQoJCSAgInR5cGUiOiAibW9iaWxlIg0KCQl9DQoJXSwNCgkiaW1zIjogWw0KCQl7DQoJCSAgInZhbHVlIjogInNvbWVhaW1oYW5kbGUiLA0KCQkgICJ0eXBlIjogImFpbSINCgkJfQ0KCV0sDQoJInBob3RvcyI6IFsNCgkJew0KCQkgICJ2YWx1ZSI6ICJodHRwczovL3Bob3Rvcy5leGFtcGxlLmNvbS9wcm9maWxlcGhvdG8vNzI5MzAwMDAwMDBDY25lL0YiLA0KCQkgICJ0eXBlIjogInBob3RvIg0KCQl9LA0KCQl7DQoJCSAgInZhbHVlIjogImh0dHBzOi8vcGhvdG9zLmV4YW1wbGUuY29tL3Byb2ZpbGVwaG90by83MjkzMDAwMDAwMENjbmUvVCIsDQoJCSAgInR5cGUiOiAidGh1bWJuYWlsIg0KCQl9DQoJXSwNCgkidXNlclR5cGUiOiAiRW1wbG95ZWUiLA0KCSJ0aXRsZSI6ICJUb3VyIEd1aWRlIiwNCgkibWFuYWdlciI6IHsNCgkJImRpc3BsYXlOYW1lIjogIk1hbmR5IFBlcHBlcmlkZ2UiDQoJfSwNCgkicHJlZmVycmVkTGFuZ3VhZ2UiOiAiZW5fVVMiLA0KCSJsb2NhbGUiOiAiZW5fVVMiLA0KCSJ0aW1lem9uZSI6ICJBbWVyaWNhL0RlbnZlciIsDQoJImdyb3VwcyI6IFsNCgkJew0KCQkgICJkaXNwbGF5IjogIlRvdXIgR3VpZGVzIiwNCgkJICAidmFsdWUiOiAiMDAzMDAwMDAwMDVOMlk2QUEiLA0KCQkgICJwcmltYXJ5IjogdHJ1ZQ0KCQl9LA0KCQl7DQoJCSAgImRpc3BsYXkiOiAiRW1wbG95ZWVzIiwNCgkJICAidmFsdWUiOiAiMDAzMDAwMDAwMDVOMzRINzgiDQoJCX0sDQoJCXsNCgkJICAiZGlzcGxheSI6ICJVUyBFbXBsb3llZXMiLA0KCQkgICJ2YWx1ZSI6ICIwMDMwMDAwMDAwNU45OFlUMSINCgkJfQ0KCV0sDQoJInVybjpzY2ltOnNjaGVtYXM6ZXh0ZW5zaW9uOmVudGVycHJpc2U6MS4wIjogew0KCQkiZW1wbG95ZWVOdW1iZXIiOiAiNzAxOTg0IiwNCgkJImNvc3RDZW50ZXIiOiAiNDEzMCIsDQoJCSJvcmdhbml6YXRpb24iOiAiVW5pdmVyc2FsIFN0dWRpb3MiLA0KCQkiZGl2aXNpb24iOiAiVGhlbWUgUGFyayIsDQoJCSJkZXBhcnRtZW50IjogIlRvdXIgT3BlcmF0aW9ucyIsDQoJCSJtYW5hZ2VyIjogew0KCQkJCSJtYW5hZ2VySWQiOiAiMDA1RDAwMDAwMDFBUVJFIiwNCgkJCQkiZGlzcGxheU5hbWUiOiAiSm9obiBTbWl0aCINCgkJfQ0KCX0sDQoJIm1ldGEiOiB7DQoJCSJjcmVhdGVkIjogIjIwMTAtMDEtMjNUMDQ6NTY6MjJaIiwNCgkJImxhc3RNb2RpZmllZCI6ICIyMDExLTA1LTEzVDA0OjQyOjM0WiINCgl9DQp9";
        String stringUser = new String(Base64.decode(base64User.getBytes()));

        User user = new User(stringUser, Resource.ENCODING_JSON);

        validateCoreUser(user);

        validateEnterpriseAttributes(user);
    }

    @Test
    public void create() throws Exception {
        User user = new User("005D0000001Az1u");

        user.setAttribute("externalId", "701984");
        user.setAttribute("userName", "bjensen@example.com");
        user.setAttribute("name.formatted", "Ms. Barbara J Jensen III");
        user.setAttribute("name.familyName", "Jensen");
        user.setAttribute("name.givenName", "Barbara");
        user.setAttribute("name.middleName", "Jane");
        user.setAttribute("name.honorificPrefix", "Ms.");
        user.setAttribute("name.honorificSuffix", "III");
        user.setAttribute("displayName", "Babs Jensen");
        user.setAttribute("nickName", "Babs");
        user.setAttribute("profileUrl", "https://login.example.com/bjensen");
        List<MultiValuedType<String>> emails = new ArrayList<MultiValuedType<String>>();
        emails.add(new MultiValuedType<String>("bjensen@example.com", "work", true, false));
        emails.add(new MultiValuedType<String>("babs@jensen.org", "home", false, false));
        user.setEmails(emails);

        List<MultiValuedType<Address>> addresses = new ArrayList<MultiValuedType<Address>>();
        addresses.add(new MultiValuedType<Address>(new Address("100 Universal City Plaza\nHollywood, CA 91608 USA",
                "100 Universal City Plaza", "Hollywood", "CA", "91608", "USA"), "work", true, false));
        addresses.add(new MultiValuedType<Address>(new Address("456 Hollywood Blvd\nHollywood, CA 91608 USA", "456 Hollywood Blvd",
                "Hollywood", "CA", "91608", "USA"), "home", false, false));
        user.setAddresses(addresses);

        List<MultiValuedType<String>> phoneNumbers = new ArrayList<MultiValuedType<String>>();
        phoneNumbers.add(new MultiValuedType<String>("800-864-8377", "work", false, false));
        phoneNumbers.add(new MultiValuedType<String>("818-123-4567", "mobile", false, false));
        user.setPhoneNumbers(phoneNumbers);

        List<MultiValuedType<String>> ims = new ArrayList<MultiValuedType<String>>();
        ims.add(new MultiValuedType<String>("someaimhandle", "aim", false, false));
        user.setIms(ims);

        List<MultiValuedType<String>> photos = new ArrayList<MultiValuedType<String>>();
        photos.add(new MultiValuedType<String>("https://photos.example.com/profilephoto/72930000000Ccne/F", "photo", false, false));
        photos.add(new MultiValuedType<String>("https://photos.example.com/profilephoto/72930000000Ccne/T", "thumbnail", false, false));
        user.setPhotos(photos);

        user.setAttribute("userType", "Employee");
        user.setAttribute("title", "Tour Guide");
        user.setAttribute("preferredLanguage", "en_US");
        user.setAttribute("locale", "en_US");
        user.setAttribute("timezone", "America/Denver");

        List<MultiValuedType<String>> groups = new ArrayList<MultiValuedType<String>>();
        groups.add(new MultiValuedType<String>("00300000005N2Y6AA", null, "Tour Guides", true));
        groups.add(new MultiValuedType<String>("00300000005N34H78", null, "Employees", false));
        groups.add(new MultiValuedType<String>("00300000005N98YT1", null, "US Employees", false));
        user.setGroups(groups);

        user.setAttribute("urn:scim:schemas:extension:enterprise:1.0.employeeNumber", "701984");
        user.setAttribute("urn:scim:schemas:extension:enterprise:1.0.costCenter", "4130");
        user.setAttribute("urn:scim:schemas:extension:enterprise:1.0.organization", "Universal Studios");
        user.setAttribute("urn:scim:schemas:extension:enterprise:1.0.division", "Theme Park");
        user.setAttribute("urn:scim:schemas:extension:enterprise:1.0.department", "Tour Operations");
        user.setAttribute("urn:scim:schemas:extension:enterprise:1.0.manager.managerId", "005D0000001AQRE");
        user.setAttribute("urn:scim:schemas:extension:enterprise:1.0.manager.displayName", "John Smith");
        // user.setAttribute("meta.created", "2010-01-23T04:56:22Z");
        // user.setAttribute("meta.lastModified", "2011-05-13T04:42:34Z");

        validateCoreUser(user);
        validateEnterpriseAttributes(user);
    }

    @Test
    public void encode() throws Exception {
        User user = new User("005D0000001Az1u");

        setAttributesOnUser(user);

        String jsonUser = user.getUser(Resource.ENCODING_JSON);
        String xmlUser = user.getUser(Resource.ENCODING_XML);

        containsCheck(jsonUser);
        containsCheck(xmlUser);

    }

    @Test
    public void encodePartial() throws Exception {
        User user = new User("005D0000001Az1u");

        setAttributesOnUser(user);

        List<String> includeAttributes = new ArrayList<String>();
        includeAttributes.add("userName");
        includeAttributes.add("name.givenName");
        includeAttributes.add("addresses.streetAddress");
        includeAttributes.add("urn:scim:schemas:extension:enterprise:1.0.employeeNumber");
        includeAttributes.add("urn:scim:schemas:extension:enterprise:1.0.manager.displayName");

        String jsonUser = user.getUser(Resource.ENCODING_JSON, includeAttributes);
        String xmlUser = user.getUser(Resource.ENCODING_XML, includeAttributes);

        partialContainsCheck(jsonUser);
        partialContainsCheck(xmlUser);
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

        List<MultiValuedType<String>> emails = new ArrayList<MultiValuedType<String>>();
        emails.add(new MultiValuedType<String>("karl@andersson.se", "home", false, false));
        emails.add(new MultiValuedType<String>("karl.andersson@work.com", "work", true, false));
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

        List<MultiValuedType<String>> l1 = new ArrayList<MultiValuedType<String>>();
        l1.add(new MultiValuedType<String>("String1", "type1", true, false));
        user1.setGroups(l1);
        List<MultiValuedType<String>> l2 = new ArrayList<MultiValuedType<String>>();
        l2.add(new MultiValuedType<String>("String1", "type1", true, false));
        user1eq1.setGroups(l2);
        List<MultiValuedType<String>> l3 = new ArrayList<MultiValuedType<String>>();
        l3.add(new MultiValuedType<String>("not equal", "type1", true, false));
        user1noteq4.setGroups(l3);
        List<MultiValuedType<String>> l4 = new ArrayList<MultiValuedType<String>>();
        l4.add(new MultiValuedType<String>("String1", "type1", true, false));
        user1noteq5.setGroups(l4);

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

        List<MultiValuedType<Address>> plural1 = new ArrayList<MultiValuedType<Address>>();
        Address workAddress = new Address();
        workAddress.setCountry("Sweden");
        workAddress.setRegion("Stockholm");
        plural1.add(new MultiValuedType<Address>(workAddress, "work", true, false));
        user1.setAddresses(plural1);

        List<MultiValuedType<Address>> plural2 = new ArrayList<MultiValuedType<Address>>();
        Address workAddress2 = new Address();
        workAddress2.setCountry("Sweden");
        workAddress2.setRegion("Stockholm");
        plural2.add(new MultiValuedType<Address>(workAddress2, "work", true, false));
        user1eq1.setAddresses(plural2);

        List<MultiValuedType<Address>> plural3 = new ArrayList<MultiValuedType<Address>>();
        Address workAddress3 = new Address();
        workAddress3.setCountry("Sweden");
        workAddress3.setRegion("not equal");
        plural3.add(new MultiValuedType<Address>(workAddress3, "work", true, false));
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

    private void validateCoreUser(User user) {
        Assert.assertEquals("005D0000001Az1u", user.getId());
        Assert.assertEquals("701984", user.getExternalId());
        Assert.assertEquals("bjensen@example.com", user.getUserName());
        Assert.assertEquals("Ms. Barbara J Jensen III", user.getName().getFormatted());
        Assert.assertEquals("Jensen", user.getName().getFamilyName());
        Assert.assertEquals("Barbara", user.getName().getGivenName());
        Assert.assertEquals("Jane", user.getName().getMiddleName());
        Assert.assertEquals("Ms.", user.getName().getHonorificPrefix());
        Assert.assertEquals("III", user.getName().getHonorificSuffix());
        Assert.assertEquals("Babs Jensen", user.getDisplayName());
        Assert.assertEquals("Babs", user.getNickName());
        Assert.assertEquals("https://login.example.com/bjensen", user.getProfileUrl());

        {
            List<MultiValuedType<String>> emails = user.getEmails();
            Assert.assertEquals(2, emails.size());
            for (MultiValuedType<String> email : emails) {
                if (!("bjensen@example.com".equals(email.getValue()) && "work".equals(email.getType()) && email.isPrimary())
                        && !("babs@jensen.org".equals(email.getValue()) && "home".equals(email.getType()) && !email.isPrimary())) {
                    Assert.fail("emails are not matching");
                }
            }
        }
        {
            List<MultiValuedType<String>> phoneNumbers = user.getPhoneNumbers();
            for (MultiValuedType<String> phonenumber : phoneNumbers) {
                if (!("800-864-8377".equals(phonenumber.getValue()) && "work".equals(phonenumber.getType()) && !phonenumber.isPrimary())
                        && !("818-123-4567".equals(phonenumber.getValue()) && "mobile".equals(phonenumber.getType()) && !phonenumber
                                .isPrimary())) {
                    Assert.fail("phoneNumbers are not matching");
                }
            }
        }

        {
            List<MultiValuedType<String>> ims = user.getIms();
            for (MultiValuedType<String> im : ims) {
                if (!("someaimhandle".equals(im.getValue()) && "aim".equals(im.getType()) && !im.isPrimary())) {
                    Assert.fail("ims are not matching");
                }
            }
        }
        {
            List<MultiValuedType<String>> photos = user.getPhotos();
            for (MultiValuedType<String> photo : photos) {
                if (!("https://photos.example.com/profilephoto/72930000000Ccne/F".equals(photo.getValue())
                        && "photo".equals(photo.getType()) && !photo.isPrimary())
                        && !("https://photos.example.com/profilephoto/72930000000Ccne/T".equals(photo.getValue())
                                && "thumbnail".equals(photo.getType()) && !photo.isPrimary())) {
                    Assert.fail("photos are not matching");
                }
            }
        }

        {
            List<MultiValuedType<String>> groups = user.getGroups();
            for (MultiValuedType<String> group : groups) {
                if (!("00300000005N2Y6AA".equals(group.getValue()) && "Tour Guides".equals(group.getDisplay()) && group.isPrimary())
                        && !("00300000005N34H78".equals(group.getValue()) && "Employees".equals(group.getDisplay()) && !group.isPrimary())
                        && !("00300000005N98YT1".equals(group.getValue()) && "US Employees".equals(group.getDisplay()) && !group
                                .isPrimary())) {
                    Assert.fail("groups are not matching");
                }
            }
        }
        {
            MultiValuedType<Address> address1 = new MultiValuedType<Address>(new Address(
                    "100 Universal City Plaza\nHollywood, CA 91608 USA", "100 Universal City Plaza", "Hollywood", "CA", "91608", "USA"),
                    "work", true, false);
            MultiValuedType<Address> address2 = new MultiValuedType<Address>(new Address("456 Hollywood Blvd\nHollywood, CA 91608 USA",
                    "456 Hollywood Blvd", "Hollywood", "CA", "91608", "USA"), "home", false, false);
            List<MultiValuedType<Address>> addresses = user.getAddresses();
            for (MultiValuedType<Address> address : addresses) {
                if (!address1.equals(address) && !address2.equals(address)) {
                    Assert.fail("phoneNumbers not matching");
                }
            }
        }
        Assert.assertEquals("Employee", user.getUserType());
        Assert.assertEquals("Tour Guide", user.getTitle());
        Assert.assertEquals("en_US", user.getPreferredLanguage());
        Assert.assertEquals("en_US", user.getLocale());
        Assert.assertEquals("America/Denver", user.getTimezone());
        // Assert.assertEquals(new GregorianCalendar(arg0, arg1, arg2, arg3,
        // arg4, arg5), user.getMeta().getCreated());
        // Assert.assertEquals(new GregorianCalendar(arg0, arg1, arg2, arg3,
        // arg4, arg5), user.getMeta().getCreated());

        // TODO validate meta
    }

    private void validateEnterpriseAttributes(User user) throws UnknownExtension, UnknownAttribute {
        EnterpriseAttributes enterpriseAttributes = user.getExtension(EnterpriseAttributes.class);

        Assert.assertEquals("4130", enterpriseAttributes.getCostCenter());
        Assert.assertEquals("Tour Operations", enterpriseAttributes.getDepartment());
        Assert.assertEquals("Theme Park", enterpriseAttributes.getDivision());
        Assert.assertEquals("701984", enterpriseAttributes.getEmployeeNumber());
        Assert.assertEquals("John Smith", enterpriseAttributes.getManager().getDisplayName());
        Assert.assertEquals("005D0000001AQRE", enterpriseAttributes.getManager().getManagerId());
        Assert.assertEquals("Universal Studios", enterpriseAttributes.getOrganization());

        Assert.assertEquals("4130", user.getAttribute("urn:scim:schemas:extension:enterprise:1.0.costCenter"));
        Assert.assertEquals("Tour Operations", user.getAttribute("urn:scim:schemas:extension:enterprise:1.0.department"));
        Assert.assertEquals("Theme Park", user.getAttribute("urn:scim:schemas:extension:enterprise:1.0.division"));
        Assert.assertEquals("701984", user.getAttribute("urn:scim:schemas:extension:enterprise:1.0.employeeNumber"));
        Assert.assertEquals("John Smith", user.getAttribute("urn:scim:schemas:extension:enterprise:1.0.manager.displayName"));
        Assert.assertEquals("005D0000001AQRE", user.getAttribute("urn:scim:schemas:extension:enterprise:1.0.manager.managerId"));
        Assert.assertEquals("Universal Studios", user.getAttribute("urn:scim:schemas:extension:enterprise:1.0.organization"));
    }

    private void setAttributesOnUser(User user) throws UnknownAttribute {
        user.setAttribute("externalId", "701984");
        user.setAttribute("userName", "bjensen@example.com");
        user.setAttribute("name.formatted", "Ms. Barbara J Jensen III");
        user.setAttribute("name.familyName", "Jensen");
        user.setAttribute("name.givenName", "Barbara");
        user.setAttribute("name.middleName", "Jane");
        user.setAttribute("name.honorificPrefix", "Ms.");
        user.setAttribute("name.honorificSuffix", "III");
        user.setAttribute("displayName", "Babs Jensen");
        user.setAttribute("nickName", "Babs");
        user.setAttribute("profileUrl", "https://login.example.com/bjensen");
        List<MultiValuedType<String>> emails = new ArrayList<MultiValuedType<String>>();
        emails.add(new MultiValuedType<String>("bjensen@example.com", "work", true, false));
        emails.add(new MultiValuedType<String>("babs@jensen.org", "home", false, false));
        user.setEmails(emails);

        List<MultiValuedType<Address>> addresses = new ArrayList<MultiValuedType<Address>>();
        addresses.add(new MultiValuedType<Address>(new Address("100 Universal City Plaza\nHollywood, CA 91608 USA",
                "100 Universal City Plaza", "Hollywood", "CA", "91608", "USA"), "work", true, false));
        addresses.add(new MultiValuedType<Address>(new Address("456 Hollywood Blvd\nHollywood, CA 91608 USA", "456 Hollywood Blvd",
                "Hollywood", "CA", "91608", "USA"), "home", false, false));
        user.setAddresses(addresses);

        List<MultiValuedType<String>> phoneNumbers = new ArrayList<MultiValuedType<String>>();
        phoneNumbers.add(new MultiValuedType<String>("800-864-8377", "work", false, false));
        phoneNumbers.add(new MultiValuedType<String>("818-123-4567", "mobile", false, false));
        user.setPhoneNumbers(phoneNumbers);

        List<MultiValuedType<String>> ims = new ArrayList<MultiValuedType<String>>();
        ims.add(new MultiValuedType<String>("someaimhandle", "aim", false, false));
        user.setIms(ims);

        List<MultiValuedType<String>> photos = new ArrayList<MultiValuedType<String>>();
        photos.add(new MultiValuedType<String>("https://photos.example.com/profilephoto/72930000000Ccne/F", "photo", false, false));
        photos.add(new MultiValuedType<String>("https://photos.example.com/profilephoto/72930000000Ccne/T", "thumbnail", false, false));
        user.setPhotos(photos);

        user.setAttribute("userType", "Employee");
        user.setAttribute("title", "Tour Guide");
        user.setAttribute("preferredLanguage", "en_US");
        user.setAttribute("locale", "en_US");
        user.setAttribute("timezone", "America/Denver");

        List<MultiValuedType<String>> groups = new ArrayList<MultiValuedType<String>>();
        groups.add(new MultiValuedType<String>("00300000005N2Y6AA", null, "Tour Guides", true));
        groups.add(new MultiValuedType<String>("00300000005N34H78", null, "Employees", false));
        groups.add(new MultiValuedType<String>("00300000005N98YT1", null, "US Employees", false));
        user.setGroups(groups);

        user.setAttribute("urn:scim:schemas:extension:enterprise:1.0.employeeNumber", "701984");
        user.setAttribute("urn:scim:schemas:extension:enterprise:1.0.costCenter", "4130");
        user.setAttribute("urn:scim:schemas:extension:enterprise:1.0.organization", "Universal Studios");
        user.setAttribute("urn:scim:schemas:extension:enterprise:1.0.division", "Theme Park");
        user.setAttribute("urn:scim:schemas:extension:enterprise:1.0.department", "Tour Operations");
        user.setAttribute("urn:scim:schemas:extension:enterprise:1.0.manager.managerId", "005D0000001AQRE");
        user.setAttribute("urn:scim:schemas:extension:enterprise:1.0.manager.displayName", "John Smith");
    }

    private void containsCheck(String stringUser) {

        Assert.assertTrue(stringUser.contains("urn:scim:schemas:core:1.0"));
        Assert.assertTrue(stringUser.contains("urn:scim:schemas:extension:enterprise:1.0"));
        Assert.assertTrue(stringUser.contains("005D0000001Az1u"));
        Assert.assertTrue(stringUser.contains("701984"));
        Assert.assertTrue(stringUser.contains("bjensen@example.com"));
        Assert.assertTrue(stringUser.contains("Jensen"));
        Assert.assertTrue(stringUser.contains("Barbara"));
        Assert.assertTrue(stringUser.contains("Jane"));
        Assert.assertTrue(stringUser.contains("Ms."));
        Assert.assertTrue(stringUser.contains("III"));
        Assert.assertTrue(stringUser.contains("Ms. Barbara J Jensen III"));
        Assert.assertTrue(stringUser.contains("Babs Jensen"));
        Assert.assertTrue(stringUser.contains("Babs"));
        Assert.assertTrue(stringUser.contains("https://login.example.com/bjensen"));
        Assert.assertTrue(stringUser.contains("bjensen@example.com"));
        Assert.assertTrue(stringUser.contains("work"));
        Assert.assertTrue(stringUser.contains("babs@jensen.org"));
        Assert.assertTrue(stringUser.contains("home"));
        Assert.assertTrue(stringUser.contains("work"));
        Assert.assertTrue(stringUser.contains("100 Universal City Plaza"));
        Assert.assertTrue(stringUser.contains("Hollywood"));
        Assert.assertTrue(stringUser.contains("CA"));
        Assert.assertTrue(stringUser.contains("91608"));
        Assert.assertTrue(stringUser.contains("USA"));
        Assert.assertTrue(stringUser.contains("home"));
        Assert.assertTrue(stringUser.contains("456 Hollywood Blvd"));
        Assert.assertTrue(stringUser.contains("Hollywood"));
        Assert.assertTrue(stringUser.contains("CA"));
        Assert.assertTrue(stringUser.contains("91608"));
        Assert.assertTrue(stringUser.contains("USA"));
        Assert.assertTrue(stringUser.contains("800-864-8377"));
        Assert.assertTrue(stringUser.contains("work"));
        Assert.assertTrue(stringUser.contains("818-123-4567"));
        Assert.assertTrue(stringUser.contains("mobile"));
        Assert.assertTrue(stringUser.contains("someaimhandle"));
        Assert.assertTrue(stringUser.contains("aim"));
        Assert.assertTrue(stringUser.contains("https://photos.example.com/profilephoto/72930000000Ccne/F"));
        Assert.assertTrue(stringUser.contains("photo"));
        Assert.assertTrue(stringUser.contains("https://photos.example.com/profilephoto/72930000000Ccne/T"));
        Assert.assertTrue(stringUser.contains("thumbnail"));
        Assert.assertTrue(stringUser.contains("Employee"));
        Assert.assertTrue(stringUser.contains("Tour Guide"));
        Assert.assertTrue(stringUser.contains("en_US"));
        Assert.assertTrue(stringUser.contains("America/Denver"));
        Assert.assertTrue(stringUser.contains("Tour Guides"));
        Assert.assertTrue(stringUser.contains("00300000005N2Y6AA"));
        Assert.assertTrue(stringUser.contains("true"));
        Assert.assertTrue(stringUser.contains("Employees"));
        Assert.assertTrue(stringUser.contains("00300000005N34H78"));
        Assert.assertTrue(stringUser.contains("US Employees"));
        Assert.assertTrue(stringUser.contains("00300000005N98YT1"));
        Assert.assertTrue(stringUser.contains("701984"));
        Assert.assertTrue(stringUser.contains("4130"));
        Assert.assertTrue(stringUser.contains("Universal Studios"));
        Assert.assertTrue(stringUser.contains("Theme Park"));
        Assert.assertTrue(stringUser.contains("Tour Operations"));
        Assert.assertTrue(stringUser.contains("005D0000001AQRE"));
        Assert.assertTrue(stringUser.contains("John Smith"));

        // TODO validate date
        // Assert.assertTrue(stringUser.contains("2010-01-23T04:56:22Z"));
        // Assert.assertTrue(stringUser.contains("2011-05-13T04:42:34Z"));

        Assert.assertTrue(stringUser.contains("456 Hollywood Blvd"));
        Assert.assertTrue(stringUser.contains("Hollywood, CA 91608 USA"));
        Assert.assertTrue(stringUser.contains("100 Universal City Plaza"));
        Assert.assertTrue(stringUser.contains("Hollywood, CA 91608 USA"));

    }

    private void partialContainsCheck(String stringUser) {

        Assert.assertTrue(stringUser.contains("urn:scim:schemas:core:1.0"));
        Assert.assertTrue(stringUser.contains("005D0000001Az1u"));
        Assert.assertTrue(stringUser.contains("bjensen@example.com"));
        Assert.assertTrue(stringUser.contains("100 Universal City Plaza"));

        Assert.assertTrue(stringUser.contains("456 Hollywood Blvd"));
        Assert.assertTrue(stringUser.contains("Barbara"));
        Assert.assertTrue(stringUser.contains("701984"));
        Assert.assertTrue(stringUser.contains("urn:scim:schemas:extension:enterprise:1.0"));

        Assert.assertFalse(stringUser.contains("Jensen"));
        Assert.assertFalse(stringUser.contains("Jane"));
        Assert.assertFalse(stringUser.contains("Ms."));
        Assert.assertFalse(stringUser.contains("III"));
        Assert.assertFalse(stringUser.contains("Ms. Barbara J Jensen III"));
        Assert.assertFalse(stringUser.contains("Babs Jensen"));
        Assert.assertFalse(stringUser.contains("Babs"));
        Assert.assertFalse(stringUser.contains("https://login.example.com/bjensen"));
        Assert.assertFalse(stringUser.contains("babs@jensen.org"));

        Assert.assertFalse(stringUser.contains("CA"));
        Assert.assertFalse(stringUser.contains("91608"));
        Assert.assertFalse(stringUser.contains("USA"));

        Assert.assertFalse(stringUser.contains("91608"));
        Assert.assertFalse(stringUser.contains("USA"));
        Assert.assertFalse(stringUser.contains("800-864-8377"));

        Assert.assertFalse(stringUser.contains("818-123-4567"));
        Assert.assertFalse(stringUser.contains("mobile"));
        Assert.assertFalse(stringUser.contains("someaimhandle"));
        Assert.assertFalse(stringUser.contains("aim"));
        Assert.assertFalse(stringUser.contains("https://photos.example.com/profilephoto/72930000000Ccne/F"));
        Assert.assertFalse(stringUser.contains("photo"));
        Assert.assertFalse(stringUser.contains("https://photos.example.com/profilephoto/72930000000Ccne/T"));
        Assert.assertFalse(stringUser.contains("thumbnail"));
        Assert.assertFalse(stringUser.contains("Employee"));
        Assert.assertFalse(stringUser.contains("Tour Guide"));
        Assert.assertFalse(stringUser.contains("en_US"));
        Assert.assertFalse(stringUser.contains("America/Denver"));
        Assert.assertFalse(stringUser.contains("Tour Guides"));
        Assert.assertFalse(stringUser.contains("00300000005N2Y6AA"));

        Assert.assertFalse(stringUser.contains("Employees"));
        Assert.assertFalse(stringUser.contains("00300000005N34H78"));
        Assert.assertFalse(stringUser.contains("US Employees"));
        Assert.assertFalse(stringUser.contains("00300000005N98YT1"));

        Assert.assertFalse(stringUser.contains("4130"));
        Assert.assertFalse(stringUser.contains("Universal Studios"));
        Assert.assertFalse(stringUser.contains("Theme Park"));
        Assert.assertFalse(stringUser.contains("Tour Operations"));
        Assert.assertFalse(stringUser.contains("005D0000001AQRE"));
        Assert.assertFalse(stringUser.contains("John Smith"));
        Assert.assertFalse(stringUser.contains("2010-01-23T04:56:22Z"));
        Assert.assertFalse(stringUser.contains("2011-05-13T04:42:34Z"));
    }

}
