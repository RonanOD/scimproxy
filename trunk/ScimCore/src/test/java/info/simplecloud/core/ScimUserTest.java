package info.simplecloud.core;

import java.util.ArrayList;
import java.util.List;

import info.simplecloud.core.execeptions.FailedToGetValue;
import info.simplecloud.core.execeptions.FailedToSetValue;
import info.simplecloud.core.execeptions.InvalidUser;
import info.simplecloud.core.execeptions.UnhandledAttributeType;
import info.simplecloud.core.execeptions.UnknowExtension;
import info.simplecloud.core.execeptions.UnknownEncoding;
import info.simplecloud.core.execeptions.UnknownType;
import info.simplecloud.core.types.Name;
import info.simplecloud.core.types.PluralType;

import org.apache.xmlbeans.impl.util.Base64;
import org.junit.Assert;
import org.junit.Test;

public class ScimUserTest {

    @Test
    public void patch() throws UnknownEncoding, InvalidUser, UnhandledAttributeType, FailedToSetValue, UnknownType, InstantiationException,
            IllegalAccessException, FailedToGetValue, UnknowExtension {
        String patch = "ewogICJlbXBsb3llZU51bWJlciI6ICJhYmMiLAogICJuYW1lIjogewogICAgImZvcm1hdHRlZCI6ICJtci4gTmlzc2UgSm9oYW5zc29uIiwKICAgICJmYW1pbHlOYW1lIjogIkpvaGFuc3NvbiIKICB9LAogICJlbWFpbHMiOiBbCiAgICB7CiAgICAgICJ0eXBlIjogIndvcmsiLAogICAgICAicHJpbWFyeSI6IHRydWUsCiAgICAgICJ2YWx1ZSI6ICJuaXNzZUB3b3JrLmNvbSIKICAgIH0sCiAgICB7CiAgICAgICJ0eXBlIjogImhvbWUiLAogICAgICAicHJpbWFyeSI6IHRydWUsCiAgICAgICJ2YWx1ZSI6ICJuaXNzZUBqb2hhbnNzb24uY29tIgogICAgfQogIF0sCiAgIm1ldGEiOiB7CiAgICAiYXR0cmlidXRlcyI6IFsKICAgICAgImVtYWlscyIsCiAgICAgICJkZXBhcnRtZW50IgogICAgXQogIH0KfQ==";
        patch = new String(Base64.decode(patch.getBytes()));
        ScimUser oldUser = new ScimUser();
        oldUser.setId("123-123-123");
        oldUser.setName(new Name("mr. Nisse Svensson", "Svensson", "Nisse", null, "mr.", null));
        List<PluralType<String>> emails = new ArrayList<PluralType<String>>();
        emails.add(new PluralType<String>("nisse@work.com", "work", true));
        emails.add(new PluralType<String>("nisse@svensson.com", "home", true));
        // TODO add Address test

        oldUser.patch(patch, "JSON");
        Assert.assertEquals("mr. Nisse Johansson", oldUser.getName().getFormatted());
        Assert.assertEquals("Johansson", oldUser.getName().getFamilyName());
        Assert.assertEquals("Nisse", oldUser.getName().getGivenName());
    }
}
