package info.simplecloud.core.coding.decode;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.InvalidUser;
import info.simplecloud.core.testtools.ResourceReader;
import info.simplecloud.core.types.PluralType;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class JsonDecoderTest {

    @Test(expected = InvalidUser.class)
    public void decodeInvalidUser() throws InvalidUser {
        String user = "";
        ScimUser scimUser = new ScimUser();
        JsonDecoder decoder = new JsonDecoder();

        decoder.decode(user, scimUser);
    }

    @Test
    public void decode() throws InvalidUser, IOException {
   /*     String user = ResourceReader.readTextFile("ScimUser.json", getClass());
        ScimUser scimUser = new ScimUser();
        JsonDecoder decoder = new JsonDecoder();

        decoder.decode(user, scimUser);

        Assert.assertEquals("005D0000001Az1u", scimUser.getId());
        Assert.assertEquals("bjensen@example.com", scimUser.getUserName());
        Assert.assertNotNull(scimUser.getName());
        Assert.assertEquals("Ms. Barbara J Jensen III", scimUser.getName().getFormatted());
        Assert.assertEquals("Barbara", scimUser.getName().getGivenName());
        Assert.assertNotNull(scimUser.getEmails());
        Assert.assertEquals(2, scimUser.getEmails().size());
        Assert.assertTrue(scimUser.getEmails().contains(new PluralType<String>("bjensen@example.com", "work", true)));
*/    }
}
