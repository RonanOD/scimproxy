/*
 * XML Type:  AddressesType
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.AddressesType
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * An XML AddressesType(@urn:scim:schemas:core:1.0).
 *
 * This is a complex type.
 */
public class AddressesTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.AddressesType
{
    
    public AddressesTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ADDRESS$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "address");
    
    
    /**
     * Gets array of all "address" elements
     */
    public x0.scimSchemasCore1.AddressType[] getAddressArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ADDRESS$0, targetList);
            x0.scimSchemasCore1.AddressType[] result = new x0.scimSchemasCore1.AddressType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "address" element
     */
    public x0.scimSchemasCore1.AddressType getAddressArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.AddressType target = null;
            target = (x0.scimSchemasCore1.AddressType)get_store().find_element_user(ADDRESS$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "address" element
     */
    public int sizeOfAddressArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ADDRESS$0);
        }
    }
    
    /**
     * Sets array of all "address" element
     */
    public void setAddressArray(x0.scimSchemasCore1.AddressType[] addressArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(addressArray, ADDRESS$0);
        }
    }
    
    /**
     * Sets ith "address" element
     */
    public void setAddressArray(int i, x0.scimSchemasCore1.AddressType address)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.AddressType target = null;
            target = (x0.scimSchemasCore1.AddressType)get_store().find_element_user(ADDRESS$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(address);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "address" element
     */
    public x0.scimSchemasCore1.AddressType insertNewAddress(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.AddressType target = null;
            target = (x0.scimSchemasCore1.AddressType)get_store().insert_element_user(ADDRESS$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "address" element
     */
    public x0.scimSchemasCore1.AddressType addNewAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.AddressType target = null;
            target = (x0.scimSchemasCore1.AddressType)get_store().add_element_user(ADDRESS$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "address" element
     */
    public void removeAddress(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ADDRESS$0, i);
        }
    }
}
