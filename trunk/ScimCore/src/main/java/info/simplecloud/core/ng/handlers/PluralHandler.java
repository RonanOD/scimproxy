package info.simplecloud.core.ng.handlers;

import info.simplecloud.core.ng.MetaData;
import info.simplecloud.core.ng.PluralType2;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PluralHandler implements IHandler {
    
    public static final PluralHandler INSTANCE = new PluralHandler();

    private PluralHandler() {
        // Do nothing
    }

    @Override
    public Object decode(Object jsonData, Object me, MetaData internalMetaData) throws Exception {
        try {
            JSONArray jsonArray = (JSONArray) jsonData;
            List<PluralType2> result = new ArrayList<PluralType2>();
            int nrPrimary = 0;

            IHandler handler = internalMetaData.getHandler();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject internalObj = jsonArray.getJSONObject(i);

                Object value = internalObj.get("value");
                value = handler.decode(value, internalMetaData.createType(), internalMetaData.getInternalMetaData());

                String type = (String) getOptional(internalObj, "type");
                Boolean primary = (Boolean) getOptional(internalObj, "primary");
                nrPrimary += (primary ? 1 : 0);

                result.add(new PluralType2(value, type, primary));
            }

            if (nrPrimary > 1) {
                throw new Exception("Only one primary value is allowed");
            }

            return result;

        } catch (JSONException e) {
            throw new RuntimeException("Invalid user object", e);
        }
    }

    @Override
    public Object encode(Object me, MetaData internalMetaData, List<String> includeAttributes) {
        @SuppressWarnings("unchecked")
        List<PluralType2> plural = (List<PluralType2>) me;
        JSONArray result = new JSONArray();
        for (PluralType2 singular : plural) {
            JSONObject jsonObject = new JSONObject();

            IHandler handler = internalMetaData.getHandler();
            Object value = handler.encode(singular.getValue(), internalMetaData, null);

            try {
                jsonObject.put("value", value);
                jsonObject.put("type", singular.getType());
                jsonObject.put("primary", singular.isPrimary());
            } catch (JSONException e) {
                throw new RuntimeException("Internal Error", e);
            }
        }

        return result;
    }

    @Override
    public Object merge(Object from, Object to) {
        @SuppressWarnings("unchecked")
        List<PluralType2> fromPlural = (List<PluralType2>) from;
        @SuppressWarnings("unchecked")
        List<PluralType2> toPlural = (List<PluralType2>) to;

        PluralType2 fromPrimary = this.getPrimary(fromPlural);
        PluralType2 toPrimary = this.getPrimary(toPlural);
        if (fromPrimary != null) {
            if (toPrimary != null) {
                toPrimary.setPrimary(false);
            }

            PluralType2 toEquals = this.find(toPlural, fromPrimary);
            if (toEquals != null) {
                toEquals.setPrimary(fromPrimary.isPrimary());
            }
        }

        for (PluralType2 singular : fromPlural) {
            if (!toPlural.contains(singular)) {
                toPlural.add(singular);
            }
        }

        return to;
    }

    @Override
    public String toString() {
        return "PluralHandler";
    }

    private PluralType2 find(List<PluralType2> list, PluralType2 other) {
        for (PluralType2 singular : list) {
            if (singular.equals(other)) {
                return singular;
            }
        }
        return null;
    }

    private PluralType2 getPrimary(List<PluralType2> list) {
        for (PluralType2 singular : list) {
            if (singular.isPrimary()) {
                return singular;
            }
        }
        return null;
    }

    private Object getOptional(JSONObject jsonObject, String name) {
        try {
            return jsonObject.get(name);
        } catch (JSONException e) {
            return null;
        }
    }

}
