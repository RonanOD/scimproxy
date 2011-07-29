package info.simplecloud.core.ng.handlers;

import java.util.List;

import info.simplecloud.core.ng.MetaData;

public class StringHandler implements IHandler {

    public static final StringHandler INSTANCE = new StringHandler();

    private StringHandler() {
        // Do nothing
    }

    @Override
    public Object decode(Object jsonData, Object me, MetaData internalMetaData) {
        return jsonData;
    }

    @Override
    public Object encode(Object me, MetaData internalMetaData, List<String> includeAttributes) {
        return me;
    }

    @Override
    public Object merge(Object from, Object to) {
        return from;
    }

    @Override
    public String toString() {
        return "StringHandler";
    }

}
