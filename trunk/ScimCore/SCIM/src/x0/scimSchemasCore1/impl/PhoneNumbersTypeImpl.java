/*
 * XML Type:  PhoneNumbersType
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.PhoneNumbersType
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * An XML PhoneNumbersType(@urn:scim:schemas:core:1.0).
 *
 * This is a complex type.
 */
public class PhoneNumbersTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.PhoneNumbersType
{
    
    public PhoneNumbersTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PHONENUMBER$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "phoneNumber");
    
    
    /**
     * Gets array of all "phoneNumber" elements
     */
    public x0.scimSchemasCore1.PhoneNumberType[] getPhoneNumberArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PHONENUMBER$0, targetList);
            x0.scimSchemasCore1.PhoneNumberType[] result = new x0.scimSchemasCore1.PhoneNumberType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "phoneNumber" element
     */
    public x0.scimSchemasCore1.PhoneNumberType getPhoneNumberArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhoneNumberType target = null;
            target = (x0.scimSchemasCore1.PhoneNumberType)get_store().find_element_user(PHONENUMBER$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "phoneNumber" element
     */
    public int sizeOfPhoneNumberArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PHONENUMBER$0);
        }
    }
    
    /**
     * Sets array of all "phoneNumber" element
     */
    public void setPhoneNumberArray(x0.scimSchemasCore1.PhoneNumberType[] phoneNumberArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(phoneNumberArray, PHONENUMBER$0);
        }
    }
    
    /**
     * Sets ith "phoneNumber" element
     */
    public void setPhoneNumberArray(int i, x0.scimSchemasCore1.PhoneNumberType phoneNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhoneNumberType target = null;
            target = (x0.scimSchemasCore1.PhoneNumberType)get_store().find_element_user(PHONENUMBER$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(phoneNumber);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "phoneNumber" element
     */
    public x0.scimSchemasCore1.PhoneNumberType insertNewPhoneNumber(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhoneNumberType target = null;
            target = (x0.scimSchemasCore1.PhoneNumberType)get_store().insert_element_user(PHONENUMBER$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "phoneNumber" element
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
    
    /**
     * Removes the ith "phoneNumber" element
     */
    public void removePhoneNumber(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PHONENUMBER$0, i);
        }
    }
}
