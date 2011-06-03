package info.simplecloud.core;

import java.util.HashMap;
import java.util.Map;

public class ComplexType {
    private static final String ID_SEPARATOR = ".";
    private Map<String, Object> data         = new HashMap<String, Object>();

    public Object getAttribute(String id) {
        String[] ids = id.split("\\.");

        Object result = this;
        for (String localId : ids) {
            ComplexType current = null;

            if (result instanceof ComplexType) {
                current = (ComplexType) result;
            }

            result = current.data.get(localId);
            if (result == null) {
                return null;
            }

            if (id.endsWith(localId)) {
                return result;
            }
        }

        return null;
    }

    public ComplexType setAttribute(String id, Object attribute) {
        if (id == null || id.contains(ID_SEPARATOR)) {
            throw new IllegalArgumentException("id may not be null or contain '.', id: " + id);
        }

        this.data.put(id, attribute);
        return this;
    }
}
