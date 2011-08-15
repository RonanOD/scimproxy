package info.simplecloud.core.coding.encode;

import info.simplecloud.core.Resource;
import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.EncodingFailed;
import info.simplecloud.core.exceptions.InvalidUser;

import java.util.List;

public interface IUserEncoder {

    String encode(Resource data) throws EncodingFailed;

    String encode(Resource data, List<String> includeAttributes) throws EncodingFailed;

    String encode(List<User> scimUsers) throws EncodingFailed;

    String encode(List<User> scimUsers, List<String> includeAttributes) throws EncodingFailed;

}
