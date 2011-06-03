package info.simplecloud.core.encoding;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.EncodingFailed;

import java.util.Map;

public interface IUserEncoder {

    String encode(ScimUser scimUser) throws EncodingFailed;

    public void addMe(Map<String, IUserEncoder> encoders);
}
