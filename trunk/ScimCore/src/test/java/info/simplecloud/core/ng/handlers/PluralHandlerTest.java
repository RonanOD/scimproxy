package info.simplecloud.core.ng.handlers;

import java.util.ArrayList;
import java.util.List;

import info.simplecloud.core.ng.PluralType2;
import info.simplecloud.core.ng.Types;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class PluralHandlerTest {
    private static PluralHandler ph = PluralHandler.INSTANCE;

    @Test
    public void parse() throws Exception {
        JSONObject item1 = new JSONObject();
        item1.put("value", "ValueString1");
        item1.put("type", "TypeString1");
        item1.put("primary", true);
        JSONObject item2 = new JSONObject();
        item2.put("value", "ValueString2");
        item2.put("type", "TypeString2");
        item2.put("primary", false);
        JSONArray indata = new JSONArray();
        indata.put(item1);
        indata.put(item2);

        @SuppressWarnings("unchecked")
        List<PluralType2> result = (List<PluralType2>) ph.decode(indata, null, Types.META_SIMPLE_STRING);

        Assert.assertTrue(result.contains(new PluralType2("ValueString1", "TypeString1", true)));
        Assert.assertTrue(result.contains(new PluralType2("ValueString2", "TypeString2", false)));
    }

    @Test(expected = Exception.class)
    public void parseMultiplePrimary() throws Exception {
        JSONObject item1 = new JSONObject();
        item1.put("value", "ValueString1");
        item1.put("type", "TypeString1");
        item1.put("primary", true);
        JSONObject item2 = new JSONObject();
        item2.put("value", "ValueString2");
        item2.put("type", "TypeString2");
        item2.put("primary", true);
        JSONArray indata = new JSONArray();
        indata.put(item1);
        indata.put(item2);

        ph.decode(indata, null, Types.META_SIMPLE_STRING);
    }

    @Test
    public void encode() throws JSONException {
        List<PluralType2> indata = new ArrayList<PluralType2>();
        indata.add(new PluralType2("ValueString1", "TypeString1", true));
        indata.add(new PluralType2("ValueString2", "TypeString2", false));

        JSONArray result = (JSONArray) ph.encode(indata, Types.META_SIMPLE_STRING, null);

        for (int i = 0; i < result.length(); i++) {
            JSONObject item = result.getJSONObject(i);
            String value = item.getString("value");
            String type = item.getString("type");
            boolean primary = item.getBoolean("primary");
            Assert.assertTrue(indata.contains(new PluralType2(value, type, primary)));
        }
    }

    @Test
    public void merge() {
        PluralType2 item1 = new PluralType2("ValueString1", "TypeString1", true);
        PluralType2 item2 = new PluralType2("ValueString2", "TypeString2", false);
        PluralType2 item3 = new PluralType2("ValueString3", "TypeString3", false);
        PluralType2 item4 = new PluralType2("ValueString4", "TypeString4", false);
        List<PluralType2> from = new ArrayList<PluralType2>();
        from.add(item1);
        from.add(item2);
        List<PluralType2> to = new ArrayList<PluralType2>();
        to.add(item3);
        to.add(item4);

        @SuppressWarnings("unchecked")
        List<PluralType2> result = (List<PluralType2>) ph.merge(from, to);

        Assert.assertTrue(result.contains(item1));
        Assert.assertTrue(result.contains(item2));
        Assert.assertTrue(result.contains(item3));
        Assert.assertTrue(result.contains(item4));
    }

    @Test
    public void mergeMultiplePrimary() {
        PluralType2 item1 = new PluralType2("ValueString1", "TypeString1", true);
        PluralType2 item4 = new PluralType2("ValueString4", "TypeString4", true);
        List<PluralType2> from = new ArrayList<PluralType2>();
        from.add(item1);
        List<PluralType2> to = new ArrayList<PluralType2>();
        to.add(item4);

        @SuppressWarnings("unchecked")
        List<PluralType2> result = (List<PluralType2>) ph.merge(from, to);

        for (PluralType2 singular : result) {
            if (singular.getValue().equals("ValueString1")) {
                Assert.assertTrue(singular.isPrimary());
            }
            if (singular.getValue().equals("ValueString4")) {
                Assert.assertFalse(singular.isPrimary());
            }
        }
    }

    @Test
    public void mergeChangePrimary() {

        PluralType2 item1 = new PluralType2("ValueString1", "TypeString1", false);
        PluralType2 item2 = new PluralType2("ValueString2", "TypeString2", true);
        PluralType2 item3 = new PluralType2("ValueString1", "TypeString1", true);
        PluralType2 item4 = new PluralType2("ValueString2", "TypeString2", false);
        List<PluralType2> from = new ArrayList<PluralType2>();
        from.add(item1);
        from.add(item2);
        List<PluralType2> to = new ArrayList<PluralType2>();
        to.add(item3);
        to.add(item4);

        @SuppressWarnings("unchecked")
        List<PluralType2> result = (List<PluralType2>) ph.merge(from, to);

        for (PluralType2 singular : result) {
            if (singular.getValue().equals("ValueString1")) {
                Assert.assertFalse(singular.isPrimary());
            }
            if (singular.getValue().equals("ValueString2")) {
                Assert.assertTrue(singular.isPrimary());
            }
        }
    }
}
