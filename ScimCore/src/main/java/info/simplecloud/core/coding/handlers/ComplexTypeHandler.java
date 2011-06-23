package info.simplecloud.core.coding.handlers;

import info.simplecloud.core.Attribute;
import info.simplecloud.core.coding.ReflectionHelper;
import info.simplecloud.core.execeptions.FailedToGetValue;
import info.simplecloud.core.execeptions.FailedToSetValue;
import info.simplecloud.core.execeptions.UnhandledAttributeType;
import info.simplecloud.core.execeptions.UnknownType;
import info.simplecloud.core.types.Address;
import info.simplecloud.core.types.ComplexType;
import info.simplecloud.core.types.Meta;
import info.simplecloud.core.types.Name;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class ComplexTypeHandler implements ITypeHandler {

    private static Map<String, ITypeHandler> typeHandlers = new HashMap<String, ITypeHandler>();
    private static Map<String, Class>        complexTypes = new HashMap<String, Class>();
    static {
        typeHandlers.put(StringHandler.class.getName(), new StringHandler());
        typeHandlers.put(IntegerHandler.class.getName(), new IntegerHandler());
        typeHandlers.put(CalendarHandler.class.getName(), new CalendarHandler());
        typeHandlers.put(ComplexTypeHandler.class.getName(), new ComplexTypeHandler());
        typeHandlers.put(PluralSimpleListTypeHandler.class.getName(), new PluralSimpleListTypeHandler());
        typeHandlers.put(PluralComplexListTypeHandler.class.getName(), new PluralComplexListTypeHandler());
        typeHandlers.put(StringListHandler.class.getName(), new StringListHandler());

        complexTypes.put("name", Name.class);
        complexTypes.put("meta", Meta.class);
        complexTypes.put("address", Address.class);
    }

    @Override
    public Object decode(JSONObject scimUserJson, String attributeId) throws JSONException, UnhandledAttributeType, FailedToSetValue,
            UnknownType, InstantiationException, IllegalAccessException, ParseException {

        JSONObject complexObject = scimUserJson.getJSONObject(attributeId);

        return internalDecode(complexObject, attributeId);
    }

    protected Object internalDecode(JSONObject complexObject, String attributeId) throws UnknownType, InstantiationException,
            IllegalAccessException, UnhandledAttributeType, JSONException, FailedToSetValue, ParseException {
        Class<ComplexType> type = complexTypes.get(attributeId.toLowerCase());
        if (type == null) {
            throw new UnknownType("");
        }
        Object result = type.newInstance();
        for (Method method : result.getClass().getMethods()) {
            if (method.isAnnotationPresent(Attribute.class)) {
                Attribute attribute = method.getAnnotation(Attribute.class);
                String internalAttributeId = attribute.schemaName();

                if (complexObject.has(internalAttributeId)) {

                    String handlerName = attribute.codingHandler().getName();
                    ITypeHandler handler = typeHandlers.get(handlerName);
                    if (handler == null) {
                        throw new UnhandledAttributeType("Han no handler for '" + handlerName + "', attribute='" + internalAttributeId
                                + "' and class='" + result.getClass() + "'");
                    }
                    // TODO think of something smarter
                    String setter = "s" + method.getName().substring(1);
                    Object arg = handler.decode(complexObject, internalAttributeId);

                    try {
                        Method setMethod = ReflectionHelper.getMethod(setter, type);
                        setMethod.invoke(result, arg);
                    } catch (Exception e) {
                        throw new FailedToSetValue("Failed to invoke method '" + setter + "' on '" + type.getName() + "'", e);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void encode(JSONObject scimUserJson, String attributeId, Object object) throws JSONException, UnhandledAttributeType,
            FailedToSetValue, UnknownType, InstantiationException, IllegalAccessException, FailedToGetValue {
        ComplexType value = (ComplexType) object;
        JSONObject jsonValue = internalEncode(value);
        scimUserJson.put(attributeId, jsonValue);
    }

    protected JSONObject internalEncode(ComplexType type) throws JSONException, FailedToGetValue {
        JSONObject result = new JSONObject();

        for (Method method : type.getClass().getMethods()) {
            try {
                if (method.isAnnotationPresent(Attribute.class)) {
                    Attribute attribute = method.getAnnotation(Attribute.class);
                    String attributeId = attribute.schemaName();
                    String handlerName = attribute.codingHandler().getName();
                    ITypeHandler handler = typeHandlers.get(handlerName);
                    if (handler == null) {
                        throw new UnhandledAttributeType("Han no handler for '" + handlerName + "', attribute='" + attributeId
                                + "' and class='" + result.getClass() + "'");
                    }

                    Object object = method.invoke(type);
                    if (object != null) {
                        handler.encode(result, attributeId, object);
                    }
                }
            } catch (Exception e) {
                // TODO create good message
                throw new FailedToGetValue("", e);
            }
        }

        return result;
    }
}
