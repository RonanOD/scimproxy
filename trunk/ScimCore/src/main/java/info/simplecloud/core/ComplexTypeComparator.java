package info.simplecloud.core;

import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.core.types.ComplexType;

import java.util.Comparator;

public class ComplexTypeComparator implements Comparator<ComplexType> {
    private String  compareAttribute;
    private boolean sortAscending;

    public ComplexTypeComparator(String compareAttribute, boolean sortAscending) {
        this.compareAttribute = compareAttribute;
        this.sortAscending = sortAscending;
    }

    @Override
    public int compare(ComplexType complex0, ComplexType complex1) {


        try {
            Object attribute0 = complex0.getAttribute(this.compareAttribute);
            Object attribute1 = complex1.getAttribute(this.compareAttribute);

            // TODO null attributes should be first/last depending on sortOrder
            if (attribute0 == attribute1) {
                return 0;
            } else if (attribute0 == null) {
                return 1;
            } else if (attribute1 == null) {
                return -1;
            }

            if (!(attribute0 instanceof Comparable)) {
                throw new RuntimeException("Failed to compare '" + complex0 + "' and '" + complex1 + "', attribute '"
                        + this.compareAttribute + "' is not comparable");
            }
            Comparable comparable0 = (Comparable) attribute0;
            if (!(attribute1 instanceof Comparable)) {
                throw new RuntimeException("Failed to compare '" + complex0 + "' and '" + complex1 + "', attribute '"
                        + this.compareAttribute + "' is not comparable");
            }
            Comparable comparable1 = (Comparable) attribute1;

            return (this.sortAscending ? comparable0.compareTo(comparable1) : comparable1.compareTo(comparable0));
        } catch (UnknownAttribute e) {
            throw new RuntimeException("Failed to compare '" + complex0 + "' and '" + complex1 + "'", e);
        }

    }
}
