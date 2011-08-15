package info.simplecloud.core.handlers;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.coding.decode.IDecodeHandler;
import info.simplecloud.core.coding.encode.IEncodeHandler;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.merging.IMerger;
import info.simplecloud.core.types.PluralType;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PluralHandler implements IDecodeHandler, IEncodeHandler, IMerger {

    @Override
    public Object decode(Object jsonData, Object me, MetaData internalMetaData) throws InvalidUser {
        try {
            JSONArray jsonArray = (JSONArray) jsonData;
            List<PluralType> result = new ArrayList<PluralType>();
            int nrPrimary = 0;

            IDecodeHandler decoder = internalMetaData.getDecoder();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject internalObj = jsonArray.getJSONObject(i);

                Object value = internalObj.get("value");
                value = decoder.decode(value, internalMetaData.newInstance(), internalMetaData.getInternalMetaData());

                String type = (String) getOptional(internalObj, "type");
                Boolean primary = (Boolean) getOptional(internalObj, "primary");
                primary = (primary == null ? false : primary);
                Boolean delete = (Boolean) getOptional(internalObj, "delete");
                delete = (delete == null ? false : delete);

                nrPrimary += (primary ? 1 : 0);

                result.add(new PluralType(value, type, primary, delete));
            }

            if (nrPrimary > 1) {
                throw new InvalidUser("Only one primary value is allowed");
            }

            return result;

        } catch (JSONException e) {
            throw new RuntimeException("Invalid user object", e);
        }
    }

    @Override
    public Object encode(Object me, List<String> includeAttributes, MetaData internalMetaData) {
        List<PluralType> plural = (List<PluralType>) me;
        JSONArray result = new JSONArray();
        for (PluralType singular : plural) {
            JSONObject jsonObject = new JSONObject();

            IEncodeHandler encoder = internalMetaData.getEncoder();
            Object value = encoder.encode(singular.getValue(), null, null);

            try {
                jsonObject.put("value", value);
                jsonObject.put("type", singular.getType());
                if (singular.isPrimary()) {
                    jsonObject.put("primary", singular.isPrimary());
                }
                if (singular.isDelete()) {
                    jsonObject.put("delete", singular.isPrimary());
                }

                result.put(jsonObject);
            } catch (JSONException e) {
                throw new RuntimeException("Internal error, encoding plural type", e);
            }
        }

        return result;
    }

    @Override
    public Object merge(Object from, Object to) {
        List<PluralType> toList = (List<PluralType>) to;
        List<PluralType> fromList = (List<PluralType>) from;

        for (PluralType singular : fromList) {
            if (singular.isDelete()) {
                toList.remove(singular);
            } else if (singular.isPrimary()) {
                toList.remove(singular);
                clearPrimary(toList);
                // TODO merge
                toList.add(singular);
            } else if (toList.contains(singular)) {
                toList.remove(singular);
                // TODO merge
                toList.add(singular);
            } else {
                toList.add(singular);
            }
        }
        return to;
    }

    private void clearPrimary(List<PluralType> list) {
        for (PluralType singular : list) {
            singular.setPrimary(false);
        }
    }

    private Object getOptional(JSONObject jsonObject, String name) {
        try {
            return jsonObject.get(name);
        } catch (JSONException e) {
            return null;
        }
    }

}
