package info.simplecloud.core.coding.decode;

import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.exceptions.UnknownExtension;
import info.simplecloud.core.extensions.EnterpriseAttributes;
import junit.framework.Assert;

import org.apache.xmlbeans.XmlException;
import org.junit.BeforeClass;
import org.junit.Test;

public class XmlDecoderTest {

    @BeforeClass
    public static void setUp() {
        User.registerExtension(EnterpriseAttributes.class);
    }

    @Test
    public void decode() throws InvalidUser, XmlException, UnknownExtension {

        XmlDecoder decoder = new XmlDecoder();
        User data = new User("ABC123");
        String user = "<urn:User xmlns:urn=\"urn:scim:schemas:core:1.0\"><id>ABCDE-12345-EFGHI-78910</id><userName>kaan</userName><externalId>djfkhasdjkfha</externalId><name><familyName>Andersson</familyName><givenName>Karl</givenName></name><displayName>Kalle</displayName><nickName>Kalle Anka</nickName><profileUrl>https://example.com</profileUrl><title>master</title><userType>super</userType><preferredLanguage>swedish</preferredLanguage><locale>sv</locale><password>kan123!</password><emails><email><value>karl@andersson.se</value><primary>false</primary><type>home</type></email><email><value>karl.andersson@work.com</value><primary>true</primary><type>work</type></email></emails><urn1:division xmlns:urn1=\"urn:scim:schemas:extension:enterprise:1.0\">Test division</urn1:division><urn1:manager xmlns:urn1=\"urn:scim:schemas:extension:enterprise:1.0\"><managerId>MI-123ABC</managerId><displayName>Hugo Boss</displayName></urn1:manager></urn:User>";
        decoder.decode(user, data);

        Assert.assertEquals("ABCDE-12345-EFGHI-78910", data.getId());
        Assert.assertEquals("kaan", data.getUserName());
        Assert.assertEquals("Andersson", data.getName().getFamilyName());
        Assert.assertEquals("karl@andersson.se", data.getEmails().get(0).getValue());
        EnterpriseAttributes ea = data.getExtension(EnterpriseAttributes.class);
        Assert.assertEquals("MI-123ABC", ea.getManager().getManagerId());
        Assert.assertEquals("Test division", ea.getDivision());
    }

    @Test
    public void decodeList() throws InvalidUser, XmlException, UnknownExtension {
        // TODO implement test
    }

}
