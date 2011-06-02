/*
 * An XML document type.
 * Localname: phoneNumber
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.PhoneNumberDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one phoneNumber(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class PhoneNumberDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.PhoneNumberDocument
{
    
    public PhoneNumberDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PHONENUMBER$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "phoneNumber");
    
    
    /**
     * Gets the "phoneNumber" element
     */
    public x0.scimSchemasCore1.PhoneNumberType getPhoneNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhoneNumberType target = null;
            target = (x0.scimSchemasCore1.PhoneNumberType)get_store().find_element_user(PHONENUMBER$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "phoneNumber" element
     */
    public void setPhoneNumber(x0.scimSchemasCore1.PhoneNumberType phoneNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhoneNumberType target = null;
            target = (x0.scimSchemasCore1.PhoneNumberType)get_store().find_element_user(PHONENUMBER$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.PhoneNumberType)get_store().add_element_user(PHONENUMBER$0);
            }
            target.set(phoneNumber);
        }
    }
    
    /**
     * Appends and returns a new empty "phoneNumber" element
     */
    public x0.scimSchemasCore1.PhoneNumberType addNewPhoneNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhoneNumberType target = null;
            target = (x0.scimSchemasCore1.PhoneNumberType)get_store().add_element_user(PHONENUMBER$0);
            return target;
        }
    }
}
