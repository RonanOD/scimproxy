package info.simplecloud.core.coding.decode;

import info.simplecloud.core.Resource;
import info.simplecloud.core.User;
import info.simplecloud.core.annotations.Complex;
import info.simplecloud.core.coding.ReflectionHelper;
import info.simplecloud.core.exceptions.FactoryNotFoundException;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.handlers.ComplexHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.xmlbeans.XmlException;

import x0.scimSchemasCore1.Response;
import x0.scimSchemasCore1.Response.Resources;

public class XmlDecoder implements IResourceDecoder {

    @Override
    public void decode(String user, Resource data) throws InvalidUser {
        try {
            if (!data.getClass().isAnnotationPresent(Complex.class)) {
                throw new RuntimeException("Missing annotation complex on, '" + data.getClass().getName() + "'");
            }
            Complex complex = data.getClass().getAnnotation(Complex.class);
            Class<?> factory = ReflectionHelper.getFactory(complex.xmlDoc());
            Method parse = factory.getMethod("parse", String.class);
            Object xmlResource = parse.invoke(null, user);

            String name = complex.xmlType().getName();
            String getterName = "get";
            getterName += name.substring(name.lastIndexOf('.') + 1);
            Object xmlResource2 = xmlResource.getClass().getMethod(getterName).invoke(xmlResource);

            this.internalDecode(xmlResource2, data);

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
        try {
            Response resp = Response.Factory.parse(resourcesList);
            Resources xmlResources = resp.getResources();

            x0.scimSchemasCore1.Resource[] xmlResourceArray = xmlResources.getResourceArray();

            for (x0.scimSchemasCore1.Resource res : xmlResourceArray) {
                // TODO this is wrong
                User data = new User("tmp");
                Resource resource = internalDecode(res, data);
                resources.add(resource);
            }

        } catch (XmlException e) {
            throw new RuntimeException("Internal error, decoding xml", e);
        }
    }

    private Resource internalDecode(Object resources, Resource data) throws InvalidUser {
        return (Resource) new ComplexHandler().decodeXml(resources, data, null);
    }

}
