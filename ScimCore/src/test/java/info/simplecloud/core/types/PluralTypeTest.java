package info.simplecloud.core.types;

import info.simplecloud.core.types.PluralType;

import org.junit.Assert;
import org.junit.Test;

public class PluralTypeTest {
    
    @Test
    public void equals() {
        PluralType<String> in1 = new PluralType<String>("test", "test", true);
        PluralType<String> in1Equal1 = new PluralType<String>("test", "test", true);
        PluralType<String> in1Equal2 = in1;
        PluralType<String> in1NotEqual1 = new PluralType<String>("test1", "test", true);
        PluralType<String> in1NotEqual2 = new PluralType<String>("test", "test1", true);
        PluralType<String> in1NotEqual3 = new PluralType<String>("test", "test", false);
        
        Assert.assertTrue(in1.equals(in1Equal1));
        Assert.assertTrue(in1.equals(in1Equal2));
        Assert.assertFalse(in1.equals(in1NotEqual1));
        Assert.assertFalse(in1.equals(in1NotEqual2));
        Assert.assertFalse(in1.equals(in1NotEqual3));
        Assert.assertFalse(in1.equals(new Object()));
    }
}
