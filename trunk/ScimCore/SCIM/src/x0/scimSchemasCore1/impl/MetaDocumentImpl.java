/*
 * An XML document type.
 * Localname: meta
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.MetaDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one meta(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class MetaDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.MetaDocument
{
    
    public MetaDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName META$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "meta");
    
    
    /**
     * Gets the "meta" element
     */
    public x0.scimSchemasCore1.MetaType getMeta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.MetaType target = null;
            target = (x0.scimSchemasCore1.MetaType)get_store().find_element_user(META$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "meta" element
     */
    public void setMeta(x0.scimSchemasCore1.MetaType meta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.MetaType target = null;
            target = (x0.scimSchemasCore1.MetaType)get_store().find_element_user(META$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.MetaType)get_store().add_element_user(META$0);
            }
            target.set(meta);
        }
    }
    
    /**
     * Appends and returns a new empty "meta" element
     */
    public x0.scimSchemasCore1.MetaType addNewMeta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.MetaType target = null;
            target = (x0.scimSchemasCore1.MetaType)get_store().add_element_user(META$0);
            return target;
        }
    }
}
