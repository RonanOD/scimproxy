package info.simplecloud.core.types;

import info.simplecloud.core.Attribute;
import info.simplecloud.core.execeptions.FailedToGetValue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ComplexType {
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

    public Integer getAttributeInteger(String id) {
        Object integer = this.getAttribute(id);
        return (integer == null ? null : (Integer) integer);
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

    public boolean equals(ComplexType otherCt, String... attributeIds) {

        for (String id : attributeIds) {
            Object me = this.data.get(id);
            Object other = otherCt.data.get(id);
            if (me != null && !me.equals(other)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == this) {
            return true;
        }

        if (!(otherObj instanceof ComplexType)) {
            return false;
        }
        ComplexType otherCt = (ComplexType) otherObj;

        if (this.data.size() != otherCt.data.size()) {
            return false;
        }

        return this.equals(otherCt, this.data.keySet().toArray(new String[] {}));
    }

    public String toString(String... attributeIds) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String id : attributeIds) {
            Object obj = this.data.get(id);
            if (obj != null) {
                stringBuilder.append(id);
                stringBuilder.append(": ");
                stringBuilder.append(obj.toString());
                stringBuilder.append(", ");
            }
        }

        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    @Override
    public String toString() {
        return this.toString(this.data.keySet().toArray(new String[] {}));
    }

    public void removeAttribute(String id) {
        this.data.remove(id);
    }

    public void merge(ComplexType from) throws FailedToGetValue {

        for (Method method : from.getClass().getMethods()) {
            if (method.isAnnotationPresent(Attribute.class)) {
                Attribute attribute = method.getAnnotation(Attribute.class);
                String attributeId = attribute.schemaName();
                if (this.getAttribute(attributeId) != null && this.getAttribute(attributeId) instanceof ComplexType) {
                    ComplexType fromTmp = (ComplexType) from.getAttribute(attributeId);
                    ComplexType toTmp = (ComplexType) this.getAttribute(attributeId);
                    if(fromTmp != null) {                        
                        toTmp.merge(fromTmp);
                    }
                } else if (from.getAttribute(attributeId) != null) {
                    this.setAttribute(attributeId, from.getAttribute(attributeId));
                }
            }
        }
    }

    private void copyAttributes(ComplexType from, String[] attributes) {
        if (attributes == null) {
            return;
        }

        for (String attribute : attributes) {
            if (from.getAttribute(attribute) != null) {
                this.setAttribute(attribute, from.getAttribute(attribute));
            }
        }
    }

}
