package info.simplecloud.core.coding.encode;

import info.simplecloud.core.MetaData;

import java.util.List;

public interface IEncodeHandler {

    Object encode(Object me, List<String> includeAttributes, MetaData internalMetaData);

    Object encodeXml(Object me, List<String> includeAttributes, MetaData internalMetaData);

}
