package info.simplecloud.core.handlers;

import java.util.List;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.coding.decode.IDecodeHandler;
import info.simplecloud.core.coding.encode.IEncodeHandler;
import info.simplecloud.core.exceptions.InvalidUser;
import info.simplecloud.core.merging.IMerger;

public class BooleanHandler implements IDecodeHandler,IEncodeHandler, IMerger {

    @Override
    public Object decode(Object value, Object instance, MetaData internalMetaData) throws InvalidUser {
        return value;
    }
    @Override
    public Object encode(Object me, List<String> includeAttributes, MetaData internalMetaData) {
        return me;
    }
    
    @Override
    public Object merge(Object from, Object to) {
        return from;
    }
}
