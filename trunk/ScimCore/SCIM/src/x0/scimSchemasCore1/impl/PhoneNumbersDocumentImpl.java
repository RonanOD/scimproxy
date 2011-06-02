/*
 * An XML document type.
 * Localname: phoneNumbers
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.PhoneNumbersDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one phoneNumbers(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class PhoneNumbersDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.PhoneNumbersDocument
{
    
    public PhoneNumbersDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PHONENUMBERS$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "phoneNumbers");
    
    
    /**
     * Gets the "phoneNumbers" element
     */
    public x0.scimSchemasCore1.PhoneNumbersType getPhoneNumbers()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhoneNumbersType target = null;
            target = (x0.scimSchemasCore1.PhoneNumbersType)get_store().find_element_user(PHONENUMBERS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "phoneNumbers" element
     */
    public void setPhoneNumbers(x0.scimSchemasCore1.PhoneNumbersType phoneNumbers)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhoneNumbersType target = null;
            target = (x0.scimSchemasCore1.PhoneNumbersType)get_store().find_element_user(PHONENUMBERS$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.PhoneNumbersType)get_store().add_element_user(PHONENUMBERS$0);
            }
            target.set(phoneNumbers);
        }
    }
    
    /**
     * Appends and returns a new empty "phoneNumbers" element
     */
    public x0.scimSchemasCore1.PhoneNumbersType addNewPhoneNumbers()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhoneNumbersType target = null;
            target = (x0.scimSchemasCore1.PhoneNumbersType)get_store().add_element_user(PHONENUMBERS$0);
            return target;
        }
    }
}
