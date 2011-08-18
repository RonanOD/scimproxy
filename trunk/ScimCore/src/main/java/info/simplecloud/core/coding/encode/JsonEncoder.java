package info.simplecloud.core.coding.encode;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.Resource;
import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.annotations.Extension;
import info.simplecloud.core.exceptions.EncodingFailed;
import info.simplecloud.core.handlers.ComplexHandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonEncoder implements IUserEncoder {
    private static final int INDENT_SIZE = Integer.parseInt(System.getProperty(JsonEncoder.class.getName() + ".INDENT_SIZE", "2"));

    @Override
    public String encode(Resource data) throws EncodingFailed {
        return encode(data, null);
    }

    @Override
    public String encode(Resource data, List<String> includeAttributes) throws EncodingFailed {
        try {
            JSONObject obj = internalEncode(data, includeAttributes);
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
    public String encode(List<Resource> scimUsers, List<String> includeAttributes) throws EncodingFailed {
        try {
            JSONObject result = new JSONObject();

            if (scimUsers == null) {
                scimUsers = new ArrayList<Resource>();
            }

            // TODO: Should this be done in core? Return the JSON list of more
            // resporces when you send an List into encode method?
            JSONArray users = new JSONArray();
            int counter = 0;
            for (Resource scimUser : scimUsers) {
                JSONObject o = internalEncode(scimUser, includeAttributes);
                if (o != null) {
                    users.put(o);
                    counter++;
                }
            }

            result.put("totalResults", counter);

            result.put("entry", users);
            // TODO: SPEC: REST: Return meta location. Should location be sent
            // to this method or always include it in storage for each user?

            return INDENT_SIZE == -1 ? result.toString() : result.toString(INDENT_SIZE);
        } catch (JSONException e) {
            throw new EncodingFailed("Failed to build response user set", e);
        }
    }

    private JSONObject internalEncode(Resource data, List<String> attributesList) throws JSONException {
        JSONObject result = (JSONObject) new ComplexHandler().encode(data, attributesList, null);

        for (Object extension : data.getExtensions()) {

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

                if (attributesList != null && !attributesList.contains(metaData.getName())) {
                    continue;
                }

                IEncodeHandler encoder = metaData.getEncoder();

                try {
                    Object object = method.invoke(extension);
                    if (object != null) {
                        Object encodedValue = encoder.encode(extensionJson, attributesList, metaData.getInternalMetaData());
                        extensionJson.put(metaData.getName(), encodedValue);
                    }
                } catch (Exception e) {
                    throw new EncodingFailed("failed to read data from object", e);
                }

            }
            
            result.put(extensionMetaData.schema(), extensionMetaData);
        }

        return result;
    }
}
