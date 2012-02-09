package info.simplecloud.core.types;

import info.simplecloud.core.types.MultiValuedType;

import org.junit.Assert;
import org.junit.Test;

public class MultiValuedTypeTest {

    @Test
    public void equals() {
        MultiValuedType<String> in1 = new MultiValuedType<String>("test", "test", true, false);
        MultiValuedType<String> in1Equal1 = new MultiValuedType<String>("test", "test", true, false);
        MultiValuedType<String> in1Equal2 = in1;
        MultiValuedType<String> in1NotEqual1 = new MultiValuedType<String>("test1", "test", true, false);
        MultiValuedType<String> in1Equal3 = new MultiValuedType<String>("test", "test1", true, false);
        MultiValuedType<String> in1Equal4 = new MultiValuedType<String>("test", "test", false, false);

        Assert.assertTrue(in1.equals(in1Equal1));
        Assert.assertTrue(in1.equals(in1Equal2));
        Assert.assertFalse(in1.equals(in1NotEqual1));
        Assert.assertTrue(in1.equals(in1Equal3));
        Assert.assertTrue(in1.equals(in1Equal4));
        Assert.assertFalse(in1.equals(new Object()));
    }
}
