package info.simplecloud.core.coding.handlers;

import info.simplecloud.core.execeptions.FailedToGetValue;
import info.simplecloud.core.execeptions.FailedToSetValue;
import info.simplecloud.core.execeptions.UnhandledAttributeType;
import info.simplecloud.core.execeptions.UnknownType;
import info.simplecloud.core.types.PluralType;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class PluralSimpleListTypeHandlerTest {
    private static PluralSimpleListTypeHandler pslth = new PluralSimpleListTypeHandler();

    @Test
    public void encode() throws JSONException, UnhandledAttributeType, FailedToSetValue, UnknownType, InstantiationException,
            IllegalAccessException, FailedToGetValue {
        List<PluralType<String>> plural = new ArrayList<PluralType<String>>();
        plural.add(new PluralType<String>("String 1", "type 1", true));
        plural.add(new PluralType<String>("String 2", "type 2", false));
        plural.add(new PluralType<String>("String 3", "type 3", false));

        JSONObject scimUserJson = new JSONObject();

        pslth.encode(scimUserJson, "testkey", plural);

        JSONArray array = scimUserJson.getJSONArray("testkey");

        for (int i = 0; i < array.length(); i++) {
            String value = array.getJSONObject(i).getString("value");
            String type = array.getJSONObject(i).getString("type");
            boolean primary = array.getJSONObject(i).getBoolean("primary");

            Assert.assertTrue(plural.contains(new PluralType<String>(value, type, primary)));
        }
    }

    @Test
    public void decode() throws JSONException, UnhandledAttributeType, FailedToSetValue, UnknownType, InstantiationException,
            IllegalAccessException {
        JSONObject scimUserJson = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject plural1 = new JSONObject();
        plural1.put("value", "String 1");
        plural1.put("type", "type 1");
        plural1.put("primary", true);
        array.put(plural1);
        JSONObject plural2 = new JSONObject();
        plural2.put("value", "String 2");
        plural2.put("type", "type 2");
        plural2.put("primary", false);
        array.put(plural2);
        JSONObject plural3 = new JSONObject();
        plural3.put("value", "String 3");
        plural3.put("type", "type 3");
        plural3.put("primary", false);
        array.put(plural3);
        scimUserJson.put("testkey", array);

        List<PluralType<String>> plural = (List<PluralType<String>>) pslth.decode(scimUserJson, "testkey");

        Assert.assertTrue(plural.contains(new PluralType<String>("String 1", "type 1", true)));
        Assert.assertTrue(plural.contains(new PluralType<String>("String 2", "type 2", false)));
        Assert.assertTrue(plural.contains(new PluralType<String>("String 3", "type 3", false)));

    }
}
