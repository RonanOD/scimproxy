package info.simplecloud.core;

import info.simplecloud.core.execeptions.InvalidUser;
import info.simplecloud.core.execeptions.UnknownEncoding;
import info.simplecloud.core.execeptions.UnknownExtension;
import info.simplecloud.core.extensions.EnterpriseAttributes;
import info.simplecloud.core.testtools.ResourceReader;
import info.simplecloud.core.types.Address;
import info.simplecloud.core.types.Name;
import info.simplecloud.core.types.PluralType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ScimUserTest {

    @Test
    public void patch() throws UnknownEncoding, InvalidUser, IOException  {
        String patch = ResourceReader.readTextFile("ScimUser.json", getClass());
        ScimUser oldUser = new ScimUser();
        oldUser.setId("123-123-123");
        oldUser.setName(new Name("mr. Nisse Svensson", "Svensson", "Nisse", null, "mr.", null));
        List<PluralType<String>> emails = new ArrayList<PluralType<String>>();
        emails.add(new PluralType<String>("nisse@work.com", "work", true));
        emails.add(new PluralType<String>("nisse@svensson.com", "home", true));

        List<PluralType<Address>> plural = new ArrayList<PluralType<Address>>();
        Address workAddress = new Address();
        workAddress.setCountry("Sweden");
        workAddress.setRegion("Stockholm");
        plural.add(new PluralType<Address>(workAddress, "work", true));
        oldUser.setAddresses(plural);
        
        oldUser.patch(patch, "JSON");
        Assert.assertEquals("mr. Nisse Johansson", oldUser.getName().getFormatted());
        Assert.assertEquals("Johansson", oldUser.getName().getFamilyName());
        Assert.assertEquals("Nisse", oldUser.getName().getGivenName());
        Assert.assertTrue(oldUser.getAddresses().contains(new PluralType<Address>(workAddress, "work", true)));
    }
    @Before
    public void init() {
        ScimUser.registerExtension(EnterpriseAttributes.class);
    }

    @Test
    public void equals() throws UnknownExtension {
        ScimUser user1 = new ScimUser();
        ScimUser user1eq1 = new ScimUser();
        ScimUser user1noteq1 = new ScimUser();
        ScimUser user1noteq2 = new ScimUser();
        ScimUser user1noteq3 = new ScimUser();
        ScimUser user1noteq4 = new ScimUser();
        ScimUser user1noteq5 = new ScimUser();

        user1.setId("id-123");
        user1eq1.setId("id-123");
        user1noteq1.setId("not equal");
        user1noteq2.setId("id-123");
        user1noteq3.setId("id-123");
        user1noteq4.setId("id-123");
        user1noteq5.setId("id-123");

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
        l1.add(new PluralType<String>("String1", "type1", true));
        user1.setGroups(l1);
        List<PluralType<String>> l2 = new ArrayList<PluralType<String>>();
        l2.add(new PluralType<String>("String1", "type1", true));
        user1eq1.setGroups(l2);
        List<PluralType<String>> l3 = new ArrayList<PluralType<String>>();
        l3.add(new PluralType<String>("not equal", "type1", true));
        user1noteq4.setGroups(l3);
        List<PluralType<String>> l4 = new ArrayList<PluralType<String>>();
        l4.add(new PluralType<String>("String1", "type1", true));
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

        List<PluralType<Address>> plural1 = new ArrayList<PluralType<Address>>();
        Address workAddress = new Address();
        workAddress.setCountry("Sweden");
        workAddress.setRegion("Stockholm");
        plural1.add(new PluralType<Address>(workAddress, "work", true));
        user1.setAddresses(plural1);

        List<PluralType<Address>> plural2 = new ArrayList<PluralType<Address>>();
        Address workAddress2 = new Address();
        workAddress2.setCountry("Sweden");
        workAddress2.setRegion("Stockholm");
        plural2.add(new PluralType<Address>(workAddress2, "work", true));
        user1eq1.setAddresses(plural2);

        List<PluralType<Address>> plural3 = new ArrayList<PluralType<Address>>();
        Address workAddress3 = new Address();
        workAddress3.setCountry("Sweden");
        workAddress3.setRegion("not equal");
        plural3.add(new PluralType<Address>(workAddress3, "work", true));
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
