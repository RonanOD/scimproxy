package info.simplecloud.core.coding.decode;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.exceptions.InvalidUser;

public interface IDecodeHandler {

    Object decode(Object value, Object me, MetaData internalMetaData) throws InvalidUser;

    Object decodeXml(Object value, Object me, MetaData internalMetaData) throws InvalidUser;
}
