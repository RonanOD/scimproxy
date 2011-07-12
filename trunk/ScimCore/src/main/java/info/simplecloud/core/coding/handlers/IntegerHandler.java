package info.simplecloud.core.coding.handlers;

import org.json.JSONException;
import org.json.JSONObject;

public class IntegerHandler implements ITypeHandler {

    @Override
    public Object decode(JSONObject scimUserJson, String attributeId) throws JSONException {
        return scimUserJson.getInt(attributeId);
    }

    @Override
    public void encode(JSONObject scimUserJson, String attributeId, Object object) {
        if(attributeId == null) {
            throw new IllegalArgumentException("The attribute key may not be null");
        }
        try {
            Integer value = (Integer) object;
            scimUserJson.put(attributeId, value);
        } catch (JSONException e) {
            // Should not happen since we did the null check earlier
        }
    }

}
