package info.simplecloud.core.coding.encode;

import info.simplecloud.core.Resource;
import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.EncodingFailed;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.core.exceptions.UnknownEncoding;

import org.junit.Test;

public class XmlEncodeTest {

    @Test
    public void encode() throws UnknownEncoding, UnknownAttribute {
        // TODO work directly towards XmlEncode, we do not want to test user

        User user = new User("ABC123");
        user.setUserName("Kalle");
        user.setAttribute("name.givenName", "Karl");
        user.getUser(Resource.ENCODING_XML);
    }

    @Test
    public void encodePartial() throws UnknownEncoding, UnknownAttribute {
        // TODO implement test, test to set include attributes
    }

    @Test
    public void encodeList() throws EncodingFailed {
        // TODO implement test
    }

    @Test
    public void encodeListPartial() throws EncodingFailed {
        // TODO implement test, test to set include attributes
    }

}
