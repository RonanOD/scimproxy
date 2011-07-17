package info.simplecloud.core.tools;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.types.Address;
import info.simplecloud.core.types.Name;
import info.simplecloud.core.types.PluralType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ScimUserComparatorTest {
    @Test
    public void sortOnSimple() {
        ScimUser user1 = new ScimUser();
        user1.setUserName("Arne");
        ScimUser user2 = new ScimUser();
        user2.setUserName("Bertil");
        ScimUser user3 = new ScimUser();
        user3.setUserName("Cesar");
        ScimUser user4 = new ScimUser();
        
        List<ScimUser> users = new ArrayList<ScimUser>();
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user1);
        
        ScimUserComparator<ScimUser> ascending = new ScimUserComparator<ScimUser>(ScimUser.ATTRIBUTE_USER_NAME, true);
        ScimUserComparator<ScimUser> descending = new ScimUserComparator<ScimUser>(ScimUser.ATTRIBUTE_USER_NAME, false);
        
        Collections.sort(users, ascending);
        Assert.assertEquals(user1, users.get(0));
        Assert.assertEquals(user2, users.get(1));
        Assert.assertEquals(user3, users.get(2));
        Assert.assertEquals(user4, users.get(3));
        
        Collections.sort(users, descending);
        Assert.assertEquals(user3, users.get(0));
        Assert.assertEquals(user2, users.get(1));
        Assert.assertEquals(user1, users.get(2));
        Assert.assertEquals(user4, users.get(3));
    }

    @Test
    public void sortOnComplex() {
        ScimUser user1 = new ScimUser();
        user1.setName(new Name(null, "Andersson", "Arne", null, null, null));
        ScimUser user2 = new ScimUser();
        user2.setName(new Name(null, "Andersson", "Bertil", null, null, null));
        ScimUser user3 = new ScimUser();
        user3.setName(new Name(null, "Berg", "Arne", null, null, null));
        ScimUser user4 = new ScimUser();
        user4.setName(new Name(null, "Berg", "Bertil", null, null, null));
        ScimUser user5 = new ScimUser();
        user5.setName(new Name(null, null, "Arne", null, null, null));
        ScimUser user6 = new ScimUser();
        user6.setName(new Name(null, null, "Bertil", null, null, null));
        ScimUser user7 = new ScimUser();
        user7.setName(new Name(null, null, null, "sad", null, null));
        ScimUser user8 = new ScimUser();
        user8.setDisplayName("test");
        
        List<ScimUser> users = new ArrayList<ScimUser>();
        users.add(user7);
        users.add(user1);
        users.add(user4);
        users.add(user6);
        users.add(user8);
        users.add(user2);
        users.add(user5);
        users.add(user3);
        

        ScimUserComparator<ScimUser> ascending = new ScimUserComparator<ScimUser>(ScimUser.ATTRIBUTE_NAME, true);
        ScimUserComparator<ScimUser> descending = new ScimUserComparator<ScimUser>(ScimUser.ATTRIBUTE_NAME, false);

        Collections.sort(users, ascending);
        
        Assert.assertEquals(user1, users.get(0));
        Assert.assertEquals(user2, users.get(1));
        Assert.assertEquals(user3, users.get(2));
        Assert.assertEquals(user4, users.get(3));
        Assert.assertEquals(user5, users.get(4));
        Assert.assertEquals(user6, users.get(5));
        Assert.assertEquals(user7, users.get(6));
        Assert.assertEquals(user8, users.get(7));
        
        
        Collections.sort(users, descending);

        Assert.assertEquals(user7, users.get(0));
        Assert.assertEquals(user6, users.get(1));
        Assert.assertEquals(user5, users.get(2));
        Assert.assertEquals(user4, users.get(3));
        Assert.assertEquals(user3, users.get(4));
        Assert.assertEquals(user2, users.get(5));
        Assert.assertEquals(user1, users.get(6));
        Assert.assertEquals(user8, users.get(7));
        
    }

    @Test
    public void sortOnPlural() {
        List<PluralType<String>> list1 = new ArrayList<PluralType<String>>();
        ScimUser user1 = new ScimUser();
        list1.add(new PluralType<String>("arne@andersson.se", null, true));
        user1.setEmails(list1);

        List<PluralType<String>> list2 = new ArrayList<PluralType<String>>();
        ScimUser user2 = new ScimUser();
        list2.add(new PluralType<String>("arne@andersson.se", null, false));
        list2.add(new PluralType<String>("bertil@andersson.se", null, true));
        user2.setEmails(list2);

        List<PluralType<String>> list3 = new ArrayList<PluralType<String>>();
        ScimUser user3 = new ScimUser();
        list3.add(new PluralType<String>("gustav@andersson.se", null, false));
        list3.add(new PluralType<String>("erik@andersson.se", null, false));
        user3.setEmails(list3);
        
        ScimUser user4 = new ScimUser();
        user4.setUserName("test");
        
        List<ScimUser> users = new ArrayList<ScimUser>();
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user1);
        
        ScimUserComparator<ScimUser> ascending = new ScimUserComparator<ScimUser>(ScimUser.ATTRIBUTE_EMAILS, true);
        ScimUserComparator<ScimUser> descending = new ScimUserComparator<ScimUser>(ScimUser.ATTRIBUTE_EMAILS, false);

        
        Collections.sort(users, ascending);
        
        Assert.assertEquals(user1, users.get(0));
        Assert.assertEquals(user2, users.get(1));
        Assert.assertEquals(user3, users.get(2));
        Assert.assertEquals(user4, users.get(3));
        
        
        Collections.sort(users, descending);
        
        Assert.assertEquals(user3, users.get(0));
        Assert.assertEquals(user2, users.get(1));
        Assert.assertEquals(user1, users.get(2));
        Assert.assertEquals(user4, users.get(3));
    }

    @Test
    public void sortOnPluralComplex() {
        List<PluralType<Address>> list1 = new ArrayList<PluralType<Address>>();
        ScimUser user1 = new ScimUser();
        list1.add(new PluralType<Address>(new Address(null, "a street", null, null, null, null), null, true));
        user1.setAddresses(list1);

        List<PluralType<Address>> list2 = new ArrayList<PluralType<Address>>();
        ScimUser user2 = new ScimUser();
        list2.add(new PluralType<Address>(new Address(null, "b street", null, null, null, null), null, false));
        list2.add(new PluralType<Address>(new Address(null, "c street", null, null, null, null), null, true));
        user2.setAddresses(list2);

        List<PluralType<Address>> list3 = new ArrayList<PluralType<Address>>();
        ScimUser user3 = new ScimUser();
        list3.add(new PluralType<Address>(new Address(null, "d street", null, null, null, null), null, false));
        list3.add(new PluralType<Address>(new Address(null, "e street", null, null, null, null), null, false));
        user3.setAddresses(list3);
        
        ScimUser user4 = new ScimUser();
        user4.setUserName("test");
        
        List<ScimUser> users = new ArrayList<ScimUser>();
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user1);
        
        ScimUserComparator<ScimUser> ascending = new ScimUserComparator<ScimUser>(ScimUser.ATTRIBUTE_ADDRESSES, true);
        ScimUserComparator<ScimUser> descending = new ScimUserComparator<ScimUser>(ScimUser.ATTRIBUTE_ADDRESSES, false);

        
        Collections.sort(users, ascending);
        
        Assert.assertEquals(user1, users.get(0));
        Assert.assertEquals(user2, users.get(1));
        Assert.assertEquals(user3, users.get(2));
        Assert.assertEquals(user4, users.get(3));
        
        
        Collections.sort(users, descending);
        
        Assert.assertEquals(user3, users.get(0));
        Assert.assertEquals(user2, users.get(1));
        Assert.assertEquals(user1, users.get(2));
        Assert.assertEquals(user4, users.get(3));
    }
}
