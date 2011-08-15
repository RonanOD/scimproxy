package info.simplecloud.core.handlers;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.coding.decode.IDecodeHandler;
import info.simplecloud.core.coding.encode.IEncodeHandler;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.core.merging.IMerger;
import info.simplecloud.core.types.ComplexType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class ComplexHandler implements IDecodeHandler, IEncodeHandler, IMerger {

    @Override
    public Object decode(Object jsonData, Object me, MetaData internalMetaData) throws InvalidUser {
        ComplexType complexObject = (ComplexType) me;
        JSONObject jsonObject = (JSONObject) jsonData;

        Iterator<String> names = jsonObject.keys();

        while (names.hasNext()) {
            String name = names.next();
            if (!complexObject.hasAttribute(name)) {
                continue;
            }

            try {

                MetaData metadata;
                metadata = complexObject.getMetaData(name);

                IDecodeHandler decoder = metadata.getDecoder();

                Object result = decoder.decode(jsonObject.get(name), metadata.newInstance(), metadata.getInternalMetaData());

                complexObject.setAttribute(name, result);

            } catch (UnknownAttribute e) {
                throw new RuntimeException("Internal error decoding complex type", e);
            } catch (JSONException e) {
                throw new RuntimeException("Internal error decoding complex type", e);
            }
        }

        return complexObject;
    }

    @Override
    public Object encode(Object me, List<String> includeAttributes, MetaData internalMetaData) {
        ComplexType complexObject = (ComplexType) me;
        JSONObject result = new JSONObject();

        for (String name : complexObject.getNames()) {
            if (includeAttributes != null && !includeAttributes.isEmpty() && !includeAttributes.contains(name)) {
                // We have a list of attributes and this one is not in it.
                continue;
            }
            try {

                MetaData metaData = complexObject.getMetaData(name);
                Object value = complexObject.getAttribute(name);

                if (value != null) {
                    IEncodeHandler encoder = metaData.getEncoder();
                    Object encodedValue = encoder.encode(value, stripLevel(includeAttributes), metaData.getInternalMetaData());
                    result.put(name, encodedValue);
                }
            } catch (JSONException e) {
                throw new RuntimeException("Internal error, encoding conplex type", e);
            } catch (UnknownAttribute e) {
                throw new RuntimeException("Internal error, encoding conplex type", e);
            }
        }

        return result;
    }

    @Override
    public Object merge(Object from, Object to) {
        ComplexType toComplex = (ComplexType) to;
        ComplexType fromComplex = (ComplexType) from;

        for (String name : fromComplex.getNames()) {
            Object toAttribute;
            try {
                toAttribute = toComplex.getAttribute(name);
                Object fromAttribute = fromComplex.getAttribute(name);
                MetaData metaData = toComplex.getMetaData(name);

                IMerger merger = metaData.getMerger();

                if (toAttribute == null && fromAttribute != null) {
                    toComplex.setAttribute(name, fromAttribute);
                } else if (toAttribute != null && fromAttribute != null) {
                    Object result = merger.merge(fromAttribute, toAttribute);
                    toComplex.setAttribute(name, result);
                }
            } catch (UnknownAttribute e) {
                throw new RuntimeException("Internal merge error", e);
            }
        }

        return to;
    }

    private List<String> stripLevel(List<String> includeAttributes) {
        if (includeAttributes == null || includeAttributes.isEmpty()) {
            return null;
        }

        List<String> result = new ArrayList<String>();
        for (String attribute : includeAttributes) {
            if (attribute.contains(".")) {
                attribute = attribute.substring(attribute.indexOf("."), attribute.length());
                if (!attribute.isEmpty()) {
                    result.add(attribute);
                }
            }
        }

        return (result.isEmpty() ? null : result);
    }
}
