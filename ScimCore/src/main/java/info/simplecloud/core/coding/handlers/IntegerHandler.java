package info.simplecloud.core.coding.handlers;

import info.simplecloud.core.execeptions.FailedToGetValue;
import info.simplecloud.core.execeptions.FailedToSetValue;
import info.simplecloud.core.execeptions.UnhandledAttributeType;
import info.simplecloud.core.execeptions.UnknownType;

import org.json.JSONException;
import org.json.JSONObject;

public class IntegerHandler implements ITypeHandler {

    @Override
    public Object decode(JSONObject scimUserJson, String attributeId) throws JSONException {
        return scimUserJson.getInt(attributeId);
    }

    @Override
    public void encode(JSONObject scimUserJson, String attributeId, Object object) throws JSONException, UnhandledAttributeType,
            FailedToSetValue, UnknownType, InstantiationException, IllegalAccessException, FailedToGetValue {
        Integer value = (Integer) object;
        scimUserJson.put(attributeId, value);
    }

}
