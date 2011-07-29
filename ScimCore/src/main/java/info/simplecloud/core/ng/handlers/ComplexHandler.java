package info.simplecloud.core.ng.handlers;

import info.simplecloud.core.ng.ComplexType2;
import info.simplecloud.core.ng.MetaData;
import info.simplecloud.core.ng.ScimPair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class ComplexHandler implements IHandler {

    public static final ComplexHandler INSTANCE = new ComplexHandler();

    private ComplexHandler() {
        // Do nothing
    }

    @Override
    public Object decode(Object jsonData, Object me, MetaData internalMetaData) {
        ComplexType2 complexObject = (ComplexType2) me;
        JSONObject jsonObject = (JSONObject) jsonData;

        @SuppressWarnings("unchecked")
        Iterator<String> keys = jsonObject.keys();

        while (keys.hasNext()) {
            try {
                String key = keys.next();
                MetaData metaData = complexObject.getMetaData(key);

                if (metaData == null) {
                    continue;
                }

                IHandler handler = metaData.getHandler();

                Object result = handler.decode(jsonObject.get(key), metaData.createType(), metaData.getInternalMetaData());

                complexObject.setAttribute(key, result);

            } catch (Exception e) {
                throw new RuntimeException("Internal error", e);
            }
        }

        return complexObject;
    }

    @Override
    public Object encode(Object me, MetaData internalMetaData, List<String> includeAttributes) {
        ComplexType2 complexObject = (ComplexType2) me;
        JSONObject result = new JSONObject();
        Set<String> keys = complexObject.getKeys();

        for (String key : keys) {
            ScimPair<MetaData, Object> attribute = complexObject.getAttributeScimPair(key);
            IHandler handler = attribute.meta.getHandler();
            if (includeAttributes != null && !includeAttributes.contains(key)) {
                // We have a list of attributes and this one is not in it.
                continue;
            }

            Object value = handler.encode(attribute.value, attribute.meta.getInternalMetaData(), stripLevel(includeAttributes));

            try {
                // TODO incert prefix
                // result.put(attribute.meta.getNameSpacePrefix() + key, value);
                result.put(key, value);
            } catch (JSONException e) {
                throw new RuntimeException("Internal Error", e);
            }
        }

        return result;
    }

    @Override
    public Object merge(Object from, Object to) {
        ComplexType2 fromComplex = (ComplexType2) from;
        ComplexType2 toComplex = (ComplexType2) to;

        Set<String> keys = fromComplex.getKeys();
        for (String key : keys) {
            if (key.equals("meta")) {
                // We do not merge meta
                continue;
            }

            ScimPair<MetaData, Object> fromPair = fromComplex.getAttributeScimPair(key);
            ScimPair<MetaData, Object> toPair = toComplex.getAttributeScimPair(key);

            if (fromPair.value == null) {
                // Nothing to merge here
                continue;
            }

            IHandler handler = fromPair.meta.getHandler();
            Object resultAttribute = handler.merge(fromPair.value, toPair.value);

            try {
                toComplex.setAttribute(key, resultAttribute);
            } catch (Exception e) {
                throw new RuntimeException("Internal error", e);
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

    @Override
    public String toString() {
        return "ComplexHandler";
    }
}
