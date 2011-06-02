/*
 * An XML document type.
 * Localname: profileUrl
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.ProfileUrlDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one profileUrl(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class ProfileUrlDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.ProfileUrlDocument
{
    
    public ProfileUrlDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PROFILEURL$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "profileUrl");
    
    
    /**
     * Gets the "profileUrl" element
     */
    public java.lang.String getProfileUrl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROFILEURL$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "profileUrl" element
     */
    public org.apache.xmlbeans.XmlString xgetProfileUrl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROFILEURL$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "profileUrl" element
     */
    public void setProfileUrl(java.lang.String profileUrl)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROFILEURL$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROFILEURL$0);
            }
            target.setStringValue(profileUrl);
        }
    }
    
    /**
     * Sets (as xml) the "profileUrl" element
     */
    public void xsetProfileUrl(org.apache.xmlbeans.XmlString profileUrl)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROFILEURL$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PROFILEURL$0);
            }
            target.set(profileUrl);
        }
    }
}
