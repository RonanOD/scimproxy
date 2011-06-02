package info.simplecloud.core.encoding;

import info.simplecloud.core.ScimUser;

import java.util.Map;


public class JsonEncoder implements IUserEncoder {
    private static String[] names = { "json", "JSON" };

    public void addMe(Map<String, IUserEncoder> encoders) {
        for (String name : names) {
            encoders.put(name, this);
        }
    }

    @Override
    public String encode(ScimUser scimUser) {
        // TODO Create json representation
        return null;
    }

}
