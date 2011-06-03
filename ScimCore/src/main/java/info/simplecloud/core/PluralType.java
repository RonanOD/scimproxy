package info.simplecloud.core;

public class PluralType<T> {
    private T       value;
    private String  type;
    private boolean primary;

    public PluralType(T value, String type, boolean primary) {
        this.value = value; // TODO clone to make PluralType immutable
        this.type = type;
        this.primary = primary;
    }

    public T getValue() {
        return this.value; // TODO clone to make PluralType immutable;
    }

    public String getType() {
        return this.type;
    }

    public boolean getPrimary() {
        return this.primary;
    }
}
