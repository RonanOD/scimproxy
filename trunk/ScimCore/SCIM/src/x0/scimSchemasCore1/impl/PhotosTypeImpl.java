/*
 * XML Type:  PhotosType
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.PhotosType
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * An XML PhotosType(@urn:scim:schemas:core:1.0).
 *
 * This is a complex type.
 */
public class PhotosTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.PhotosType
{
    
    public PhotosTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PHOTO$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "photo");
    
    
    /**
     * Gets array of all "photo" elements
     */
    public x0.scimSchemasCore1.PhotoType[] getPhotoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PHOTO$0, targetList);
            x0.scimSchemasCore1.PhotoType[] result = new x0.scimSchemasCore1.PhotoType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "photo" element
     */
    public x0.scimSchemasCore1.PhotoType getPhotoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhotoType target = null;
            target = (x0.scimSchemasCore1.PhotoType)get_store().find_element_user(PHOTO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "photo" element
     */
    public int sizeOfPhotoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PHOTO$0);
        }
    }
    
    /**
     * Sets array of all "photo" element
     */
    public void setPhotoArray(x0.scimSchemasCore1.PhotoType[] photoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(photoArray, PHOTO$0);
        }
    }
    
    /**
     * Sets ith "photo" element
     */
    public void setPhotoArray(int i, x0.scimSchemasCore1.PhotoType photo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhotoType target = null;
            target = (x0.scimSchemasCore1.PhotoType)get_store().find_element_user(PHOTO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(photo);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "photo" element
     */
    public x0.scimSchemasCore1.PhotoType insertNewPhoto(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            x0.scimSchemasCore1.PhotoType target = null;
            target = (x0.scimSchemasCore1.PhotoType)get_store().insert_element_user(PHOTO$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "photo" element
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
    
    /**
     * Removes the ith "photo" element
     */
    public void removePhoto(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PHOTO$0, i);
        }
    }
}
