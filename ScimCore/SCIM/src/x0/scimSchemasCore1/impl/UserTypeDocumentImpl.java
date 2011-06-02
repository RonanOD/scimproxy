/*
 * An XML document type.
 * Localname: userType
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.UserTypeDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one userType(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class UserTypeDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.UserTypeDocument
{
    
    public UserTypeDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName USERTYPE$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "userType");
    
    
    /**
     * Gets the "userType" element
     */
    public java.lang.String getUserType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERTYPE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "userType" element
     */
    public org.apache.xmlbeans.XmlString xgetUserType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERTYPE$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "userType" element
     */
    public void setUserType(java.lang.String userType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERTYPE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(USERTYPE$0);
            }
            target.setStringValue(userType);
        }
    }
    
    /**
     * Sets (as xml) the "userType" element
     */
    public void xsetUserType(org.apache.xmlbeans.XmlString userType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERTYPE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(USERTYPE$0);
            }
            target.set(userType);
        }
    }
}
