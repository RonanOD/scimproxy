package info.simplecloud.core.coding;

import java.lang.reflect.Method;

public class ReflectionHelper {
    public static Method getMethod(String name, Class<?> clazz) throws NoSuchMethodException {
        for(Method method : clazz.getMethods()) {
            if(name.equals(method.getName())) {
                return method;
            }
        }
        throw new NoSuchMethodException("could not find: " + name + " on " + clazz.getName());
    }
}
