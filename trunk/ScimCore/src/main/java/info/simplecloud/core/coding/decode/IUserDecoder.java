package info.simplecloud.core.coding.decode;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.InvalidUser;

import java.util.List;
import java.util.Map;



public interface IUserDecoder {

    public void addMe(Map<String, IUserDecoder> decoders);

    void decode(String user, ScimUser data) throws InvalidUser;

    void decode(String userList, List<ScimUser> users) throws InvalidUser;

}
