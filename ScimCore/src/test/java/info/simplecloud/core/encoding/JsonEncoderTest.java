package info.simplecloud.core.encoding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import info.simplecloud.core.Address;
import info.simplecloud.core.ComplexType;
import info.simplecloud.core.Name;
import info.simplecloud.core.PluralType;
import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.EncodingFailed;

import org.junit.Assert;
import org.junit.Test;

public class JsonEncoderTest {

    @Test
    public void encode() throws EncodingFailed {
        ScimUser scimUser = getUser("yhgty-ujhyu-iolki", "samuel", "samuel@erdtman.se", "samuel.erdtman@nexussafe.com", "12345",
                "67890");

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
    public void encodeSet() throws EncodingFailed {
        String[] ids = new String[]{"1abcd", "2asdkjlfhaöksdf", "3fakljsdhflas"};
        String[] name = new String[]{"olle", "nisse", "kalle"};
        String[] emails1 = new String[]{"olle@home.com", "nisse@home.com", "kalle@home.com"};
        String[] emails2 = new String[]{"olle@work.com", "nisse@work.com", "kalle@work.com"};
        String[] postcodes1 = new String[]{"11111", "22222", "33333"};
        String[] postcodes2 = new String[]{"44444", "55555", "66666"};
        
        
        List<ScimUser> users = new ArrayList<ScimUser>();
        
        for(int i=0; i<ids.length; i++) {
            users.add(getUser(ids[i], name[i], emails1[i], emails2[i], postcodes1[i], postcodes2[i]));            
        }
        String jsonUsers = new JsonEncoder().encode(users);
        Assert.assertTrue(jsonUsers.contains("\"totalResults\": " + users.size()));
        
        for(int i=0; i<ids.length; i++) {
            Assert.assertTrue(jsonUsers.contains(ids[i]));
            Assert.assertTrue(jsonUsers.contains(emails1[i]));
            Assert.assertTrue(jsonUsers.contains(emails2[i]));
            Assert.assertTrue(jsonUsers.contains(postcodes1[i]));
            Assert.assertTrue(jsonUsers.contains(postcodes2[i]));
        }
    }

    private ScimUser getUser(String id, String name, String email1, String email2, String postcode1, String postcode2) {
        ScimUser scimUser = new ScimUser();

        scimUser.setAttribute(ScimUser.ATTRIBUTE_ID, id);
        scimUser.setAttribute(ScimUser.ATTRIBUTE_NAME,
                new Name().setAttribute(Name.ATTRIBUTE_GIVEN_NAME, name).setAttribute(Name.ATTRIBUTE_HONORIFIC_PREFIX, "mr."));

        List<PluralType<String>> emails = new LinkedList<PluralType<String>>();
        emails.add(new PluralType<String>(email1, "private", true));
        emails.add(new PluralType<String>(email2, "work", false));
        scimUser.setAttribute(ScimUser.ATTRIBUTE_EMAILS, emails);

        List<PluralType<ComplexType>> addresses = new LinkedList<PluralType<ComplexType>>();
        addresses.add(new PluralType<ComplexType>(new Address().setAttribute(Address.ATTRIBUTE_CONTRY, "Sweeden").setAttribute(
                Address.ATTRIBUTE_POSTAL_CODE, postcode1), "home", true));
        addresses.add(new PluralType<ComplexType>(new Address().setAttribute(Address.ATTRIBUTE_CONTRY, "England").setAttribute(
                Address.ATTRIBUTE_POSTAL_CODE, postcode2), "work", false));

        scimUser.setAttribute(ScimUser.ATTRIBUTE_ADDRESSES, addresses);

        return scimUser;
    }
}
