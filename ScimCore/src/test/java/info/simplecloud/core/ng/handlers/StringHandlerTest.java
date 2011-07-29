package info.simplecloud.core.ng.handlers;

import org.junit.Assert;
import org.junit.Test;

public class StringHandlerTest {
    private static StringHandler sh = StringHandler.INSTANCE;

    @Test
    public void parse() {
        Assert.assertEquals("TestData", sh.decode("TestData", null, null));
    }

    @Test
    public void encode() {
        Assert.assertEquals("TestData", sh.encode("TestData", null, null));
    }

    @Test
    public void merge() {
        Assert.assertEquals("TestData", sh.merge("TestData", null));
    }
}
