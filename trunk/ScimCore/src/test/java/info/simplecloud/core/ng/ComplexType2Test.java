package info.simplecloud.core.ng;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComplexType2Test {
    private ComplexType2 complex;
    private ComplexType2 level2Complex;
    private ComplexType2 level3Complex;

    @Before
    public void setUp() {
        Map<String, ScimPair<MetaData, Object>> levle3Tree = new HashMap<String, ScimPair<MetaData, Object>>();
        levle3Tree.put("level3Simple1", new ScimPair<MetaData, Object>(Types.META_SIMPLE_STRING, null));
        levle3Tree.put("level3Simple2", new ScimPair<MetaData, Object>(Types.META_SIMPLE_STRING, null));
        level3Complex = new ComplexType2(levle3Tree);

        Map<String, ScimPair<MetaData, Object>> level2Tree = new HashMap<String, ScimPair<MetaData, Object>>();
        level2Tree.put("internalSimple1", new ScimPair<MetaData, Object>(Types.META_SIMPLE_STRING, null));
        level2Tree.put("internalSimple2", new ScimPair<MetaData, Object>(Types.META_SIMPLE_STRING, null));
        level2Tree.put("internalSimple3", new ScimPair<MetaData, Object>(Types.META_SIMPLE_STRING, null));
        level2Tree.put("nextComplex", new ScimPair<MetaData, Object>(Types.META_COMPLEX, level3Complex));
        level2Complex = new ComplexType2(level2Tree);

        Map<String, ScimPair<MetaData, Object>> tree = new HashMap<String, ScimPair<MetaData, Object>>();
        tree.put("simple", new ScimPair<MetaData, Object>(Types.META_SIMPLE_STRING, null));
        tree.put("pluralSimple", new ScimPair<MetaData, Object>(Types.META_PLURAL_SIMPLE, null));
        tree.put("pluralComplex", new ScimPair<MetaData, Object>(Types.META_PLURAL_COMPLEX, null));
        tree.put("complex", new ScimPair<MetaData, Object>(Types.META_COMPLEX, level2Complex));
        complex = new ComplexType2(tree);
    }

    @Test(expected = Exception.class)
    public void unknownAttributesGet() throws Exception {
        complex.getAttribute("test", Object.class);
    }

    @Test(expected = Exception.class)
    public void unknownAttributesSet() throws Exception {
        complex.setAttribute("test", new Object());
    }

    @Test(expected = Exception.class)
    public void levelUnknownAttributesGet() throws Exception {
        complex.getAttribute("test.test", Object.class);
    }

    @Test(expected = Exception.class)
    public void levelUnknownAttributesSet() throws Exception {
        complex.setAttribute("test.test", new Object());
    }

    @Test
    public void simple() throws Exception {
        complex.setAttribute("simple", "TestString");
        String result = complex.getAttribute("simple", String.class);

        Assert.assertEquals("TestString", result);
    }

    @Test
    public void pluralSimple() throws Exception {
        List<PluralType2> indata = new ArrayList<PluralType2>();
        indata.add(new PluralType2("string1", "home", true));
        indata.add(new PluralType2("string2", "work", false));

        complex.setAttribute("pluralSimple", indata);

        List<PluralType2> result = complex.getAttribute("pluralSimple", List.class);

        List<PluralType2> expected = new ArrayList<PluralType2>();
        expected.add(new PluralType2("string1", "home", true));
        expected.add(new PluralType2("string2", "work", false));

        Assert.assertEquals(expected, result);
    }

    @Test
    public void pluralComplex() throws Exception {

    }

    @Test
    public void complex() throws Exception {
        complex.setAttribute("complex.internalSimple1", "internal1");
        complex.setAttribute("complex.internalSimple2", "internal2");

        String result1 = complex.getAttribute("complex.internalSimple1", String.class);
        String result2 = complex.getAttribute("complex.internalSimple2", String.class);
        String result3 = complex.getAttribute("complex.internalSimple3", String.class);

        Assert.assertEquals("internal1", result1);
        Assert.assertEquals("internal2", result2);
        Assert.assertNull(result3);
    }

    @Test(expected = Exception.class)
    public void secondLevelTypeMissmatchGet() throws Exception {
        complex.setAttribute("simple", "empty");
        complex.getAttribute("simple.unknown", String.class);

    }

    @Test(expected = Exception.class)
    public void secondLevelTypeMissmatchSet() throws Exception {
        complex.setAttribute("simple.unknown", "empty");
    }

    @Test
    public void getMetadata() throws Exception {
        Assert.assertEquals(Types.META_SIMPLE_STRING, complex.getMetaData("simple"));
        Assert.assertEquals(Types.META_PLURAL_SIMPLE, complex.getMetaData("pluralSimple"));
        Assert.assertEquals(Types.META_PLURAL_COMPLEX, complex.getMetaData("pluralComplex"));
        Assert.assertEquals(Types.META_COMPLEX, complex.getMetaData("complex"));

        Assert.assertEquals(Types.META_COMPLEX, complex.getMetaData("complex.nextComplex"));
        Assert.assertEquals(Types.META_SIMPLE_STRING, complex.getMetaData("complex.nextComplex.level3Simple1"));

    }

    @Test
    public void getUnknownMetadata() throws Exception {
        Assert.assertNull(complex.getMetaData("unknown"));
    }

    @Test(expected = Exception.class)
    public void firstLevelTypeMissmatch() throws Exception {
        complex.getAttribute("simple", Integer.class);
    }
}
