package info.simplecloud.core.coding.decode;

import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.InvalidUser;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

public class JsonDecoderTest {
  private static final String USER_ROLES_JSON = "{" +
      "  \"schemas\": [" +
      "     \"urn:scim:schemas:core:1.0\"," +
      "  ]," +
      "  \"id\":\"1630297303850073\"," +
      "  \"userName\": \"jbloggs\u0040test.com\"," +
      "  \"roles\": [" +
      "     {" +
      "        \"value\": \"administrator\"," +
      "        \"type\": \"work\"," +
      "        \"primary\": true" +
      "     }" +
      "  ]," +
      "  \"name\": {" +
      "     \"familyName\": \"Bloggs\"," +
      "     \"givenName\": \"Joseph\"" +
      "  }," +
      "  \"active\": true," +
      "  \"emails\": [" +
      "     {" +
      "        \"primary\": true," +
      "        \"type\": \"work\"," +
      "        \"value\": \"jbloggs\u0040test.com\"" +
      "     }" +
      "  ]" +
      "}";

    @Test(expected = InvalidUser.class)
    public void decodeInvalidUser() throws InvalidUser {
        String user = "";
        User scimUser = new User("123");
        JsonDecoder decoder = new JsonDecoder();

        decoder.decode(user, scimUser);
    }

    @Test
    public void decodeMinimal() throws InvalidUser {
        String jsonUser = "{\"schemas\": [\"urn:scim:schemas:core:1.0\"],\"id\": \"005D0000001Az1u\","
                + "\"userName\": \"bjensen@example.com\"}";
        User user = new User("123");
        JsonDecoder decoder = new JsonDecoder();

        decoder.decode(jsonUser, user);
        
    }

    @Test
    public void decodeRoles() throws InvalidUser {
        User user = new User("123");
        JsonDecoder decoder = new JsonDecoder();

        decoder.decode(USER_ROLES_JSON, user);
        Assert.assertEquals("administrator", user.getRoles().get(0).getValue());
    }    

    @Test
    public void decode() throws InvalidUser, IOException {
        /*
         * String user = ResourceReader.readTextFile("ScimUser.json",
         * getClass()); ScimUser scimUser = new ScimUser(); JsonDecoder decoder
         * = new JsonDecoder();
         * 
         * decoder.decode(user, scimUser);
         * 
         * Assert.assertEquals("005D0000001Az1u", scimUser.getId());
         * Assert.assertEquals("bjensen@example.com", scimUser.getUserName());
         * Assert.assertNotNull(scimUser.getName());
         * Assert.assertEquals("Ms. Barbara J Jensen III",
         * scimUser.getName().getFormatted()); Assert.assertEquals("Barbara",
         * scimUser.getName().getGivenName());
         * Assert.assertNotNull(scimUser.getEmails()); Assert.assertEquals(2,
         * scimUser.getEmails().size());
         * Assert.assertTrue(scimUser.getEmails().contains(new
         * PluralType<String>("bjensen@example.com", "work", true)));
         */
        
        // TODO implement test
    }
}
