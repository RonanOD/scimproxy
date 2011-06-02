/*
 * XML Type:  EmailsType
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.EmailsType
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * An XML EmailsType(@urn:scim:schemas:core:1.0).
 *
 * This is a complex type.
 */
public class EmailsTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.EmailsType
{
    
    public EmailsTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName EMAIL$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "email");
    
    
    /**
     * Gets array of all "email" elements
     */
    public x0.scimSchemasCore1.EmailType[] getEmailArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(EMAIL$0, targetList);
            x0.scimSchemasCore1.EmailType[] result = new x0.scimSchemasCore1.EmailType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "email" element
     */
    public x0.scimSchemasCore1.EmailType getEmailArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.EmailType target = null;
            target = (x0.scimSchemasCore1.EmailType)get_store().find_element_user(EMAIL$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "email" element
     */
    public int sizeOfEmailArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAIL$0);
        }
    }
    
    /**
     * Sets array of all "email" element
     */
    public void setEmailArray(x0.scimSchemasCore1.EmailType[] emailArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(emailArray, EMAIL$0);
        }
    }
    
    /**
     * Sets ith "email" element
     */
    public void setEmailArray(int i, x0.scimSchemasCore1.EmailType email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.EmailType target = null;
            target = (x0.scimSchemasCore1.EmailType)get_store().find_element_user(EMAIL$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(email);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "email" element
     */
    public x0.scimSchemasCore1.EmailType insertNewEmail(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.EmailType target = null;
            target = (x0.scimSchemasCore1.EmailType)get_store().insert_element_user(EMAIL$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "email" element
     */
    public x0.scimSchemasCore1.EmailType addNewEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.EmailType target = null;
            target = (x0.scimSchemasCore1.EmailType)get_store().add_element_user(EMAIL$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "email" element
     */
    public void removeEmail(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAIL$0, i);
        }
    }
}
