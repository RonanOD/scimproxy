package info.simplecloud.core.coding.decode;

import info.simplecloud.core.Resource;
import info.simplecloud.core.annotations.Complex;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.handlers.ComplexHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.xmlbeans.XmlException;

import x0.scimSchemasCore1.Address;
import x0.scimSchemasCore1.Name;
import x0.scimSchemasCore1.PluralAttribute;
import x0.scimSchemasCore1.User;
import x0.scimSchemasCore1.User.Addresses;
import x0.scimSchemasCore1.User.Emails;

public class XmlDecoder implements IResourceDecoder {

    @Override
    public void decode(String user, Resource data) throws InvalidUser {
        try {
            if (!data.getClass().isAnnotationPresent(Complex.class)) {
                throw new RuntimeException("Missing annotation complex on, '" + data.getClass().getName() + "'");
            }
            Complex complex = data.getClass().getAnnotation(Complex.class);
            Object Factory = complex.xmlType().getField("Factory");
            Method parse = Factory.getClass().getMethod("parse", String.class);
            Object xmlResource = parse.invoke(null, user);
            
            data = (Resource) new ComplexHandler().decodeXml(xmlResource, data, null);

            // TODO read extensions
        } catch (SecurityException e) {
            throw new RuntimeException("Internal error, decoding xml", e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Internal error, decoding xml", e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Internal error, decoding xml", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Internal error, decoding xml", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Internal error, decoding xml", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Internal error, decoding xml", e);
        }
    }

    @Override
    public void decode(String userList, List<Resource> users) throws InvalidUser {
        // TODO Auto-generated method stub

    }

}
