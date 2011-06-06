package info.simplecloud.core;

import java.util.Calendar;

public class Meta extends ComplexType {
    public static final String ATTRIBUTE_CREATED       = "created";
    public static final String ATTRIBUTE_LAST_MODIFIED = "lastModified";
    public static final String simple[]                = { ATTRIBUTE_CREATED, ATTRIBUTE_LAST_MODIFIED };

    public Calendar getCreated() {
        return super.getAttributeCalendar(ATTRIBUTE_CREATED);
    }

    public Calendar getLastModified() {
        return super.getAttributeCalendar(ATTRIBUTE_LAST_MODIFIED);
    }

    public void setCreated(Calendar created) {
        super.setAttribute(ATTRIBUTE_CREATED, created);
    }

    public void setLastModified(Calendar lastModified) {
        super.setAttribute(ATTRIBUTE_LAST_MODIFIED, lastModified);
    }

    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj) {
            return true;
        }
        if (!(otherObj instanceof Meta)) {
            return false;
        }
        Meta otherMeta = (Meta) otherObj;

        return super.equals(otherMeta, simple);
    }

    @Override
    public String toString() {
        return super.toString(simple);
    }
}
