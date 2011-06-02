/*
 * XML Type:  AddressType
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.AddressType
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1;


/**
 * An XML AddressType(@urn:scim:schemas:core:1.0).
 *
 * This is a complex type.
 */
public interface AddressType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AddressType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s49AFFD295516B9521E99EB5B1895FF95").resolveHandle("addresstype3382type");
    
    /**
     * Gets the "formatted" element
     */
    java.lang.String getFormatted();
    
    /**
     * Gets (as xml) the "formatted" element
     */
    org.apache.xmlbeans.XmlString xgetFormatted();
    
    /**
     * True if has "formatted" element
     */
    boolean isSetFormatted();
    
    /**
     * Sets the "formatted" element
     */
    void setFormatted(java.lang.String formatted);
    
    /**
     * Sets (as xml) the "formatted" element
     */
    void xsetFormatted(org.apache.xmlbeans.XmlString formatted);
    
    /**
     * Unsets the "formatted" element
     */
    void unsetFormatted();
    
    /**
     * Gets the "streetAddress" element
     */
    java.lang.String getStreetAddress();
    
    /**
     * Gets (as xml) the "streetAddress" element
     */
    org.apache.xmlbeans.XmlString xgetStreetAddress();
    
    /**
     * True if has "streetAddress" element
     */
    boolean isSetStreetAddress();
    
    /**
     * Sets the "streetAddress" element
     */
    void setStreetAddress(java.lang.String streetAddress);
    
    /**
     * Sets (as xml) the "streetAddress" element
     */
    void xsetStreetAddress(org.apache.xmlbeans.XmlString streetAddress);
    
    /**
     * Unsets the "streetAddress" element
     */
    void unsetStreetAddress();
    
    /**
     * Gets the "locality" element
     */
    java.lang.String getLocality();
    
    /**
     * Gets (as xml) the "locality" element
     */
    org.apache.xmlbeans.XmlString xgetLocality();
    
    /**
     * True if has "locality" element
     */
    boolean isSetLocality();
    
    /**
     * Sets the "locality" element
     */
    void setLocality(java.lang.String locality);
    
    /**
     * Sets (as xml) the "locality" element
     */
    void xsetLocality(org.apache.xmlbeans.XmlString locality);
    
    /**
     * Unsets the "locality" element
     */
    void unsetLocality();
    
    /**
     * Gets the "region" element
     */
    java.lang.String getRegion();
    
    /**
     * Gets (as xml) the "region" element
     */
    org.apache.xmlbeans.XmlString xgetRegion();
    
    /**
     * True if has "region" element
     */
    boolean isSetRegion();
    
    /**
     * Sets the "region" element
     */
    void setRegion(java.lang.String region);
    
    /**
     * Sets (as xml) the "region" element
     */
    void xsetRegion(org.apache.xmlbeans.XmlString region);
    
    /**
     * Unsets the "region" element
     */
    void unsetRegion();
    
    /**
     * Gets the "postalCode" element
     */
    java.lang.String getPostalCode();
    
    /**
     * Gets (as xml) the "postalCode" element
     */
    org.apache.xmlbeans.XmlString xgetPostalCode();
    
    /**
     * True if has "postalCode" element
     */
    boolean isSetPostalCode();
    
    /**
     * Sets the "postalCode" element
     */
    void setPostalCode(java.lang.String postalCode);
    
    /**
     * Sets (as xml) the "postalCode" element
     */
    void xsetPostalCode(org.apache.xmlbeans.XmlString postalCode);
    
    /**
     * Unsets the "postalCode" element
     */
    void unsetPostalCode();
    
    /**
     * Gets the "country" element
     */
    java.lang.String getCountry();
    
    /**
     * Gets (as xml) the "country" element
     */
    org.apache.xmlbeans.XmlString xgetCountry();
    
    /**
     * True if has "country" element
     */
    boolean isSetCountry();
    
    /**
     * Sets the "country" element
     */
    void setCountry(java.lang.String country);
    
    /**
     * Sets (as xml) the "country" element
     */
    void xsetCountry(org.apache.xmlbeans.XmlString country);
    
    /**
     * Unsets the "country" element
     */
    void unsetCountry();
    
    /**
     * Gets the "type" attribute
     */
    java.lang.String getType();
    
    /**
     * Gets (as xml) the "type" attribute
     */
    org.apache.xmlbeans.XmlString xgetType();
    
    /**
     * True if has "type" attribute
     */
    boolean isSetType();
    
    /**
     * Sets the "type" attribute
     */
    void setType(java.lang.String type);
    
    /**
     * Sets (as xml) the "type" attribute
     */
    void xsetType(org.apache.xmlbeans.XmlString type);
    
    /**
     * Unsets the "type" attribute
     */
    void unsetType();
    
    /**
     * Gets the "primary" attribute
     */
    boolean getPrimary();
    
    /**
     * Gets (as xml) the "primary" attribute
     */
    org.apache.xmlbeans.XmlBoolean xgetPrimary();
    
    /**
     * True if has "primary" attribute
     */
    boolean isSetPrimary();
    
    /**
     * Sets the "primary" attribute
     */
    void setPrimary(boolean primary);
    
    /**
     * Sets (as xml) the "primary" attribute
     */
    void xsetPrimary(org.apache.xmlbeans.XmlBoolean primary);
    
    /**
     * Unsets the "primary" attribute
     */
    void unsetPrimary();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static x0.scimSchemasCore1.AddressType newInstance() {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static x0.scimSchemasCore1.AddressType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static x0.scimSchemasCore1.AddressType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static x0.scimSchemasCore1.AddressType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static x0.scimSchemasCore1.AddressType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static x0.scimSchemasCore1.AddressType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static x0.scimSchemasCore1.AddressType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static x0.scimSchemasCore1.AddressType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static x0.scimSchemasCore1.AddressType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static x0.scimSchemasCore1.AddressType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static x0.scimSchemasCore1.AddressType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static x0.scimSchemasCore1.AddressType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static x0.scimSchemasCore1.AddressType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static x0.scimSchemasCore1.AddressType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static x0.scimSchemasCore1.AddressType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static x0.scimSchemasCore1.AddressType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static x0.scimSchemasCore1.AddressType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static x0.scimSchemasCore1.AddressType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (x0.scimSchemasCore1.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
