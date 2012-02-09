package info.simplecloud.core.coding.encode;

import info.simplecloud.core.Resource;
import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.core.types.Address;
import info.simplecloud.core.types.ComplexType;
import info.simplecloud.core.types.Name;
import info.simplecloud.core.types.MultiValuedType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class JsonEncoderTest {

    @Test
    public void encode() throws UnknownAttribute {
        User scimUser = getUser("yhgty-ujhyu-iolki", "samuel", "samuel@erdtman.se", "samuel.erdtman@nexussafe.com", "12345", "67890");

        String jsonUser = new JsonEncoder().encode(scimUser);
        Assert.assertTrue(jsonUser.contains("yhgty-ujhyu-iolki"));
        Assert.assertTrue(jsonUser.contains("samuel"));
        Assert.assertTrue(jsonUser.contains("mr."));
        Assert.assertTrue(jsonUser.contains("samuel@erdtman.se"));
        Assert.assertTrue(jsonUser.contains("samuel.erdtman@nexussafe.com"));
        Assert.assertTrue(jsonUser.contains("12345"));
        Assert.assertTrue(jsonUser.contains("67890"));
    }

    @Test
    public void encodeSet() throws UnknownAttribute {
        String[] ids = new String[] { "1abcd", "2asdkjlfhaöksdf", "3fakljsdhflas" };
        String[] name = new String[] { "olle", "nisse", "kalle" };
        String[] emails1 = new String[] { "olle@home.com", "nisse@home.com", "kalle@home.com" };
        String[] emails2 = new String[] { "olle@work.com", "nisse@work.com", "kalle@work.com" };
        String[] postcodes1 = new String[] { "11111", "22222", "33333" };
        String[] postcodes2 = new String[] { "44444", "55555", "66666" };

        List<Resource> users = new ArrayList<Resource>();

        for (int i = 0; i < ids.length; i++) {
            users.add(getUser(ids[i], name[i], emails1[i], emails2[i], postcodes1[i], postcodes2[i]));
        }
        String jsonUsers = new JsonEncoder().encode(users);
        Assert.assertTrue(jsonUsers.contains("\"totalResults\": " + users.size()));

        for (int i = 0; i < ids.length; i++) {
            Assert.assertTrue(jsonUsers.contains(ids[i]));
            Assert.assertTrue(jsonUsers.contains(emails1[i]));
            Assert.assertTrue(jsonUsers.contains(emails2[i]));
            Assert.assertTrue(jsonUsers.contains(postcodes1[i]));
            Assert.assertTrue(jsonUsers.contains(postcodes2[i]));
        }

        List<String> includeAttributes = new ArrayList<String>();
        includeAttributes.add("addresses");
        includeAttributes.add("name");
        includeAttributes.add("emails");
        includeAttributes.add("id");
        jsonUsers = new JsonEncoder().encode(users, includeAttributes);
        Assert.assertTrue(jsonUsers.contains("\"totalResults\": " + users.size()));

        for (int i = 0; i < ids.length; i++) {
            Assert.assertTrue(jsonUsers.contains(ids[i]));
            Assert.assertTrue(jsonUsers.contains(emails1[i]));
            Assert.assertTrue(jsonUsers.contains(emails2[i]));
            Assert.assertTrue(jsonUsers.contains(postcodes1[i]));
            Assert.assertTrue(jsonUsers.contains(postcodes2[i]));
        }

        includeAttributes = new ArrayList<String>();
        includeAttributes.add("addresses");
        includeAttributes.add("name");
        includeAttributes.add("id");
        jsonUsers = new JsonEncoder().encode(users, includeAttributes);
        Assert.assertTrue(jsonUsers.contains("\"totalResults\": " + users.size()));

        for (int i = 0; i < ids.length; i++) {
            Assert.assertTrue(jsonUsers.contains(ids[i]));
            Assert.assertFalse(jsonUsers.contains(emails1[i]));
            Assert.assertFalse(jsonUsers.contains(emails2[i]));
            Assert.assertTrue(jsonUsers.contains(postcodes1[i]));
            Assert.assertTrue(jsonUsers.contains(postcodes2[i]));
        }

        includeAttributes = new ArrayList<String>();
        includeAttributes.add("addresses");
        includeAttributes.add("emails");
        includeAttributes.add("id");
        jsonUsers = new JsonEncoder().encode(users, includeAttributes);
        Assert.assertTrue(jsonUsers.contains("\"totalResults\": " + users.size()));

        for (int i = 0; i < ids.length; i++) {
            Assert.assertTrue(jsonUsers.contains(ids[i]));
            Assert.assertTrue(jsonUsers.contains(emails1[i]));
            Assert.assertTrue(jsonUsers.contains(emails2[i]));
            Assert.assertTrue(jsonUsers.contains(postcodes1[i]));
            Assert.assertTrue(jsonUsers.contains(postcodes2[i]));
        }

        includeAttributes = new ArrayList<String>();
        includeAttributes.add("name");
        includeAttributes.add("emails");
        includeAttributes.add("id");
        jsonUsers = new JsonEncoder().encode(users, includeAttributes);
        Assert.assertTrue(jsonUsers.contains("\"totalResults\": " + users.size()));

        for (int i = 0; i < ids.length; i++) {
            Assert.assertTrue(jsonUsers.contains(ids[i]));
            Assert.assertTrue(jsonUsers.contains(emails1[i]));
            Assert.assertTrue(jsonUsers.contains(emails2[i]));
            Assert.assertFalse(jsonUsers.contains(postcodes1[i]));
            Assert.assertFalse(jsonUsers.contains(postcodes2[i]));
        }
    }

    private User getUser(String id, String name, String email1, String email2, String postcode1, String postcode2) throws UnknownAttribute {
        User scimUser = new User("123");

        scimUser.setAttribute("id", id);
        scimUser.setAttribute("name",
                new Name().setAttribute("givenName", name).setAttribute("honorificSuffix", "mr."));

        List<MultiValuedType<String>> emails = new LinkedList<MultiValuedType<String>>();
        emails.add(new MultiValuedType<String>(email1, "private", true, false));
        emails.add(new MultiValuedType<String>(email2, "work", false, false));
        scimUser.setAttribute("emails", emails);

        List<MultiValuedType<ComplexType>> addresses = new LinkedList<MultiValuedType<ComplexType>>();
        addresses.add(new MultiValuedType<ComplexType>(new Address().setAttribute("country", "Sweeden").setAttribute(
                "postalCode", postcode1), "home", true, false));
        addresses.add(new MultiValuedType<ComplexType>(new Address().setAttribute("country", "England").setAttribute(
                "postalCode", postcode2), "work", false, false));

        scimUser.setAttribute("addresses", addresses);

        return scimUser;
    }
}
