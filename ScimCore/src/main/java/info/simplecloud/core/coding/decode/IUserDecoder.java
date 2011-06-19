package info.simplecloud.core.coding.decode;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.FailedToSetValue;
import info.simplecloud.core.execeptions.InvalidUser;
import info.simplecloud.core.execeptions.UnhandledAttributeType;
import info.simplecloud.core.execeptions.UnknownType;

import java.util.Map;



public interface IUserDecoder {

    public void addMe(Map<String, IUserDecoder> decoders);

    void decode(String user, ScimUser data) throws InvalidUser, UnhandledAttributeType, FailedToSetValue, UnknownType,
            InstantiationException, IllegalAccessException;
}
