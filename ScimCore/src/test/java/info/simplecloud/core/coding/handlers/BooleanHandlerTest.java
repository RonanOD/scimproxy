package info.simplecloud.core.coding.handlers;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class BooleanHandlerTest {
    private static BooleanHandler bh = new BooleanHandler();

    @Test
    public void encode() throws JSONException {

        JSONObject scimUserJson = new JSONObject();
        bh.encode(scimUserJson, "trueKey", true);
        bh.encode(scimUserJson, "falseKey", false);

        Assert.assertEquals(scimUserJson.getBoolean("trueKey"), true);
        Assert.assertEquals(scimUserJson.getBoolean("falseKey"), false);
    }

    @Test
    public void decode() throws JSONException {

        JSONObject scimUserJson = new JSONObject();
        scimUserJson.put("trueKey", true);
        scimUserJson.put("falseKey", false);
        Object resultTrue = bh.decode(scimUserJson, "trueKey");
        Object resultFalse = bh.decode(scimUserJson, "falseKey");

        Assert.assertEquals(true, resultTrue);
        Assert.assertEquals(false, resultFalse);
    }
}
