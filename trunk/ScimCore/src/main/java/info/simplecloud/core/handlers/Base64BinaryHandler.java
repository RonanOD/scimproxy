package info.simplecloud.core.handlers;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.xmlbeans.impl.util.Base64;
import org.apache.xmlbeans.impl.values.XmlObjectBase;
import org.json.JSONException;
import org.json.JSONObject;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.coding.decode.IDecodeHandler;
import info.simplecloud.core.coding.encode.IEncodeHandler;
import info.simplecloud.core.merging.IMerger;

public class Base64BinaryHandler implements IDecodeHandler, IEncodeHandler, IMerger {

    @Override
    public Object decode(Object value, Object instance, MetaData internalMetaData) {
        try {
            String tmpValue = (String) value;
            return Base64.decode(tmpValue.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to decode Base64Binary data", e);
        }
    }

    @Override
    public Object decodeXml(Object value, Object newInstance, MetaData internalMetaData) {
        try {
            Method getValueMethod = value.getClass().getMethod("getValue");
            XmlObjectBase internalValue = (XmlObjectBase) getValueMethod.invoke(value);
            String tmpValue = internalValue.getStringValue();
            return Base64.decode(tmpValue.getBytes("UTF-8"));
        } catch (SecurityException e) {
            throw new RuntimeException("Failed to decode xml to Base64Binary", e);
        } catch (NoSuchMethodException e) {
            return decode(value, newInstance, internalMetaData);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to decode xml to Base64Binary", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to decode xml to Base64Binary", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to decode xml to Base64Binary", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to decode xml to Base64Binary", e);
        }
    }

    @Override
    public Object encode(Object me, List<String> includeAttributes, MetaData internalMetaData, JSONObject internalJsonObject) {
        try {
            return new String(Base64.encode((byte[]) me), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to encode Base64Binary into xml", e);
        }
    }

    @Override
    public Object encodeXml(Object me, List<String> includeAttributes, MetaData internalMetaData, Object xmlObject) {
        if (xmlObject == null) {
            return encode(me, includeAttributes, internalMetaData, null);
        }

        Object internalXmlObject;
        try {
            internalXmlObject = HandlerHelper.createInternalXmlObject(xmlObject, "Value");
            if (internalXmlObject == null) {
                throw new RuntimeException("Faield to encode string '" + me + "', could not create anytype node");
            }
            String tmpValue = new String(Base64.encode((byte[]) me), "UTF-8");
            ((XmlObjectBase) internalXmlObject).setStringValue(tmpValue);
            return internalXmlObject;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to encode Base64Binary into xml", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to encode Base64Binary into xml", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to encode Base64Binary into xml", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to encode Base64Binary into xml", e);
        }
    }

    @Override
    public Object merge(Object from, Object to) {
        return from;
    }
}
