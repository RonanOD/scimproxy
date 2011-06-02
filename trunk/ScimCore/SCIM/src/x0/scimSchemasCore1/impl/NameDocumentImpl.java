/*
 * An XML document type.
 * Localname: name
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.NameDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one name(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class NameDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.NameDocument
{
    
    public NameDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName NAME$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "name");
    
    
    /**
     * Gets the "name" element
     */
    public x0.scimSchemasCore1.NameType getName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.NameType target = null;
            target = (x0.scimSchemasCore1.NameType)get_store().find_element_user(NAME$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
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
            target = (x0.scimSchemasCore1.NameType)get_store().find_element_user(NAME$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.NameType)get_store().add_element_user(NAME$0);
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
            target = (x0.scimSchemasCore1.NameType)get_store().add_element_user(NAME$0);
            return target;
        }
    }
}
