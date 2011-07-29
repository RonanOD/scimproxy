package info.simplecloud.core.ng.comparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class BooleanComparatorTest {

    @Test
    public void sort() {
        List<Boolean> list = new ArrayList<Boolean>();
        list.add(false);
        list.add(null);
        list.add(true);
        
        Collections.sort(list, BooleanComparator.ASCENDING);
        Assert.assertFalse(list.get(0));
        Assert.assertTrue(list.get(1));
        Assert.assertNull(list.get(2));
        
        Collections.sort(list, BooleanComparator.DESCENDING);
        Assert.assertTrue(list.get(0));
        Assert.assertFalse(list.get(1));
        Assert.assertNull(list.get(2));
    }
}
