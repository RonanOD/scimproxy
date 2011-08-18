package info.simplecloud.core.coding.encode;

import info.simplecloud.core.Resource;
import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.core.exceptions.UnknownEncoding;

import org.junit.Test;

public class XmlEncodeTest {

    @Test
    public void encode() throws UnknownEncoding, UnknownAttribute {
        
        
        User user = new User("ABC123");
        user.setUserName("Kalle");
        user.setAttribute("name.givenName", "Karl");
        System.out.println(user.getUser(Resource.ENCODING_XML));
    }
}
