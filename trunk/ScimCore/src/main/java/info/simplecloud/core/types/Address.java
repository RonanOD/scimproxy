package info.simplecloud.core.types;

import info.simplecloud.core.Attribute;
import info.simplecloud.core.coding.handlers.StringHandler;

public class Address extends ComplexType {
    public static final String ATTRIBUTE_FORMATTED      = "formatted";
    public static final String ATTRIBUTE_STREET_ADDRESS = "streetAddress";
    public static final String ATTRIBUTE_LOCALITY       = "locality";
    public static final String ATTRIBUTE_REGION         = "region";
    public static final String ATTRIBUTE_POSTAL_CODE    = "postalCode";
    public static final String ATTRIBUTE_CONTRY         = "country";

    @Attribute(schemaName = "formatted", codingHandler = StringHandler.class)
    public String getFormatted() {
        return super.getAttributeString(ATTRIBUTE_FORMATTED);
    }

    @Attribute(schemaName = "streetAddress", codingHandler = StringHandler.class)
    public String getStreetAddress() {
        return super.getAttributeString(ATTRIBUTE_STREET_ADDRESS);
    }

    @Attribute(schemaName = "locality", codingHandler = StringHandler.class)
    public String getLocality() {
        return super.getAttributeString(ATTRIBUTE_LOCALITY);
    }

    @Attribute(schemaName = "region", codingHandler = StringHandler.class)
    public String getRegion() {
        return super.getAttributeString(ATTRIBUTE_REGION);
    }

    @Attribute(schemaName = "postalCode", codingHandler = StringHandler.class)
    public String getPostalCode() {
        return super.getAttributeString(ATTRIBUTE_POSTAL_CODE);
    }

    @Attribute(schemaName = "country", codingHandler = StringHandler.class)
    public String getCountry() {
        return super.getAttributeString(ATTRIBUTE_CONTRY);
    }

    public void setFormatted(String formatted) {
        super.setAttribute(ATTRIBUTE_FORMATTED, formatted);
    }

    public void setStreetAddress(String streetAddress) {
        super.setAttribute(ATTRIBUTE_STREET_ADDRESS, streetAddress);
    }

    public void setLocality(String locality) {
        super.setAttribute(ATTRIBUTE_LOCALITY, locality);
    }

    public void setRegion(String region) {
        super.setAttribute(ATTRIBUTE_REGION, region);
    }

    public void setPostalCode(String postalCode) {
        super.setAttribute(ATTRIBUTE_POSTAL_CODE, postalCode);
    }

    public void setCountry(String country) {
        super.setAttribute(ATTRIBUTE_CONTRY, country);
    }

    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj) {
            return true;
        }
        if (!(otherObj instanceof Address)) {
            return false;
        }
        Address otherAddress = (Address) otherObj;

        // TODO fix this
        return super.equals(otherAddress, null);
    }

    @Override
    public String toString() {
        // TODO fix this
        return super.toString(null);
    }
}
