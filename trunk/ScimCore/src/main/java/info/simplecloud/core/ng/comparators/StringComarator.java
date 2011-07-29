package info.simplecloud.core.ng.comparators;

import java.util.Comparator;

public class StringComarator implements Comparator<String> {

    public static final StringComarator ASCENDING = new StringComarator(true);
    public static final StringComarator DESCENDING = new StringComarator(false);
    
    private boolean sortAscending;

    private StringComarator(boolean sortAscending) {
        this.sortAscending = sortAscending;
    }

    @Override
    public int compare(String string1, String string2) {
        
        if (string1 == string2) {
            return 0;
        } else if (string1 == null) {
            return 1;
        } else if (string2 == null) {
            return -1;
        }
        
        return (this.sortAscending ? string1.compareTo(string2) : string2.compareTo(string1));
    }

}
