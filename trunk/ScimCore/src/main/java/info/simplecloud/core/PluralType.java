package info.simplecloud.core;

public class PluralType<T> {
    private T       value;
    private String  type;
    private boolean primary;

    public PluralType(T value, String type, boolean primary) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot create plural object without value");
        }
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

    @Override
    public boolean equals(Object otherObj) {
        if (!(otherObj instanceof PluralType)) {
            return false;
        }
        PluralType otherPlural = (PluralType) otherObj;

        if (!(this.type == otherPlural.type) || (this.type != null && !this.type.equals(otherPlural.type))) {
            return false;
        }

        if (this.primary != otherPlural.primary) {
            return false;
        }

        return this.value.equals(otherPlural);
    }

    @Override
    public String toString() {
        return "Value: '" + this.value.toString() + "', type: '" + this.type + "', primary";
    }
}
