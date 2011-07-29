package info.simplecloud.core.ng.comparators;

import info.simplecloud.core.ng.ComplexType2;
import info.simplecloud.core.ng.MetaData;

import java.util.Comparator;

public class ComplexComparator implements Comparator<ComplexType2> {

    private String   compareAttribute;
    private boolean  sortAscending;
    private Class<?> type;

    public ComplexComparator(String compareAttribute, boolean sortAscending, Class<?> type) {
        // TODO validate that compareAttribute exists
        this.compareAttribute = compareAttribute;
        this.sortAscending = sortAscending;
        this.type = type;
    }

    @Override
    public int compare(ComplexType2 complex1, ComplexType2 complex2) {
        
        if (complex1 == complex2) {
            return 0;
        } else if (complex1 == null) {
            return 1;
        } else if (complex2 == null) {
            return -1;
        }
        
        try {
            Object attribute1 = complex1.getAttribute(this.compareAttribute, this.type);
            Object attribute2 = complex2.getAttribute(this.compareAttribute, this.type);
            
            MetaData metaData = complex1.getMetaData(this.compareAttribute);
            if(metaData == null) {
                throw new RuntimeException("Found no metadata for " + this.compareAttribute);
            }
            
            Comparator<?> comparator = metaData.getComparator(this.sortAscending);
            return ((Comparator<Object>)comparator).compare(attribute1, attribute2);
        } catch (Exception e) {
            throw new RuntimeException("Internal error", e);
        }
        
    }

}
