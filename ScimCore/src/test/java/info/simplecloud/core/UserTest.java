package info.simplecloud.core;


import info.simplecloud.core.types.Name;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class UserTest {
    @Test
    public void create() throws Exception {
        User user = new User("ABCDE-12345-EFGHI-78910");

        user.setAttribute("userName", "kaan");
        user.setAttribute("externalId", "djfkhasdjkfha");
        user.setAttribute("name", new Name());
        user.setAttribute("name.givenName", "Karl");
        user.setAttribute("name.familyName", "Andersson");
        user.setAttribute("displayName", "Kalle");
        user.setAttribute("nickName", "Kalle Anka");
        user.setAttribute("profileUrl", "https://example.com");
        user.setAttribute("title", "master");
        user.setAttribute("userType", "super");
        user.setAttribute("preferredLanguage", "swedish");
        user.setAttribute("locale", "sv");
        user.setAttribute("password", "kan123!");

        user.toString();
    }

    @Test
    public void encode() throws Exception {
        User user = new User("ABCDE-12345-EFGHI-78910");

        user.setAttribute("userName", "kaan");
        user.setAttribute("externalId", "djfkhasdjkfha");
        user.setAttribute("name", new Name());
        user.setAttribute("name.givenName", "Karl");
        user.setAttribute("name.familyName", "Andersson");
        user.setAttribute("displayName", "Kalle");
        user.setAttribute("nickName", "Kalle Anka");
        user.setAttribute("profileUrl", "https://example.com");
        user.setAttribute("title", "master");
        user.setAttribute("userType", "super");
        user.setAttribute("preferredLanguage", "swedish");
        user.setAttribute("locale", "sv");
        user.setAttribute("password", "kan123!");
        
        /*
        List<PluralType2> emails = new ArrayList<PluralType2>();
        emails.add(new PluralType2("karl@andersson.se", "home", false));
        emails.add(new PluralType2("karl.andersson@work.com", "work", true));
        user.setAttribute("emails", emails);
        */
        
        user.getUser("json");
    }

    @Test
    public void encodePartial() throws Exception {
        User user = new User("ABCDE-12345-EFGHI-78910");

        user.setAttribute("userName", "kaan");
        user.setAttribute("externalId", "djfkhasdjkfha");
        user.setAttribute("name", new Name());
        user.setAttribute("name.givenName", "Karl");
        user.setAttribute("name.familyName", "Andersson");
        user.setAttribute("displayName", "Kalle");
        user.setAttribute("nickName", "Kalle Anka");
        user.setAttribute("profileUrl", "https://example.com");
        user.setAttribute("title", "master");
        user.setAttribute("userType", "super");
        user.setAttribute("preferredLanguage", "swedish");
        user.setAttribute("locale", "sv");
        user.setAttribute("password", "kan123!");
        /*
        List<PluralType2> emails = new ArrayList<PluralType2>();
        emails.add(new PluralType2("karl@andersson.se", "home", false));
        emails.add(new PluralType2("karl.andersson@work.com", "work", true));
        user.setAttribute("emails", emails);
        */
        
        List<String> includeAttributes = new ArrayList<String>();
        includeAttributes.add("userName");
        includeAttributes.add("title");
        
        user.getUser("json",includeAttributes);
    }

    @Test
    public void decode() throws Exception {
        User user = new User("ABCDE-12345-EFGHI-78910");

        user.setAttribute("userName", "kaan");
        user.setAttribute("externalId", "djfkhasdjkfha");
        user.setAttribute("name", new Name());
        user.setAttribute("name.givenName", "Karl");
        user.setAttribute("name.familyName", "Andersson");
        user.setAttribute("displayName", "Kalle");
        user.setAttribute("nickName", "Kalle Anka");
        user.setAttribute("profileUrl", "https://example.com");
        user.setAttribute("title", "master");
        user.setAttribute("userType", "super");
        user.setAttribute("preferredLanguage", "swedish");
        user.setAttribute("locale", "sv");
        user.setAttribute("password", "kan123!");

        /*
        List<PluralType2> emails = new ArrayList<PluralType2>();
        emails.add(new PluralType2("karl@andersson.se", "home", false));
        emails.add(new PluralType2("karl.andersson@work.com", "work", true));
        user.setAttribute("emails", emails);
        */
        
        List<String> includeAttributes = new ArrayList<String>();
        includeAttributes.add("userName");
        includeAttributes.add("title");
        user.getUser("json");
        User user2 = new User(user.getUser("json"), "json");
        user2.getUser("json");
    }

    @Test
    public void patch() {
        
    }

    @Test
    public void sort() {

    }

}
