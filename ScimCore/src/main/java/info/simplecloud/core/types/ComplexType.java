package info.simplecloud.core.types;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.coding.ReflectionHelper;
import info.simplecloud.core.exceptions.UnknownAttribute;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class ComplexType {

    public <T> T getAttribute(String name) throws UnknownAttribute {
        if (name.contains(".")) {
            String localName = name.substring(0, name.indexOf("."));
            String nextName = name.substring(name.indexOf(".") + 1, name.length());

            Method method = findAttributeMethod(localName);
            ComplexType next = (ComplexType) invokeMethod(method, localName);
            if (next == null) {
                return null;
            }
            return next.getAttribute(nextName);
        } else {
            Method method = findAttributeMethod(name);
            return (T) invokeMethod(method, name);
        }
    }

    public ComplexType setAttribute(String name, Object attribute) throws UnknownAttribute {
        if (name.contains(".")) {
            String localName = name.substring(0, name.indexOf("."));
            String nextName = name.substring(name.indexOf(".") + 1, name.length());

            Method method = findAttributeMethod(localName);
            ComplexType next = (ComplexType) invokeMethod(method, localName);
            if (next == null) {
                next = this.getMetaData(localName).newInstance();
                this.setAttribute(localName, next);
            }
            next.setAttribute(nextName, attribute);
        } else {
            Method method = findAttributeMethod(name);
            if (method == null) {
                throw new UnknownAttribute("Has no method for attribute '" + name + "'");
            }
            
            String setter = "s" + method.getName().substring(1);
            Method setMethod = null;
            try {
                setMethod = ReflectionHelper.getMethod(setter, this.getClass());
                setMethod.invoke(this, attribute);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Failed to call setter '" + setter + "' on '" + setMethod.getDeclaringClass().getName()
                        + "' to set attribute '" + name + "'", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to call setter '" + setter + "' on '" + setMethod.getDeclaringClass().getName()
                        + "' to set attribute '" + name + "'", e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Failed to call setter '" + setter + "' on '" + setMethod.getDeclaringClass().getName()
                        + "' to set attribute '" + name + "'", e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Failed to call setter '" + setter + "' on '" + setMethod.getDeclaringClass().getName()
                        + "' to set attribute '" + name + "'", e);
            }
        }

        return this;
    }

    public void removeAttribute(String id) throws UnknownAttribute {
        this.setAttribute(id, null);
    }

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == this) {
            return true;
        }

        if (!(otherObj instanceof ComplexType)) {
            return false;
        }
        ComplexType otherComplex = (ComplexType) otherObj;

        for (Method method : this.getClass().getMethods()) {
            if (!method.isAnnotationPresent(Attribute.class)) {
                continue;
            }

            Attribute attribute = method.getAnnotation(Attribute.class);
            try {
                Object otherInternal = otherComplex.getAttribute(attribute.name());
                Object meInternal = method.invoke(this, new Object[] {});

                if (otherInternal == meInternal) {
                    continue;
                }
                if ((meInternal != null && !meInternal.equals(otherInternal))
                        || (otherInternal != null && !otherInternal.equals(meInternal))) {
                    return false;
                }

            } catch (UnknownAttribute e) {
                throw new RuntimeException("Internal error", e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Internal error", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Internal error", e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Internal error", e);
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");

        for (Method method : this.getClass().getMethods()) {
            if (!method.isAnnotationPresent(Attribute.class)) {
                continue;
            }

            try {
                Object attribute = method.invoke(this, new Object[] {});
                if(attribute == null) {
                    // we do not need to print null
                    continue;
                }
                Attribute metadata = method.getAnnotation(Attribute.class);
                sb.append(metadata.name()).append(": ");
                sb.append(attribute.toString()).append(", ");
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("toString internal error", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("toString internal error", e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("toString internal error", e);
            }
        }

        sb.append(")");
        return sb.toString();
    }

    private Object invokeMethod(Method method, String name) throws UnknownAttribute {
        try {
            if (method == null) {
                throw new UnknownAttribute("Could not find attribute '" + name + "'");
            }
            return method.invoke(this, new Object[] {});
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to read value for attribute '" + name + "'", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to read value for attribute '" + name + "'", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to read value for attribute '" + name + "'", e);
        }
    }

    private Method findAttributeMethod(String name) {
        Class<?> currentClass = this.getClass();
        for (Method method : currentClass.getMethods()) {
            if (!method.isAnnotationPresent(Attribute.class)) {
                // Not an attribute method, continue with next
                continue;
            }

            Attribute annotation = method.getAnnotation(Attribute.class);
            if (!name.equals(annotation.name())) {
                // Not correct method, continue with next
                continue;
            }

            return method;
        }

        return null;
    }

    public List<String> getNames() {
        List<String> result = new ArrayList<String>();

        for (Method method : this.getClass().getMethods()) {
            if (!method.isAnnotationPresent(Attribute.class)) {
                continue;
            }
            Attribute metaData = method.getAnnotation(Attribute.class);
            result.add(metaData.name());
        }

        return result;
    }

    public MetaData getMetaData(String name) throws UnknownAttribute {
        return this.getMetaData(name, this);
    }
    
    public MetaData getMetaData(String name, Object on) throws UnknownAttribute {
        for (Method method : on.getClass().getMethods()) {
            if (!method.isAnnotationPresent(Attribute.class)) {
                // Not an attribute method, continue with next
                continue;
            }

            Attribute metaData = method.getAnnotation(Attribute.class);

            if (name.equals(metaData.name())) {
                return new MetaData(metaData);
            }
        }

        throw new UnknownAttribute("Could not find metadata for attribute '" + name + "'");
    }

    public boolean hasAttribute(String name) {
        try {
            this.getMetaData(name);
            return true;
        } catch (UnknownAttribute e) {
            return false;
        }
    }
}
