package info.simplecloud.core.coding.handlers;

import org.json.JSONException;
import org.json.JSONObject;

public interface ITypeHandler {

    Object decode(JSONObject scimUserJson, String attributeId) throws JSONException;

    void encode(JSONObject scimUserJson, String attributeId, Object object);

}
