package info.simplecloud.core.types;

import info.simplecloud.core.types.PluralType;

import org.junit.Assert;
import org.junit.Test;

public class PluralTypeTest {

    @Test
    public void equals() {
        PluralType<String> in1 = new PluralType<String>("test", "test", true, false);
        PluralType<String> in1Equal1 = new PluralType<String>("test", "test", true, false);
        PluralType<String> in1Equal2 = in1;
        PluralType<String> in1NotEqual1 = new PluralType<String>("test1", "test", true, false);
        PluralType<String> in1Equal3 = new PluralType<String>("test", "test1", true, false);
        PluralType<String> in1Equal4 = new PluralType<String>("test", "test", false, false);

        Assert.assertTrue(in1.equals(in1Equal1));
        Assert.assertTrue(in1.equals(in1Equal2));
        Assert.assertFalse(in1.equals(in1NotEqual1));
        Assert.assertTrue(in1.equals(in1Equal3));
        Assert.assertTrue(in1.equals(in1Equal4));
        Assert.assertFalse(in1.equals(new Object()));
    }
}
