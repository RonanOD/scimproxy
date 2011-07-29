package info.simplecloud.core.ng.comparators;

import info.simplecloud.core.ng.PluralType2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PluralComparatorTest {

    @Test
    public void sortWithPrimary() {
        List<PluralType2> pluralList1 = new ArrayList<PluralType2>();
        List<PluralType2> pluralList2 = new ArrayList<PluralType2>();
        List<PluralType2> pluralList3 = new ArrayList<PluralType2>();
        List<List<PluralType2>> list = new ArrayList<List<PluralType2>>();

        pluralList1.add(new PluralType2("Arne", "name", true));
        pluralList1.add(new PluralType2("Bertil", "name", false));
        pluralList1.add(new PluralType2("Cesar", "name", false));

        pluralList2.add(new PluralType2("David", "name", false));
        pluralList2.add(new PluralType2("Erik", "name", true));
        pluralList2.add(new PluralType2("Fredrik", "name", false));

        pluralList3.add(new PluralType2("Gustav", "name", false));
        pluralList3.add(new PluralType2("Hendrik", "name", false));
        pluralList3.add(new PluralType2("Ivar", "name", true));

        list.add(pluralList1);
        list.add(pluralList3);
        list.add(pluralList2);

        PluralComparator ascending = new PluralComparator(StringComarator.ASCENDING);
        PluralComparator descending = new PluralComparator(StringComarator.DESCENDING);

        Collections.sort(list, ascending);
        Assert.assertEquals(pluralList1, list.get(0));
        Assert.assertEquals(pluralList2, list.get(1));
        Assert.assertEquals(pluralList3, list.get(2));

        Collections.sort(list, descending);
        Assert.assertEquals(pluralList3, list.get(0));
        Assert.assertEquals(pluralList2, list.get(1));
        Assert.assertEquals(pluralList1, list.get(2));
    }

    @Test
    public void sortWithoutPrimary() {
        List<PluralType2> pluralList1 = new ArrayList<PluralType2>();
        List<PluralType2> pluralList2 = new ArrayList<PluralType2>();
        List<PluralType2> pluralList3 = new ArrayList<PluralType2>();
        List<List<PluralType2>> list = new ArrayList<List<PluralType2>>();

        pluralList1.add(new PluralType2("Arne", "name", false));
        pluralList1.add(new PluralType2("Bertil", "name", false));
        pluralList1.add(new PluralType2("Cesar", "name", false));

        pluralList2.add(new PluralType2("David", "name", false));
        pluralList2.add(new PluralType2("Erik", "name", false));
        pluralList2.add(new PluralType2("Fredrik", "name", false));

        pluralList3.add(new PluralType2("Gustav", "name", false));
        pluralList3.add(new PluralType2("Hendrik", "name", false));
        pluralList3.add(new PluralType2("Ivar", "name", false));

        list.add(pluralList1);
        list.add(pluralList3);
        list.add(pluralList2);

        PluralComparator ascending = new PluralComparator(StringComarator.ASCENDING);
        PluralComparator descending = new PluralComparator(StringComarator.DESCENDING);

        Collections.sort(list, ascending);
        Assert.assertEquals(pluralList1, list.get(0));
        Assert.assertEquals(pluralList2, list.get(1));
        Assert.assertEquals(pluralList3, list.get(2));

        Collections.sort(list, descending);
        Assert.assertEquals(pluralList3, list.get(0));
        Assert.assertEquals(pluralList2, list.get(1));
        Assert.assertEquals(pluralList1, list.get(2));
    }
}
