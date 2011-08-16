package info.simplecloud.core.handlers;

import info.simplecloud.core.exceptions.InvalidUser;
import junit.framework.Assert;

import org.junit.Test;

public class BooleanHandlerTest {
    private BooleanHandler bh = new BooleanHandler();

    @Test
    public void decode() throws InvalidUser {
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
