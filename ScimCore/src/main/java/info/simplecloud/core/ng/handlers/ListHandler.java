package info.simplecloud.core.ng.handlers;

import info.simplecloud.core.ng.MetaData;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class ListHandler implements IHandler {

    public static final ListHandler INSTANCE = new ListHandler();

    private ListHandler() {
        // Do nothing
    }

    @Override
    public Object decode(Object jsonData, Object me, MetaData internalMetaData) {
        try {

            JSONArray jsonArray = (JSONArray) jsonData;
            List<String> result = new ArrayList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                String value = jsonArray.getString(i);
                result.add(value);
            }
            return result;
        } catch (JSONException e) {
            throw new RuntimeException("Invalid user object", e);
        }
    }

    @Override
    public Object encode(Object me, MetaData internalMetaData, List<String> includeAttributes) {
        @SuppressWarnings("unchecked")
        List<String> list = (List<String>) me;
        JSONArray result = new JSONArray();

        for (String item : list) {
            result.put(item);
        }

        return result;
    }

    @Override
    public Object merge(Object from, Object to) {
        // We do not need this at the moment, since only used in meta data which
        // is not merged.

        return null;
    }

    @Override
    public String toString() {
        return "ListHandler";
    }

}
