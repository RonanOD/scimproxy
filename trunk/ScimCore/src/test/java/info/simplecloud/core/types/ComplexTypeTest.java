package info.simplecloud.core.types;

import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.core.handlers.ComplexHandler;
import info.simplecloud.core.handlers.PluralHandler;
import info.simplecloud.core.handlers.StringHandler;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ComplexTypeTest {

    private class ComplexTestType extends ComplexType {
        private List<PluralType<String>> pluralAttribute;
        private ComplexTestType          complexAttribute;
        private String                   simpleAttribute;
        private String                   simpleAttribute2;

        @SuppressWarnings("unused")
        public void setPluralAttribute(List<PluralType<String>> pluralAttribute) {
            this.pluralAttribute = pluralAttribute;
        }

        @SuppressWarnings("unused")
        public void setComplexAttribute(ComplexTestType complexAttribute) {
            this.complexAttribute = complexAttribute;
        }

        @SuppressWarnings("unused")
        public void setSimpleAttribute(String simpleAttribute) {
            this.simpleAttribute = simpleAttribute;
        }

        @SuppressWarnings("unused")
        public void setSimpleAttribute2(String simpleAttribute2) {
            this.simpleAttribute2 = simpleAttribute2;
        }

        @SuppressWarnings("unused")
        @Attribute(name = "simpleAttribute2", handler = StringHandler.class)
        public String getSimpleAttribute2() {
            return this.simpleAttribute2;
        }

        @SuppressWarnings("unused")
        @Attribute(name = "simpleAttribute", handler = StringHandler.class)
        public String getSimpleAttribute() {
            return this.simpleAttribute;
        }

        @SuppressWarnings("unused")
        @Attribute(name = "complexAttribute", handler = ComplexHandler.class, type = ComplexTestType.class)
        public ComplexTestType getComplexAttribute() {
            return this.complexAttribute;
        }

        @SuppressWarnings("unused")
        @Attribute(name = "pluralAttribute", handler = PluralHandler.class, internalHandler = ComplexHandler.class)
        public List<PluralType<String>> getPluralAttribute() {
            return this.pluralAttribute;
        }
    }

    @Test
    public void getSetAttribute() throws UnknownAttribute {
        ComplexTestType ct = new ComplexTestType();
        ct.setAttribute("simpleAttribute", "simple data");

        ComplexTestType ct2 = new ComplexTestType();
        ct2.setAttribute("simpleAttribute", "simple data2");
        ct2.setAttribute("complexAttribute", ct);

        ct2.setAttribute("complexAttribute.simpleAttribute", "Hello");

        Assert.assertEquals("simple data2", ct2.getAttribute("simpleAttribute"));
        Assert.assertEquals("Hello", ct2.getAttribute("complexAttribute.simpleAttribute"));
    }

    @Test
    public void equals() throws UnknownAttribute {
        ComplexType ct = new ComplexTestType().setAttribute("simpleAttribute", "Test value 1");
        ComplexType ctEquals1 = new ComplexTestType().setAttribute("simpleAttribute", "Test value 1");
        ComplexType ctEquals2 = ct;
        ComplexType ctNotEquals1 = new ComplexTestType().setAttribute("simpleAttribute", "Test value not equals");
        ComplexType ctNotEquals3 = new ComplexTestType().setAttribute("simpleAttribute", "Test value 1").setAttribute("simpleAttribute2",
                "Test value 2");

        Assert.assertTrue(ct.equals(ctEquals1));
        Assert.assertTrue(ct.equals(ctEquals2));
        Assert.assertTrue(ctEquals1.equals(ct));
        Assert.assertTrue(ctEquals2.equals(ct));

        Assert.assertFalse(ct.equals(new Object()));
        Assert.assertFalse(ct.equals(ctNotEquals1));
        Assert.assertFalse(ct.equals(ctNotEquals3));
        Assert.assertFalse(ctNotEquals3.equals(ct));
        Assert.assertFalse(ctNotEquals3.equals(ct));
    }

    @Test
    public void merge() throws UnknownAttribute {

        ComplexType from = new ComplexTestType().setAttribute("simpleAttribute", "Test value from");
        ComplexType to = new ComplexTestType().setAttribute("simpleAttribute2", "Test value to");

        to = (ComplexType) new ComplexHandler().merge(from, to);
        Assert.assertEquals("Test value to", to.getAttribute("simpleAttribute2"));
        Assert.assertEquals("Test value from", to.getAttribute("simpleAttribute"));

        from = new ComplexTestType().setAttribute("simpleAttribute", "Hello").setAttribute("complexAttribute",
                new ComplexTestType().setAttribute("simpleAttribute", "World"));

        to = (ComplexType) new ComplexHandler().merge(from, to);

        Assert.assertNotNull(to.getAttribute("complexAttribute"));
        Assert.assertEquals(
                ((ComplexType) to.getAttribute("complexAttribute")).getAttribute("simpleAttribute"), "World");

        List<PluralType<String>> pluralList = new ArrayList<PluralType<String>>();
        pluralList.add(new PluralType<String>("nisse@kalle.com", "work", true, false));

        from = new ComplexTestType().setAttribute("pluralAttribute", pluralList);
        List<PluralType<String>> pluralListExpected = new ArrayList<PluralType<String>>();
        pluralListExpected.add(new PluralType<String>("nisse@kalle.com", "work", true, false));

        to = (ComplexType) new ComplexHandler().merge(from, to);
        Assert.assertEquals(to.getAttribute("pluralAttribute"), pluralListExpected);

        List<PluralType<String>> pluralList2 = new ArrayList<PluralType<String>>();
        pluralList2.add(new PluralType<String>("arne@kalle.com", "home", true, false));
        from = new ComplexTestType().setAttribute("pluralAttribute", pluralList2);

        pluralListExpected.add(new PluralType<String>("arne@kalle.com", "home", true, false));

        to = (ComplexType) new ComplexHandler().merge(from, to);
        Assert.assertEquals(pluralListExpected, to.getAttribute("pluralAttribute"));
    }

}
