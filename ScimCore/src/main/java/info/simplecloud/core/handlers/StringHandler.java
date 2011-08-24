package info.simplecloud.core.handlers;

import java.util.List;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.coding.decode.IDecodeHandler;
import info.simplecloud.core.coding.encode.IEncodeHandler;
import info.simplecloud.core.merging.IMerger;

public class StringHandler implements IDecodeHandler, IEncodeHandler, IMerger {

    @Override
    public Object decode(Object value, Object instance, MetaData internalMetaData) {
        return HandlerHelper.typeCheck(value, String.class);
    }

    @Override
    public Object decodeXml(Object value, Object newInstance, MetaData internalMetaData) {
        return HandlerHelper.typeCheck(value, String.class);
    }

    @Override
    public Object encode(Object me, List<String> includeAttributes, MetaData internalMetaData) {
        return HandlerHelper.typeCheck(me, String.class);
    }

    @Override
    public Object encodeXml(Object me, List<String> includeAttributes, MetaData internalMetaData, Object xmlObject) {
        return HandlerHelper.typeCheck(me, String.class);
    }

    @Override
    public Object merge(Object from, Object to) {
        return HandlerHelper.typeCheck(from, String.class);
    }
}
