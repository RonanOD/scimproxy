package info.simplecloud.core.ng;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ComplexType2 {
    private Map<String, ScimPair<MetaData, Object>> data    = new HashMap<String, ScimPair<MetaData, Object>>();

    private boolean                                 touched = false;

    public ComplexType2() {
        // Do nothing
    }

    public ComplexType2(Map<String, ScimPair<MetaData, Object>> tree) {
        Set<String> keys = tree.keySet();

        for (String key : keys) {
            ScimPair<MetaData, Object> treePair = tree.get(key);
            ScimPair<MetaData, Object> localPair = new ScimPair<MetaData, Object>(treePair.meta, null);
            if (treePair.value != null) {
                localPair.value = new ComplexType2(treePair.value);
            }

            data.put(key, localPair);
        }
    }

    private ComplexType2(Object value) {
        ComplexType2 map = (ComplexType2) value;

        Set<String> keys = map.getKeys();
        for (String key : keys) {
            ScimPair<MetaData, Object> mapPair = map.getAttributeScimPair(key);
            ScimPair<MetaData, Object> localPair = new ScimPair<MetaData, Object>(mapPair.meta, null);
            if (mapPair.value != null) {
                localPair.value = new ComplexType2(mapPair.value);
            }

            data.put(key, localPair);
        }
    }

    public void setAttribute(String name, Object attribute) throws Exception {

        if (name.contains(".")) {
            String localName = name.substring(0, name.indexOf("."));
            String nextName = name.substring(name.indexOf(".") + 1, name.length());

            if (!data.containsKey(localName)) {
                throw new Exception("Has no attribute " + name);
            }

            ScimPair<MetaData, Object> storedAttribute = data.get(localName);

            if (storedAttribute.value instanceof ComplexType2) {
                ((ComplexType2) storedAttribute.value).setAttribute(nextName, attribute);
            } else {
                throw new Exception("Type missmatch'" + nextName + "' is not complex type");
            }
        } else {
            if (!data.containsKey(name)) {
                throw new Exception("Has no attribute " + name);
            }

            ScimPair<MetaData, Object> storedAttribute = data.get(name);

            storedAttribute.meta.validateType(attribute);

            storedAttribute.value = attribute;

            touched = true;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String name, Class<?> type) throws Exception {
        if (name.contains(".")) {
            String localName = name.substring(0, name.indexOf("."));
            String nextName = name.substring(name.indexOf(".") + 1, name.length());

            if (!data.containsKey(localName)) {
                throw new Exception("Has no attribute " + localName);
            }
            ScimPair<MetaData, Object> storedAttribute = data.get(localName);

            if (storedAttribute.value == null) {
                return null;
            }

            if (storedAttribute.value instanceof ComplexType2) {
                ComplexType2 storedComplexAttribute = (ComplexType2) storedAttribute.value;
                if (storedComplexAttribute.isTouched()) {
                    return (T)storedComplexAttribute.getAttribute(nextName, type);
                }
            } else {
                throw new Exception("Type missmatch'" + nextName + "' is not complex type");
            }
        }

        if (!data.containsKey(name)) {
            throw new Exception("Has no attribute '" + name + "'");
        }

        ScimPair<MetaData, Object> storedAttribute = data.get(name);

        if (storedAttribute.meta.getType() != type) {
            throw new Exception("Type missmatch, trying to get attribute of type '" + type + "' that is '" + storedAttribute.meta.getType()
                    + "'");
        }

        if (storedAttribute.value instanceof ComplexType2) {
            ComplexType2 storedComplexAttribute = (ComplexType2) storedAttribute.value;
            if (!storedComplexAttribute.isTouched()) {
                return null;
            }
        }

        return (T) storedAttribute.value;
    }

    public MetaData getMetaData(String name) throws Exception {
        if (name.contains(".")) {

            String localName = name.substring(0, name.indexOf("."));
            String nextName = name.substring(name.indexOf(".") + 1, name.length());

            ScimPair<MetaData, Object> storedAttribute = data.get(localName);

            if (storedAttribute.value == null) {
                return null;
            }

            if (storedAttribute.value instanceof ComplexType2) {
                return ((ComplexType2) storedAttribute.value).getMetaData(nextName);
            } else {
                throw new Exception("Type missmatch'" + nextName + "' is not complex type");
            }
        }

        if (!this.data.containsKey(name)) {
            return null;
        }

        return this.data.get(name).meta;
    }

    public ScimPair<MetaData, Object> getAttributeScimPair(String name) {
        return this.data.get(name);
    }

    public Set<String> getKeys() {
        return this.data.keySet();
    }

    private boolean isTouched() {
        return this.touched;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        Set<String> keys = this.data.keySet();
        
        for (String key : keys) {
            if(this.data.get(key).value == null) {
                continue;
            }

            sb.append(" ");
            sb.append(key);
            sb.append(": '");
            sb.append(this.data.get(key).value);
            sb.append("'");
            
        }

        sb.append(" )");
        return sb.toString();
    }
}
