package info.simplecloud.core.coding.encode;

import info.simplecloud.core.Resource;
import info.simplecloud.core.exceptions.EncodingFailed;

import java.util.List;

public interface IUserEncoder {

    String encode(Resource resource) throws EncodingFailed;

    String encode(Resource resource, List<String> includeAttributes) throws EncodingFailed;

    String encode(List<Resource> resources, List<String> includeAttributes) throws EncodingFailed;

    String encode(List<Resource> resources) throws EncodingFailed;

}
