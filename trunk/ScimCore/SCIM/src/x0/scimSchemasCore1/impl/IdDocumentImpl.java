/*
 * An XML document type.
 * Localname: id
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.IdDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one id(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class IdDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.IdDocument
{
    
    public IdDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ID$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "id");
    
    
    /**
     * Gets the "id" element
     */
    public java.lang.String getId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "id" element
     */
    public x0.scimSchemasCore1.NonEmptyString xgetId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NonEmptyString target = null;
            target = (x0.scimSchemasCore1.NonEmptyString)get_store().find_element_user(ID$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "id" element
     */
    public void setId(java.lang.String id)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ID$0);
            }
            target.setStringValue(id);
        }
    }
    
    /**
     * Sets (as xml) the "id" element
     */
    public void xsetId(x0.scimSchemasCore1.NonEmptyString id)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NonEmptyString target = null;
            target = (x0.scimSchemasCore1.NonEmptyString)get_store().find_element_user(ID$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.NonEmptyString)get_store().add_element_user(ID$0);
            }
            target.set(id);
        }
    }
}
