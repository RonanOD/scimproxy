package info.simplecloud.core;

import info.simplecloud.core.execeptions.UnknownAttribute;

import org.junit.Assert;
import org.junit.Test;

public class ComplexTypeTest {

    @Test
    public void getAttribute() throws UnknownAttribute {
        Object test = new Object();
        ComplexType ct = new ComplexType().addAttribute("test", test);

        Assert.assertEquals(test, ct.getAttribute("test"));
    }
}
