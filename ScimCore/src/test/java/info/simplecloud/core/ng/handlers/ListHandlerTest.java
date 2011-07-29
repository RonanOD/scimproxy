package info.simplecloud.core.ng.handlers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

public class ListHandlerTest {
    private static ListHandler lh = ListHandler.INSTANCE;

    @Test
    public void parse() {
        JSONArray indata = new JSONArray();
        indata.put("String1");
        indata.put("String2");
        indata.put("String3");
        indata.put("String4");

        @SuppressWarnings("unchecked")
        List<String> result = (List<String>) lh.decode(indata, null, null);

        Assert.assertTrue(result.contains("String1"));
        Assert.assertTrue(result.contains("String2"));
        Assert.assertTrue(result.contains("String3"));
        Assert.assertTrue(result.contains("String4"));
    }

    @Test
    public void encode() throws JSONException {
        List<String> indata = new ArrayList<String>();
        indata.add("TestString1");
        indata.add("TestString2");
        indata.add("TestString3");
        indata.add("TestString4");

        JSONArray result = (JSONArray) lh.encode(indata, null, null);

        for (int i = 0; i < result.length(); i++) {
            Assert.assertTrue(indata.contains(result.getString(i)));
        }
    }

    @Test
    public void merge() {
        Assert.assertNull(lh.merge(null, null));
    }
}
