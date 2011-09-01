package info.simplecloud.core.coding.decode;

import junit.framework.Assert;
import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.InvalidUser;

import org.junit.Test;

public class XmlDecoderTest {

    @Test
    public void decode() throws InvalidUser {

        XmlDecoder decoder = new XmlDecoder();
        User data = new User("ABC123");
        String user = "<urn:User xmlns:urn=\"urn:scim:schemas:core:1.0\"><id>ABCDE-12345-EFGHI-78910</id><userName>kaan</userName><externalId>djfkhasdjkfha</externalId><name><familyName>Andersson</familyName><givenName>Karl</givenName></name><displayName>Kalle</displayName><nickName>Kalle Anka</nickName><profileUrl>https://example.com</profileUrl><title>master</title><userType>super</userType><preferredLanguage>swedish</preferredLanguage><locale>sv</locale><password>kan123!</password><emails><email><value>karl@andersson.se</value><primary>false</primary><type>home</type></email><email><value>karl.andersson@work.com</value><primary>true</primary><type>work</type></email></emails></urn:User>";
        decoder.decode(user, data);

        
        Assert.assertEquals("ABCDE-12345-EFGHI-78910", data.getId());
        Assert.assertEquals("kaan", data.getUserName());
        Assert.assertEquals("Andersson", data.getName().getFamilyName());
        Assert.assertEquals("karl@andersson.se", data.getEmails().get(0).getValue());
        
        
        x0.scimSchemasCore1.User.Factory.newInstance().newCursor().toEndToken();
    }
}
