/*
 * An XML document type.
 * Localname: division
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.DivisionDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one division(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class DivisionDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.DivisionDocument
{
    
    public DivisionDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DIVISION$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "division");
    
    
    /**
     * Gets the "division" element
     */
    public java.lang.String getDivision()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DIVISION$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "division" element
     */
    public org.apache.xmlbeans.XmlString xgetDivision()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DIVISION$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "division" element
     */
    public void setDivision(java.lang.String division)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DIVISION$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DIVISION$0);
            }
            target.setStringValue(division);
        }
    }
    
    /**
     * Sets (as xml) the "division" element
     */
    public void xsetDivision(org.apache.xmlbeans.XmlString division)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DIVISION$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DIVISION$0);
            }
            target.set(division);
        }
    }
}
