package info.simplecloud.core;

import info.simplecloud.core.execeptions.UnknownAttribute;

import org.junit.Assert;
import org.junit.Test;

public class ComplexTypeTest {

    @Test
    public void getAttribute() throws UnknownAttribute {
        Object test = new Object();
        ComplexType ct = new ComplexType();
        ct.setAttribute("test1", "Hello")
                .setAttribute("test2", test)
                .setAttribute("test3", test)
                .setAttribute(
                        "parent",
                        new ComplexType().setAttribute("child",
                                new ComplexType().setAttribute("grandchild1", "World").setAttribute("grandchild2", "G2")));

        Assert.assertNotNull(ct.getAttribute("parent"));
        Assert.assertNotNull(ct.getAttribute("parent.child"));
        Assert.assertEquals("World", ct.getAttribute("parent.child.grandchild1"));
        Assert.assertEquals("G2", ct.getAttribute("parent.child.grandchild2"));
        Assert.assertEquals("Hello", ct.getAttribute("test1"));
        Assert.assertNull(ct.getAttribute("unknown"));
        Assert.assertNull(ct.getAttribute("parent.child.unknown"));
    }
}
