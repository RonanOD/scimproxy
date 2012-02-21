package info.simplecloud.core.handlers;

import info.simplecloud.core.exceptions.InvalidUser;
import junit.framework.Assert;

import org.apache.xmlbeans.impl.values.XmlObjectBase;
import org.junit.Test;

import x0.scimSchemasCore1.MultiValuedAttribute;

public class BooleanHandlerTest {
    private BooleanHandler bh = new BooleanHandler();

    @Test
    public void decode() throws InvalidUser {
        Assert.assertEquals(true, bh.decode(true, null, null));
        Assert.assertEquals(false, bh.decode(false, null, null));
        Assert.assertEquals(true, bh.decodeXml(true, null, null));
        Assert.assertEquals(false, bh.decodeXml(false, null, null));
    }

    @Test
    public void decodeComplexXml() {
        MultiValuedAttribute multiValuedAttribute = MultiValuedAttribute.Factory.newInstance();
        XmlObjectBase xmlObject = (XmlObjectBase) multiValuedAttribute.addNewValue();
        xmlObject.setStringValue("true");

        Assert.assertEquals(true, bh.decodeXml(multiValuedAttribute, null, null));
    }

    @Test
    public void encode() {
        Assert.assertEquals(true, bh.encode(true, null, null));
        Assert.assertEquals(false, bh.encode(false, null, null));
        Assert.assertEquals(true, bh.encodeXml(true, null, null, null));
        Assert.assertEquals(false, bh.encodeXml(false, null, null, null));
    }

    @Test
    public void encodeComplexXml() {
        MultiValuedAttribute multiValuedAttribute = MultiValuedAttribute.Factory.newInstance();
        bh.encodeXml(false, null, null, multiValuedAttribute);
        XmlObjectBase xmlObject = (XmlObjectBase) multiValuedAttribute.getValue();
        Assert.assertEquals("false", xmlObject.getStringValue());
    }

    @Test
    public void merge() {
        Assert.assertEquals(true, bh.merge(true, null));
        Assert.assertEquals(false, bh.merge(false, null));
    }
}
