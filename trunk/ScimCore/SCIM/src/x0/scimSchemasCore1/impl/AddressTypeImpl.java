/*
 * XML Type:  AddressType
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.AddressType
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * An XML AddressType(@urn:scim:schemas:core:1.0).
 *
 * This is a complex type.
 */
public class AddressTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.AddressType
{
    
    public AddressTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FORMATTED$0 = 
        new javax.xml.namespace.QName("", "formatted");
    private static final javax.xml.namespace.QName STREETADDRESS$2 = 
        new javax.xml.namespace.QName("", "streetAddress");
    private static final javax.xml.namespace.QName LOCALITY$4 = 
        new javax.xml.namespace.QName("", "locality");
    private static final javax.xml.namespace.QName REGION$6 = 
        new javax.xml.namespace.QName("", "region");
    private static final javax.xml.namespace.QName POSTALCODE$8 = 
        new javax.xml.namespace.QName("", "postalCode");
    private static final javax.xml.namespace.QName COUNTRY$10 = 
        new javax.xml.namespace.QName("", "country");
    private static final javax.xml.namespace.QName TYPE$12 = 
        new javax.xml.namespace.QName("", "type");
    private static final javax.xml.namespace.QName PRIMARY$14 = 
        new javax.xml.namespace.QName("", "primary");
    
    
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
     * True if has "formatted" element
     */
    public boolean isSetFormatted()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FORMATTED$0) != 0;
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
     * Unsets the "formatted" element
     */
    public void unsetFormatted()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FORMATTED$0, 0);
        }
    }
    
    /**
     * Gets the "streetAddress" element
     */
    public java.lang.String getStreetAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STREETADDRESS$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "streetAddress" element
     */
    public org.apache.xmlbeans.XmlString xgetStreetAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STREETADDRESS$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "streetAddress" element
     */
    public boolean isSetStreetAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STREETADDRESS$2) != 0;
        }
    }
    
    /**
     * Sets the "streetAddress" element
     */
    public void setStreetAddress(java.lang.String streetAddress)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STREETADDRESS$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STREETADDRESS$2);
            }
            target.setStringValue(streetAddress);
        }
    }
    
    /**
     * Sets (as xml) the "streetAddress" element
     */
    public void xsetStreetAddress(org.apache.xmlbeans.XmlString streetAddress)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STREETADDRESS$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STREETADDRESS$2);
            }
            target.set(streetAddress);
        }
    }
    
    /**
     * Unsets the "streetAddress" element
     */
    public void unsetStreetAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STREETADDRESS$2, 0);
        }
    }
    
    /**
     * Gets the "locality" element
     */
    public java.lang.String getLocality()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALITY$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "locality" element
     */
    public org.apache.xmlbeans.XmlString xgetLocality()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOCALITY$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "locality" element
     */
    public boolean isSetLocality()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LOCALITY$4) != 0;
        }
    }
    
    /**
     * Sets the "locality" element
     */
    public void setLocality(java.lang.String locality)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALITY$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LOCALITY$4);
            }
            target.setStringValue(locality);
        }
    }
    
    /**
     * Sets (as xml) the "locality" element
     */
    public void xsetLocality(org.apache.xmlbeans.XmlString locality)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOCALITY$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LOCALITY$4);
            }
            target.set(locality);
        }
    }
    
    /**
     * Unsets the "locality" element
     */
    public void unsetLocality()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LOCALITY$4, 0);
        }
    }
    
    /**
     * Gets the "region" element
     */
    public java.lang.String getRegion()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REGION$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "region" element
     */
    public org.apache.xmlbeans.XmlString xgetRegion()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REGION$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "region" element
     */
    public boolean isSetRegion()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(REGION$6) != 0;
        }
    }
    
    /**
     * Sets the "region" element
     */
    public void setRegion(java.lang.String region)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REGION$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REGION$6);
            }
            target.setStringValue(region);
        }
    }
    
    /**
     * Sets (as xml) the "region" element
     */
    public void xsetRegion(org.apache.xmlbeans.XmlString region)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REGION$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REGION$6);
            }
            target.set(region);
        }
    }
    
    /**
     * Unsets the "region" element
     */
    public void unsetRegion()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(REGION$6, 0);
        }
    }
    
    /**
     * Gets the "postalCode" element
     */
    public java.lang.String getPostalCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(POSTALCODE$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "postalCode" element
     */
    public org.apache.xmlbeans.XmlString xgetPostalCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(POSTALCODE$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "postalCode" element
     */
    public boolean isSetPostalCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(POSTALCODE$8) != 0;
        }
    }
    
    /**
     * Sets the "postalCode" element
     */
    public void setPostalCode(java.lang.String postalCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(POSTALCODE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(POSTALCODE$8);
            }
            target.setStringValue(postalCode);
        }
    }
    
    /**
     * Sets (as xml) the "postalCode" element
     */
    public void xsetPostalCode(org.apache.xmlbeans.XmlString postalCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(POSTALCODE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(POSTALCODE$8);
            }
            target.set(postalCode);
        }
    }
    
    /**
     * Unsets the "postalCode" element
     */
    public void unsetPostalCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(POSTALCODE$8, 0);
        }
    }
    
    /**
     * Gets the "country" element
     */
    public java.lang.String getCountry()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COUNTRY$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "country" element
     */
    public org.apache.xmlbeans.XmlString xgetCountry()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COUNTRY$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "country" element
     */
    public boolean isSetCountry()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COUNTRY$10) != 0;
        }
    }
    
    /**
     * Sets the "country" element
     */
    public void setCountry(java.lang.String country)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COUNTRY$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COUNTRY$10);
            }
            target.setStringValue(country);
        }
    }
    
    /**
     * Sets (as xml) the "country" element
     */
    public void xsetCountry(org.apache.xmlbeans.XmlString country)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COUNTRY$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COUNTRY$10);
            }
            target.set(country);
        }
    }
    
    /**
     * Unsets the "country" element
     */
    public void unsetCountry()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COUNTRY$10, 0);
        }
    }
    
    /**
     * Gets the "type" attribute
     */
    public java.lang.String getType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TYPE$12);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "type" attribute
     */
    public org.apache.xmlbeans.XmlString xgetType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TYPE$12);
            return target;
        }
    }
    
    /**
     * True if has "type" attribute
     */
    public boolean isSetType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(TYPE$12) != null;
        }
    }
    
    /**
     * Sets the "type" attribute
     */
    public void setType(java.lang.String type)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TYPE$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TYPE$12);
            }
            target.setStringValue(type);
        }
    }
    
    /**
     * Sets (as xml) the "type" attribute
     */
    public void xsetType(org.apache.xmlbeans.XmlString type)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TYPE$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TYPE$12);
            }
            target.set(type);
        }
    }
    
    /**
     * Unsets the "type" attribute
     */
    public void unsetType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(TYPE$12);
        }
    }
    
    /**
     * Gets the "primary" attribute
     */
    public boolean getPrimary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PRIMARY$14);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "primary" attribute
     */
    public org.apache.xmlbeans.XmlBoolean xgetPrimary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_attribute_user(PRIMARY$14);
            return target;
        }
    }
    
    /**
     * True if has "primary" attribute
     */
    public boolean isSetPrimary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PRIMARY$14) != null;
        }
    }
    
    /**
     * Sets the "primary" attribute
     */
    public void setPrimary(boolean primary)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PRIMARY$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PRIMARY$14);
            }
            target.setBooleanValue(primary);
        }
    }
    
    /**
     * Sets (as xml) the "primary" attribute
     */
    public void xsetPrimary(org.apache.xmlbeans.XmlBoolean primary)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_attribute_user(PRIMARY$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_attribute_user(PRIMARY$14);
            }
            target.set(primary);
        }
    }
    
    /**
     * Unsets the "primary" attribute
     */
    public void unsetPrimary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PRIMARY$14);
        }
    }
}
