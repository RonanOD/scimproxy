package info.simplecloud.core.coding.decode;

import info.simplecloud.core.Attribute;
import info.simplecloud.core.ScimUser;
import info.simplecloud.core.coding.ReflectionHelper;
import info.simplecloud.core.coding.handlers.CalendarHandler;
import info.simplecloud.core.coding.handlers.ComplexTypeHandler;
import info.simplecloud.core.coding.handlers.ITypeHandler;
import info.simplecloud.core.coding.handlers.IntegerHandler;
import info.simplecloud.core.coding.handlers.PluralComplexListTypeHandler;
import info.simplecloud.core.coding.handlers.PluralSimpleListTypeHandler;
import info.simplecloud.core.coding.handlers.StringHandler;
import info.simplecloud.core.coding.handlers.StringListHandler;
import info.simplecloud.core.execeptions.FailedToSetValue;
import info.simplecloud.core.execeptions.InvalidUser;
import info.simplecloud.core.execeptions.UnhandledAttributeType;
import info.simplecloud.core.execeptions.UnknownType;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonDecoder implements IUserDecoder {
    private static String[]                  names        = { "json", "JSON" };
    private static Map<String, ITypeHandler> typeHandlers = new HashMap<String, ITypeHandler>();
    static {
        typeHandlers.put(StringHandler.class.getName(), new StringHandler());
        typeHandlers.put(IntegerHandler.class.getName(), new IntegerHandler());
        typeHandlers.put(CalendarHandler.class.getName(), new CalendarHandler());
        typeHandlers.put(ComplexTypeHandler.class.getName(), new ComplexTypeHandler());
        typeHandlers.put(PluralSimpleListTypeHandler.class.getName(), new PluralSimpleListTypeHandler());
        typeHandlers.put(PluralComplexListTypeHandler.class.getName(), new PluralComplexListTypeHandler());
        typeHandlers.put(StringListHandler.class.getName(), new StringListHandler());
    }

    @Override
    public void addMe(Map<String, IUserDecoder> decoders) {
        for (String name : names) {
            decoders.put(name, this);
        }
    }

    @Override
    public void decode(String user, ScimUser data) throws InvalidUser, UnhandledAttributeType, FailedToSetValue, UnknownType,
            InstantiationException, IllegalAccessException, ParseException {
        try {
            JSONObject scimUserJson = new JSONObject(user);

            for (Object extension : data.getExtensions()) {
                for (Method method : extension.getClass().getMethods()) {
                    if (method.isAnnotationPresent(Attribute.class)) {
                        Attribute attribute = method.getAnnotation(Attribute.class);
                        String attributeId = attribute.schemaName();

                        if (scimUserJson.has(attributeId)) {
                            String handlerName = attribute.codingHandler().getName();
                            ITypeHandler handler = typeHandlers.get(handlerName);
                            if (handler == null) {
                                throw new UnhandledAttributeType("Got no handler for, type:'" + handlerName + "', method: '"
                                        + method.getName() + "', class: '" + extension.getClass().getName() + "'");
                            }
                            // TODO think of something smarter
                            String setter = "s" + method.getName().substring(1);
                            Object arg = handler.decode(scimUserJson, attributeId);

                            try {
                                Method setMethod = ReflectionHelper.getMethod(setter, extension.getClass());
                                setMethod.invoke(extension, arg);
                            } catch (Exception e) {
                                // TODO create message and change name of class
                                throw new FailedToSetValue("setter: " + setter + "arg: " + arg, e);
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            throw new InvalidUser("Failed to parse user", e);
        }
    }

	@Override
	public void decode(String userList, List<ScimUser> users) throws InvalidUser, UnhandledAttributeType, FailedToSetValue, UnknownType, InstantiationException, IllegalAccessException, ParseException {

		try {
	        JSONObject userListJson = new JSONObject(userList);
	        if (userListJson.has("entry")) {
	            JSONArray jsonUsers = userListJson.getJSONArray("entry");
	            for (int i = 0; i < jsonUsers.length(); i++) {
	                JSONObject user = jsonUsers.getJSONObject(i);

	                ScimUser data = new ScimUser();
	                decode(user.toString(), data);
	                users.add(data);
	            }
	        }

		} catch (JSONException e) {
			throw new InvalidUser("Failed to parse user", e);
		}

	}

}
