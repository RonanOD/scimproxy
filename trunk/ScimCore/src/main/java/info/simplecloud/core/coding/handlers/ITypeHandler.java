package info.simplecloud.core.coding.handlers;

import info.simplecloud.core.execeptions.FailedToGetValue;
import info.simplecloud.core.execeptions.FailedToSetValue;
import info.simplecloud.core.execeptions.UnhandledAttributeType;
import info.simplecloud.core.execeptions.UnknownType;

import org.json.JSONException;
import org.json.JSONObject;

public interface ITypeHandler {

    Object decode(JSONObject scimUserJson, String attributeId) throws JSONException, UnhandledAttributeType, FailedToSetValue, UnknownType,
            InstantiationException, IllegalAccessException;

    void encode(JSONObject scimUserJson, String attributeId, Object object) throws JSONException, UnhandledAttributeType, FailedToSetValue,
            UnknownType, InstantiationException, IllegalAccessException, FailedToGetValue;

}
