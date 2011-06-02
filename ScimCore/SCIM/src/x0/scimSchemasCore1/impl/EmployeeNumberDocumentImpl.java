/*
 * An XML document type.
 * Localname: employeeNumber
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.EmployeeNumberDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one employeeNumber(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class EmployeeNumberDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.EmployeeNumberDocument
{
    
    public EmployeeNumberDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName EMPLOYEENUMBER$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "employeeNumber");
    
    
    /**
     * Gets the "employeeNumber" element
     */
    public java.lang.String getEmployeeNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMPLOYEENUMBER$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "employeeNumber" element
     */
    public x0.scimSchemasCore1.AlphanumericString xgetEmployeeNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.AlphanumericString target = null;
            target = (x0.scimSchemasCore1.AlphanumericString)get_store().find_element_user(EMPLOYEENUMBER$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "employeeNumber" element
     */
    public void setEmployeeNumber(java.lang.String employeeNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMPLOYEENUMBER$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMPLOYEENUMBER$0);
            }
            target.setStringValue(employeeNumber);
        }
    }
    
    /**
     * Sets (as xml) the "employeeNumber" element
     */
    public void xsetEmployeeNumber(x0.scimSchemasCore1.AlphanumericString employeeNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.AlphanumericString target = null;
            target = (x0.scimSchemasCore1.AlphanumericString)get_store().find_element_user(EMPLOYEENUMBER$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.AlphanumericString)get_store().add_element_user(EMPLOYEENUMBER$0);
            }
            target.set(employeeNumber);
        }
    }
}
