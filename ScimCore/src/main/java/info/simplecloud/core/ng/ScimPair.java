package info.simplecloud.core.ng;

public class ScimPair<M, V> {
    public M meta;
    public V value;

    public ScimPair(M meta, V value) {
        this.meta = meta;
        this.value = value;
    }

    @Override
    public String toString() {
        return "(ScimPair - value: '" + this.value + "', meta: '" + this.meta + "')";
    }
}
