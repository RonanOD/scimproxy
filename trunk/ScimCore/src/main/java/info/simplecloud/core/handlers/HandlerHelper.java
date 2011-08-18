package info.simplecloud.core.handlers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerHelper {
    public static Object createInternalXmlObject(Object xmlObject, String name) throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        try {
            String methodName = "addNew";
            methodName += name.substring(0, 1).toUpperCase();
            methodName += name.substring(1);
            Method adder = xmlObject.getClass().getMethod(methodName, new Class<?>[] {});
            return adder.invoke(xmlObject, new Object[] {});
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
