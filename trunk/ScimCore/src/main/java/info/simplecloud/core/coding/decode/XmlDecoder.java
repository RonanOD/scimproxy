package info.simplecloud.core.coding.decode;

import info.simplecloud.core.Resource;
import info.simplecloud.core.annotations.Complex;
import info.simplecloud.core.coding.ReflectionHelper;
import info.simplecloud.core.exceptions.FactoryNotFoundException;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.handlers.ComplexHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import x0.scimSchemasCore1.ResourceDocument;
import x0.scimSchemasCore1.Response.Resources;

public class XmlDecoder implements IResourceDecoder {

    @Override
    public void decode(String user, Resource data) throws InvalidUser {
        try {
            if (!data.getClass().isAnnotationPresent(Complex.class)) {
                throw new RuntimeException("Missing annotation complex on, '" + data.getClass().getName() + "'");
            }
            Complex complex = data.getClass().getAnnotation(Complex.class);
            Class<?> factory = ReflectionHelper.getFactory(complex.getClass());
            Method parse = factory.getMethod("parse", String.class);
            Object xmlResource = parse.invoke(null, user);
            
            data = (Resource) new ComplexHandler().decodeXml(xmlResource, data, null);

            // TODO read extensions
        } catch (SecurityException e) {
            throw new RuntimeException("Internal error, decoding xml", e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Internal error, decoding xml", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Internal error, decoding xml", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Internal error, decoding xml", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Internal error, decoding xml", e);
        } catch (FactoryNotFoundException e) {
            throw new RuntimeException("Internal error, decoding xml", e);
        }
    }

    @Override
    public void decode(String resourcesList, List<Resource> resources) throws InvalidUser {
        // TODO Auto-generated method stub
    }

}
