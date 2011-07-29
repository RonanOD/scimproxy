package info.simplecloud.core.ng.comparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class StringComparatorTest {
    @Test
    public void sort() {
        List<String> list = new ArrayList<String>();
        list.add(null);
        list.add("Arne");
        list.add("Cesar");
        list.add("Bertil");

        Collections.sort(list, StringComarator.ASCENDING);
        Assert.assertEquals("Arne", list.get(0));
        Assert.assertEquals("Bertil", list.get(1));
        Assert.assertEquals("Cesar", list.get(2));
        Assert.assertNull(list.get(3));

        Collections.sort(list, StringComarator.DESCENDING);
        Assert.assertNull(list.get(3));
        Assert.assertEquals("Arne", list.get(2));
        Assert.assertEquals("Bertil", list.get(1));
        Assert.assertEquals("Cesar", list.get(0));
    }
}
