package info.simplecloud.core.types;

import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.handlers.DateHandler;
import info.simplecloud.core.handlers.ListHandler;
import info.simplecloud.core.handlers.StringHandler;

import java.util.Calendar;
import java.util.List;

public class Meta extends ComplexType {

    private Calendar     created;
    private Calendar     lastModified;
    private String       version;
    private String       location;
    private List<String> attributes;

    @Attribute(name = "created", handler = DateHandler.class)
    public Calendar getCreated() {
        return this.created;
    }

    @Attribute(name = "lastModified", handler = DateHandler.class)
    public Calendar getLastModified() {
        return this.lastModified;
    }

    @Attribute(name = "version", handler = StringHandler.class)
    public String getVersion() {
        return this.version;
    }

    @Attribute(name = "location", handler = StringHandler.class)
    public String getLocation() {
        return this.location;
    }

    @Attribute(name = "attributes", handler = ListHandler.class)
    public List<String> getAttributes() {
        return this.attributes;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public void setLastModified(Calendar lastModified) {
        this.lastModified = lastModified;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
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
