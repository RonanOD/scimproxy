package info.simplecloud.core.coding.encode;

import info.simplecloud.core.MetaData;

import java.util.List;

import org.json.JSONObject;

public interface IEncodeHandler {

    Object encode(Object me, List<String> includeAttributes, MetaData internalMetaData, JSONObject internalJsonObject);

    Object encodeXml(Object me, List<String> includeAttributes, MetaData internalMetaData, Object internalXmlObject);

}
