package info.simplecloud.core.coding.encode;

import info.simplecloud.core.Resource;
import info.simplecloud.core.annotations.Complex;
import info.simplecloud.core.coding.ReflectionHelper;
import info.simplecloud.core.exceptions.FactoryNotFoundException;
import info.simplecloud.core.handlers.ComplexHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import x0.scimSchemasCore1.ResourceDocument;
import x0.scimSchemasCore1.Response;
import x0.scimSchemasCore1.Response.Resources;
import x0.scimSchemasCore1.ResponseDocument;

public class XmlEncoder implements IUserEncoder {

    @Override
    public String encode(Resource resource) {
        return this.encode(resource, null);
    }

    @Override
    public String encode(Resource resource, List<String> attributesList) {
        x0.scimSchemasCore1.Resource xmlResource = this.internalEncode(resource, attributesList);
        ResourceDocument doc = ResourceDocument.Factory.newInstance();
        doc.set(xmlResource);
        
        return doc.xmlText();
    }

    @Override
    public String encode(List<Resource> resources) {
        return this.encode(resources, null);
    }

    @Override
    public String encode(List<Resource> resources, List<String> includeAttributes) {
        Response resp = Response.Factory.newInstance();
        Resources xmlResources = resp.addNewResources();
        resp.setTotalResults(resources.size());
        x0.scimSchemasCore1.Resource[] xmlResourceArray = new x0.scimSchemasCore1.Resource[resources.size()];

        for (int i = 0; i < resources.size(); i++) {
            xmlResourceArray[i] = this.internalEncode(resources.get(i), includeAttributes);
        }
        xmlResources.setResourceArray(xmlResourceArray);

        return ResponseDocument.Factory.newInstance().set(resp).xmlText();
    }

    private Object createXmlObject(Resource resource) {
        try {
            if (!resource.getClass().isAnnotationPresent(Complex.class)) {
                throw new RuntimeException("Missing annotation complex on, '" + resource.getClass().getName() + "'");
            }
            Complex complexMetadata = resource.getClass().getAnnotation(Complex.class);
            Class<?> factory = ReflectionHelper.getFactory(complexMetadata.xmlType());
            Method parse = factory.getMethod("newInstance");
            return parse.invoke(null);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Internal error, encoding xml", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Internal error, encoding xml", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Internal error, encoding xml", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Internal error, encoding xml", e);
        } catch (FactoryNotFoundException e) {
            throw new RuntimeException("Internal error, encoding xml", e);
        }
    }

    private x0.scimSchemasCore1.Resource internalEncode(Resource resource, List<String> attributesList) {
        try {
            Object xmlObject = createXmlObject(resource);

            x0.scimSchemasCore1.Resource xmlResource = (x0.scimSchemasCore1.Resource) new ComplexHandler().encodeXml(resource, null, null,
                    xmlObject);

            // TODO encode extensions

            return xmlResource;
        } catch (SecurityException e) {
            throw new RuntimeException("Internal error, encoding xml", e);
        }
    }
}
