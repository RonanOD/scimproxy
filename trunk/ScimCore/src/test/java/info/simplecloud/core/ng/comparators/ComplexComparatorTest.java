package info.simplecloud.core.ng.comparators;

import info.simplecloud.core.ng.ComplexType2;
import info.simplecloud.core.ng.MetaData;
import info.simplecloud.core.ng.ScimPair;
import info.simplecloud.core.ng.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComplexComparatorTest {
    private ComplexType2 complex1;
    private ComplexType2 complex2;
    private ComplexType2 complex3;
    private ComplexType2 complex4;

    @Before
    public void setUp() {
        Map<String, ScimPair<MetaData, Object>> tree = new HashMap<String, ScimPair<MetaData, Object>>();
        tree.put("simple", new ScimPair<MetaData, Object>(Types.META_SIMPLE_STRING, null));
        complex1 = new ComplexType2(tree);
        complex2 = new ComplexType2(tree);
        complex3 = new ComplexType2(tree);
        complex4 = new ComplexType2(tree);
    }

    @Test
    public void sort() throws Exception {
        complex1.setAttribute("simple", "Arne");
        complex2.setAttribute("simple", "Bertil");
        complex3.setAttribute("simple", "Cesar");
        complex4.setAttribute("simple", null);

        List<ComplexType2> list = new ArrayList<ComplexType2>();
        list.add(complex1);
        list.add(complex4);
        list.add(complex3);
        list.add(complex2);

        ComplexComparator ascending = new ComplexComparator("simple", true, String.class);
        ComplexComparator descending = new ComplexComparator("simple", false, String.class);

        Collections.sort(list, ascending);
        Assert.assertEquals(complex1, list.get(0));
        Assert.assertEquals(complex2, list.get(1));
        Assert.assertEquals(complex3, list.get(2));
        Assert.assertEquals(complex4, list.get(3));

        Collections.sort(list, descending);
        Assert.assertEquals(complex3, list.get(0));
        Assert.assertEquals(complex2, list.get(1));
        Assert.assertEquals(complex1, list.get(2));
        Assert.assertEquals(complex4, list.get(3));
    }
}
