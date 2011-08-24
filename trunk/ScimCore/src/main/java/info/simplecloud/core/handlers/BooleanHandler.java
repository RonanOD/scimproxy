package info.simplecloud.core.handlers;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.coding.decode.IDecodeHandler;
import info.simplecloud.core.coding.encode.IEncodeHandler;
import info.simplecloud.core.merging.IMerger;

import java.util.List;

public class BooleanHandler implements IDecodeHandler, IEncodeHandler, IMerger {

    @Override
    public Object decode(Object value, Object instance, MetaData internalMetaData) {
        return HandlerHelper.typeCheck(value, Boolean.class);
    }

    @Override
    public Object decodeXml(Object value, Object newInstance, MetaData internalMetaData) {
        return HandlerHelper.typeCheck(value, Boolean.class);
    }

    @Override
    public Object encode(Object me, List<String> includeAttributes, MetaData internalMetaData) {
        return HandlerHelper.typeCheck(me, Boolean.class);
    }

    @Override
    public Object encodeXml(Object me, List<String> includeAttributes, MetaData internalMetaData, Object xmlObject) {
        return HandlerHelper.typeCheck(me, Boolean.class);
    }

    @Override
    public Object merge(Object from, Object to) {
        return HandlerHelper.typeCheck(from, Boolean.class);
    }

}
