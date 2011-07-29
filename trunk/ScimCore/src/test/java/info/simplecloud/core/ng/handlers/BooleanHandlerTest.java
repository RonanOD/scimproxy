package info.simplecloud.core.ng.handlers;

import org.junit.Assert;
import org.junit.Test;

public class BooleanHandlerTest {
    private static BooleanHandler bh = BooleanHandler.INSTANCE;

    @Test
    public void parse() {
        Assert.assertEquals(true, bh.decode(true, null, null));
        Assert.assertEquals(false, bh.decode(false, null, null));
    }

    @Test
    public void encode() {
        Assert.assertEquals(true, bh.encode(true, null, null));
        Assert.assertEquals(false, bh.encode(false, null, null));
    }

    @Test
    public void merge() {
        Assert.assertEquals(true, bh.merge(true, null));
        Assert.assertEquals(false, bh.merge(false, null));
    }
}
