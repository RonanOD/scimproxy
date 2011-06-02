/*
 * An XML document type.
 * Localname: photos
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.PhotosDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one photos(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class PhotosDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.PhotosDocument
{
    
    public PhotosDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PHOTOS$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "photos");
    
    
    /**
     * Gets the "photos" element
     */
    public x0.scimSchemasCore1.PhotosType getPhotos()
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhotosType target = null;
            target = (x0.scimSchemasCore1.PhotosType)get_store().find_element_user(PHOTOS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
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
            target = (x0.scimSchemasCore1.PhotosType)get_store().find_element_user(PHOTOS$0, 0);
            if (target == null)
            {
                target = (x0.scimSchemasCore1.PhotosType)get_store().add_element_user(PHOTOS$0);
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
            target = (x0.scimSchemasCore1.PhotosType)get_store().add_element_user(PHOTOS$0);
            return target;
        }
    }
}
