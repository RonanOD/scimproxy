package info.simplecloud.core.encoding;

import info.simplecloud.core.ScimUser;

import java.util.Map;


import x0.scimSchemasCore1.ScimUserDocument;
import x0.scimSchemasCore1.ScimUserType;

public class XmlEncoder implements IUserEncoder {
    private static String[] names = { "xml", "XML" };

    @Override
    public void addMe(Map<String, IUserEncoder> encoders) {
        for (String name : names) {
            encoders.put(name, this);
        }
    }

    @Override
    public String encode(ScimUser scimUser) {
        ScimUserType scimUserBean = ScimUserType.Factory.newInstance();
        scimUserBean.setId("005D0000001Az1u");
        scimUserBean.setUserName("bjensen@example.com");
        
        // TODO read from scimUser

        ScimUserDocument doc = ScimUserDocument.Factory.newInstance();
        doc.setScimUser(scimUserBean);
        return doc.xmlText();
    }

}
