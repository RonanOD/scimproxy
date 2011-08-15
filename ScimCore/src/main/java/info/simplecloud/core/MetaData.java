package info.simplecloud.core;

import info.simplecloud.core.annotations.Attribute;
import info.simplecloud.core.coding.decode.IDecodeHandler;
import info.simplecloud.core.coding.encode.IEncodeHandler;
import info.simplecloud.core.exceptions.UnknownAttribute;
import info.simplecloud.core.merging.IMerger;
import info.simplecloud.core.types.ComplexType;
import info.simplecloud.core.types.DummyType;

public class MetaData {
    private Attribute attribute;
    private Class<?>  handler;
    private Class<?>  type;

    public MetaData(Attribute attribute) {
        this.attribute = attribute;
    }

    public MetaData(Class<?> handler, Class<?> type) {
        this.handler = handler;
        this.type = type;
    }

    private Class<?> internalGetDecoder() {
        return (attribute == null ? this.handler : attribute.handler());
    }

    private Class<?> internalGetEncoder() {
        return (attribute == null ? this.handler : attribute.handler());
    }

    private Class<?> internalGetMerger() {
        return (attribute == null ? this.handler : attribute.handler());
    }

    private Class<?> internalGetType() {
        return (attribute == null ? this.type : attribute.type());
    }

    public ComplexType newInstance() {
        try {
            Class<?> type = this.internalGetType();

            if (type == DummyType.class) {
                return null;
            }
            return (ComplexType) type.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Internal error getting merger", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Internal error getting merger", e);
        }
    }

    public IMerger getMerger() throws UnknownAttribute {
        try {
            return (IMerger) this.internalGetMerger().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Internal error getting merger", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Internal error getting merger", e);
        }
    }

    public IDecodeHandler getDecoder() {
        try {
            return (IDecodeHandler) this.internalGetDecoder().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Internal error getting merger", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Internal error getting merger", e);
        }
    }

    public IEncodeHandler getEncoder() {
        try {
            return (IEncodeHandler) this.internalGetEncoder().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Internal error getting merger", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Internal error getting merger", e);
        }
    }

    public MetaData getInternalMetaData() {
        return new MetaData(this.attribute.internalHandler(), this.attribute.internalType());
    }

    public String getName() {
        return this.attribute.name();
    }

}
