package info.simplecloud.core.coding.decode;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.Resource;
import info.simplecloud.core.User;
import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.annotations.Extension;
import info.simplecloud.core.coding.ReflectionHelper;
import info.simplecloud.core.exceptions.DecodingFailed;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.UnknownAttribute;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonDecoder implements IResourceDecoder {

    @Override
    public void decode(String user, Resource data) throws InvalidUser {
        try {
            JSONObject userJson = new JSONObject(user);

            // Decode core attributes
            for (String name : data.getNames()) {
                try {
                    if (!userJson.has(name)) {
                        continue;
                    }
                    Object value = userJson.get(name);
                    MetaData metaData = data.getMetaData(name);
                    IDecodeHandler decoder = metaData.getDecoder();
                    Object decodedValue = decoder.decode(value, metaData.newInstance(), metaData.getInternalMetaData());

                    data.setAttribute(name, decodedValue);
                } catch (UnknownAttribute e) {
                    new RuntimeException("Internal error", e);
                }
            }

            // Decode extension attributes
            for (Object extension : data.getExtensions()) {

                if (!extension.getClass().isAnnotationPresent(Extension.class)) {
                    throw new InvalidUser("The extension '" + extension.getClass().getName()
                            + "' has no namespace, try to add Extension annotation to class");
                }

                Extension ExtensionMetaData = extension.getClass().getAnnotation(Extension.class);

                if (!userJson.has(ExtensionMetaData.schema())) {
                    continue;
                }
                try {
                    JSONObject extensionJson = userJson.getJSONObject(ExtensionMetaData.schema());
                    for (Method method : extension.getClass().getMethods()) {
                        if (!method.isAnnotationPresent(Attribute.class)) {
                            continue;
                        }
                        MetaData metaData = new MetaData(method.getAnnotation(Attribute.class));

                        if (!extensionJson.has(metaData.getName())) {
                            continue;
                        }

                        Object value = userJson.get(metaData.getName());
                        IDecodeHandler decoder = metaData.getDecoder();
                        Object type = metaData.newInstance();

                        Object decodedValue = decoder.decode(value, type, metaData.getInternalMetaData());

                        // TODO think of something smarter
                        String setter = "s" + method.getName().substring(1);

                        try {
                            Method setMethod = ReflectionHelper.getMethod(setter, extension.getClass());
                            setMethod.invoke(extension, decodedValue);
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException("Internal error, failed to invoke: " + setter + " with arg: " + decodedValue, e);
                        } catch (IllegalArgumentException e) {
                            throw new RuntimeException("Internal error, failed to invoke: " + setter + " with arg: " + decodedValue, e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Internal error, failed to invoke: " + setter + " with arg: " + decodedValue, e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException("Internal error, failed to invoke: " + setter + " with arg: " + decodedValue, e);
                        }

                    }
                } catch (JSONException e) {
                    // No attribute by this name, no big deal take text
                }
            }

        } catch (JSONException e) {
            throw new InvalidUser("Failed to parse user", e);
        }
    }

    @Override
    public void decode(String userList, List<Resource> resources) throws InvalidUser {

        try {
        	if(userList != null && !"".equals(userList)) {
	        		
	            JSONObject userListJson = new JSONObject(userList);
	            if (userListJson.has("entry")) {
	                JSONArray jsonUsers = userListJson.getJSONArray("entry");
	                for (int i = 0; i < jsonUsers.length(); i++) {
	                    JSONObject user = jsonUsers.getJSONObject(i);
	
	                    // TODO this is wrong
	                    User data = new User("tmp");
	                    decode(user.toString(), data);
	                    resources.add(data);
	                }
	            }
        	}

        } catch (JSONException e) {
            throw new InvalidUser("Failed to parse user", e);
        }

    }

}
