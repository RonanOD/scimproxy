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
    
    @Test
    public void equals() {
        ComplexType ct = new ComplexType().setAttribute("testid1", "Test value 1");
        ComplexType ctEquals1 = new ComplexType().setAttribute("testid1", "Test value 1");
        ComplexType ctEquals2 = ct;
        ComplexType ctNotEquals1 = new ComplexType().setAttribute("testid1", "Test value not equals");
        ComplexType ctNotEquals2 = new ComplexType().setAttribute("notFound", "Test value 1");
        ComplexType ctNotEquals3 = new ComplexType().setAttribute("testid1", "Test value 1").setAttribute("testid2", "Test value 2");
        ComplexType ctNotEquals4 = new ComplexType().setAttribute("testid1", new Object());
        
        Assert.assertTrue(ct.equals(ctEquals1));
        Assert.assertTrue(ct.equals(ctEquals2));
        Assert.assertTrue(ct.equals(ctEquals1));
        Assert.assertTrue(ct.equals(ctEquals1));
        Assert.assertTrue(ct.equals(ctEquals1));
        
        Assert.assertFalse(ct.equals(new Object()));
        Assert.assertFalse(ct.equals(ctNotEquals1));
        Assert.assertFalse(ct.equals(ctNotEquals2));
        Assert.assertFalse(ct.equals(ctNotEquals3));
        Assert.assertFalse(ct.equals(ctNotEquals4));
    }
}
