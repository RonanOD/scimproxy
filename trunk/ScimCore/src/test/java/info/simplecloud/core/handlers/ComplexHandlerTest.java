package info.simplecloud.core.handlers;

import java.util.ArrayList;
import java.util.List;

import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.core.types.ComplexType;
import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import x0.scimSchemasCore1.Name;
import x0.scimSchemasCore1.User;

public class ComplexHandlerTest {
    public class ComplexTestType extends ComplexType {
        private ComplexTestType complexAttribute;
        private String          simpleAttribute;
        private String          simpleAttribute2;

        public void setComplexAttribute(ComplexTestType complexAttribute) {
            this.complexAttribute = complexAttribute;
        }

        public void setSimpleAttribute(String simpleAttribute) {
            this.simpleAttribute = simpleAttribute;
        }

        public void setSimpleAttribute2(String simpleAttribute2) {
            this.simpleAttribute2 = simpleAttribute2;
        }

        @Attribute(name = "simpleAttribute2", handler = StringHandler.class)
        public String getSimpleAttribute2() {
            return this.simpleAttribute2;
        }

        @Attribute(name = "simpleAttribute", handler = StringHandler.class)
        public String getSimpleAttribute() {
            return this.simpleAttribute;
        }

        @Attribute(name = "complexAttribute", handler = ComplexHandler.class, type = ComplexTestType.class)
        public ComplexTestType getComplexAttribute() {
            return this.complexAttribute;
        }
    }

    private static ComplexHandler ch = new ComplexHandler();

    @Test
    public void decode() throws JSONException, InvalidUser {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("simpleAttribute", "simple 1");
        jsonObject.put("simpleAttribute2", "simple 2");

        ComplexTestType result = (ComplexTestType) ch.decode(jsonObject, new ComplexTestType(), null);

        Assert.assertEquals("simple 1", result.getSimpleAttribute());
        Assert.assertEquals("simple 2", result.getSimpleAttribute2());
    }

    @Test
    public void encodeAllSimple() throws JSONException {
        ComplexTestType indata = new ComplexTestType();
        indata.setSimpleAttribute("simple 1");
        indata.setSimpleAttribute2("simple 2");

        JSONObject jsonObject = (JSONObject) ch.encode(indata, null, null);

        Assert.assertEquals("simple 1", jsonObject.getString("simpleAttribute"));
        Assert.assertEquals("simple 2", jsonObject.getString("simpleAttribute2"));
    }

    public void encodeSomeSimple() throws JSONException {
        ComplexTestType indata = new ComplexTestType();
        indata.setSimpleAttribute("simple 1");
        indata.setSimpleAttribute2("simple 2");

        List<String> attributes = new ArrayList<String>();
        attributes.add("simpleAttribute");

        JSONObject jsonObject = (JSONObject) ch.encode(indata, attributes, null);

        Assert.assertEquals("simple 1", jsonObject.getString("simpleAttribute"));
        try {
            Assert.assertEquals("simple 2", jsonObject.getString("simpleAttribute2"));
            Assert.fail();
        } catch (JSONException e) {
        }
    }

    @Test
    public void encodeAllComplex() throws JSONException {
        ComplexTestType indatasub = new ComplexTestType();
        indatasub.setSimpleAttribute("simple 1");
        indatasub.setSimpleAttribute2("simple 2");
        ComplexTestType indata = new ComplexTestType();
        indata.setSimpleAttribute("simple 3");
        indata.setSimpleAttribute2("simple 4");
        indata.setComplexAttribute(indatasub);

        JSONObject jsonObject = (JSONObject) ch.encode(indata, null, null);

        Assert.assertEquals("simple 3", jsonObject.getString("simpleAttribute"));
        Assert.assertEquals("simple 4", jsonObject.getString("simpleAttribute2"));
        JSONObject jsonObjectSub = jsonObject.getJSONObject("complexAttribute");
        Assert.assertEquals("simple 1", jsonObjectSub.getString("simpleAttribute"));
        Assert.assertEquals("simple 2", jsonObjectSub.getString("simpleAttribute2"));

    }

    public void encodeSomeComplex() throws JSONException {
        ComplexTestType indatasub = new ComplexTestType();
        indatasub.setSimpleAttribute("simple 1");
        indatasub.setSimpleAttribute2("simple 2");
        ComplexTestType indata = new ComplexTestType();
        indata.setSimpleAttribute("simple 3");
        indata.setSimpleAttribute2("simple 4");
        indata.setComplexAttribute(indatasub);

        List<String> attributes = new ArrayList<String>();
        attributes.add("simpleAttribute");
        attributes.add("complexAttribute.simpleAttribute");

        JSONObject jsonObject = (JSONObject) ch.encode(indata, attributes, null);

        Assert.assertEquals("simple 3", jsonObject.getString("simpleAttribute"));
        JSONObject jsonObjectSub = jsonObject.getJSONObject("complexAttribute");
        Assert.assertEquals("simple 1", jsonObjectSub.getString("simpleAttribute"));

        try {
            Assert.assertEquals("simple 2", jsonObjectSub.getString("simpleAttribute2"));
            Assert.fail();
        } catch (JSONException e) {
        }
        try {
            Assert.assertEquals("simple 4", jsonObject.getString("simpleAttribute2"));
            Assert.fail();
        } catch (JSONException e) {
        }
    }

    @Test
    public void encodeXml() throws InvalidUser, UnknownAttribute {
        info.simplecloud.core.User user = new info.simplecloud.core.User("");
        user.setId("ABC123");
        user.setUserName("kalle");
        user.setAttribute("name.givenName", "Karl");
        user.setAttribute("name.familyName", "Svensson");

        User xmlUser = (User) ch.encodeXml(user, null, null, User.Factory.newInstance());

        Assert.assertEquals("ABC123", xmlUser.getId());
        Assert.assertEquals("kalle", xmlUser.getUserName());
        Assert.assertEquals("Karl", xmlUser.getName().getGivenName());
        Assert.assertEquals("Svensson", xmlUser.getName().getFamilyName());

    }

    @Test
    public void decodeXml() throws InvalidUser, UnknownAttribute {
        User user = User.Factory.newInstance();
        user.setId("ABC123");
        user.setUserName("kalle");
        Name name = user.addNewName();
        name.setGivenName("Karl");
        name.setFamilyName("Svensson");

        info.simplecloud.core.User scimUser = (info.simplecloud.core.User) ch.decodeXml(user, new info.simplecloud.core.User(""), null);

        Assert.assertEquals("ABC123", scimUser.getAttribute("id"));
        Assert.assertEquals("kalle", scimUser.getAttribute("userName"));
        Assert.assertEquals("Karl", scimUser.getAttribute("name.givenName"));
        Assert.assertEquals("Svensson", scimUser.getAttribute("name.familyName"));
    }

    @Test
    public void merge() {
        // TODO implement test
    }
}
