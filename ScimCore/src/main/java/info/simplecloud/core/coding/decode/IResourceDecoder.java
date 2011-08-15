package info.simplecloud.core.coding.decode;

import info.simplecloud.core.Resource;
import info.simplecloud.core.exceptions.InvalidUser;

import java.util.List;

public interface IResourceDecoder {

    void decode(String user, Resource data) throws InvalidUser;

    void decode(String userList, List<Resource> users) throws InvalidUser;
}
