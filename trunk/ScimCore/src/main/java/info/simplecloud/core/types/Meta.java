package info.simplecloud.core.types;

import info.simplecloud.core.Attribute;
import info.simplecloud.core.coding.handlers.CalendarHandler;
import info.simplecloud.core.coding.handlers.StringHandler;
import info.simplecloud.core.coding.handlers.StringListHandler;

import java.util.Calendar;
import java.util.List;

public class Meta extends ComplexType {
    public static final String ATTRIBUTE_CREATED       = "created";
    public static final String ATTRIBUTE_LAST_MODIFIED = "lastModified";
    public static final String ATTRIBUTE_VERSION       = "version";
    public static final String ATTRIBUTE_LOCATION      = "location";
    public static final String ATTRIBUTE_ATTRIBUTES    = "attributes";

    @Attribute(schemaName = "version", codingHandler = CalendarHandler.class)
    public Calendar getCreated() {
        return super.getAttributeCalendar(ATTRIBUTE_CREATED);
    }

    @Attribute(schemaName = "version", codingHandler = CalendarHandler.class)
    public Calendar getLastModified() {
        return super.getAttributeCalendar(ATTRIBUTE_LAST_MODIFIED);
    }

    @Attribute(schemaName = "version", codingHandler = StringHandler.class)
    public String getVersion() {
        return super.getAttributeString(ATTRIBUTE_VERSION);
    }

    @Attribute(schemaName = "location", codingHandler = StringHandler.class)
    public String getLocation() {
        return super.getAttributeString(ATTRIBUTE_LOCATION);
    }

    @Attribute(schemaName = "attributes", codingHandler = StringListHandler.class)
    public List<String> getAttributes() {
        Object attributeList = super.getAttribute(ATTRIBUTE_ATTRIBUTES);
        return attributeList == null ? null : (List<String>) attributeList;
    }

    public void setCreated(Calendar created) {
        super.setAttribute(ATTRIBUTE_CREATED, created);
    }

    public void setLastModified(Calendar lastModified) {
        super.setAttribute(ATTRIBUTE_LAST_MODIFIED, lastModified);
    }

    public void setVersion(String version) {
        super.setAttribute(ATTRIBUTE_VERSION, version);
    }

    public void setLocation(String location) {
        super.setAttribute(ATTRIBUTE_LOCATION, location);
    }

    public void setAttributes(List<String> attributes) {
        super.setAttribute(ATTRIBUTE_ATTRIBUTES, attributes);
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

        return super.equals(otherMeta);
    }
}
