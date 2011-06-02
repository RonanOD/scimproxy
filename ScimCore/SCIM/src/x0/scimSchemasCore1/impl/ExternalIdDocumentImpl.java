/*
 * An XML document type.
 * Localname: externalId
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.ExternalIdDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one externalId(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class ExternalIdDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.ExternalIdDocument
{
    
    public ExternalIdDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName EXTERNALID$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "externalId");
    
    
    /**
     * Gets the "externalId" element
     */
    public java.lang.String getExternalId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "externalId" element
     */
    public x0.scimSchemasCore1.NonEmptyString xgetExternalId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NonEmptyString target = null;
            target = (x0.scimSchemasCore1.NonEmptyString)get_store().find_element_user(EXTERNALID$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "externalId" element
     */
    public void setExternalId(java.lang.String externalId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EXTERNALID$0);
            }
            target.setStringValue(externalId);
        }
    }
    
    /**
     * Sets (as xml) the "externalId" element
     */
    public void xsetExternalId(x0.scimSchemasCore1.NonEmptyString externalId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NonEmptyString target = null;
            target = (x0.scimSchemasCore1.NonEmptyString)get_store().find_element_user(EXTERNALID$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.NonEmptyString)get_store().add_element_user(EXTERNALID$0);
            }
            target.set(externalId);
        }
    }
}
