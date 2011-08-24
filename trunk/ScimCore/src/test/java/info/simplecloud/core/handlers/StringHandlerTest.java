package info.simplecloud.core.handlers;

import junit.framework.Assert;

import org.junit.Test;

public class StringHandlerTest {
    private static StringHandler sh = new StringHandler();

    @Test
    public void decode() {
        Assert.assertEquals("indata1", sh.decode("indata1", null, null));
        Assert.assertEquals("indata2", sh.decodeXml("indata2", null, null));
    }

    @Test
    public void encode() {
        Assert.assertEquals("indata3", sh.encode("indata3", null, null));
        Assert.assertEquals("indata4", sh.encodeXml("indata4", null, null, null));
    }

    @Test
    public void merge() {
        Assert.assertEquals("from", sh.merge("from", "to"));
    }
}
