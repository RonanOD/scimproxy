package info.simplecloud.core.coding.handlers;

import info.simplecloud.core.Attribute;
import info.simplecloud.core.coding.ReflectionHelper;
import info.simplecloud.core.execeptions.DecodingFailed;
import info.simplecloud.core.execeptions.EncodingFailed;
import info.simplecloud.core.execeptions.UnhandledAttributeType;
import info.simplecloud.core.execeptions.UnknownType;
import info.simplecloud.core.types.Address;
import info.simplecloud.core.types.ComplexType;
import info.simplecloud.core.types.Meta;
import info.simplecloud.core.types.Name;

import java.lang.reflect.Method;
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
        typeHandlers.put(BooleanHandler.class.getName(), new BooleanHandler());
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
    public Object decode(JSONObject scimUserJson, String attributeId) throws JSONException {

        JSONObject complexObject = scimUserJson.getJSONObject(attributeId);

        return internalDecode(complexObject, attributeId);
    }

    protected Object internalDecode(JSONObject complexObject, String attributeId) throws JSONException {
        Class<ComplexType> type = complexTypes.get(attributeId.toLowerCase());
        if (type == null) {
            throw new UnknownType("could not find a type matching '" + attributeId + "'");
        }
        Object result;
        try {
            result = type.newInstance();
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
                            throw new DecodingFailed("Failed to invoke method '" + setter + "' on '" + type.getName() + "'", e);
                        }
                    }
                }
            }
            return result;
        } catch (InstantiationException e1) {
            throw new DecodingFailed("Dailed to decode", e1);
        } catch (IllegalAccessException e1) {
            throw new DecodingFailed("Failed to decode", e1);
        }
    }

    @Override
    public void encode(JSONObject scimUserJson, String attributeId, Object object) {
        if (attributeId == null) {
            throw new IllegalArgumentException("The attribute key may not be null");
        }

        try {
            ComplexType value = (ComplexType) object;
            JSONObject jsonValue = internalEncode(value);
            scimUserJson.put(attributeId, jsonValue);
        } catch (JSONException e) {
            // Should not happen since we did the null check earlier
        }
    }

    protected JSONObject internalEncode(ComplexType type) throws JSONException {
        JSONObject result = new JSONObject();

        for (Method method : type.getClass().getMethods()) {
            if (method.isAnnotationPresent(Attribute.class)) {
                Attribute attribute = method.getAnnotation(Attribute.class);
                String attributeId = attribute.schemaName();
                String handlerName = attribute.codingHandler().getName();
                ITypeHandler handler = typeHandlers.get(handlerName);
                if (handler == null) {
                    throw new UnhandledAttributeType("Han no handler for '" + handlerName + "', attribute='" + attributeId
                            + "' and class='" + result.getClass() + "'");
                }
                Object object = null;
                try {
                    object = method.invoke(type);
                } catch (Exception e) {
                    throw new EncodingFailed("failed to get attribute '" + attributeId + "' on type '" + type.getClass().getName() + "'", e);
                }
                if (object != null) {
                    handler.encode(result, attributeId, object);
                }
            }
        }

        return result;
    }
}
