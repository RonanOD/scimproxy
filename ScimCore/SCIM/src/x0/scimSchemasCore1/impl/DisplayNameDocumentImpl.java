/*
 * An XML document type.
 * Localname: displayName
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.DisplayNameDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one displayName(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class DisplayNameDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.DisplayNameDocument
{
    
    public DisplayNameDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DISPLAYNAME$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "displayName");
    
    
    /**
     * Gets the "displayName" element
     */
    public java.lang.String getDisplayName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISPLAYNAME$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "displayName" element
     */
    public x0.scimSchemasCore1.NonEmptyString xgetDisplayName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NonEmptyString target = null;
            target = (x0.scimSchemasCore1.NonEmptyString)get_store().find_element_user(DISPLAYNAME$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "displayName" element
     */
    public void setDisplayName(java.lang.String displayName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISPLAYNAME$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DISPLAYNAME$0);
            }
            target.setStringValue(displayName);
        }
    }
    
    /**
     * Sets (as xml) the "displayName" element
     */
    public void xsetDisplayName(x0.scimSchemasCore1.NonEmptyString displayName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NonEmptyString target = null;
            target = (x0.scimSchemasCore1.NonEmptyString)get_store().find_element_user(DISPLAYNAME$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.NonEmptyString)get_store().add_element_user(DISPLAYNAME$0);
            }
            target.set(displayName);
        }
    }
}
