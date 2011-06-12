package info.simplecloud.core;

import java.util.Calendar;

public class Meta extends ComplexType {
    public static final String ATTRIBUTE_CREATED       = "created";
    public static final String ATTRIBUTE_LAST_MODIFIED = "lastModified";
    public static final String ATTRIBUTE_ETAG = "ETag";
    public static final String ATTRIBUTE_LOCATION = "location";
    public static final String ATTRIBUTE_ATTRIBUTE = "attributes";
    public static final String simple[]                = { ATTRIBUTE_CREATED, ATTRIBUTE_LAST_MODIFIED, ATTRIBUTE_ETAG, ATTRIBUTE_LOCATION, ATTRIBUTE_ATTRIBUTE};

    public Calendar getCreated() {
        return super.getAttributeCalendar(ATTRIBUTE_CREATED);
    }

    public Calendar getLastModified() {
        return super.getAttributeCalendar(ATTRIBUTE_LAST_MODIFIED);
    }

    public String getETag() {
        return super.getAttributeString(ATTRIBUTE_ETAG);
    }

    public String getLocation() {
        return super.getAttributeString(ATTRIBUTE_LOCATION);
    }

    public Object getAttributes() {
        return super.getAttribute(ATTRIBUTE_ATTRIBUTE);
    }

    
    public void setCreated(Calendar created) {
        super.setAttribute(ATTRIBUTE_CREATED, created);
    }

    public void setLastModified(Calendar lastModified) {
        super.setAttribute(ATTRIBUTE_LAST_MODIFIED, lastModified);
    }

    public void setETag(String etag) {
        super.setAttribute(ATTRIBUTE_ETAG, etag);
    }

    public void setLocation(String location) {
        super.setAttribute(ATTRIBUTE_LOCATION, location);
    }
    
    public void setAttributes(Object attribute) {
    	super.setAttribute(ATTRIBUTE_ATTRIBUTE, attribute);
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
