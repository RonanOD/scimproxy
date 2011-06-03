package info.simplecloud.core.decoding;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.InvalidUser;

import java.util.Map;

import org.apache.xmlbeans.XmlException;

import x0.scimSchemasCore1.ScimUserType;

public class XmlDecoder implements IUserDecoder {
    private static String[] names = { "xml", "XML" };

    @Override
    public void addMe(Map<String, IUserDecoder> decoders) {
        for (String name : names) {
            decoders.put(name, this);
        }
    }

    @Override
    public void decode(String user, ScimUser scimUser) throws InvalidUser {
        try {
            ScimUserType scimUserBean = ScimUserType.Factory.parse(user);
            scimUserBean.getId();
            // TODO put all elements in to scimUser with getters and setters
        } catch (XmlException e) {
            throw new InvalidUser("Failed to parse user", e);
        }
    }

}
