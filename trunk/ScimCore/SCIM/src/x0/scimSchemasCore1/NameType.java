/*
 * XML Type:  NameType
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.NameType
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1;


/**
 * An XML NameType(@urn:scim:schemas:core:1.0).
 *
 * This is a complex type.
 */
public interface NameType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(NameType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s49AFFD295516B9521E99EB5B1895FF95").resolveHandle("nametype590btype");
    
    /**
     * Gets the "formatted" element
     */
    java.lang.String getFormatted();
    
    /**
     * Gets (as xml) the "formatted" element
     */
    org.apache.xmlbeans.XmlString xgetFormatted();
    
    /**
     * Sets the "formatted" element
     */
    void setFormatted(java.lang.String formatted);
    
    /**
     * Sets (as xml) the "formatted" element
     */
    void xsetFormatted(org.apache.xmlbeans.XmlString formatted);
    
    /**
     * Gets the "familyName" element
     */
    java.lang.String getFamilyName();
    
    /**
     * Gets (as xml) the "familyName" element
     */
    org.apache.xmlbeans.XmlString xgetFamilyName();
    
    /**
     * Sets the "familyName" element
     */
    void setFamilyName(java.lang.String familyName);
    
    /**
     * Sets (as xml) the "familyName" element
     */
    void xsetFamilyName(org.apache.xmlbeans.XmlString familyName);
    
    /**
     * Gets the "givenName" element
     */
    java.lang.String getGivenName();
    
    /**
     * Gets (as xml) the "givenName" element
     */
    org.apache.xmlbeans.XmlString xgetGivenName();
    
    /**
     * Sets the "givenName" element
     */
    void setGivenName(java.lang.String givenName);
    
    /**
     * Sets (as xml) the "givenName" element
     */
    void xsetGivenName(org.apache.xmlbeans.XmlString givenName);
    
    /**
     * Gets the "middleName" element
     */
    java.lang.String getMiddleName();
    
    /**
     * Gets (as xml) the "middleName" element
     */
    org.apache.xmlbeans.XmlString xgetMiddleName();
    
    /**
     * Sets the "middleName" element
     */
    void setMiddleName(java.lang.String middleName);
    
    /**
     * Sets (as xml) the "middleName" element
     */
    void xsetMiddleName(org.apache.xmlbeans.XmlString middleName);
    
    /**
     * Gets the "honorificPrefix" element
     */
    java.lang.String getHonorificPrefix();
    
    /**
     * Gets (as xml) the "honorificPrefix" element
     */
    org.apache.xmlbeans.XmlString xgetHonorificPrefix();
    
    /**
     * Sets the "honorificPrefix" element
     */
    void setHonorificPrefix(java.lang.String honorificPrefix);
    
    /**
     * Sets (as xml) the "honorificPrefix" element
     */
    void xsetHonorificPrefix(org.apache.xmlbeans.XmlString honorificPrefix);
    
    /**
     * Gets the "honorificSuffix" element
     */
    java.lang.String getHonorificSuffix();
    
    /**
     * Gets (as xml) the "honorificSuffix" element
     */
    org.apache.xmlbeans.XmlString xgetHonorificSuffix();
    
    /**
     * Sets the "honorificSuffix" element
     */
    void setHonorificSuffix(java.lang.String honorificSuffix);
    
    /**
     * Sets (as xml) the "honorificSuffix" element
     */
    void xsetHonorificSuffix(org.apache.xmlbeans.XmlString honorificSuffix);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static x0.scimSchemasCore1.NameType newInstance() {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static x0.scimSchemasCore1.NameType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static x0.scimSchemasCore1.NameType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static x0.scimSchemasCore1.NameType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static x0.scimSchemasCore1.NameType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static x0.scimSchemasCore1.NameType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static x0.scimSchemasCore1.NameType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static x0.scimSchemasCore1.NameType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static x0.scimSchemasCore1.NameType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static x0.scimSchemasCore1.NameType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static x0.scimSchemasCore1.NameType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static x0.scimSchemasCore1.NameType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static x0.scimSchemasCore1.NameType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static x0.scimSchemasCore1.NameType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static x0.scimSchemasCore1.NameType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static x0.scimSchemasCore1.NameType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static x0.scimSchemasCore1.NameType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static x0.scimSchemasCore1.NameType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (x0.scimSchemasCore1.NameType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
