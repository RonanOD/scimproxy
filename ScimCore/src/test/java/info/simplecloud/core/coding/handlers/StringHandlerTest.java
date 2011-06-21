package info.simplecloud.core.coding.handlers;

import info.simplecloud.core.execeptions.FailedToGetValue;
import info.simplecloud.core.execeptions.FailedToSetValue;
import info.simplecloud.core.execeptions.UnhandledAttributeType;
import info.simplecloud.core.execeptions.UnknownType;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class StringHandlerTest {
    private static StringHandler sh = new StringHandler();

    @Test
    public void encode() throws JSONException, UnhandledAttributeType, FailedToSetValue, UnknownType, InstantiationException,
            IllegalAccessException, FailedToGetValue {
        JSONObject scimUserJson = new JSONObject();
        sh.encode(scimUserJson, "displayName", "Kalle");
        
        Assert.assertEquals("Kalle", scimUserJson.getString("displayName"));
    }

    @Test
    public void decode() throws JSONException, UnhandledAttributeType, FailedToSetValue, UnknownType, InstantiationException, IllegalAccessException {
        JSONObject scimUserJson = new JSONObject();
        scimUserJson.put("testkey", "Hello World");
        Object result = sh.decode(scimUserJson, "testkey");
        
        Assert.assertEquals("Hello World", result);
    }
}
