/*
 * An XML document type.
 * Localname: ims
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.ImsDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one ims(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class ImsDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.ImsDocument
{
    
    public ImsDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IMS$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "ims");
    
    
    /**
     * Gets the "ims" element
     */
    public x0.scimSchemasCore1.ImsType getIms()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.ImsType target = null;
            target = (x0.scimSchemasCore1.ImsType)get_store().find_element_user(IMS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "ims" element
     */
    public void setIms(x0.scimSchemasCore1.ImsType ims)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.ImsType target = null;
            target = (x0.scimSchemasCore1.ImsType)get_store().find_element_user(IMS$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.ImsType)get_store().add_element_user(IMS$0);
            }
            target.set(ims);
        }
    }
    
    /**
     * Appends and returns a new empty "ims" element
     */
    public x0.scimSchemasCore1.ImsType addNewIms()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.ImsType target = null;
            target = (x0.scimSchemasCore1.ImsType)get_store().add_element_user(IMS$0);
            return target;
        }
    }
}
