package info.simplecloud.core.coding.decode;

import info.simplecloud.core.Resource;
import info.simplecloud.core.exceptions.InvalidUser;

import java.util.List;

public interface IResourceDecoder {

    void decode(String resourceString, Resource resource) throws InvalidUser;

    void decode(String resourceListString, List<Resource> resources, Class<?> type) throws InvalidUser;
}
