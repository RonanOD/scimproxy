package info.simplecloud.core;

public class Name extends ComplexType {
    public static final String  ATTRIBUTE_FORMATTED        = "formatted";
    public static final String  ATTRIBUTE_FAMILY_NAME      = "familyName";
    public static final String  ATTRIBUTE_GIVEN_NAME       = "givenName";
    public static final String  ATTRIBUTE_MIDDLE_NAME      = "middleName";
    public static final String  ATTRIBUTE_HONORIFIC_PREFIX = "honorificPrefix";
    public static final String  ATTRIBUTE_HONORIFIC_SUFFIX = "honorificSuffix";
    private static final String simple[]                   = { ATTRIBUTE_FORMATTED, ATTRIBUTE_FAMILY_NAME, ATTRIBUTE_GIVEN_NAME,
            ATTRIBUTE_MIDDLE_NAME, ATTRIBUTE_HONORIFIC_PREFIX, ATTRIBUTE_HONORIFIC_SUFFIX };

    public Name(String formatted, String familyName, String givenName, String middleName, String honorificPrefix, String honorificSuffix) {
        this.setFormatted(formatted);
        this.setFamilyName(familyName);
        this.setGivenName(givenName);
        this.setMiddleName(middleName);
        this.setHonorificPrefix(honorificPrefix);
        this.setHonorificSuffix(honorificSuffix);
    }

    public Name() {
        // Need this too
    }

    @Override
    public String[] getSimple() {
        return simple;
    }

    @Override
    public String[] getPlural() {
        return null;
    }

    @Override
    public String[] getComplex() {
        return null;
    }

    public String getFormatted() {
        return super.getAttributeString(ATTRIBUTE_FORMATTED);
    }

    public String getFamilyName() {
        return super.getAttributeString(ATTRIBUTE_FAMILY_NAME);
    }

    public String getGivenName() {
        return super.getAttributeString(ATTRIBUTE_GIVEN_NAME);
    }

    public String getMiddleName() {
        return super.getAttributeString(ATTRIBUTE_MIDDLE_NAME);
    }

    public String getHonorificPrefix() {
        return super.getAttributeString(ATTRIBUTE_HONORIFIC_PREFIX);
    }

    public String getHonorificSuffix() {
        return super.getAttributeString(ATTRIBUTE_HONORIFIC_SUFFIX);
    }

    public void setFormatted(String formatted) {
        super.setAttribute(ATTRIBUTE_FORMATTED, formatted);
    }

    public void setFamilyName(String familyName) {
        super.setAttribute(ATTRIBUTE_FAMILY_NAME, familyName);
    }

    public void setGivenName(String givenName) {
        super.setAttribute(ATTRIBUTE_GIVEN_NAME, givenName);
    }

    public void setMiddleName(String middleName) {
        super.setAttribute(ATTRIBUTE_MIDDLE_NAME, middleName);
    }

    public void setHonorificPrefix(String honorificPrefix) {
        super.setAttribute(ATTRIBUTE_HONORIFIC_PREFIX, honorificPrefix);
    }

    public void setHonorificSuffix(String honorificSuffix) {
        super.setAttribute(ATTRIBUTE_HONORIFIC_SUFFIX, honorificSuffix);
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

        return super.equals(otherName, simple);
    }

    @Override
    public String toString() {
        return super.toString(simple);
    }
}
