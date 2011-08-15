package info.simplecloud.core.types;

import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.handlers.StringHandler;

public class Address extends ComplexType {

    private String formatted;
    private String streetAddress;
    private String locality;
    private String region;
    private String postalCode;
    private String country;

    public Address(String formatted, String streetAddress, String locality, String region, String postalCode, String country) {
        this.setFormatted(formatted);
        this.setStreetAddress(streetAddress);
        this.setLocality(locality);
        this.setRegion(region);
        this.setPostalCode(postalCode);
        this.setCountry(country);
    }

    public Address() {

    }

    @Attribute(name = "formatted", handler = StringHandler.class)
    public String getFormatted() {
        return this.formatted;
    }

    @Attribute(name = "streetAddress", handler = StringHandler.class)
    public String getStreetAddress() {
        return this.streetAddress;
    }

    @Attribute(name = "locality", handler = StringHandler.class)
    public String getLocality() {
        return this.locality;
    }

    @Attribute(name = "region", handler = StringHandler.class)
    public String getRegion() {
        return this.region;
    }

    @Attribute(name = "postalCode", handler = StringHandler.class)
    public String getPostalCode() {
        return this.postalCode;
    }

    @Attribute(name = "country", handler = StringHandler.class)
    public String getCountry() {
        return this.country;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCountry(String country) {
        this.country = country;
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

        return super.equals(otherAddress);
    }
}
