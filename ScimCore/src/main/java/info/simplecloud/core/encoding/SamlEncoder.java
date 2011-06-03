package info.simplecloud.core.encoding;

import java.util.Map;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.decoding.IUserDecoder;
import info.simplecloud.core.execeptions.InvalidUser;

public class SamlEncoder implements IUserDecoder {

    @Override
    public void addMe(Map<String, IUserDecoder> decoders) {
        // TODO Auto-generated method stub

    }

    @Override
    public void decode(String user, ScimUser scimUser) throws InvalidUser {
        // TODO Auto-generated method stub

    }

}
