/*
 * An XML document type.
 * Localname: utcOffset
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.UtcOffsetDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one utcOffset(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class UtcOffsetDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.UtcOffsetDocument
{
    
    public UtcOffsetDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName UTCOFFSET$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "utcOffset");
    
    
    /**
     * Gets the "utcOffset" element
     */
    public java.util.Calendar getUtcOffset()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UTCOFFSET$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "utcOffset" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetUtcOffset()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(UTCOFFSET$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "utcOffset" element
     */
    public void setUtcOffset(java.util.Calendar utcOffset)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UTCOFFSET$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(UTCOFFSET$0);
            }
            target.setCalendarValue(utcOffset);
        }
    }
    
    /**
     * Sets (as xml) the "utcOffset" element
     */
    public void xsetUtcOffset(org.apache.xmlbeans.XmlDateTime utcOffset)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(UTCOFFSET$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(UTCOFFSET$0);
            }
            target.set(utcOffset);
        }
    }
}
