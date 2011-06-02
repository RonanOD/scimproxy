/*
 * An XML document type.
 * Localname: photo
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.PhotoDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one photo(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class PhotoDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.PhotoDocument
{
    
    public PhotoDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PHOTO$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "photo");
    
    
    /**
     * Gets the "photo" element
     */
    public x0.scimSchemasCore1.PhotoType getPhoto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhotoType target = null;
            target = (x0.scimSchemasCore1.PhotoType)get_store().find_element_user(PHOTO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "photo" element
     */
    public void setPhoto(x0.scimSchemasCore1.PhotoType photo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhotoType target = null;
            target = (x0.scimSchemasCore1.PhotoType)get_store().find_element_user(PHOTO$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.PhotoType)get_store().add_element_user(PHOTO$0);
            }
            target.set(photo);
        }
    }
    
    /**
     * Appends and returns a new empty "photo" element
     */
    public x0.scimSchemasCore1.PhotoType addNewPhoto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhotoType target = null;
            target = (x0.scimSchemasCore1.PhotoType)get_store().add_element_user(PHOTO$0);
            return target;
        }
    }
}
