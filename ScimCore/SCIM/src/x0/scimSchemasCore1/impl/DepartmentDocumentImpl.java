/*
 * An XML document type.
 * Localname: department
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.DepartmentDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one department(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class DepartmentDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.DepartmentDocument
{
    
    public DepartmentDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DEPARTMENT$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "department");
    
    
    /**
     * Gets the "department" element
     */
    public java.lang.String getDepartment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPARTMENT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "department" element
     */
    public org.apache.xmlbeans.XmlString xgetDepartment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEPARTMENT$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "department" element
     */
    public void setDepartment(java.lang.String department)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPARTMENT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEPARTMENT$0);
            }
            target.setStringValue(department);
        }
    }
    
    /**
     * Sets (as xml) the "department" element
     */
    public void xsetDepartment(org.apache.xmlbeans.XmlString department)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEPARTMENT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEPARTMENT$0);
            }
            target.set(department);
        }
    }
}
