package info.simplecloud.core.coding.decode;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.types.ComplexType;

public interface IDecodeHandler {

    Object decode(Object value, Object instanceOf, MetaData internalMetaData) throws InvalidUser;
}
