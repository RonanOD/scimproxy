package info.simplecloud.core.coding.handlers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StringListHandler implements ITypeHandler {

    @Override
    public Object decode(JSONObject scimUserJson, String attributeId) throws JSONException {

        List<String> result = new ArrayList<String>();

        JSONArray data = scimUserJson.getJSONArray(attributeId);
        for (int i = 0; i < data.length(); i++) {
            result.add(data.getString(i));
        }

        return result;
    }

    @Override
    public void encode(JSONObject scimUserJson, String attributeId, Object object) {
        if (attributeId == null) {
            throw new IllegalArgumentException("The attribute key may not be null");
        }

        try {
            JSONArray result = new JSONArray();
            List<String> data = (List<String>) object;
            for (String attribute : data) {
                result.put(attribute);
            }
            scimUserJson.put(attributeId, result);
        } catch (JSONException e) {
            // Should not happen since we did the null check earlier
        }
    }

}
