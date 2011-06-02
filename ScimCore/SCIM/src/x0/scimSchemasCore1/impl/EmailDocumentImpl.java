/*
 * An XML document type.
 * Localname: email
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.EmailDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one email(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class EmailDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.EmailDocument
{
    
    public EmailDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName EMAIL$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "email");
    
    
    /**
     * Gets the "email" element
     */
    public x0.scimSchemasCore1.EmailType getEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.EmailType target = null;
            target = (x0.scimSchemasCore1.EmailType)get_store().find_element_user(EMAIL$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "email" element
     */
    public void setEmail(x0.scimSchemasCore1.EmailType email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.EmailType target = null;
            target = (x0.scimSchemasCore1.EmailType)get_store().find_element_user(EMAIL$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.EmailType)get_store().add_element_user(EMAIL$0);
            }
            target.set(email);
        }
    }
    
    /**
     * Appends and returns a new empty "email" element
     */
    public x0.scimSchemasCore1.EmailType addNewEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.EmailType target = null;
            target = (x0.scimSchemasCore1.EmailType)get_store().add_element_user(EMAIL$0);
            return target;
        }
    }
}
