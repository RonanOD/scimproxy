package info.simplecloud.core.handlers;

import junit.framework.Assert;

import org.junit.Test;

public class StringHandlerTest {
    private static StringHandler sh = new StringHandler();

    @Test
    public void decode() {
        Assert.assertEquals("indata", sh.decode("indata", null, null));
    }

    @Test
    public void encode() {
        Assert.assertEquals("indata", sh.encode("indata", null, null));
    }

    @Test
    public void merge() {
        Assert.assertEquals("from", sh.merge("from", "to"));
    }
}
