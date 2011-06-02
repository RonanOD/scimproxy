/*
 * An XML document type.
 * Localname: costCenter
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.CostCenterDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one costCenter(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class CostCenterDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.CostCenterDocument
{
    
    public CostCenterDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName COSTCENTER$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "costCenter");
    
    
    /**
     * Gets the "costCenter" element
     */
    public java.lang.String getCostCenter()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COSTCENTER$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "costCenter" element
     */
    public org.apache.xmlbeans.XmlString xgetCostCenter()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COSTCENTER$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "costCenter" element
     */
    public void setCostCenter(java.lang.String costCenter)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COSTCENTER$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COSTCENTER$0);
            }
            target.setStringValue(costCenter);
        }
    }
    
    /**
     * Sets (as xml) the "costCenter" element
     */
    public void xsetCostCenter(org.apache.xmlbeans.XmlString costCenter)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COSTCENTER$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COSTCENTER$0);
            }
            target.set(costCenter);
        }
    }
}
