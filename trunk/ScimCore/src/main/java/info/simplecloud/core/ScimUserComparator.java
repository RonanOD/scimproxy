package info.simplecloud.core;

import java.util.Comparator;

public class ScimUserComparator<T> implements Comparator<ScimUser> {

    private String  compareAttribute;
    private boolean sortAscending;

    public ScimUserComparator(String compareAttribute, boolean sortAscending) {
        this.compareAttribute = compareAttribute;
        this.sortAscending = sortAscending;
    }

    @Override
    public int compare(ScimUser user1, ScimUser user2) {
        Comparable<T> obj1 = user1.getComparable(this.compareAttribute);
        Comparable<T> obj2 = user2.getComparable(this.compareAttribute);
        return this.sortAscending ? obj1.compareTo((T) obj2) : obj2.compareTo((T) obj1);
    }

}
