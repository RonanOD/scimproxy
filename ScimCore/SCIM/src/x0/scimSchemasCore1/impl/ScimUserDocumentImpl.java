/*
 * An XML document type.
 * Localname: ScimUser
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.ScimUserDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one ScimUser(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class ScimUserDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.ScimUserDocument
{
    
    public ScimUserDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SCIMUSER$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "ScimUser");
    
    
    /**
     * Gets the "ScimUser" element
     */
    public x0.scimSchemasCore1.ScimUserType getScimUser()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.ScimUserType target = null;
            target = (x0.scimSchemasCore1.ScimUserType)get_store().find_element_user(SCIMUSER$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "ScimUser" element
     */
    public void setScimUser(x0.scimSchemasCore1.ScimUserType scimUser)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.ScimUserType target = null;
            target = (x0.scimSchemasCore1.ScimUserType)get_store().find_element_user(SCIMUSER$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.ScimUserType)get_store().add_element_user(SCIMUSER$0);
            }
            target.set(scimUser);
        }
    }
    
    /**
     * Appends and returns a new empty "ScimUser" element
     */
    public x0.scimSchemasCore1.ScimUserType addNewScimUser()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.ScimUserType target = null;
            target = (x0.scimSchemasCore1.ScimUserType)get_store().add_element_user(SCIMUSER$0);
            return target;
        }
    }
}
