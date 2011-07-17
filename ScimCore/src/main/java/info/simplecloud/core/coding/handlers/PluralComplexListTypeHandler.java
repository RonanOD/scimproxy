package info.simplecloud.core.coding.handlers;

import info.simplecloud.core.types.ComplexType;
import info.simplecloud.core.types.PluralType;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PluralComplexListTypeHandler implements ITypeHandler {

    private static ComplexTypeHandler complexTypeHandler = new ComplexTypeHandler();

    @Override
    public Object decode(JSONObject scimUserJson, String attributeId) throws JSONException {
        List<PluralType<Object>> result = new ArrayList<PluralType<Object>>();

        JSONArray plural = scimUserJson.getJSONArray(attributeId);
        for (int i = 0; i < plural.length(); i++) {
            boolean primary = false;
            String type = null;
            JSONObject sigular = plural.getJSONObject(i);

            try {
                type = sigular.getString("type");
            } catch (JSONException e) {
                // ignore, type is not mandatory
            }
            try {
                primary = sigular.getBoolean("primary");
            } catch (JSONException e) {
                // ignore, primary is not mandatory
            }
            // TODO fix some name mapping thing since there is no name for
            // plural complex like normal complex
            Object value = complexTypeHandler.internalDecode(sigular, "address");

            result.add(new PluralType<Object>(value, type, primary));
        }
        return result;
    }

    @Override
    public void encode(JSONObject scimUserJson, String attributeId, Object object) {
        if (attributeId == null) {
            throw new IllegalArgumentException("The attribute key may not be null");
        }

        try {
            List<PluralType<ComplexType>> plural = (List<PluralType<ComplexType>>) object;
            JSONArray jsonPlural = new JSONArray();
            for (PluralType<ComplexType> singular : plural) {
                JSONObject jsonSingular = complexTypeHandler.internalEncode(singular.getValue());
                jsonSingular.put("type", singular.getType());
                jsonSingular.put("primary", singular.isPrimary());

                jsonPlural.put(jsonSingular);
            }
            scimUserJson.put(attributeId, jsonPlural);
        } catch (JSONException e) {
            // Should not happen since we did the null check earlier
        }

    }

}
