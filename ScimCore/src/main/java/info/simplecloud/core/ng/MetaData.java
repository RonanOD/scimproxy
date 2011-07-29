package info.simplecloud.core.ng;

import info.simplecloud.core.ng.handlers.IHandler;

import java.util.Comparator;

public class MetaData {
    private Class<?>      type;
    private IHandler      handler;
    private MetaData      internalMetaData;
    private String        nameSpacePrefix;
    private Comparator<?> ascending;
    private Comparator<?> descending;

    public MetaData(Class<?> type, IHandler handler, MetaData internalMetaData, String nameSpacePrefix, Comparator<?> ascending,
            Comparator<?> descending) {
        this.type = type;
        this.handler = handler;
        this.internalMetaData = internalMetaData;
        this.nameSpacePrefix = nameSpacePrefix;
        this.ascending = ascending;
        this.descending = descending;
    }

    public void validateType(Object attribute) throws Exception {
        if (attribute == null) {
            return; // null is for for clearing values
        }

        if (this.type.isInterface()) {
            Class<?>[] interfaces = attribute.getClass().getInterfaces();
            for (Class<?> clazz : interfaces) {
                if (this.type == clazz) {
                    // We are good the attribute implements our interface
                    return;
                }
            }
        }

        if (this.type == attribute.getClass()) {
            return;
        }

        throw new Exception("Attrbute '" + attribute + "' is wrong type, should be '" + type.getName() + "'");
    }

    public Object createType() {
        try {
            return type.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + type.getName(), e);
        }
    }

    public IHandler getHandler() {
        return this.handler;
    }

    public MetaData getInternalMetaData() {
        return this.internalMetaData;
    }

    public String getNameSpacePrefix() {
        return this.nameSpacePrefix;
    }

    public Comparator<?> getComparator(boolean sortAscending) {
        return (sortAscending ? this.ascending : this.descending);
    }

    public Class<?> getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof MetaData)) {
            return false;
        }

        MetaData otherMetaData = (MetaData) other;

        return (this.ascending == otherMetaData.ascending) && (this.descending == otherMetaData.descending)
                && (this.handler == otherMetaData.handler) && (this.internalMetaData == otherMetaData.internalMetaData)
                && (this.nameSpacePrefix != null && this.nameSpacePrefix.equals(otherMetaData.nameSpacePrefix))
                && (this.type == otherMetaData.type);

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(MetaData - type: '");
        sb.append(type.getName());
        sb.append("' handler: '");
        sb.append(handler.toString());
        sb.append("' nameSpacePrefix: '");
        sb.append(nameSpacePrefix);
        if (this.internalMetaData != null) {
            sb.append("' internalMetaData: '");
            sb.append(internalMetaData);
        }
        sb.append("'");

        return sb.toString();
    }
}
