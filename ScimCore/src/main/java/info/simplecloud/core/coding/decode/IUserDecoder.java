package info.simplecloud.core.coding.decode;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.InvalidUser;

import java.util.List;

public interface IUserDecoder {

    void decode(String user, ScimUser data) throws InvalidUser;

    void decode(String userList, List<ScimUser> users) throws InvalidUser;

}
