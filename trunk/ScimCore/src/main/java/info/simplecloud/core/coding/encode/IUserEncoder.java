package info.simplecloud.core.coding.encode;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.EncodingFailed;

import java.util.List;
import java.util.Map;

public interface IUserEncoder {

    public void addMe(Map<String, IUserEncoder> encoders);

    String encode(ScimUser data) throws EncodingFailed;

    String encode(ScimUser data, List<String> includeAttributes) throws EncodingFailed;

    String encode(List<ScimUser> scimUsers) throws EncodingFailed;

    String encode(List<ScimUser> scimUsers, List<String> includeAttributes) throws EncodingFailed;

}
