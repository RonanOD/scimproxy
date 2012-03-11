package info.simplecloud.core.coding.encode;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.Resource;
import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.annotations.Extension;
import info.simplecloud.core.exceptions.EncodingFailed;
import info.simplecloud.core.handlers.ComplexHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonEncoder implements IUserEncoder {
    private static final int INDENT_SIZE = Integer.parseInt(System.getProperty(JsonEncoder.class.getName() + ".INDENT_SIZE", "2"));

    @Override
    public String encode(Resource resource) throws EncodingFailed {
        return encode(resource, null);
    }

    @Override
    public String encode(Resource resource, List<String> includeAttributes) throws EncodingFailed {
        try {
            JSONObject obj = internalEncode(resource, includeAttributes);
            if (obj != null) {
                return obj.toString(2);
            } else {
                return null;
            }
        } catch (JSONException e) {
            throw new EncodingFailed("Failed to build user", e);
        }
    }

    @Override
    public String encode(List<Resource> scimUsers) throws EncodingFailed {
        return this.encode(scimUsers, null);
    }

    @Override
    public String encode(List<Resource> resources, List<String> includeAttributes) throws EncodingFailed {
        try {
            JSONObject result = new JSONObject();

            if (resources == null) {
                resources = new ArrayList<Resource>();
            }

            // TODO: Should this be done in core? Return the JSON list of more
            // resporces when you send an List into encode method?
            JSONArray users = new JSONArray();
            int counter = 0;
            for (Resource scimUser : resources) {
                JSONObject o = internalEncode(scimUser, includeAttributes);
                if (o != null) {
                    users.put(o);
                    counter++;
                }
            }

            result.put("totalResults", counter);
            result.put("schemas", new JSONArray("[\"urn:scim:schemas:core:1.0\"]"));
            result.put("Resources", users);
            // TODO: SPEC: REST: Return meta location. Should location be sent
            // to this method or always include it in storage for each user?

            return INDENT_SIZE == -1 ? result.toString() : result.toString(INDENT_SIZE);
        } catch (JSONException e) {
            throw new EncodingFailed("Failed to build response user set", e);
        }
    }

    private JSONObject internalEncode(Resource resource, List<String> includeAttributes) throws JSONException {
        JSONObject result = (JSONObject) new ComplexHandler().encode(resource, includeAttributes, null, null);

        for (Object extension : resource.getExtensions()) {

            if (!extension.getClass().isAnnotationPresent(Extension.class)) {
                throw new RuntimeException("The extension '" + extension.getClass().getName()
                        + "' has no namespace, try to add Extension annotation to class");
            }

            Extension extensionMetaData = extension.getClass().getAnnotation(Extension.class);

            JSONObject extensionJson = new JSONObject();

            for (Method method : extension.getClass().getMethods()) {
                if (!method.isAnnotationPresent(Attribute.class)) {
                    continue;
                }

                MetaData metaData = new MetaData(method.getAnnotation(Attribute.class));
                String attributeName = extensionMetaData.schema() + "." + metaData.getName();
                if (includeAttributes != null && !includeAttributes.contains(attributeName)) {
                    continue;
                }

                IEncodeHandler encoder = metaData.getEncoder();

                try {
                    Object value = method.invoke(extension);
                    if (value != null) {
                        Object encodedValue = encoder.encode(value, includeAttributes, metaData.getInternalMetaData(), null);
                        extensionJson.put(metaData.getName(), encodedValue);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Internal error, failed to read data from object", e);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Internal error, failed to read data from object", e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException("Internal error, failed to read data from object", e);
                }

            }

            result.put(extensionMetaData.schema(), extensionJson);
        }

        return result;
    }
}
