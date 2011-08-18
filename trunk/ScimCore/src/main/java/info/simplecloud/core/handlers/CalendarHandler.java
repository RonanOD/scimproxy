package info.simplecloud.core.handlers;

import info.simplecloud.core.MetaData;
import info.simplecloud.core.coding.decode.IDecodeHandler;
import info.simplecloud.core.coding.encode.IEncodeHandler;
import info.simplecloud.core.merging.IMerger;

import java.util.List;

public class CalendarHandler implements IDecodeHandler, IEncodeHandler, IMerger {

    @Override
    public Object decode(Object jsonData, Object me, MetaData internalMetaData) {
        // TODO Auto-generated method stub
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Object decodeXml(Object value, Object newInstance, MetaData internalMetaData) {
        // TODO Auto-generated method stub
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Object encode(Object me, List<String> includeAttributes, MetaData internalMetaData) {
        // TODO Auto-generated method stub
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Object encodeXml(Object me, List<String> includeAttributes, MetaData internalMetaData, Object xmlObject) {
        // TODO Auto-generated method stub
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Object merge(Object from, Object to) {
        // TODO Auto-generated method stub
        throw new RuntimeException("Not implemented");
    }

}
