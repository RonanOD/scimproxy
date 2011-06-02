/*
 * XML Type:  ScimUserType
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.ScimUserType
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1;


/**
 * An XML ScimUserType(@urn:scim:schemas:core:1.0).
 *
 * This is a complex type.
 */
public interface ScimUserType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ScimUserType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s49AFFD295516B9521E99EB5B1895FF95").resolveHandle("scimusertype345ftype");
    
    /**
     * Gets the "id" element
     */
    java.lang.String getId();
    
    /**
     * Gets (as xml) the "id" element
     */
    x0.scimSchemasCore1.NonEmptyString xgetId();
    
    /**
     * Sets the "id" element
     */
    void setId(java.lang.String id);
    
    /**
     * Sets (as xml) the "id" element
     */
    void xsetId(x0.scimSchemasCore1.NonEmptyString id);
    
    /**
     * Gets the "externalId" element
     */
    java.lang.String getExternalId();
    
    /**
     * Gets (as xml) the "externalId" element
     */
    x0.scimSchemasCore1.NonEmptyString xgetExternalId();
    
    /**
     * True if has "externalId" element
     */
    boolean isSetExternalId();
    
    /**
     * Sets the "externalId" element
     */
    void setExternalId(java.lang.String externalId);
    
    /**
     * Sets (as xml) the "externalId" element
     */
    void xsetExternalId(x0.scimSchemasCore1.NonEmptyString externalId);
    
    /**
     * Unsets the "externalId" element
     */
    void unsetExternalId();
    
    /**
     * Gets the "userName" element
     */
    java.lang.String getUserName();
    
    /**
     * Gets (as xml) the "userName" element
     */
    x0.scimSchemasCore1.NonEmptyString xgetUserName();
    
    /**
     * Sets the "userName" element
     */
    void setUserName(java.lang.String userName);
    
    /**
     * Sets (as xml) the "userName" element
     */
    void xsetUserName(x0.scimSchemasCore1.NonEmptyString userName);
    
    /**
     * Gets the "name" element
     */
    x0.scimSchemasCore1.NameType getName();
    
    /**
     * True if has "name" element
     */
    boolean isSetName();
    
    /**
     * Sets the "name" element
     */
    void setName(x0.scimSchemasCore1.NameType name);
    
    /**
     * Appends and returns a new empty "name" element
     */
    x0.scimSchemasCore1.NameType addNewName();
    
    /**
     * Unsets the "name" element
     */
    void unsetName();
    
    /**
     * Gets the "displayName" element
     */
    java.lang.String getDisplayName();
    
    /**
     * Gets (as xml) the "displayName" element
     */
    x0.scimSchemasCore1.NonEmptyString xgetDisplayName();
    
    /**
     * True if has "displayName" element
     */
    boolean isSetDisplayName();
    
    /**
     * Sets the "displayName" element
     */
    void setDisplayName(java.lang.String displayName);
    
    /**
     * Sets (as xml) the "displayName" element
     */
    void xsetDisplayName(x0.scimSchemasCore1.NonEmptyString displayName);
    
    /**
     * Unsets the "displayName" element
     */
    void unsetDisplayName();
    
    /**
     * Gets the "nickName" element
     */
    java.lang.String getNickName();
    
    /**
     * Gets (as xml) the "nickName" element
     */
    org.apache.xmlbeans.XmlString xgetNickName();
    
    /**
     * True if has "nickName" element
     */
    boolean isSetNickName();
    
    /**
     * Sets the "nickName" element
     */
    void setNickName(java.lang.String nickName);
    
    /**
     * Sets (as xml) the "nickName" element
     */
    void xsetNickName(org.apache.xmlbeans.XmlString nickName);
    
    /**
     * Unsets the "nickName" element
     */
    void unsetNickName();
    
    /**
     * Gets the "profileUrl" element
     */
    java.lang.String getProfileUrl();
    
    /**
     * Gets (as xml) the "profileUrl" element
     */
    org.apache.xmlbeans.XmlString xgetProfileUrl();
    
    /**
     * True if has "profileUrl" element
     */
    boolean isSetProfileUrl();
    
    /**
     * Sets the "profileUrl" element
     */
    void setProfileUrl(java.lang.String profileUrl);
    
    /**
     * Sets (as xml) the "profileUrl" element
     */
    void xsetProfileUrl(org.apache.xmlbeans.XmlString profileUrl);
    
    /**
     * Unsets the "profileUrl" element
     */
    void unsetProfileUrl();
    
    /**
     * Gets the "employeeNumber" element
     */
    java.lang.String getEmployeeNumber();
    
    /**
     * Gets (as xml) the "employeeNumber" element
     */
    x0.scimSchemasCore1.AlphanumericString xgetEmployeeNumber();
    
    /**
     * True if has "employeeNumber" element
     */
    boolean isSetEmployeeNumber();
    
    /**
     * Sets the "employeeNumber" element
     */
    void setEmployeeNumber(java.lang.String employeeNumber);
    
    /**
     * Sets (as xml) the "employeeNumber" element
     */
    void xsetEmployeeNumber(x0.scimSchemasCore1.AlphanumericString employeeNumber);
    
    /**
     * Unsets the "employeeNumber" element
     */
    void unsetEmployeeNumber();
    
    /**
     * Gets the "userType" element
     */
    java.lang.String getUserType();
    
    /**
     * Gets (as xml) the "userType" element
     */
    org.apache.xmlbeans.XmlString xgetUserType();
    
    /**
     * True if has "userType" element
     */
    boolean isSetUserType();
    
    /**
     * Sets the "userType" element
     */
    void setUserType(java.lang.String userType);
    
    /**
     * Sets (as xml) the "userType" element
     */
    void xsetUserType(org.apache.xmlbeans.XmlString userType);
    
    /**
     * Unsets the "userType" element
     */
    void unsetUserType();
    
    /**
     * Gets the "title" element
     */
    java.lang.String getTitle();
    
    /**
     * Gets (as xml) the "title" element
     */
    org.apache.xmlbeans.XmlString xgetTitle();
    
    /**
     * True if has "title" element
     */
    boolean isSetTitle();
    
    /**
     * Sets the "title" element
     */
    void setTitle(java.lang.String title);
    
    /**
     * Sets (as xml) the "title" element
     */
    void xsetTitle(org.apache.xmlbeans.XmlString title);
    
    /**
     * Unsets the "title" element
     */
    void unsetTitle();
    
    /**
     * Gets the "manager" element
     */
    java.lang.String getManager();
    
    /**
     * Gets (as xml) the "manager" element
     */
    org.apache.xmlbeans.XmlString xgetManager();
    
    /**
     * True if has "manager" element
     */
    boolean isSetManager();
    
    /**
     * Sets the "manager" element
     */
    void setManager(java.lang.String manager);
    
    /**
     * Sets (as xml) the "manager" element
     */
    void xsetManager(org.apache.xmlbeans.XmlString manager);
    
    /**
     * Unsets the "manager" element
     */
    void unsetManager();
    
    /**
     * Gets the "preferredLanguage" element
     */
    java.lang.String getPreferredLanguage();
    
    /**
     * Gets (as xml) the "preferredLanguage" element
     */
    org.apache.xmlbeans.XmlString xgetPreferredLanguage();
    
    /**
     * True if has "preferredLanguage" element
     */
    boolean isSetPreferredLanguage();
    
    /**
     * Sets the "preferredLanguage" element
     */
    void setPreferredLanguage(java.lang.String preferredLanguage);
    
    /**
     * Sets (as xml) the "preferredLanguage" element
     */
    void xsetPreferredLanguage(org.apache.xmlbeans.XmlString preferredLanguage);
    
    /**
     * Unsets the "preferredLanguage" element
     */
    void unsetPreferredLanguage();
    
    /**
     * Gets the "locale" element
     */
    java.lang.String getLocale();
    
    /**
     * Gets (as xml) the "locale" element
     */
    org.apache.xmlbeans.XmlString xgetLocale();
    
    /**
     * True if has "locale" element
     */
    boolean isSetLocale();
    
    /**
     * Sets the "locale" element
     */
    void setLocale(java.lang.String locale);
    
    /**
     * Sets (as xml) the "locale" element
     */
    void xsetLocale(org.apache.xmlbeans.XmlString locale);
    
    /**
     * Unsets the "locale" element
     */
    void unsetLocale();
    
    /**
     * Gets the "utcOffset" element
     */
    java.util.Calendar getUtcOffset();
    
    /**
     * Gets (as xml) the "utcOffset" element
     */
    org.apache.xmlbeans.XmlDateTime xgetUtcOffset();
    
    /**
     * True if has "utcOffset" element
     */
    boolean isSetUtcOffset();
    
    /**
     * Sets the "utcOffset" element
     */
    void setUtcOffset(java.util.Calendar utcOffset);
    
    /**
     * Sets (as xml) the "utcOffset" element
     */
    void xsetUtcOffset(org.apache.xmlbeans.XmlDateTime utcOffset);
    
    /**
     * Unsets the "utcOffset" element
     */
    void unsetUtcOffset();
    
    /**
     * Gets the "costCenter" element
     */
    java.lang.String getCostCenter();
    
    /**
     * Gets (as xml) the "costCenter" element
     */
    org.apache.xmlbeans.XmlString xgetCostCenter();
    
    /**
     * True if has "costCenter" element
     */
    boolean isSetCostCenter();
    
    /**
     * Sets the "costCenter" element
     */
    void setCostCenter(java.lang.String costCenter);
    
    /**
     * Sets (as xml) the "costCenter" element
     */
    void xsetCostCenter(org.apache.xmlbeans.XmlString costCenter);
    
    /**
     * Unsets the "costCenter" element
     */
    void unsetCostCenter();
    
    /**
     * Gets the "organization" element
     */
    java.lang.String getOrganization();
    
    /**
     * Gets (as xml) the "organization" element
     */
    org.apache.xmlbeans.XmlString xgetOrganization();
    
    /**
     * True if has "organization" element
     */
    boolean isSetOrganization();
    
    /**
     * Sets the "organization" element
     */
    void setOrganization(java.lang.String organization);
    
    /**
     * Sets (as xml) the "organization" element
     */
    void xsetOrganization(org.apache.xmlbeans.XmlString organization);
    
    /**
     * Unsets the "organization" element
     */
    void unsetOrganization();
    
    /**
     * Gets the "division" element
     */
    java.lang.String getDivision();
    
    /**
     * Gets (as xml) the "division" element
     */
    org.apache.xmlbeans.XmlString xgetDivision();
    
    /**
     * True if has "division" element
     */
    boolean isSetDivision();
    
    /**
     * Sets the "division" element
     */
    void setDivision(java.lang.String division);
    
    /**
     * Sets (as xml) the "division" element
     */
    void xsetDivision(org.apache.xmlbeans.XmlString division);
    
    /**
     * Unsets the "division" element
     */
    void unsetDivision();
    
    /**
     * Gets the "department" element
     */
    java.lang.String getDepartment();
    
    /**
     * Gets (as xml) the "department" element
     */
    org.apache.xmlbeans.XmlString xgetDepartment();
    
    /**
     * True if has "department" element
     */
    boolean isSetDepartment();
    
    /**
     * Sets the "department" element
     */
    void setDepartment(java.lang.String department);
    
    /**
     * Sets (as xml) the "department" element
     */
    void xsetDepartment(org.apache.xmlbeans.XmlString department);
    
    /**
     * Unsets the "department" element
     */
    void unsetDepartment();
    
    /**
     * Gets the "meta" element
     */
    x0.scimSchemasCore1.MetaType getMeta();
    
    /**
     * True if has "meta" element
     */
    boolean isSetMeta();
    
    /**
     * Sets the "meta" element
     */
    void setMeta(x0.scimSchemasCore1.MetaType meta);
    
    /**
     * Appends and returns a new empty "meta" element
     */
    x0.scimSchemasCore1.MetaType addNewMeta();
    
    /**
     * Unsets the "meta" element
     */
    void unsetMeta();
    
    /**
     * Gets the "emails" element
     */
    x0.scimSchemasCore1.EmailsType getEmails();
    
    /**
     * True if has "emails" element
     */
    boolean isSetEmails();
    
    /**
     * Sets the "emails" element
     */
    void setEmails(x0.scimSchemasCore1.EmailsType emails);
    
    /**
     * Appends and returns a new empty "emails" element
     */
    x0.scimSchemasCore1.EmailsType addNewEmails();
    
    /**
     * Unsets the "emails" element
     */
    void unsetEmails();
    
    /**
     * Gets the "phoneNumbers" element
     */
    x0.scimSchemasCore1.PhoneNumbersType getPhoneNumbers();
    
    /**
     * True if has "phoneNumbers" element
     */
    boolean isSetPhoneNumbers();
    
    /**
     * Sets the "phoneNumbers" element
     */
    void setPhoneNumbers(x0.scimSchemasCore1.PhoneNumbersType phoneNumbers);
    
    /**
     * Appends and returns a new empty "phoneNumbers" element
     */
    x0.scimSchemasCore1.PhoneNumbersType addNewPhoneNumbers();
    
    /**
     * Unsets the "phoneNumbers" element
     */
    void unsetPhoneNumbers();
    
    /**
     * Gets the "ims" element
     */
    x0.scimSchemasCore1.ImsType getIms();
    
    /**
     * True if has "ims" element
     */
    boolean isSetIms();
    
    /**
     * Sets the "ims" element
     */
    void setIms(x0.scimSchemasCore1.ImsType ims);
    
    /**
     * Appends and returns a new empty "ims" element
     */
    x0.scimSchemasCore1.ImsType addNewIms();
    
    /**
     * Unsets the "ims" element
     */
    void unsetIms();
    
    /**
     * Gets the "photos" element
     */
    x0.scimSchemasCore1.PhotosType getPhotos();
    
    /**
     * True if has "photos" element
     */
    boolean isSetPhotos();
    
    /**
     * Sets the "photos" element
     */
    void setPhotos(x0.scimSchemasCore1.PhotosType photos);
    
    /**
     * Appends and returns a new empty "photos" element
     */
    x0.scimSchemasCore1.PhotosType addNewPhotos();
    
    /**
     * Unsets the "photos" element
     */
    void unsetPhotos();
    
    /**
     * Gets the "groups" element
     */
    x0.scimSchemasCore1.GroupsType getGroups();
    
    /**
     * True if has "groups" element
     */
    boolean isSetGroups();
    
    /**
     * Sets the "groups" element
     */
    void setGroups(x0.scimSchemasCore1.GroupsType groups);
    
    /**
     * Appends and returns a new empty "groups" element
     */
    x0.scimSchemasCore1.GroupsType addNewGroups();
    
    /**
     * Unsets the "groups" element
     */
    void unsetGroups();
    
    /**
     * Gets the "addresses" element
     */
    x0.scimSchemasCore1.AddressesType getAddresses();
    
    /**
     * True if has "addresses" element
     */
    boolean isSetAddresses();
    
    /**
     * Sets the "addresses" element
     */
    void setAddresses(x0.scimSchemasCore1.AddressesType addresses);
    
    /**
     * Appends and returns a new empty "addresses" element
     */
    x0.scimSchemasCore1.AddressesType addNewAddresses();
    
    /**
     * Unsets the "addresses" element
     */
    void unsetAddresses();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static x0.scimSchemasCore1.ScimUserType newInstance() {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static x0.scimSchemasCore1.ScimUserType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static x0.scimSchemasCore1.ScimUserType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static x0.scimSchemasCore1.ScimUserType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static x0.scimSchemasCore1.ScimUserType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static x0.scimSchemasCore1.ScimUserType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static x0.scimSchemasCore1.ScimUserType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static x0.scimSchemasCore1.ScimUserType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static x0.scimSchemasCore1.ScimUserType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static x0.scimSchemasCore1.ScimUserType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static x0.scimSchemasCore1.ScimUserType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static x0.scimSchemasCore1.ScimUserType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static x0.scimSchemasCore1.ScimUserType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static x0.scimSchemasCore1.ScimUserType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static x0.scimSchemasCore1.ScimUserType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static x0.scimSchemasCore1.ScimUserType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static x0.scimSchemasCore1.ScimUserType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static x0.scimSchemasCore1.ScimUserType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (x0.scimSchemasCore1.ScimUserType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
