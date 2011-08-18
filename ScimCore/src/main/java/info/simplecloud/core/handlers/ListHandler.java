package info.simplecloud.core.handlers;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.coding.decode.IDecodeHandler;
import info.simplecloud.core.coding.encode.IEncodeHandler;
import info.simplecloud.core.merging.IMerger;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class ListHandler implements IDecodeHandler, IEncodeHandler, IMerger {

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
    public Object decodeXml(Object value, Object newInstance, MetaData internalMetaData) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object encode(Object me, List<String> includeAttributes, MetaData internalMetaData) {
        @SuppressWarnings("unchecked")
        List<String> list = (List<String>) me;
        JSONArray result = new JSONArray();

        for (String item : list) {
            result.put(item);
        }

        return result;
    }

    @Override
    public Object encodeXml(Object me, List<String> includeAttributes, MetaData internalMetaData) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object merge(Object from, Object to) {
        // TODO Auto-generated method stub
        return null;
    }

}
