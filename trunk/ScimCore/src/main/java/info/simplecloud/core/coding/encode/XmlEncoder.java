package info.simplecloud.core.coding.encode;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.Resource;
import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.annotations.Complex;
import info.simplecloud.core.annotations.Extension;
import info.simplecloud.core.coding.ReflectionHelper;
import info.simplecloud.core.exceptions.FactoryNotFoundException;
import info.simplecloud.core.handlers.ComplexHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;

import x0.scimSchemasCore1.Response;
import x0.scimSchemasCore1.Response.Resources;
import x0.scimSchemasCore1.ResponseDocument;

public class XmlEncoder implements IUserEncoder {
    private static final boolean PRETTY_PRINT = Boolean.parseBoolean(System.getProperty(JsonEncoder.class.getName() + ".PRETTY_PRINT",
                                                      "true"));

    @Override
    public String encode(Resource resource) {
        return this.encode(resource, null);
    }

    @Override
    public String encode(Resource resource, List<String> includeAttributes) {
        try {
            x0.scimSchemasCore1.Resource xmlResource = this.internalEncode(resource, includeAttributes);

            Complex complex = resource.getClass().getAnnotation(Complex.class);
            Class<?> factory = ReflectionHelper.getFactory(complex.xmlDoc());
            XmlObject doc = (XmlObject) factory.getMethod("newInstance").invoke(null);

            String name = complex.xmlType().getName();
            String setterName = "set";
            setterName += name.substring(name.lastIndexOf('.') + 1);
            doc.getClass().getMethod(setterName, complex.xmlType()).invoke(doc, xmlResource);

            return (PRETTY_PRINT ? doc.toString() : doc.xmlText());
        } catch (FactoryNotFoundException e) {
            throw new RuntimeException("Internal error, xml encode failed", e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Internal error, xml encode failed", e);
        } catch (SecurityException e) {
            throw new RuntimeException("Internal error, xml encode failed", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Internal error, xml encode failed", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Internal error, xml encode failed", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Internal error, xml encode failed", e);
        }
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

        ResponseDocument doc = ResponseDocument.Factory.newInstance();
        doc.setResponse(resp);
        return (PRETTY_PRINT ? doc.toString() : doc.xmlText());
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

            x0.scimSchemasCore1.Resource xmlResource = (x0.scimSchemasCore1.Resource) new ComplexHandler().encodeXml(resource,
                    attributesList, null, xmlObject);

            // TODO check include attributes for extensions too
            List<Object> extensions = resource.getExtensions();
            for (Object extension : extensions) {
                if (!extension.getClass().isAnnotationPresent(Extension.class)) {
                    throw new RuntimeException("The extension '" + extension.getClass().getName()
                            + "' has no namespace, try to add Extension annotation to class");
                }

                Extension extensionMetaData = extension.getClass().getAnnotation(Extension.class);

                for (Method method : extension.getClass().getMethods()) {
                    if (!method.isAnnotationPresent(Attribute.class)) {
                        continue;
                    }

                    Object data = method.invoke(extension);
                    if (data == null) {
                        continue;
                    }

                    MetaData metaData = new MetaData(method.getAnnotation(Attribute.class));
                    String attributeName = extensionMetaData.schema() + "." + metaData.getName();
                    if (attributesList != null && !attributesList.contains(attributeName)) {
                        continue;
                    }

                    Class<?> factory = ReflectionHelper.getFactory(metaData.getXmlDoc());
                    XmlObject doc = (XmlObject) factory.getMethod("newInstance").invoke(null);
                    Object innerXml = null;
                    try {
                        String adder = "addNew";
                        adder += metaData.getName().substring(0, 1).toUpperCase();
                        adder += metaData.getName().substring(1);
                        innerXml = doc.getClass().getMethod(adder).invoke(doc);
                    } catch (NoSuchMethodException e) {
                        // It is okay this is a simple type
                    }
                    IEncodeHandler encoder = metaData.getEncoder();
                    Object result = encoder.encodeXml(data, attributesList, metaData.getInternalMetaData(), innerXml);

                    String setter = "set";
                    setter += metaData.getName().substring(0, 1).toUpperCase();
                    setter += metaData.getName().substring(1);
                    ReflectionHelper.getMethod(setter, doc.getClass()).invoke(doc, result);

                    XmlCursor docCursor = doc.newCursor();
                    docCursor.toFirstChild();

                    XmlCursor cursor = xmlResource.newCursor();
                    cursor.toEndToken();
                    docCursor.moveXml(cursor);
                    cursor.dispose();
                    docCursor.dispose();
                }
            }

            return xmlResource;
        } catch (SecurityException e) {
            throw new RuntimeException("Internal error, encoding xml", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Internal error, encoding xml", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Internal error, encoding xml", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Internal error, encoding xml", e);
        } catch (FactoryNotFoundException e) {
            throw new RuntimeException("Internal error, encoding xml", e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Internal error, encoding xml", e);
        }
    }
}
