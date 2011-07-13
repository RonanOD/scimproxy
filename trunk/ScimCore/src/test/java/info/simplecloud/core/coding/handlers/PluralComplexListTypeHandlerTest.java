package info.simplecloud.core.coding.handlers;

import info.simplecloud.core.types.Address;
import info.simplecloud.core.types.PluralType;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class PluralComplexListTypeHandlerTest {

    private static PluralComplexListTypeHandler pclth = new PluralComplexListTypeHandler();

    @Test
    public void encode() throws JSONException {
        List<PluralType<Address>> plural = new ArrayList<PluralType<Address>>();
        Address workAddress = new Address();
        workAddress.setCountry("Sweden");
        workAddress.setRegion("Stockholm");
        plural.add(new PluralType<Address>(workAddress, "work", true));
        Address homeAddress = new Address();
        homeAddress.setCountry("England");
        homeAddress.setRegion("London");
        plural.add(new PluralType<Address>(homeAddress, "home", false));

        JSONObject scimUserJson = new JSONObject();

        pclth.encode(scimUserJson, "testkey", plural);

        JSONArray array = scimUserJson.getJSONArray("testkey");

        for (int i = 0; i < array.length(); i++) {
            String type = array.getJSONObject(i).getString("type");
            boolean primary = array.getJSONObject(i).getBoolean("primary");

            if (type.equals("home")) {
                Assert.assertEquals(false, primary);
                Assert.assertEquals("England", array.getJSONObject(i).getString("country"));
                Assert.assertEquals("London", array.getJSONObject(i).getString("region"));
            } else {
                Assert.assertEquals(true, primary);
                Assert.assertEquals("Sweden", array.getJSONObject(i).getString("country"));
                Assert.assertEquals("Stockholm", array.getJSONObject(i).getString("region"));
            }
        }
    }

    @Test
    public void decode() throws JSONException {
        JSONObject scimUserJson = new JSONObject();
        JSONArray jsonPlural = new JSONArray();

        JSONObject jsonWorkAddress = new JSONObject();
        jsonWorkAddress.put("country", "Sweden");
        jsonWorkAddress.put("region", "Stockholm");
        jsonWorkAddress.put("type", "work");
        jsonWorkAddress.put("primary", true);
        jsonPlural.put(jsonWorkAddress);

        JSONObject jsonHomeAddress = new JSONObject();
        jsonHomeAddress.put("country", "England");
        jsonHomeAddress.put("region", "London");
        jsonHomeAddress.put("type", "home");
        jsonHomeAddress.put("primary", false);
        jsonPlural.put(jsonHomeAddress);

        scimUserJson.put("addresses", jsonPlural);

        List<PluralType<Address>> plural = (List<PluralType<Address>>) pclth.decode(scimUserJson, "addresses");

        Address workAddress = new Address();
        workAddress.setCountry("Sweden");
        workAddress.setRegion("Stockholm");
        Assert.assertTrue(plural.contains(new PluralType<Address>(workAddress, "work", true)));

        Address homeAddress = new Address();
        homeAddress.setCountry("England");
        homeAddress.setRegion("London");
        Assert.assertTrue(plural.contains(new PluralType<Address>(homeAddress, "home", false)));

    }
}
