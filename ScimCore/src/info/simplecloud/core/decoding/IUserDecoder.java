package info.simplecloud.core.decoding;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.InvalidUserException;

import java.util.Map;



public interface IUserDecoder {

    public void addMe(Map<String, IUserDecoder> decoders);

    public void decode(String user, ScimUser scimUser) throws InvalidUserException;
}
