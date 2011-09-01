package info.simplecloud.core.handlers;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.types.DummyType;
import info.simplecloud.core.types.PluralType;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

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

        Assert.assertTrue(result.contains(new PluralType<String>("item1", null, false, false)));
        Assert.assertTrue(result.contains(new PluralType<String>("item2", null, false, false)));
        Assert.assertTrue(result.contains(new PluralType<String>("item3", null, false, false)));
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
            Assert.assertTrue(indata.contains(new PluralType<String>(value, null, false, false)));
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

        Assert.assertTrue(result.contains(new PluralType<String>("email1", null, false, false)));
        Assert.assertTrue(result.contains(new PluralType<String>("email2", null, false, false)));
        Assert.assertTrue(result.contains(new PluralType<String>("email3", null, false, false)));
    }

    @Test(expected = InvalidUser.class)
    public void decodeXmlMultiplePrimary() throws JSONException, InvalidUser {
        Emails emails = Emails.Factory.newInstance();
        PluralAttribute email1 = emails.addNewEmail();
        email1.setPrimary(false);
        email1.setValue("email1");
        email1.setType("home");
        PluralAttribute email2 = emails.addNewEmail();
        email2.setPrimary(true);
        email2.setValue("email2");
        email2.setType("work");
        PluralAttribute email3 = emails.addNewEmail();
        email3.setPrimary(true);
        email3.setValue("email3");
        email3.setType("bonus");

        ph.decodeXml(emails, null, new MetaData(StringHandler.class, DummyType.class, "email"));
    }

    @Test
    public void encodeXml() throws JSONException {
        List<PluralType<String>> indata = new ArrayList<PluralType<String>>();
        indata.add(new PluralType<String>("email1", "work", false, false));
        indata.add(new PluralType<String>("email2", "home", false, false));
        indata.add(new PluralType<String>("email3", "bonus", false, false));
        Emails emails = Emails.Factory.newInstance();

        emails = (Emails) ph.encodeXml(indata, null, new MetaData(StringHandler.class, DummyType.class, "email"), emails);

        for (PluralAttribute singular : emails.getEmailArray()) {
            Assert.assertTrue(indata.contains(new PluralType<String>(singular.getValue(), null, false, false)));
        }
    }

    @Test
    public void merge() {

        List<PluralType<String>> from = new ArrayList<PluralType<String>>();
        from.add(new PluralType<String>("email1", "work", false, false));
        from.add(new PluralType<String>("email2", "home", false, false));
        from.add(new PluralType<String>("email3", "bonus", false, false));
        List<PluralType<String>> to = new ArrayList<PluralType<String>>();
        to = (List<PluralType<String>>) ph.merge(from, to);
        Assert.assertEquals(from.size(), to.size());
        Assert.assertEquals(from, to);

        from = new ArrayList<PluralType<String>>();
        from.add(new PluralType<String>("email3", "bonus", false, true));
        to = (List<PluralType<String>>) ph.merge(from, to);
        Assert.assertEquals(2, to.size());
        Assert.assertTrue(to.contains(new PluralType<String>("email2", "home", false, false)));
        Assert.assertTrue(to.contains(new PluralType<String>("email1", "work", false, false)));

        from = new ArrayList<PluralType<String>>();
        from.add(new PluralType<String>("email3", "bonus", true, false));
        to.get(0).setPrimary(true);
        to = (List<PluralType<String>>) ph.merge(from, to);
        int primaries = 0;
        for (PluralType<String> singular : to) {
            if (singular.isPrimary()) {
                primaries++;
                Assert.assertEquals(new PluralType<String>("email3", "bonus", true, false), singular);
            }
        }
        Assert.assertEquals(1, primaries);

        from = new ArrayList<PluralType<String>>();
        from.add(new PluralType<String>("email4", "home", false, false));
        from.add(new PluralType<String>("email5", "bonus", false, false));
        to = (List<PluralType<String>>) ph.merge(from, to);
        Assert.assertTrue(to.contains(new PluralType<String>("email1", null, false, false)));
        Assert.assertTrue(to.contains(new PluralType<String>("email2", null, false, false)));
        Assert.assertTrue(to.contains(new PluralType<String>("email3", null, false, false)));
        Assert.assertTrue(to.contains(new PluralType<String>("email4", null, false, false)));
        Assert.assertTrue(to.contains(new PluralType<String>("email5", null, false, false)));
    }
}
