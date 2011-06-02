/*
 * XML Type:  ImsType
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.ImsType
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * An XML ImsType(@urn:scim:schemas:core:1.0).
 *
 * This is a complex type.
 */
public class ImsTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.ImsType
{
    
    public ImsTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IM$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "im");
    
    
    /**
     * Gets array of all "im" elements
     */
    public x0.scimSchemasCore1.ImType[] getImArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IM$0, targetList);
            x0.scimSchemasCore1.ImType[] result = new x0.scimSchemasCore1.ImType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "im" element
     */
    public x0.scimSchemasCore1.ImType getImArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.ImType target = null;
            target = (x0.scimSchemasCore1.ImType)get_store().find_element_user(IM$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "im" element
     */
    public int sizeOfImArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IM$0);
        }
    }
    
    /**
     * Sets array of all "im" element
     */
    public void setImArray(x0.scimSchemasCore1.ImType[] imArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(imArray, IM$0);
        }
    }
    
    /**
     * Sets ith "im" element
     */
    public void setImArray(int i, x0.scimSchemasCore1.ImType im)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.ImType target = null;
            target = (x0.scimSchemasCore1.ImType)get_store().find_element_user(IM$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(im);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "im" element
     */
    public x0.scimSchemasCore1.ImType insertNewIm(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.ImType target = null;
            target = (x0.scimSchemasCore1.ImType)get_store().insert_element_user(IM$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "im" element
     */
    public x0.scimSchemasCore1.ImType addNewIm()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.ImType target = null;
            target = (x0.scimSchemasCore1.ImType)get_store().add_element_user(IM$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "im" element
     */
    public void removeIm(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IM$0, i);
        }
    }
}
