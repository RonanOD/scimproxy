package info.simplecloud.core.coding.decode;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.Resource;
import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.annotations.Complex;
import info.simplecloud.core.annotations.Extension;
import info.simplecloud.core.coding.ReflectionHelper;
import info.simplecloud.core.exceptions.FactoryNotFoundException;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.handlers.ComplexHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.xmlbeans.XmlAnySimpleType;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

import x0.scimSchemasCore1.ResourceDocument;
import x0.scimSchemasCore1.Response;
import x0.scimSchemasCore1.Response.Resources;
import x0.scimSchemasCore1.ResponseDocument;

public class XmlDecoder implements IResourceDecoder {

    @Override
    public void decode(String resourceString, Resource resource) throws InvalidUser {
        try {
            if (!resource.getClass().isAnnotationPresent(Complex.class)) {
                throw new RuntimeException("Missing annotation complex on, '" + resource.getClass().getName() + "'");
            }
            Complex complex = resource.getClass().getAnnotation(Complex.class);
            Class<?> factory = ReflectionHelper.getFactory(complex.xmlDoc());
            Method parse = factory.getMethod("parse", String.class);
            XmlObject xmlDoc = (XmlObject) parse.invoke(null, resourceString);

            String name = complex.xmlType().getName();
            String getterName = "get";
            getterName += name.substring(name.lastIndexOf('.') + 1);
            Object xmlResource2 = xmlDoc.getClass().getMethod(getterName).invoke(xmlDoc);

            this.internalDecode(xmlResource2, resource);

            for (Object extension : resource.getExtensions()) {
                if (!extension.getClass().isAnnotationPresent(Extension.class)) {
                    throw new RuntimeException("Extension '" + extension.getClass() + "' misses extension annotation");
                }

                Extension extensionMetaData = extension.getClass().getAnnotation(Extension.class);

                for (Method method : extension.getClass().getMethods()) {
                    if (!method.isAnnotationPresent(Attribute.class)) {
                        continue;
                    }

                    MetaData metaData = new MetaData(method.getAnnotation(Attribute.class));
                    XmlCursor cursor = xmlDoc.newCursor();
                    cursor.toFirstChild();
                    if (!cursor.toChild(extensionMetaData.schema(), metaData.getName())) {
                        continue;
                    }

                    Object value = cursor.getObject();
                    if (value instanceof XmlAnySimpleType) {
                        value = ((XmlAnySimpleType) value).getStringValue();
                    }

                    IDecodeHandler decoder = metaData.getDecoder();
                    Object decodedValue = decoder.decodeXml(value, metaData.newInstance(), metaData.getInternalMetaData());

                    String setterName = "s" + method.getName().substring(1);
                    Method setter = extension.getClass().getMethod(setterName, decodedValue.getClass());
                    setter.invoke(extension, decodedValue);
                }
            }
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
    public void decode(String resourcesListString, List<Resource> resources, Class<?> type) throws InvalidUser {
        try {
        	ResponseDocument doc = ResponseDocument.Factory.parse(resourcesListString);
            Response resp = doc.getResponse();
            Resources xmlResources = resp.getResources();

            x0.scimSchemasCore1.Resource[] xmlResourceArray = xmlResources.getResourceArray();

            for (x0.scimSchemasCore1.Resource res : xmlResourceArray) {
                Resource resource = internalDecode(res, (Resource)type.newInstance());
                resources.add(resource);
            }

        } catch (XmlException e) {
            throw new InvalidUser("Failed to parse resource set ", e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Internal error, decoding xml", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Internal error, decoding xml", e);
        }
    }

    private Resource internalDecode(Object resources, Resource data) throws InvalidUser {
        return (Resource) new ComplexHandler().decodeXml(resources, data, null);
    }
}
