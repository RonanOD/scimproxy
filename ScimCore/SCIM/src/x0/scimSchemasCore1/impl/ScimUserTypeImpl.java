/*
 * XML Type:  ScimUserType
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.ScimUserType
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * An XML ScimUserType(@urn:scim:schemas:core:1.0).
 *
 * This is a complex type.
 */
public class ScimUserTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.ScimUserType
{
    
    public ScimUserTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ID$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "id");
    private static final javax.xml.namespace.QName EXTERNALID$2 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "externalId");
    private static final javax.xml.namespace.QName USERNAME$4 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "userName");
    private static final javax.xml.namespace.QName NAME$6 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "name");
    private static final javax.xml.namespace.QName DISPLAYNAME$8 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "displayName");
    private static final javax.xml.namespace.QName NICKNAME$10 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "nickName");
    private static final javax.xml.namespace.QName PROFILEURL$12 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "profileUrl");
    private static final javax.xml.namespace.QName EMPLOYEENUMBER$14 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "employeeNumber");
    private static final javax.xml.namespace.QName USERTYPE$16 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "userType");
    private static final javax.xml.namespace.QName TITLE$18 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "title");
    private static final javax.xml.namespace.QName MANAGER$20 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "manager");
    private static final javax.xml.namespace.QName PREFERREDLANGUAGE$22 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "preferredLanguage");
    private static final javax.xml.namespace.QName LOCALE$24 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "locale");
    private static final javax.xml.namespace.QName UTCOFFSET$26 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "utcOffset");
    private static final javax.xml.namespace.QName COSTCENTER$28 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "costCenter");
    private static final javax.xml.namespace.QName ORGANIZATION$30 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "organization");
    private static final javax.xml.namespace.QName DIVISION$32 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "division");
    private static final javax.xml.namespace.QName DEPARTMENT$34 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "department");
    private static final javax.xml.namespace.QName META$36 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "meta");
    private static final javax.xml.namespace.QName EMAILS$38 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "emails");
    private static final javax.xml.namespace.QName PHONENUMBERS$40 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "phoneNumbers");
    private static final javax.xml.namespace.QName IMS$42 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "ims");
    private static final javax.xml.namespace.QName PHOTOS$44 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "photos");
    private static final javax.xml.namespace.QName GROUPS$46 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "groups");
    private static final javax.xml.namespace.QName ADDRESSES$48 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "addresses");
    
    
    /**
     * Gets the "id" element
     */
    public java.lang.String getId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "id" element
     */
    public x0.scimSchemasCore1.NonEmptyString xgetId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NonEmptyString target = null;
            target = (x0.scimSchemasCore1.NonEmptyString)get_store().find_element_user(ID$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "id" element
     */
    public void setId(java.lang.String id)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ID$0);
            }
            target.setStringValue(id);
        }
    }
    
    /**
     * Sets (as xml) the "id" element
     */
    public void xsetId(x0.scimSchemasCore1.NonEmptyString id)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NonEmptyString target = null;
            target = (x0.scimSchemasCore1.NonEmptyString)get_store().find_element_user(ID$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.NonEmptyString)get_store().add_element_user(ID$0);
            }
            target.set(id);
        }
    }
    
    /**
     * Gets the "externalId" element
     */
    public java.lang.String getExternalId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALID$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "externalId" element
     */
    public x0.scimSchemasCore1.NonEmptyString xgetExternalId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NonEmptyString target = null;
            target = (x0.scimSchemasCore1.NonEmptyString)get_store().find_element_user(EXTERNALID$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "externalId" element
     */
    public boolean isSetExternalId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EXTERNALID$2) != 0;
        }
    }
    
    /**
     * Sets the "externalId" element
     */
    public void setExternalId(java.lang.String externalId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EXTERNALID$2);
            }
            target.setStringValue(externalId);
        }
    }
    
    /**
     * Sets (as xml) the "externalId" element
     */
    public void xsetExternalId(x0.scimSchemasCore1.NonEmptyString externalId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NonEmptyString target = null;
            target = (x0.scimSchemasCore1.NonEmptyString)get_store().find_element_user(EXTERNALID$2, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.NonEmptyString)get_store().add_element_user(EXTERNALID$2);
            }
            target.set(externalId);
        }
    }
    
    /**
     * Unsets the "externalId" element
     */
    public void unsetExternalId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EXTERNALID$2, 0);
        }
    }
    
    /**
     * Gets the "userName" element
     */
    public java.lang.String getUserName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERNAME$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "userName" element
     */
    public x0.scimSchemasCore1.NonEmptyString xgetUserName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NonEmptyString target = null;
            target = (x0.scimSchemasCore1.NonEmptyString)get_store().find_element_user(USERNAME$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "userName" element
     */
    public void setUserName(java.lang.String userName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERNAME$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(USERNAME$4);
            }
            target.setStringValue(userName);
        }
    }
    
    /**
     * Sets (as xml) the "userName" element
     */
    public void xsetUserName(x0.scimSchemasCore1.NonEmptyString userName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NonEmptyString target = null;
            target = (x0.scimSchemasCore1.NonEmptyString)get_store().find_element_user(USERNAME$4, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.NonEmptyString)get_store().add_element_user(USERNAME$4);
            }
            target.set(userName);
        }
    }
    
    /**
     * Gets the "name" element
     */
    public x0.scimSchemasCore1.NameType getName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NameType target = null;
            target = (x0.scimSchemasCore1.NameType)get_store().find_element_user(NAME$6, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "name" element
     */
    public boolean isSetName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NAME$6) != 0;
        }
    }
    
    /**
     * Sets the "name" element
     */
    public void setName(x0.scimSchemasCore1.NameType name)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NameType target = null;
            target = (x0.scimSchemasCore1.NameType)get_store().find_element_user(NAME$6, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.NameType)get_store().add_element_user(NAME$6);
            }
            target.set(name);
        }
    }
    
    /**
     * Appends and returns a new empty "name" element
     */
    public x0.scimSchemasCore1.NameType addNewName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NameType target = null;
            target = (x0.scimSchemasCore1.NameType)get_store().add_element_user(NAME$6);
            return target;
        }
    }
    
    /**
     * Unsets the "name" element
     */
    public void unsetName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NAME$6, 0);
        }
    }
    
    /**
     * Gets the "displayName" element
     */
    public java.lang.String getDisplayName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISPLAYNAME$8, 0);
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
            target = (x0.scimSchemasCore1.NonEmptyString)get_store().find_element_user(DISPLAYNAME$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "displayName" element
     */
    public boolean isSetDisplayName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DISPLAYNAME$8) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISPLAYNAME$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DISPLAYNAME$8);
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
            target = (x0.scimSchemasCore1.NonEmptyString)get_store().find_element_user(DISPLAYNAME$8, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.NonEmptyString)get_store().add_element_user(DISPLAYNAME$8);
            }
            target.set(displayName);
        }
    }
    
    /**
     * Unsets the "displayName" element
     */
    public void unsetDisplayName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DISPLAYNAME$8, 0);
        }
    }
    
    /**
     * Gets the "nickName" element
     */
    public java.lang.String getNickName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NICKNAME$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "nickName" element
     */
    public org.apache.xmlbeans.XmlString xgetNickName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NICKNAME$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "nickName" element
     */
    public boolean isSetNickName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NICKNAME$10) != 0;
        }
    }
    
    /**
     * Sets the "nickName" element
     */
    public void setNickName(java.lang.String nickName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NICKNAME$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NICKNAME$10);
            }
            target.setStringValue(nickName);
        }
    }
    
    /**
     * Sets (as xml) the "nickName" element
     */
    public void xsetNickName(org.apache.xmlbeans.XmlString nickName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NICKNAME$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(NICKNAME$10);
            }
            target.set(nickName);
        }
    }
    
    /**
     * Unsets the "nickName" element
     */
    public void unsetNickName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NICKNAME$10, 0);
        }
    }
    
    /**
     * Gets the "profileUrl" element
     */
    public java.lang.String getProfileUrl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROFILEURL$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "profileUrl" element
     */
    public org.apache.xmlbeans.XmlString xgetProfileUrl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROFILEURL$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "profileUrl" element
     */
    public boolean isSetProfileUrl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PROFILEURL$12) != 0;
        }
    }
    
    /**
     * Sets the "profileUrl" element
     */
    public void setProfileUrl(java.lang.String profileUrl)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROFILEURL$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROFILEURL$12);
            }
            target.setStringValue(profileUrl);
        }
    }
    
    /**
     * Sets (as xml) the "profileUrl" element
     */
    public void xsetProfileUrl(org.apache.xmlbeans.XmlString profileUrl)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROFILEURL$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PROFILEURL$12);
            }
            target.set(profileUrl);
        }
    }
    
    /**
     * Unsets the "profileUrl" element
     */
    public void unsetProfileUrl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PROFILEURL$12, 0);
        }
    }
    
    /**
     * Gets the "employeeNumber" element
     */
    public java.lang.String getEmployeeNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMPLOYEENUMBER$14, 0);
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
            target = (x0.scimSchemasCore1.AlphanumericString)get_store().find_element_user(EMPLOYEENUMBER$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "employeeNumber" element
     */
    public boolean isSetEmployeeNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMPLOYEENUMBER$14) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMPLOYEENUMBER$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMPLOYEENUMBER$14);
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
            target = (x0.scimSchemasCore1.AlphanumericString)get_store().find_element_user(EMPLOYEENUMBER$14, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.AlphanumericString)get_store().add_element_user(EMPLOYEENUMBER$14);
            }
            target.set(employeeNumber);
        }
    }
    
    /**
     * Unsets the "employeeNumber" element
     */
    public void unsetEmployeeNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMPLOYEENUMBER$14, 0);
        }
    }
    
    /**
     * Gets the "userType" element
     */
    public java.lang.String getUserType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERTYPE$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "userType" element
     */
    public org.apache.xmlbeans.XmlString xgetUserType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERTYPE$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "userType" element
     */
    public boolean isSetUserType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(USERTYPE$16) != 0;
        }
    }
    
    /**
     * Sets the "userType" element
     */
    public void setUserType(java.lang.String userType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERTYPE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(USERTYPE$16);
            }
            target.setStringValue(userType);
        }
    }
    
    /**
     * Sets (as xml) the "userType" element
     */
    public void xsetUserType(org.apache.xmlbeans.XmlString userType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERTYPE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(USERTYPE$16);
            }
            target.set(userType);
        }
    }
    
    /**
     * Unsets the "userType" element
     */
    public void unsetUserType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(USERTYPE$16, 0);
        }
    }
    
    /**
     * Gets the "title" element
     */
    public java.lang.String getTitle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TITLE$18, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "title" element
     */
    public org.apache.xmlbeans.XmlString xgetTitle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TITLE$18, 0);
            return target;
        }
    }
    
    /**
     * True if has "title" element
     */
    public boolean isSetTitle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TITLE$18) != 0;
        }
    }
    
    /**
     * Sets the "title" element
     */
    public void setTitle(java.lang.String title)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TITLE$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TITLE$18);
            }
            target.setStringValue(title);
        }
    }
    
    /**
     * Sets (as xml) the "title" element
     */
    public void xsetTitle(org.apache.xmlbeans.XmlString title)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TITLE$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TITLE$18);
            }
            target.set(title);
        }
    }
    
    /**
     * Unsets the "title" element
     */
    public void unsetTitle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TITLE$18, 0);
        }
    }
    
    /**
     * Gets the "manager" element
     */
    public java.lang.String getManager()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MANAGER$20, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MANAGER$20, 0);
            return target;
        }
    }
    
    /**
     * True if has "manager" element
     */
    public boolean isSetManager()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MANAGER$20) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MANAGER$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MANAGER$20);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MANAGER$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MANAGER$20);
            }
            target.set(manager);
        }
    }
    
    /**
     * Unsets the "manager" element
     */
    public void unsetManager()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MANAGER$20, 0);
        }
    }
    
    /**
     * Gets the "preferredLanguage" element
     */
    public java.lang.String getPreferredLanguage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PREFERREDLANGUAGE$22, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PREFERREDLANGUAGE$22, 0);
            return target;
        }
    }
    
    /**
     * True if has "preferredLanguage" element
     */
    public boolean isSetPreferredLanguage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PREFERREDLANGUAGE$22) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PREFERREDLANGUAGE$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PREFERREDLANGUAGE$22);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PREFERREDLANGUAGE$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PREFERREDLANGUAGE$22);
            }
            target.set(preferredLanguage);
        }
    }
    
    /**
     * Unsets the "preferredLanguage" element
     */
    public void unsetPreferredLanguage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PREFERREDLANGUAGE$22, 0);
        }
    }
    
    /**
     * Gets the "locale" element
     */
    public java.lang.String getLocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALE$24, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "locale" element
     */
    public org.apache.xmlbeans.XmlString xgetLocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOCALE$24, 0);
            return target;
        }
    }
    
    /**
     * True if has "locale" element
     */
    public boolean isSetLocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LOCALE$24) != 0;
        }
    }
    
    /**
     * Sets the "locale" element
     */
    public void setLocale(java.lang.String locale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LOCALE$24);
            }
            target.setStringValue(locale);
        }
    }
    
    /**
     * Sets (as xml) the "locale" element
     */
    public void xsetLocale(org.apache.xmlbeans.XmlString locale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOCALE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LOCALE$24);
            }
            target.set(locale);
        }
    }
    
    /**
     * Unsets the "locale" element
     */
    public void unsetLocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LOCALE$24, 0);
        }
    }
    
    /**
     * Gets the "utcOffset" element
     */
    public java.util.Calendar getUtcOffset()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UTCOFFSET$26, 0);
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
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(UTCOFFSET$26, 0);
            return target;
        }
    }
    
    /**
     * True if has "utcOffset" element
     */
    public boolean isSetUtcOffset()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(UTCOFFSET$26) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UTCOFFSET$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(UTCOFFSET$26);
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
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(UTCOFFSET$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(UTCOFFSET$26);
            }
            target.set(utcOffset);
        }
    }
    
    /**
     * Unsets the "utcOffset" element
     */
    public void unsetUtcOffset()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(UTCOFFSET$26, 0);
        }
    }
    
    /**
     * Gets the "costCenter" element
     */
    public java.lang.String getCostCenter()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COSTCENTER$28, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COSTCENTER$28, 0);
            return target;
        }
    }
    
    /**
     * True if has "costCenter" element
     */
    public boolean isSetCostCenter()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COSTCENTER$28) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COSTCENTER$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COSTCENTER$28);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COSTCENTER$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COSTCENTER$28);
            }
            target.set(costCenter);
        }
    }
    
    /**
     * Unsets the "costCenter" element
     */
    public void unsetCostCenter()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COSTCENTER$28, 0);
        }
    }
    
    /**
     * Gets the "organization" element
     */
    public java.lang.String getOrganization()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORGANIZATION$30, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "organization" element
     */
    public org.apache.xmlbeans.XmlString xgetOrganization()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ORGANIZATION$30, 0);
            return target;
        }
    }
    
    /**
     * True if has "organization" element
     */
    public boolean isSetOrganization()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ORGANIZATION$30) != 0;
        }
    }
    
    /**
     * Sets the "organization" element
     */
    public void setOrganization(java.lang.String organization)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORGANIZATION$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ORGANIZATION$30);
            }
            target.setStringValue(organization);
        }
    }
    
    /**
     * Sets (as xml) the "organization" element
     */
    public void xsetOrganization(org.apache.xmlbeans.XmlString organization)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ORGANIZATION$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ORGANIZATION$30);
            }
            target.set(organization);
        }
    }
    
    /**
     * Unsets the "organization" element
     */
    public void unsetOrganization()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ORGANIZATION$30, 0);
        }
    }
    
    /**
     * Gets the "division" element
     */
    public java.lang.String getDivision()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DIVISION$32, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DIVISION$32, 0);
            return target;
        }
    }
    
    /**
     * True if has "division" element
     */
    public boolean isSetDivision()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DIVISION$32) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DIVISION$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DIVISION$32);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DIVISION$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DIVISION$32);
            }
            target.set(division);
        }
    }
    
    /**
     * Unsets the "division" element
     */
    public void unsetDivision()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DIVISION$32, 0);
        }
    }
    
    /**
     * Gets the "department" element
     */
    public java.lang.String getDepartment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPARTMENT$34, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEPARTMENT$34, 0);
            return target;
        }
    }
    
    /**
     * True if has "department" element
     */
    public boolean isSetDepartment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEPARTMENT$34) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPARTMENT$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEPARTMENT$34);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEPARTMENT$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEPARTMENT$34);
            }
            target.set(department);
        }
    }
    
    /**
     * Unsets the "department" element
     */
    public void unsetDepartment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEPARTMENT$34, 0);
        }
    }
    
    /**
     * Gets the "meta" element
     */
    public x0.scimSchemasCore1.MetaType getMeta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.MetaType target = null;
            target = (x0.scimSchemasCore1.MetaType)get_store().find_element_user(META$36, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "meta" element
     */
    public boolean isSetMeta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(META$36) != 0;
        }
    }
    
    /**
     * Sets the "meta" element
     */
    public void setMeta(x0.scimSchemasCore1.MetaType meta)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.MetaType target = null;
            target = (x0.scimSchemasCore1.MetaType)get_store().find_element_user(META$36, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.MetaType)get_store().add_element_user(META$36);
            }
            target.set(meta);
        }
    }
    
    /**
     * Appends and returns a new empty "meta" element
     */
    public x0.scimSchemasCore1.MetaType addNewMeta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.MetaType target = null;
            target = (x0.scimSchemasCore1.MetaType)get_store().add_element_user(META$36);
            return target;
        }
    }
    
    /**
     * Unsets the "meta" element
     */
    public void unsetMeta()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(META$36, 0);
        }
    }
    
    /**
     * Gets the "emails" element
     */
    public x0.scimSchemasCore1.EmailsType getEmails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.EmailsType target = null;
            target = (x0.scimSchemasCore1.EmailsType)get_store().find_element_user(EMAILS$38, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "emails" element
     */
    public boolean isSetEmails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAILS$38) != 0;
        }
    }
    
    /**
     * Sets the "emails" element
     */
    public void setEmails(x0.scimSchemasCore1.EmailsType emails)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.EmailsType target = null;
            target = (x0.scimSchemasCore1.EmailsType)get_store().find_element_user(EMAILS$38, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.EmailsType)get_store().add_element_user(EMAILS$38);
            }
            target.set(emails);
        }
    }
    
    /**
     * Appends and returns a new empty "emails" element
     */
    public x0.scimSchemasCore1.EmailsType addNewEmails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.EmailsType target = null;
            target = (x0.scimSchemasCore1.EmailsType)get_store().add_element_user(EMAILS$38);
            return target;
        }
    }
    
    /**
     * Unsets the "emails" element
     */
    public void unsetEmails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAILS$38, 0);
        }
    }
    
    /**
     * Gets the "phoneNumbers" element
     */
    public x0.scimSchemasCore1.PhoneNumbersType getPhoneNumbers()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhoneNumbersType target = null;
            target = (x0.scimSchemasCore1.PhoneNumbersType)get_store().find_element_user(PHONENUMBERS$40, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "phoneNumbers" element
     */
    public boolean isSetPhoneNumbers()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PHONENUMBERS$40) != 0;
        }
    }
    
    /**
     * Sets the "phoneNumbers" element
     */
    public void setPhoneNumbers(x0.scimSchemasCore1.PhoneNumbersType phoneNumbers)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhoneNumbersType target = null;
            target = (x0.scimSchemasCore1.PhoneNumbersType)get_store().find_element_user(PHONENUMBERS$40, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.PhoneNumbersType)get_store().add_element_user(PHONENUMBERS$40);
            }
            target.set(phoneNumbers);
        }
    }
    
    /**
     * Appends and returns a new empty "phoneNumbers" element
     */
    public x0.scimSchemasCore1.PhoneNumbersType addNewPhoneNumbers()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhoneNumbersType target = null;
            target = (x0.scimSchemasCore1.PhoneNumbersType)get_store().add_element_user(PHONENUMBERS$40);
            return target;
        }
    }
    
    /**
     * Unsets the "phoneNumbers" element
     */
    public void unsetPhoneNumbers()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PHONENUMBERS$40, 0);
        }
    }
    
    /**
     * Gets the "ims" element
     */
    public x0.scimSchemasCore1.ImsType getIms()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.ImsType target = null;
            target = (x0.scimSchemasCore1.ImsType)get_store().find_element_user(IMS$42, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "ims" element
     */
    public boolean isSetIms()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IMS$42) != 0;
        }
    }
    
    /**
     * Sets the "ims" element
     */
    public void setIms(x0.scimSchemasCore1.ImsType ims)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.ImsType target = null;
            target = (x0.scimSchemasCore1.ImsType)get_store().find_element_user(IMS$42, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.ImsType)get_store().add_element_user(IMS$42);
            }
            target.set(ims);
        }
    }
    
    /**
     * Appends and returns a new empty "ims" element
     */
    public x0.scimSchemasCore1.ImsType addNewIms()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.ImsType target = null;
            target = (x0.scimSchemasCore1.ImsType)get_store().add_element_user(IMS$42);
            return target;
        }
    }
    
    /**
     * Unsets the "ims" element
     */
    public void unsetIms()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IMS$42, 0);
        }
    }
    
    /**
     * Gets the "photos" element
     */
    public x0.scimSchemasCore1.PhotosType getPhotos()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhotosType target = null;
            target = (x0.scimSchemasCore1.PhotosType)get_store().find_element_user(PHOTOS$44, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "photos" element
     */
    public boolean isSetPhotos()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PHOTOS$44) != 0;
        }
    }
    
    /**
     * Sets the "photos" element
     */
    public void setPhotos(x0.scimSchemasCore1.PhotosType photos)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhotosType target = null;
            target = (x0.scimSchemasCore1.PhotosType)get_store().find_element_user(PHOTOS$44, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.PhotosType)get_store().add_element_user(PHOTOS$44);
            }
            target.set(photos);
        }
    }
    
    /**
     * Appends and returns a new empty "photos" element
     */
    public x0.scimSchemasCore1.PhotosType addNewPhotos()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhotosType target = null;
            target = (x0.scimSchemasCore1.PhotosType)get_store().add_element_user(PHOTOS$44);
            return target;
        }
    }
    
    /**
     * Unsets the "photos" element
     */
    public void unsetPhotos()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PHOTOS$44, 0);
        }
    }
    
    /**
     * Gets the "groups" element
     */
    public x0.scimSchemasCore1.GroupsType getGroups()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.GroupsType target = null;
            target = (x0.scimSchemasCore1.GroupsType)get_store().find_element_user(GROUPS$46, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "groups" element
     */
    public boolean isSetGroups()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(GROUPS$46) != 0;
        }
    }
    
    /**
     * Sets the "groups" element
     */
    public void setGroups(x0.scimSchemasCore1.GroupsType groups)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.GroupsType target = null;
            target = (x0.scimSchemasCore1.GroupsType)get_store().find_element_user(GROUPS$46, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.GroupsType)get_store().add_element_user(GROUPS$46);
            }
            target.set(groups);
        }
    }
    
    /**
     * Appends and returns a new empty "groups" element
     */
    public x0.scimSchemasCore1.GroupsType addNewGroups()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.GroupsType target = null;
            target = (x0.scimSchemasCore1.GroupsType)get_store().add_element_user(GROUPS$46);
            return target;
        }
    }
    
    /**
     * Unsets the "groups" element
     */
    public void unsetGroups()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(GROUPS$46, 0);
        }
    }
    
    /**
     * Gets the "addresses" element
     */
    public x0.scimSchemasCore1.AddressesType getAddresses()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.AddressesType target = null;
            target = (x0.scimSchemasCore1.AddressesType)get_store().find_element_user(ADDRESSES$48, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "addresses" element
     */
    public boolean isSetAddresses()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ADDRESSES$48) != 0;
        }
    }
    
    /**
     * Sets the "addresses" element
     */
    public void setAddresses(x0.scimSchemasCore1.AddressesType addresses)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.AddressesType target = null;
            target = (x0.scimSchemasCore1.AddressesType)get_store().find_element_user(ADDRESSES$48, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.AddressesType)get_store().add_element_user(ADDRESSES$48);
            }
            target.set(addresses);
        }
    }
    
    /**
     * Appends and returns a new empty "addresses" element
     */
    public x0.scimSchemasCore1.AddressesType addNewAddresses()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.AddressesType target = null;
            target = (x0.scimSchemasCore1.AddressesType)get_store().add_element_user(ADDRESSES$48);
            return target;
        }
    }
    
    /**
     * Unsets the "addresses" element
     */
    public void unsetAddresses()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ADDRESSES$48, 0);
        }
    }
}
