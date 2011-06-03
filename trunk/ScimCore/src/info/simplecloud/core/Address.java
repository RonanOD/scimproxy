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
}
