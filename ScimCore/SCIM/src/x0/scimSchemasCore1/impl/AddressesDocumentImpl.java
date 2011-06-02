/*
 * An XML document type.
 * Localname: addresses
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.AddressesDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one addresses(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class AddressesDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.AddressesDocument
{
    
    public AddressesDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ADDRESSES$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "addresses");
    
    
    /**
     * Gets the "addresses" element
     */
    public x0.scimSchemasCore1.AddressesType getAddresses()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.AddressesType target = null;
            target = (x0.scimSchemasCore1.AddressesType)get_store().find_element_user(ADDRESSES$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "addresses" element
     */
    public void setAddresses(x0.scimSchemasCore1.AddressesType addresses)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.AddressesType target = null;
            target = (x0.scimSchemasCore1.AddressesType)get_store().find_element_user(ADDRESSES$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.AddressesType)get_store().add_element_user(ADDRESSES$0);
            }
            target.set(addresses);
        }
    }
    
    /**
     * Appends and returns a new empty "addresses" element
     */
    public x0.scimSchemasCore1.AddressesType addNewAddresses()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.AddressesType target = null;
            target = (x0.scimSchemasCore1.AddressesType)get_store().add_element_user(ADDRESSES$0);
            return target;
        }
    }
}
