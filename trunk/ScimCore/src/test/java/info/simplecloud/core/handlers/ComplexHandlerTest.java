package info.simplecloud.core.handlers;

import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.types.ComplexType;
import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class ComplexHandlerTest {
    public class ComplexTestType extends ComplexType {
        private ComplexTestType          complexAttribute;
        private String                   simpleAttribute;
        private String                   simpleAttribute2;


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
    public void decode() {
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
    

    @Test
    public void encodeAllComplex() {
        
    }

    @Test
    public void merge() {
    }
}
