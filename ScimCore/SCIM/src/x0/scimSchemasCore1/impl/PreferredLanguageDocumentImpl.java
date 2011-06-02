/*
 * An XML document type.
 * Localname: preferredLanguage
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.PreferredLanguageDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one preferredLanguage(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class PreferredLanguageDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.PreferredLanguageDocument
{
    
    public PreferredLanguageDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PREFERREDLANGUAGE$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "preferredLanguage");
    
    
    /**
     * Gets the "preferredLanguage" element
     */
    public java.lang.String getPreferredLanguage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PREFERREDLANGUAGE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "preferredLanguage" element
     */
    public org.apache.xmlbeans.XmlString xgetPreferredLanguage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PREFERREDLANGUAGE$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "preferredLanguage" element
     */
    public void setPreferredLanguage(java.lang.String preferredLanguage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PREFERREDLANGUAGE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PREFERREDLANGUAGE$0);
            }
            target.setStringValue(preferredLanguage);
        }
    }
    
    /**
     * Sets (as xml) the "preferredLanguage" element
     */
    public void xsetPreferredLanguage(org.apache.xmlbeans.XmlString preferredLanguage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PREFERREDLANGUAGE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PREFERREDLANGUAGE$0);
            }
            target.set(preferredLanguage);
        }
    }
}
