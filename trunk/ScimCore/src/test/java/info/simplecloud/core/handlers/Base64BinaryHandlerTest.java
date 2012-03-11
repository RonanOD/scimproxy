package info.simplecloud.core.handlers;

import java.util.Arrays;

import junit.framework.Assert;

import org.apache.xmlbeans.impl.values.XmlObjectBase;
import org.junit.Test;

import x0.scimSchemasCore1.MultiValuedAttribute;

public class Base64BinaryHandlerTest {
    private static Base64BinaryHandler bh      = new Base64BinaryHandler();
    private static byte[]              decoded = new byte[] { 104, 101, 108, 108, 111 };
    private static String              encoded = "aGVsbG8=";

    @Test
    public void decode() {
        Assert.assertTrue(Arrays.equals(decoded, (byte[]) bh.decode(encoded, null, null)));
        Assert.assertTrue(Arrays.equals(decoded, (byte[]) bh.decodeXml(encoded, null, null)));
    }

    @Test
    public void decodeComplexXml() {
        MultiValuedAttribute multiValuedAttribute = MultiValuedAttribute.Factory.newInstance();
        XmlObjectBase xmlObject = (XmlObjectBase) multiValuedAttribute.addNewValue();
        xmlObject.setStringValue(encoded);

        Assert.assertTrue(Arrays.equals(decoded, (byte[]) bh.decodeXml(multiValuedAttribute, null, null)));
    }

    @Test
    public void encode() {
        Assert.assertEquals(encoded, bh.encode(decoded, null, null, null));
        Assert.assertEquals(encoded, bh.encodeXml(decoded, null, null, null));
    }

    @Test
    public void encodeComplexXml() {
        MultiValuedAttribute multiValuedAttribute = MultiValuedAttribute.Factory.newInstance();
        bh.encodeXml(decoded, null, null, multiValuedAttribute);
        XmlObjectBase xmlObject = (XmlObjectBase) multiValuedAttribute.getValue();

        Assert.assertEquals(encoded, xmlObject.getStringValue());
    }

    @Test
    public void merge() {
        Assert.assertEquals(decoded, bh.merge(decoded, "to"));
    }
}
