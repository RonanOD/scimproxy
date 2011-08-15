package info.simplecloud.core.types;

import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.handlers.StringHandler;

public class Name extends ComplexType {

    private String formatted;
    private String familyName;
    private String givenName;
    private String middleName;
    private String honorificPrefix;
    private String honorificSuffix;

    public Name(String formatted, String familyName, String givenName, String middleName, String honorificPrefix, String honorificSuffix) {
        this.setFormatted(formatted);
        this.setFamilyName(familyName);
        this.setGivenName(givenName);
        this.setMiddleName(middleName);
        this.setHonorificPrefix(honorificPrefix);
        this.setHonorificSuffix(honorificSuffix);
    }

    public Name() {

    }

    @Attribute(name = "formatted", handler = StringHandler.class)
    public String getFormatted() {
        return this.formatted;
    }

    @Attribute(name = "familyName", handler = StringHandler.class)
    public String getFamilyName() {
        return this.familyName;
    }

    @Attribute(name = "givenName", handler = StringHandler.class)
    public String getGivenName() {
        return this.givenName;
    }

    @Attribute(name = "middleName", handler = StringHandler.class)
    public String getMiddleName() {
        return this.middleName;
    }

    @Attribute(name = "honorificPrefix", handler = StringHandler.class)
    public String getHonorificPrefix() {
        return this.honorificPrefix;
    }

    @Attribute(name = "honorificSuffix", handler = StringHandler.class)
    public String getHonorificSuffix() {
        return this.honorificSuffix;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setHonorificPrefix(String honorificPrefix) {
        this.honorificPrefix = honorificPrefix;
    }

    public void setHonorificSuffix(String honorificSuffix) {
        this.honorificSuffix = honorificSuffix;
    }

    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj) {
            return true;
        }

        if (!(otherObj instanceof Name)) {
            return false;
        }
        Name otherName = (Name) otherObj;

        return super.equals(otherName);
    }
}
