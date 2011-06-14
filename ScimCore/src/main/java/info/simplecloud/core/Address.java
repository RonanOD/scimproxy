package info.simplecloud.core;

public class Address extends ComplexType {
    public static final String   ATTRIBUTE_FORMATTED      = "formatted";
    public static final String   ATTRIBUTE_STREET_ADDRESS = "streetAddress";
    public static final String   ATTRIBUTE_LOCALITY       = "locality";
    public static final String   ATTRIBUTE_REGION         = "region";
    public static final String   ATTRIBUTE_POSTAL_CODE    = "postalCode";
    public static final String   ATTRIBUTE_CONTRY         = "country";
    public static final String[] simple                   = { ATTRIBUTE_FORMATTED, ATTRIBUTE_STREET_ADDRESS, ATTRIBUTE_LOCALITY,
            ATTRIBUTE_REGION, ATTRIBUTE_POSTAL_CODE, ATTRIBUTE_CONTRY };

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

    public String getStreetAddress() {
        return super.getAttributeString(ATTRIBUTE_STREET_ADDRESS);
    }

    public String getLocality() {
        return super.getAttributeString(ATTRIBUTE_LOCALITY);
    }

    public String getRegion() {
        return super.getAttributeString(ATTRIBUTE_REGION);
    }

    public String getPostalCode() {
        return super.getAttributeString(ATTRIBUTE_POSTAL_CODE);
    }

    public String getHonorificSuffix() {
        return super.getAttributeString(ATTRIBUTE_CONTRY);
    }

    public void setFormatted(String formatted) {
        super.setAttribute(ATTRIBUTE_FORMATTED, formatted);
    }

    public void setStreetAddress(String streetAddress) {
        super.setAttribute(ATTRIBUTE_STREET_ADDRESS, streetAddress);
    }

    public void setlocality(String locality) {
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

        return super.equals(otherAddress, simple);
    }

    @Override
    public String toString() {
        return super.toString(simple);
    }
}
