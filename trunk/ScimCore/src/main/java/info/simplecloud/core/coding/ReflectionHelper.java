package info.simplecloud.core.coding;

import info.simplecloud.core.exceptions.FactoryNotFoundException;

import java.lang.reflect.Method;

public class ReflectionHelper {
    public static Method getMethod(String name, Class<?> clazz) throws NoSuchMethodException {
        for (Method method : clazz.getMethods()) {
            if (name.equals(method.getName())) {
                return method;
            }
        }
        throw new NoSuchMethodException("could not find: " + name + " on " + clazz.getName());
    }

    public static Class<?> getFactory(Class<?> obj) throws FactoryNotFoundException {
        for (Class<?> clazz : obj.getClasses()) {
            if (clazz.getName().endsWith("Factory")) {
                return clazz;
            }
        }
        throw new FactoryNotFoundException("faild to find factory for '" + obj.getClass().getName() + "'");
    }
}
