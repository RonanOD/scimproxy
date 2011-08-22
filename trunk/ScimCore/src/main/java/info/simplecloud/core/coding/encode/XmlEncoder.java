package info.simplecloud.core.coding.encode;

import info.simplecloud.core.Resource;
import info.simplecloud.core.annotations.Complex;
import info.simplecloud.core.coding.ReflectionHelper;
import info.simplecloud.core.exceptions.FactoryNotFoundException;
import info.simplecloud.core.handlers.ComplexHandler;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import x0.scimSchemasCore1.ResourceDocument;
import x0.scimSchemasCore1.Response.Resources;

public class XmlEncoder implements IUserEncoder {

    @Override
    public String encode(Resource resource) {
        return this.encode(resource, null);
    }

    @Override
    public String encode(List<Resource> resources) {
        Resources xmlResources =  Resources.Factory.newInstance();
        x0.scimSchemasCore1.Resource[] resourceArray = new x0.scimSchemasCore1.Resource[resources.size()];
        for (int i=0; i<resources.size(); i++) {
            resourceArray[i] = this.internalEncode(resources.get(i), null);
        }
        xmlResources.setResourceArray(resourceArray);
        
        return ResourceDocument.Factory.newInstance().set(xmlResources).xmlText();
    }

    @Override
    public String encode(Resource resource, List<String> attributesList) {
        try {
            x0.scimSchemasCore1.Resource xmlResource = this.internalEncode(resource, attributesList);

            StringWriter writer = new StringWriter();
            xmlResource.save(writer);
            return writer.toString();
        } catch (IOException e) {
            throw new RuntimeException("Internal error, encoding xml", e);
        } 
    }

    @Override
    public String encode(List<Resource> scimUsers, List<String> includeAttributes) {
        // TODO Auto-generated method stub
        return null;
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
