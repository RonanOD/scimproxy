package info.simplecloud.core.extensions.types;

import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.annotations.Complex;
import info.simplecloud.core.handlers.StringHandler;
import info.simplecloud.core.types.ComplexType;

@Complex(xmlType = x0.scimSchemasExtensionEnterprise1.Manager.class)
public class Manager extends ComplexType {

    private String managerId;
    private String displayName;

    public Manager() {

    }

    public Manager(String managerId, String displayName) {
        this.managerId = managerId;
        this.displayName = displayName;
    }

    @Attribute(name = "managerId", handler = StringHandler.class)
    public String getManagerId() {
        return this.managerId;
    }

    @Attribute(name = "displayName", handler = StringHandler.class)
    public String getDisplayName() {
        return this.displayName;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
