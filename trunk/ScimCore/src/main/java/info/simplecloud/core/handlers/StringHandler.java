package info.simplecloud.core.handlers;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.coding.decode.IDecodeHandler;
import info.simplecloud.core.coding.encode.IEncodeHandler;
import info.simplecloud.core.merging.IMerger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.xmlbeans.impl.values.XmlObjectBase;

public class StringHandler implements IDecodeHandler, IEncodeHandler, IMerger {

    @Override
    public Object decode(Object value, Object instance, MetaData internalMetaData) {
        return HandlerHelper.typeCheck(value, String.class);
    }

    @Override
    public Object decodeXml(Object value, Object newInstance, MetaData internalMetaData) {
        try {
            Method getValueMethod = value.getClass().getMethod("getValue");
            XmlObjectBase internalValue = (XmlObjectBase) getValueMethod.invoke(value);
            return internalValue.getStringValue();
        } catch (SecurityException e) {
            throw new RuntimeException("Failed to decode xml to string", e);
        } catch (NoSuchMethodException e) {
            return HandlerHelper.typeCheck(value, String.class);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to decode xml to string", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to decode xml to string", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to decode xml to string", e);
        }
    }

    @Override
    public Object encode(Object me, List<String> includeAttributes, MetaData internalMetaData) {
        return HandlerHelper.typeCheck(me, String.class);
    }

    @Override
    public Object encodeXml(Object me, List<String> includeAttributes, MetaData internalMetaData, Object xmlObject) {
        if(xmlObject == null) {            
            return HandlerHelper.typeCheck(me, String.class);
        }
        
        Object internalXmlObject;
        try {
            internalXmlObject = HandlerHelper.createInternalXmlObject(xmlObject, "Value");
            if(internalXmlObject == null) {
                throw new RuntimeException("Faield to encode string '" + me +"', could not create anytype node");
            }
            ((XmlObjectBase)internalXmlObject).setStringValue((String)me);
            return internalXmlObject;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to encode string '" + me + "' into xml", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to encode string '" + me + "' into xml", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to encode string '" + me + "' into xml", e);
        }
    }

    @Override
    public Object merge(Object from, Object to) {
        return HandlerHelper.typeCheck(from, String.class);
    }
}
