package info.simplecloud.core.handlers;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.types.DummyType;
import info.simplecloud.core.types.PluralType;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import x0.scimSchemasCore1.PluralAttribute;
import x0.scimSchemasCore1.User.Emails;

public class PluralHandlerTest {

    private static PluralHandler ph = new PluralHandler();

    @Test
    public void decode() throws JSONException, InvalidUser {
        JSONObject item1 = new JSONObject();
        item1.put("value", "item1");
        JSONObject item2 = new JSONObject();
        item2.put("value", "item2");
        item2.put("type", "email");
        JSONObject item3 = new JSONObject();
        item3.put("value", "item3");
        item3.put("type", "email");
        item3.put("primary", true);

        JSONArray indata = new JSONArray();
        indata.put(item1);
        indata.put(item2);
        indata.put(item3);
        List<PluralType<String>> result = (List<PluralType<String>>) ph.decode(indata, null, new MetaData(StringHandler.class,
                DummyType.class, "email"));

        result.contains(new PluralType<String>("item1", null, false, false));
        result.contains(new PluralType<String>("item2", null, false, false));
        result.contains(new PluralType<String>("item3", null, false, false));
    }

    @Test(expected = InvalidUser.class)
    public void decodeMultiPrimary() throws JSONException, InvalidUser {
        JSONObject item1 = new JSONObject();
        item1.put("value", "item1");
        JSONObject item2 = new JSONObject();
        item2.put("value", "item2");
        item2.put("type", "email");
        item2.put("primary", true);
        JSONObject item3 = new JSONObject();
        item3.put("value", "item3");
        item3.put("type", "email");
        item3.put("primary", true);

        JSONArray indata = new JSONArray();
        indata.put(item1);
        indata.put(item2);
        indata.put(item3);
        ph.decode(indata, null, new MetaData(StringHandler.class, DummyType.class, "email"));

    }

    @Test
    public void encode() throws JSONException {
        List<PluralType<String>> indata = new ArrayList<PluralType<String>>();
        indata.add(new PluralType<String>("item1", "work", false, false));
        indata.add(new PluralType<String>("item2", "home", false, false));
        indata.add(new PluralType<String>("item3", "bonus", false, false));

        JSONArray result = (JSONArray) ph.encode(indata, null, new MetaData(StringHandler.class, DummyType.class, "email"));

        for (int i = 0; i < result.length(); i++) {
            String value = result.getJSONObject(i).getString("value");
            indata.contains(new PluralType<String>(value, null, false, false));
        }
    }

    @Test
    public void decodeXml() throws JSONException, InvalidUser {
        Emails emails = Emails.Factory.newInstance();
        PluralAttribute email1 = emails.addNewEmail();
        email1.setPrimary(false);
        email1.setValue("email1");
        email1.setType("home");
        PluralAttribute email2 = emails.addNewEmail();
        email2.setPrimary(false);
        email2.setValue("email2");
        email2.setType("work");
        PluralAttribute email3 = emails.addNewEmail();
        email3.setPrimary(true);
        email3.setValue("email3");
        email3.setType("bonus");

        List<PluralType<String>> result = (List<PluralType<String>>) ph.decodeXml(emails, null, new MetaData(StringHandler.class,
                DummyType.class, "email"));

        result.contains(new PluralType<String>("email1", null, false, false));
        result.contains(new PluralType<String>("email2", null, false, false));
        result.contains(new PluralType<String>("email3", null, false, false));
    }

    @Test
    public void encodeXml() throws JSONException {
        // TODO implement test
    }
}
