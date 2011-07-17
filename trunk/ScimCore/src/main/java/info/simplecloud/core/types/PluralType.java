package info.simplecloud.core.types;

public class PluralType<T> implements Comparable<PluralType<?>> {
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

    public boolean isPrimary() {
        return this.primary;
    }

    @Override
    public boolean equals(Object otherObj) {
        if (!(otherObj instanceof PluralType)) {
            return false;
        }
        PluralType otherPlural = (PluralType) otherObj;

        if ((this.type != null && !this.type.equals(otherPlural.type))) {
            return false;
        }

        if (this.primary != otherPlural.primary) {
            return false;
        }

        return this.value.equals(otherPlural.value);
    }

    @Override
    public String toString() {
        return "Value: '" + this.value.toString() + "', type: '" + this.type + "', primary: " + this.primary;
    }

    @Override
    public int compareTo(PluralType<?> other) {
        if (this.getValue() instanceof Comparable) {
            Comparable value = (Comparable) this.getValue();
            return value.compareTo(other.getValue());
        }

        return 0;
    }
}
