/*
 * XML Type:  GroupsType
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.GroupsType
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * An XML GroupsType(@urn:scim:schemas:core:1.0).
 *
 * This is a complex type.
 */
public class GroupsTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.GroupsType
{
    
    public GroupsTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GROUP$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "group");
    
    
    /**
     * Gets array of all "group" elements
     */
    public x0.scimSchemasCore1.GroupType[] getGroupArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(GROUP$0, targetList);
            x0.scimSchemasCore1.GroupType[] result = new x0.scimSchemasCore1.GroupType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "group" element
     */
    public x0.scimSchemasCore1.GroupType getGroupArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.GroupType target = null;
            target = (x0.scimSchemasCore1.GroupType)get_store().find_element_user(GROUP$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "group" element
     */
    public int sizeOfGroupArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(GROUP$0);
        }
    }
    
    /**
     * Sets array of all "group" element
     */
    public void setGroupArray(x0.scimSchemasCore1.GroupType[] groupArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(groupArray, GROUP$0);
        }
    }
    
    /**
     * Sets ith "group" element
     */
    public void setGroupArray(int i, x0.scimSchemasCore1.GroupType group)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.GroupType target = null;
            target = (x0.scimSchemasCore1.GroupType)get_store().find_element_user(GROUP$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(group);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "group" element
     */
    public x0.scimSchemasCore1.GroupType insertNewGroup(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.GroupType target = null;
            target = (x0.scimSchemasCore1.GroupType)get_store().insert_element_user(GROUP$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "group" element
     */
    public x0.scimSchemasCore1.GroupType addNewGroup()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.GroupType target = null;
            target = (x0.scimSchemasCore1.GroupType)get_store().add_element_user(GROUP$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "group" element
     */
    public void removeGroup(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(GROUP$0, i);
        }
    }
}
