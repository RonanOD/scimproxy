package info.simplecloud.core.coding.encode;

import info.simplecloud.core.Attribute;
import info.simplecloud.core.ScimUser;
import info.simplecloud.core.coding.handlers.CalendarHandler;
import info.simplecloud.core.coding.handlers.ComplexTypeHandler;
import info.simplecloud.core.coding.handlers.ITypeHandler;
import info.simplecloud.core.coding.handlers.IntegerHandler;
import info.simplecloud.core.coding.handlers.PluralComplexListTypeHandler;
import info.simplecloud.core.coding.handlers.PluralSimpleListTypeHandler;
import info.simplecloud.core.coding.handlers.StringHandler;
import info.simplecloud.core.coding.handlers.StringListHandler;
import info.simplecloud.core.execeptions.EncodingFailed;
import info.simplecloud.core.execeptions.FailedToGetValue;
import info.simplecloud.core.execeptions.UnhandledAttributeType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonEncoder implements IUserEncoder {
    private static final int                 INDENT_SIZE  = Integer.parseInt(System.getProperty(JsonEncoder.class.getName()
                                                                  + ".INDENT_SIZE", "2"));
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
    public void addMe(Map<String, IUserEncoder> encoders) {
        for (String name : names) {
            encoders.put(name, this);
        }
    }

    @Override
    public String encode(ScimUser data) throws EncodingFailed, FailedToGetValue, UnhandledAttributeType {
        return encode(data, new ArrayList<String>());
    }

    @Override
    public String encode(ScimUser data, List<String> includeAttributes) throws EncodingFailed, FailedToGetValue, UnhandledAttributeType {
        try {
        	JSONObject obj = internalEncode(data, includeAttributes);
        	if(obj != null) {
                return obj.toString(2);
        	}
        	else {
        		return null;
        	}
        } catch (JSONException e) {
            throw new EncodingFailed("Failed to build user", e);
        }
    }

    @Override
    public String encode(List<ScimUser> scimUsers) throws EncodingFailed, FailedToGetValue, UnhandledAttributeType {
        return this.encode(scimUsers, new ArrayList<String>());
    }

    @Override
    public String encode(List<ScimUser> scimUsers, List<String> includeAttributes) throws EncodingFailed, FailedToGetValue, UnhandledAttributeType {
        try {
            JSONObject result = new JSONObject();

            if (scimUsers == null) {
                scimUsers = new ArrayList<ScimUser>();
            }

            // TODO: Should this be done in core? Return the JSON list of more
            // resporces when you send an List into encode method?
            JSONArray users = new JSONArray();
            int counter = 0;
            for (ScimUser scimUser : scimUsers) {
            	JSONObject o = internalEncode(scimUser, includeAttributes);
            	if(o != null) {
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

    private JSONObject internalEncode(ScimUser data, List<String> attributesList) throws JSONException, FailedToGetValue, UnhandledAttributeType {
        JSONObject result = new JSONObject();
        boolean foundAttributes = false;

        for (Object extension : data.getExtensions()) {
            for (Method method : extension.getClass().getMethods()) {
                if (method.isAnnotationPresent(Attribute.class)) {
                    Attribute attribute = method.getAnnotation(Attribute.class);
                    String attributeId = attribute.schemaName();
                    
                    
                    if (attributesList.isEmpty() || attributesList.contains(attributeId) || "id".equals(attributeId) || "meta".equals(attributeId)) {

                        String handlerName = attribute.codingHandler().getName();
                        ITypeHandler handler = typeHandlers.get(handlerName);
                        if (handler == null) {
                            throw new UnhandledAttributeType("Han no handler for '" + handlerName + "', attribute='" + attributeId
                                    + "' and class='" + result.getClass() + "'");
                        }

                        try {
                            Object object = method.invoke(extension);
                            if (object != null) {
                                // if it's only id and meta in the attribute list and none of them is in attributeList, then don't return User at all
                            	if(("id".equals(attributeId) && attributesList.contains("id")) || ("meta".equals(attributeId) && attributesList.contains("meta"))) {
                            		foundAttributes = true;
                            	}
                            	if(!"id".equals(attributeId) && !"meta".equals(attributeId)) {
                            		foundAttributes = true;
                            	}

                                handler.encode(result, attributeId, object);
                            }
                        } catch (Exception e) {
                            // TODO create good message
                            throw new FailedToGetValue("", e);
                        }
                    }
                }
            }
        }

        if(foundAttributes) {
            return result;
        }
        else {
        	return null;
        }
    }

}
