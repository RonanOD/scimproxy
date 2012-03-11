package info.simplecloud.core.handlers;

import junit.framework.Assert;

import org.apache.xmlbeans.impl.values.XmlObjectBase;
import org.junit.Test;

import x0.scimSchemasCore1.MultiValuedAttribute;

public class StringHandlerTest {
    private static StringHandler sh = new StringHandler();

    @Test
    public void decode() {
        Assert.assertEquals("indata1", sh.decode("indata1", null, null));
        Assert.assertEquals("indata2", sh.decodeXml("indata2", null, null));
    }

    @Test
    public void decodeComplexXml() {
        MultiValuedAttribute multiValuedAttribute = MultiValuedAttribute.Factory.newInstance();
        XmlObjectBase xmlObject = (XmlObjectBase) multiValuedAttribute.addNewValue();
        xmlObject.setStringValue("indata1");

        Assert.assertEquals("indata1", sh.decodeXml(multiValuedAttribute, null, null));
    }

    @Test
    public void encode() {
        Assert.assertEquals("indata3", sh.encode("indata3", null, null, null));
        Assert.assertEquals("indata4", sh.encodeXml("indata4", null, null, null));
    }

    @Test
    public void encodeComplexXml() {
        MultiValuedAttribute multiValuedAttribute = MultiValuedAttribute.Factory.newInstance();
        sh.encodeXml("indata4", null, null, multiValuedAttribute);
        XmlObjectBase xmlObject = (XmlObjectBase) multiValuedAttribute.getValue();
        Assert.assertEquals("indata4", xmlObject.getStringValue());
    }

    @Test
    public void merge() {
        Assert.assertEquals("from", sh.merge("from", "to"));
    }
}
