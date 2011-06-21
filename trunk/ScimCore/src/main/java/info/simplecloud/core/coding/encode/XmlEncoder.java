package info.simplecloud.core.coding.encode;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.EncodingFailed;

import java.util.List;
import java.util.Map;

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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String encode(List<ScimUser> scimUser) throws EncodingFailed {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String encode(ScimUser scimUser, List<String> attributesList) throws EncodingFailed {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String encode(List<ScimUser> arrayList, List<String> attributesList) throws EncodingFailed {
        // TODO Auto-generated method stub
        return null;
    }

}
