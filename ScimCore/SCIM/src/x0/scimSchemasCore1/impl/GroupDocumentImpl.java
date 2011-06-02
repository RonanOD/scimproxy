/*
 * An XML document type.
 * Localname: group
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.GroupDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one group(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class GroupDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.GroupDocument
{
    
    public GroupDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GROUP$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "group");
    
    
    /**
     * Gets the "group" element
     */
    public x0.scimSchemasCore1.GroupType getGroup()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.GroupType target = null;
            target = (x0.scimSchemasCore1.GroupType)get_store().find_element_user(GROUP$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "group" element
     */
    public void setGroup(x0.scimSchemasCore1.GroupType group)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.GroupType target = null;
            target = (x0.scimSchemasCore1.GroupType)get_store().find_element_user(GROUP$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.GroupType)get_store().add_element_user(GROUP$0);
            }
            target.set(group);
        }
    }
    
    /**
     * Appends and returns a new empty "group" element
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
}
