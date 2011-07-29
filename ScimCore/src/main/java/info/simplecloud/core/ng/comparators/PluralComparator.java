package info.simplecloud.core.ng.comparators;

import java.util.Comparator;
import java.util.List;

import info.simplecloud.core.ng.PluralType2;

public class PluralComparator implements Comparator<List<PluralType2>> {

    private Comparator<?> internalComparator;

    public PluralComparator(Comparator<?> internalComparator) {
        this.internalComparator = internalComparator;
    }


    @Override
    public int compare(List<PluralType2> list1, List<PluralType2> list2) {
        PluralType2 plural1 = getSortByObject(list1);
        PluralType2 plural2 = getSortByObject(list2);

        return ((Comparator<Object>)internalComparator).compare(plural1.getValue(), plural2.getValue());
    }

    private PluralType2 getSortByObject(List<PluralType2> plural) {
        for (PluralType2 singular : plural) {
            if (singular.isPrimary()) {
                return singular;
            }
        }

        return plural.get(0);
    }

}
