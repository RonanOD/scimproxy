/*
 * An XML document type.
 * Localname: emails
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.EmailsDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one emails(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class EmailsDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.EmailsDocument
{
    
    public EmailsDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName EMAILS$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "emails");
    
    
    /**
     * Gets the "emails" element
     */
    public x0.scimSchemasCore1.EmailsType getEmails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.EmailsType target = null;
            target = (x0.scimSchemasCore1.EmailsType)get_store().find_element_user(EMAILS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "emails" element
     */
    public void setEmails(x0.scimSchemasCore1.EmailsType emails)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.EmailsType target = null;
            target = (x0.scimSchemasCore1.EmailsType)get_store().find_element_user(EMAILS$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.EmailsType)get_store().add_element_user(EMAILS$0);
            }
            target.set(emails);
        }
    }
    
    /**
     * Appends and returns a new empty "emails" element
     */
    public x0.scimSchemasCore1.EmailsType addNewEmails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.EmailsType target = null;
            target = (x0.scimSchemasCore1.EmailsType)get_store().add_element_user(EMAILS$0);
            return target;
        }
    }
}
