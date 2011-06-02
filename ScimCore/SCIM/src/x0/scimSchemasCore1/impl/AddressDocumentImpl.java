/*
 * An XML document type.
 * Localname: address
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.AddressDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one address(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class AddressDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.AddressDocument
{
    
    public AddressDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ADDRESS$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "address");
    
    
    /**
     * Gets the "address" element
     */
    public x0.scimSchemasCore1.AddressType getAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.AddressType target = null;
            target = (x0.scimSchemasCore1.AddressType)get_store().find_element_user(ADDRESS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "address" element
     */
    public void setAddress(x0.scimSchemasCore1.AddressType address)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.AddressType target = null;
            target = (x0.scimSchemasCore1.AddressType)get_store().find_element_user(ADDRESS$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.AddressType)get_store().add_element_user(ADDRESS$0);
            }
            target.set(address);
        }
    }
    
    /**
     * Appends and returns a new empty "address" element
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
}
