package info.simplecloud.core.handlers;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.coding.decode.IDecodeHandler;
import info.simplecloud.core.coding.encode.IEncodeHandler;
import info.simplecloud.core.merging.IMerger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.xmlbeans.impl.values.XmlObjectBase;
import org.json.JSONException;
import org.json.JSONObject;

public class BooleanHandler implements IDecodeHandler, IEncodeHandler, IMerger {

    @Override
    public Object decode(Object value, Object instance, MetaData internalMetaData) {
        if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        }
        return HandlerHelper.typeCheck(value, Boolean.class);
    }

    @Override
    public Object decodeXml(Object value, Object newInstance, MetaData internalMetaData) {
        if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        }

        try {
            Method getValueMethod = value.getClass().getMethod("getValue");
            XmlObjectBase internalValue = (XmlObjectBase) getValueMethod.invoke(value);
            String tmpValue = internalValue.getStringValue();
            return Boolean.parseBoolean(tmpValue);
        } catch (SecurityException e) {
            throw new RuntimeException("Failed to decode xml to boolean", e);
        } catch (NoSuchMethodException e) {
            return HandlerHelper.typeCheck(value, Boolean.class);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to decode xml to boolean", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to decode xml to boolean", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to decode xml to boolean", e);
        }
    }

    @Override
    public Object encode(Object me, List<String> includeAttributes, MetaData internalMetaData, JSONObject internalJsonObject) {
        return HandlerHelper.typeCheck(me, Boolean.class);
    }

    @Override
    public Object encodeXml(Object me, List<String> includeAttributes, MetaData internalMetaData, Object xmlObject) {

        if (xmlObject == null) {
            return HandlerHelper.typeCheck(me, Boolean.class);
        }

        Object internalXmlObject;
        try {
            internalXmlObject = HandlerHelper.createInternalXmlObject(xmlObject, "Value");
            if (internalXmlObject == null) {
                throw new RuntimeException("Faield to encode boolean '" + me + "', could not create anytype node");
            }
            String tmpValue= Boolean.toString((Boolean) me);
            ((XmlObjectBase) internalXmlObject).setStringValue(tmpValue);
            return internalXmlObject;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to encode boolean '" + me + "' into xml", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to encode boolean '" + me + "' into xml", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to encode boolean '" + me + "' into xml", e);
        }
    }

    @Override
    public Object merge(Object from, Object to) {
        return HandlerHelper.typeCheck(from, Boolean.class);
    }

}
