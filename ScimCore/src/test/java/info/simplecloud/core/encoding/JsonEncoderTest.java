package info.simplecloud.core.encoding;

import java.util.LinkedList;
import java.util.List;

import info.simplecloud.core.Address;
import info.simplecloud.core.ComplexType;
import info.simplecloud.core.Name;
import info.simplecloud.core.PluralType;
import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.EncodingFailed;

import org.junit.Test;

public class JsonEncoderTest {

    @Test
    public void encode() throws EncodingFailed {
        ScimUser scimUser = new ScimUser();

        scimUser.setAttribute(ScimUser.ATTRIBUTE_ID, "yhgty-ujhyu-iolki");
        scimUser.setAttribute(ScimUser.ATTRIBUTE_NAME,
                new Name().setAttribute(Name.ATTRIBUTE_GIVEN_NAME, "Samuel").setAttribute(Name.ATTRIBUTE_HONORIFIC_PREFIX, "mr."));

        List<PluralType<String>> emails = new LinkedList<PluralType<String>>();
        emails.add(new PluralType<String>("samuel@erdtman.se", "private", true));
        emails.add(new PluralType<String>("samuel.erdtman@nexussafe.com", "work", false));
        scimUser.setAttribute(ScimUser.ATTRIBUTE_EMAILS, emails);

        List<PluralType<ComplexType>> addresses = new LinkedList<PluralType<ComplexType>>();
        addresses.add(new PluralType<ComplexType>(new Address().setAttribute(Address.ATTRIBUTE_CONTRY, "Sweeden").setAttribute(
                Address.ATTRIBUTE_POSTAL_CODE, "12 345"), "home", true));
        addresses.add(new PluralType<ComplexType>(new Address().setAttribute(Address.ATTRIBUTE_CONTRY, "England").setAttribute(
                Address.ATTRIBUTE_POSTAL_CODE, "67-890"), "work", false));

        scimUser.setAttribute(ScimUser.ATTRIBUTE_ADDRESSES, addresses);

        
        String jsonUser = new JsonEncoder().encode(scimUser);
        jsonUser.contains("Sweeden");
        jsonUser.contains("England");
        jsonUser.contains("samuel@erdtman.se");
        jsonUser.contains("samuel.erdtman@nexussafe.com");
        jsonUser.contains("yhgty-ujhyu-iolki");
    }
}
