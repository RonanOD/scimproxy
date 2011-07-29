package info.simplecloud.core.ng.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.simplecloud.core.ng.ComplexType2;
import info.simplecloud.core.ng.MetaData;
import info.simplecloud.core.ng.ScimPair;
import info.simplecloud.core.ng.Types;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComplexHandlerTest {
    private static ComplexHandler ch = ComplexHandler.INSTANCE;
    private ComplexType2 complex;
    private ComplexType2 complex2;

    @Before
    public void setUp(){
        Map<String, ScimPair<MetaData, Object>> tree = new HashMap<String, ScimPair<MetaData, Object>>();
        tree.put("simple1", new ScimPair<MetaData, Object>(Types.META_SIMPLE_STRING, null));
        tree.put("simple2", new ScimPair<MetaData, Object>(Types.META_SIMPLE_STRING, null));
        complex = new ComplexType2(tree);
        
        complex2 = new ComplexType2(tree);
    }
    @Test
    public void parse() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("simple1", "TestString1");
        jsonObject.put("simple2", "TestString2");
        
        ComplexType2 result = (ComplexType2) ch.decode(jsonObject, this.complex, null);
        
        Assert.assertEquals("TestString1", result.getAttribute("simple1", String.class));
        Assert.assertEquals("TestString2", result.getAttribute("simple2", String.class));
    }


    @Test
    public void encode() throws Exception {
        complex.setAttribute("simple1", "TestString1");
        complex.setAttribute("simple2", "TestString2");
        
        JSONObject result = (JSONObject) ch.encode(complex, null, null);
        
        Assert.assertEquals("TestString1", result.getString("simple1"));
        Assert.assertEquals("TestString2", result.getString("simple2"));
    }

    @Test
    public void encodeWithIncludeAttributes() throws Exception {
        complex.setAttribute("simple1", "TestString1");
        complex.setAttribute("simple2", "TestString2");
        
        List<String> includeAttributes = new ArrayList<String>();
        includeAttributes.add("simple1");
        
        JSONObject result = (JSONObject) ch.encode(complex, null, includeAttributes);
        
        Assert.assertEquals("TestString1", result.getString("simple1"));
        try {
            result.getString("simple2");
            Assert.fail();
        } catch (JSONException e) {
            // This is good
        }
    }

    @Test
    public void merge() throws Exception {
        complex.setAttribute("simple1", "TestString1");;
        complex2.setAttribute("simple2", "TestString2");
        
        ComplexType2 result = (ComplexType2) ch.merge(complex2, complex);

        Assert.assertEquals("TestString1", result.getAttribute("simple1", String.class));
        Assert.assertEquals("TestString2", result.getAttribute("simple2", String.class));
    }
}
