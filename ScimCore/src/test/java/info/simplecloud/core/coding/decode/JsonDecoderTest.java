package info.simplecloud.core.coding.decode;

import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.InvalidUser;

import java.io.IOException;

import org.junit.Test;

public class JsonDecoderTest {

    @Test(expected = InvalidUser.class)
    public void decodeInvalidUser() throws InvalidUser {
        String user = "";
        User scimUser = new User("123");
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
