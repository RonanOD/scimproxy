package info.simplecloud.core.decoding;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.InvalidUserException;

import java.util.Map;

public class SamlDecoder implements IUserDecoder {

    @Override
    public void addMe(Map<String, IUserDecoder> decoders) {
        // TODO Auto-generated method stub

    }

    @Override
    public void decode(String user, ScimUser scimUser) throws InvalidUserException {
        // TODO Auto-generated method stub

    }

}
