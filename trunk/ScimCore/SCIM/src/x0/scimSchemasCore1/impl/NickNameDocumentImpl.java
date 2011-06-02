/*
 * An XML document type.
 * Localname: nickName
 * Namespace: urn:scim:schemas:core:1.0
 * Java type: x0.scimSchemasCore1.NickNameDocument
 *
 * Automatically generated - do not modify.
 */
package x0.scimSchemasCore1.impl;
/**
 * A document containing one nickName(@urn:scim:schemas:core:1.0) element.
 *
 * This is a complex type.
 */
public class NickNameDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements x0.scimSchemasCore1.NickNameDocument
{
    
    public NickNameDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName NICKNAME$0 = 
        new javax.xml.namespace.QName("urn:scim:schemas:core:1.0", "nickName");
    
    
    /**
     * Gets the "nickName" element
     */
    public java.lang.String getNickName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NICKNAME$0, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NICKNAME$0, 0);
            return target;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NICKNAME$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NICKNAME$0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NICKNAME$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(NICKNAME$0);
            }
            target.set(nickName);
        }
    }
}
