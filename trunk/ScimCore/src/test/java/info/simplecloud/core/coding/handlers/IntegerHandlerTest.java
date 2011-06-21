package info.simplecloud.core.coding.handlers;

import info.simplecloud.core.execeptions.FailedToGetValue;
import info.simplecloud.core.execeptions.FailedToSetValue;
import info.simplecloud.core.execeptions.UnhandledAttributeType;
import info.simplecloud.core.execeptions.UnknownType;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class IntegerHandlerTest {
    private static IntegerHandler ih = new IntegerHandler();

    @Test
    public void encode() throws JSONException, UnhandledAttributeType, FailedToSetValue, UnknownType, InstantiationException,
            IllegalAccessException, FailedToGetValue {

        JSONObject scimUserJson = new JSONObject();
        ih.encode(scimUserJson, "testkey", 5);

        Assert.assertEquals(scimUserJson.getInt("testkey"), 5);
    }

    @Test
    public void decode() throws JSONException {

        JSONObject scimUserJson = new JSONObject();
        scimUserJson.put("testkey", 4);
        Object result = ih.decode(scimUserJson, "testkey");

        Assert.assertEquals(4, result);
    }

}
