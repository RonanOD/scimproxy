package info.simplecloud.core.coding.handlers;

import info.simplecloud.core.types.Name;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class ComplexTypeHandlerTest {

    private static ComplexTypeHandler cth = new ComplexTypeHandler();

    @Test
    public void encode() throws JSONException {

        Name name = new Name("Kalle Fransson", "Fransson", "Kalle", null, null, null);
        JSONObject scimUserJson = new JSONObject();

        cth.encode(scimUserJson, "name", name);

        JSONObject jsonName = scimUserJson.getJSONObject("name");
        Assert.assertEquals("Kalle Fransson", jsonName.getString("formatted"));
        Assert.assertEquals("Fransson", jsonName.getString("familyName"));
        Assert.assertEquals("Kalle", jsonName.getString("givenName"));
    }

    @Test
    public void decode() throws JSONException {
        JSONObject scimUserJson = new JSONObject();
        JSONObject jsonName = new JSONObject();
        scimUserJson.put("name", jsonName);
        jsonName.put("formatted", "Kalle Fransson");
        jsonName.put("givenName", "Kalle");
        jsonName.put("familyName", "Fransson");
        Name name = (Name) cth.decode(scimUserJson, "name");

        Assert.assertEquals("Kalle Fransson", name.getFormatted());
        Assert.assertEquals("Fransson", name.getFamilyName());
        Assert.assertEquals("Kalle", name.getGivenName());

    }
}
