/*
 * An XML document type.
 * Localname: groups
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.GroupsDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one groups(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class GroupsDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.GroupsDocument
{
    
    public GroupsDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GROUPS$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "groups");
    
    
    /**
     * Gets the "groups" element
     */
    public x0.scimSchemasCore1.GroupsType getGroups()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.GroupsType target = null;
            target = (x0.scimSchemasCore1.GroupsType)get_store().find_element_user(GROUPS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
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
            target = (x0.scimSchemasCore1.GroupsType)get_store().find_element_user(GROUPS$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.GroupsType)get_store().add_element_user(GROUPS$0);
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
            target = (x0.scimSchemasCore1.GroupsType)get_store().add_element_user(GROUPS$0);
            return target;
        }
    }
}
