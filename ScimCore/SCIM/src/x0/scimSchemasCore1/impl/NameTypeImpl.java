/*
 * XML Type:  NameType
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.NameType
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * An XML NameType(@urn:scim:schemas:core:1.0).
 *
 * This is a complex type.
 */
public class NameTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.NameType
{
    
    public NameTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FORMATTED$0 = 
        new javax.xml.namespace.QName("", "formatted");
    private static final javax.xml.namespace.QName FAMILYNAME$2 = 
        new javax.xml.namespace.QName("", "familyName");
    private static final javax.xml.namespace.QName GIVENNAME$4 = 
        new javax.xml.namespace.QName("", "givenName");
    private static final javax.xml.namespace.QName MIDDLENAME$6 = 
        new javax.xml.namespace.QName("", "middleName");
    private static final javax.xml.namespace.QName HONORIFICPREFIX$8 = 
        new javax.xml.namespace.QName("", "honorificPrefix");
    private static final javax.xml.namespace.QName HONORIFICSUFFIX$10 = 
        new javax.xml.namespace.QName("", "honorificSuffix");
    
    
    /**
     * Gets the "formatted" element
     */
    public java.lang.String getFormatted()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FORMATTED$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "formatted" element
     */
    public org.apache.xmlbeans.XmlString xgetFormatted()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FORMATTED$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "formatted" element
     */
    public void setFormatted(java.lang.String formatted)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FORMATTED$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FORMATTED$0);
            }
            target.setStringValue(formatted);
        }
    }
    
    /**
     * Sets (as xml) the "formatted" element
     */
    public void xsetFormatted(org.apache.xmlbeans.XmlString formatted)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FORMATTED$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FORMATTED$0);
            }
            target.set(formatted);
        }
    }
    
    /**
     * Gets the "familyName" element
     */
    public java.lang.String getFamilyName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAMILYNAME$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "familyName" element
     */
    public org.apache.xmlbeans.XmlString xgetFamilyName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAMILYNAME$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "familyName" element
     */
    public void setFamilyName(java.lang.String familyName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAMILYNAME$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FAMILYNAME$2);
            }
            target.setStringValue(familyName);
        }
    }
    
    /**
     * Sets (as xml) the "familyName" element
     */
    public void xsetFamilyName(org.apache.xmlbeans.XmlString familyName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAMILYNAME$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FAMILYNAME$2);
            }
            target.set(familyName);
        }
    }
    
    /**
     * Gets the "givenName" element
     */
    public java.lang.String getGivenName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GIVENNAME$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "givenName" element
     */
    public org.apache.xmlbeans.XmlString xgetGivenName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GIVENNAME$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "givenName" element
     */
    public void setGivenName(java.lang.String givenName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GIVENNAME$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(GIVENNAME$4);
            }
            target.setStringValue(givenName);
        }
    }
    
    /**
     * Sets (as xml) the "givenName" element
     */
    public void xsetGivenName(org.apache.xmlbeans.XmlString givenName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GIVENNAME$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(GIVENNAME$4);
            }
            target.set(givenName);
        }
    }
    
    /**
     * Gets the "middleName" element
     */
    public java.lang.String getMiddleName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MIDDLENAME$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "middleName" element
     */
    public org.apache.xmlbeans.XmlString xgetMiddleName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MIDDLENAME$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "middleName" element
     */
    public void setMiddleName(java.lang.String middleName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MIDDLENAME$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MIDDLENAME$6);
            }
            target.setStringValue(middleName);
        }
    }
    
    /**
     * Sets (as xml) the "middleName" element
     */
    public void xsetMiddleName(org.apache.xmlbeans.XmlString middleName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MIDDLENAME$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MIDDLENAME$6);
            }
            target.set(middleName);
        }
    }
    
    /**
     * Gets the "honorificPrefix" element
     */
    public java.lang.String getHonorificPrefix()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HONORIFICPREFIX$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "honorificPrefix" element
     */
    public org.apache.xmlbeans.XmlString xgetHonorificPrefix()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HONORIFICPREFIX$8, 0);
            return target;
        }
    }
    
    /**
     * Sets the "honorificPrefix" element
     */
    public void setHonorificPrefix(java.lang.String honorificPrefix)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HONORIFICPREFIX$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(HONORIFICPREFIX$8);
            }
            target.setStringValue(honorificPrefix);
        }
    }
    
    /**
     * Sets (as xml) the "honorificPrefix" element
     */
    public void xsetHonorificPrefix(org.apache.xmlbeans.XmlString honorificPrefix)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HONORIFICPREFIX$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(HONORIFICPREFIX$8);
            }
            target.set(honorificPrefix);
        }
    }
    
    /**
     * Gets the "honorificSuffix" element
     */
    public java.lang.String getHonorificSuffix()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HONORIFICSUFFIX$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "honorificSuffix" element
     */
    public org.apache.xmlbeans.XmlString xgetHonorificSuffix()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HONORIFICSUFFIX$10, 0);
            return target;
        }
    }
    
    /**
     * Sets the "honorificSuffix" element
     */
    public void setHonorificSuffix(java.lang.String honorificSuffix)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HONORIFICSUFFIX$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(HONORIFICSUFFIX$10);
            }
            target.setStringValue(honorificSuffix);
        }
    }
    
    /**
     * Sets (as xml) the "honorificSuffix" element
     */
    public void xsetHonorificSuffix(org.apache.xmlbeans.XmlString honorificSuffix)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HONORIFICSUFFIX$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(HONORIFICSUFFIX$10);
            }
            target.set(honorificSuffix);
        }
    }
}
