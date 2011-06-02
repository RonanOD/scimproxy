/*
 * An XML document type.
 * Localname: userName
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.UserNameDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one userName(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class UserNameDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.UserNameDocument
{
    
    public UserNameDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName USERNAME$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "userName");
    
    
    /**
     * Gets the "userName" element
     */
    public java.lang.String getUserName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERNAME$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "userName" element
     */
    public x0.scimSchemasCore1.NonEmptyString xgetUserName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NonEmptyString target = null;
            target = (x0.scimSchemasCore1.NonEmptyString)get_store().find_element_user(USERNAME$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "userName" element
     */
    public void setUserName(java.lang.String userName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERNAME$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(USERNAME$0);
            }
            target.setStringValue(userName);
        }
    }
    
    /**
     * Sets (as xml) the "userName" element
     */
    public void xsetUserName(x0.scimSchemasCore1.NonEmptyString userName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NonEmptyString target = null;
            target = (x0.scimSchemasCore1.NonEmptyString)get_store().find_element_user(USERNAME$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.NonEmptyString)get_store().add_element_user(USERNAME$0);
            }
            target.set(userName);
        }
    }
}
