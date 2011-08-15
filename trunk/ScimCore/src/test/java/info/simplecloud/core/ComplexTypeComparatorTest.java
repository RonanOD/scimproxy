package info.simplecloud.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import info.simplecloud.core.exceptions.UnknownAttribute;

import org.junit.Assert;
import org.junit.Test;

public class ComplexTypeComparatorTest {

    @Test
    public void sortOnSimple() throws UnknownAttribute {
        User user1 = new User("ABC123");
        user1.setAttribute("userName", "Arne");
        User user2 = new User("ABC123");
        user2.setAttribute("userName", "Bertil");
        User user3 = new User("ABC123");
        user3.setAttribute("userName", "Cesar");
        User user4 = new User("ABC123");

        List<User> users = new ArrayList<User>();
        users.add(user4);
        users.add(user2);
        users.add(user1);
        users.add(user3);

        ComplexTypeComparator ascending = new ComplexTypeComparator("userName", true);
        ComplexTypeComparator descending = new ComplexTypeComparator("userName", false);
        
        Collections.sort(users, ascending);
        Assert.assertEquals(user1, users.get(0));
        Assert.assertEquals(user2, users.get(1));
        Assert.assertEquals(user3, users.get(2));
        Assert.assertEquals(user4, users.get(3));

        Collections.sort(users, descending);
        Assert.assertEquals(user1, users.get(2));
        Assert.assertEquals(user2, users.get(1));
        Assert.assertEquals(user3, users.get(0));
        Assert.assertEquals(user4, users.get(3));
    }

    @Test
    public void sortOnSubSimple() throws UnknownAttribute {
        User user1 = new User("ABC123");
        user1.setAttribute("name.givenName", "Arne");
        User user2 = new User("ABC123");
        user2.setAttribute("name.givenName", "Bertil");
        User user3 = new User("ABC123");
        user3.setAttribute("name.givenName", "Cesar");
        User user4 = new User("ABC123");

        List<User> users = new ArrayList<User>();
        users.add(user4);
        users.add(user2);
        users.add(user1);
        users.add(user3);

        ComplexTypeComparator ascending = new ComplexTypeComparator("name.givenName", true);
        ComplexTypeComparator descending = new ComplexTypeComparator("name.givenName", false);

        Collections.sort(users, ascending);
        Assert.assertEquals(user1, users.get(0));
        Assert.assertEquals(user2, users.get(1));
        Assert.assertEquals(user3, users.get(2));
        Assert.assertEquals(user4, users.get(3));

        Collections.sort(users, descending);
        Assert.assertEquals(user1, users.get(2));
        Assert.assertEquals(user2, users.get(1));
        Assert.assertEquals(user3, users.get(0));
        Assert.assertEquals(user4, users.get(3));

    }
}
