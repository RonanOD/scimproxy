package info.simplecloud.core.handlers;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

import x0.scimSchemasCore1.User;

public class ListHandlerTest {
    private static ListHandler lh = new ListHandler();

    @Test
    public void decode() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("String 1");
        jsonArray.put("String 2");
        jsonArray.put("String 3");

        List<String> list = (List<String>) lh.decode(jsonArray, null, null);

        for (int i = 0; i < jsonArray.length(); i++) {
            String item = jsonArray.getString(i);
            Assert.assertTrue(list.contains(item));
        }
    }

    @Test
    public void encode() throws JSONException {
        List<String> list = new ArrayList<String>();
        list.add("String 1");
        list.add("String 2");
        list.add("String 3");

        JSONArray jsonArray = (JSONArray) lh.encode(list, null, null);

        for (int i = 0; i < jsonArray.length(); i++) {
            String item = jsonArray.getString(i);
            Assert.assertTrue(list.contains(item));
        }
    }

    @Test
    public void decodeXml() throws JSONException {
        
        // TODO implment test
    }

    @Test
    public void encodeXml() throws JSONException {
        // TODO implement test
    }
}
