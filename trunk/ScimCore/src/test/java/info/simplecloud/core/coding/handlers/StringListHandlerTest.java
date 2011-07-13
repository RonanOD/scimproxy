package info.simplecloud.core.coding.handlers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class StringListHandlerTest {
    private static StringListHandler sh = new StringListHandler();

    @Test
    public void encode() throws JSONException {
        List<String> stringList = new ArrayList<String>();
        stringList.add("String 1");
        stringList.add("String 2");
        stringList.add("String 3");

        JSONObject scimUserJson = new JSONObject();

        sh.encode(scimUserJson, "testkey", stringList);

        JSONArray jsonArray = scimUserJson.getJSONArray("testkey");

        for (int i = 0; i < jsonArray.length(); i++) {
            Assert.assertTrue(stringList.contains(jsonArray.getString(i)));
        }
    }

    @Test
    public void decode() throws JSONException {

        JSONObject scimUserJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("String 1");
        jsonArray.put("String 2");
        jsonArray.put("String 3");
        scimUserJson.put("testkey", jsonArray);
        List<String> stringList = (List<String>) sh.decode(scimUserJson, "testkey");

        for (int i = 0; i < jsonArray.length(); i++) {
            Assert.assertTrue(stringList.contains(jsonArray.getString(i)));
        }
    }
}
