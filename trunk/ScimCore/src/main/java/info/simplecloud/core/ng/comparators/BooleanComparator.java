package info.simplecloud.core.ng.comparators;

import java.util.Comparator;

public class BooleanComparator implements Comparator<Boolean> {

    public static final BooleanComparator ASCENDING  = new BooleanComparator(true);
    public static final BooleanComparator DESCENDING = new BooleanComparator(false);

    private boolean                       sortAscending;

    private BooleanComparator(boolean sortAscending) {
        this.sortAscending = sortAscending;
    }

    @Override
    public int compare(Boolean bool1, Boolean bool2) {

        if (bool1 == bool2) {
            return 0;
        } else if (bool1 == null) {
            return 1;
        } else if (bool2 == null) {
            return -1;
        }

        return this.sortAscending ? bool1.compareTo(bool2) : bool2.compareTo(bool1);
    }

}
