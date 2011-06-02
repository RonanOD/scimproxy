/*
 * An XML document type.
 * Localname: im
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.ImDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one im(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class ImDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.ImDocument
{
    
    public ImDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IM$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "im");
    
    
    /**
     * Gets the "im" element
     */
    public x0.scimSchemasCore1.ImType getIm()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.ImType target = null;
            target = (x0.scimSchemasCore1.ImType)get_store().find_element_user(IM$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "im" element
     */
    public void setIm(x0.scimSchemasCore1.ImType im)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.ImType target = null;
            target = (x0.scimSchemasCore1.ImType)get_store().find_element_user(IM$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.ImType)get_store().add_element_user(IM$0);
            }
            target.set(im);
        }
    }
    
    /**
     * Appends and returns a new empty "im" element
     */
    public x0.scimSchemasCore1.ImType addNewIm()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.ImType target = null;
            target = (x0.scimSchemasCore1.ImType)get_store().add_element_user(IM$0);
            return target;
        }
    }
}
