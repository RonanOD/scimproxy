package info.simplecloud.core.coding.encode;

import info.simplecloud.core.Resource;
import info.simplecloud.core.exceptions.EncodingFailed;

import java.util.List;

public interface IUserEncoder {

    String encode(Resource data) throws EncodingFailed;

    String encode(Resource data, List<String> includeAttributes) throws EncodingFailed;

    String encode(List<Resource> scimUsers, List<String> includeAttributes) throws EncodingFailed;

    String encode(List<Resource> scimUser) throws EncodingFailed;

}
