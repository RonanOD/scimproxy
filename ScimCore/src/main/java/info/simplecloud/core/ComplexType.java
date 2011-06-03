package info.simplecloud.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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

    public String getAttributeString(String id) {
        Object string = this.getAttribute(id);
        return (string == null ? null : (String) string);
    }

    public Calendar getAttributeCalendar(String id) {
        Object calendar = this.getAttribute(id);
        return (calendar == null ? null : (Calendar) calendar);
    }

    public ComplexType setAttribute(String id, Object attribute) {
        if (id == null || id.contains(ID_SEPARATOR)) {
            throw new IllegalArgumentException("id may not be null or contain '.', id: " + id);
        }

        this.data.put(id, attribute);
        return this;
    }

    public void addPluralAttribute(String id, PluralType item) {
        List<PluralType> list = (List<PluralType>) this.getAttribute(id);
        if (list != null) {
            list = new ArrayList<PluralType>();
            this.setAttribute(id, list);
        }
        list.add(item);
    }
}
