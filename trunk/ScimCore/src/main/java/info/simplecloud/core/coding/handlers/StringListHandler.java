package info.simplecloud.core.coding.handlers;

import java.util.ArrayList;
import java.util.List;

import info.simplecloud.core.execeptions.FailedToGetValue;
import info.simplecloud.core.execeptions.FailedToSetValue;
import info.simplecloud.core.execeptions.UnhandledAttributeType;
import info.simplecloud.core.execeptions.UnknownType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StringListHandler implements ITypeHandler {

    @Override
    public Object decode(JSONObject scimUserJson, String attributeId) throws JSONException, UnhandledAttributeType, FailedToSetValue,
            UnknownType, InstantiationException, IllegalAccessException {

        List<String> result = new ArrayList<String>();

        JSONArray data = scimUserJson.getJSONArray(attributeId);
        for (int i = 0; i < data.length(); i++) {
            result.add(data.getString(i));
        }

        return result;
    }

    @Override
    public void encode(JSONObject scimUserJson, String attributeId, Object object) throws JSONException, UnhandledAttributeType,
            FailedToSetValue, UnknownType, InstantiationException, IllegalAccessException, FailedToGetValue {
        JSONArray result = new JSONArray();

        List<String> data = (List<String>) object;
        for (String attribute : data) {
            result.put(attribute);
        }

        scimUserJson.put(attributeId, result);
    }

}
