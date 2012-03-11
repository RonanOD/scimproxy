package info.simplecloud.core.handlers;

import info.simplecloud.core.exceptions.InvalidUser;
import junit.framework.Assert;

import org.apache.xmlbeans.impl.values.XmlObjectBase;
import org.junit.Test;

import x0.scimSchemasCore1.MultiValuedAttribute;

public class IntegerHandlerTest {
    private static IntegerHandler ih = new IntegerHandler();

    @Test
    public void decode() throws InvalidUser {
        Assert.assertEquals(10, ih.decode(10, null, null));
        Assert.assertEquals(5, ih.decode(5, null, null));
        Assert.assertEquals(13, ih.decodeXml(13, null, null));
        Assert.assertEquals(9, ih.decodeXml(9, null, null));
    }

    @Test
    public void decodeComplexXml() {
        MultiValuedAttribute multiValuedAttribute = MultiValuedAttribute.Factory.newInstance();
        XmlObjectBase xmlObject = (XmlObjectBase) multiValuedAttribute.addNewValue();
        xmlObject.setStringValue("40");

        Assert.assertEquals(40, ih.decodeXml(multiValuedAttribute, null, null));
    }

    @Test
    public void encode() {
        Assert.assertEquals(11, ih.encode(11, null, null, null));
        Assert.assertEquals(6, ih.encode(6, null, null, null));
        Assert.assertEquals(12, ih.encodeXml(12, null, null, null));
        Assert.assertEquals(8, ih.encodeXml(8, null, null, null));
    }

    @Test
    public void encodeComplexXml() {
        MultiValuedAttribute multiValuedAttribute = MultiValuedAttribute.Factory.newInstance();
        ih.encodeXml(30, null, null, multiValuedAttribute);
        XmlObjectBase xmlObject = (XmlObjectBase) multiValuedAttribute.getValue();
        Assert.assertEquals("30", xmlObject.getStringValue());
    }

    @Test
    public void merge() {
        Assert.assertEquals(12, ih.merge(12, null));
        Assert.assertEquals(7, ih.merge(7, null));
    }
}
