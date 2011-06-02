package info.simplecloud.core.encoding;

import info.simplecloud.core.ScimUser;

import java.util.Map;



public interface IUserEncoder {

    String encode(ScimUser scimUser);

    public void addMe(Map<String, IUserEncoder> encoders);
}
