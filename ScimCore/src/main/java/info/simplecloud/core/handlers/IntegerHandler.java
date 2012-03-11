package info.simplecloud.core.handlers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.xmlbeans.impl.values.XmlObjectBase;
import org.json.JSONException;
import org.json.JSONObject;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.coding.decode.IDecodeHandler;
import info.simplecloud.core.coding.encode.IEncodeHandler;
import info.simplecloud.core.merging.IMerger;

public class IntegerHandler implements IDecodeHandler, IEncodeHandler, IMerger {

    @Override
    public Object decode(Object value, Object instance, MetaData internalMetaData) {
        if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        return HandlerHelper.typeCheck(value, Integer.class);
    }

    @Override
    public Object decodeXml(Object value, Object newInstance, MetaData internalMetaData) {
        if (value instanceof String) {
            return Integer.parseInt((String) value);
        }

        try {
            Method getValueMethod = value.getClass().getMethod("getValue");
            XmlObjectBase internalValue = (XmlObjectBase) getValueMethod.invoke(value);
            String tmpValue = internalValue.getStringValue();
            return Integer.parseInt(tmpValue);
        } catch (SecurityException e) {
            throw new RuntimeException("Failed to decode xml to integer", e);
        } catch (NoSuchMethodException e) {
            return HandlerHelper.typeCheck(value, Integer.class);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to decode xml to integer", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to decode xml to integer", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to decode xml to integer", e);
        }

    }

    @Override
    public Object encode(Object me, List<String> includeAttributes, MetaData internalMetaData, JSONObject internalJsonObject) {
        return HandlerHelper.typeCheck(me, Integer.class);
    }

    @Override
    public Object encodeXml(Object me, List<String> includeAttributes, MetaData internalMetaData, Object xmlObject) {
        if (xmlObject == null) {
            return HandlerHelper.typeCheck(me, Integer.class);
        }

        Object internalXmlObject;
        try {
            internalXmlObject = HandlerHelper.createInternalXmlObject(xmlObject, "Value");
            if (internalXmlObject == null) {
                throw new RuntimeException("Faield to encode integer '" + me + "', could not create anytype node");
            }
            String tmpValue = Integer.toString((Integer) me);
            ((XmlObjectBase) internalXmlObject).setStringValue(tmpValue);
            return internalXmlObject;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to encode integer '" + me + "' into xml", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to encode integer '" + me + "' into xml", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to encode integer '" + me + "' into xml", e);
        }
    }

    @Override
    public Object merge(Object from, Object to) {
        return HandlerHelper.typeCheck(from, Integer.class);
    }
}
