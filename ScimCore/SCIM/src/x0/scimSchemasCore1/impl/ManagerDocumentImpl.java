/*
 * An XML document type.
 * Localname: manager
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.ManagerDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one manager(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class ManagerDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.ManagerDocument
{
    
    public ManagerDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MANAGER$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "manager");
    
    
    /**
     * Gets the "manager" element
     */
    public java.lang.String getManager()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MANAGER$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "manager" element
     */
    public org.apache.xmlbeans.XmlString xgetManager()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MANAGER$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "manager" element
     */
    public void setManager(java.lang.String manager)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MANAGER$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MANAGER$0);
            }
            target.setStringValue(manager);
        }
    }
    
    /**
     * Sets (as xml) the "manager" element
     */
    public void xsetManager(org.apache.xmlbeans.XmlString manager)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MANAGER$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MANAGER$0);
            }
            target.set(manager);
        }
    }
}
