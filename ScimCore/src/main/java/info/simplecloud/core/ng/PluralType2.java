package info.simplecloud.core.ng;

public class PluralType2 {
    private Object  value;
    private String  type;
    private boolean primary;

    public PluralType2(Object value, String type, boolean primary) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot create plural object without value");
        }
        this.value = value;
        this.type = type;
        this.primary = primary;
    }

    public Object getValue() {
        return this.value;
    }

    public String getType() {
        return this.type;
    }

    public boolean isPrimary() {
        return this.primary;
    }

    @Override
    public boolean equals(Object otherObj) {
        if (!(otherObj instanceof PluralType2)) {
            return false;
        }
        PluralType2 otherPlural = (PluralType2) otherObj;

        if ((this.type != null && !this.type.equals(otherPlural.type))) {
            return false;
        }

        return this.value.equals(otherPlural.value);
    }

    @Override
    public String toString() {
        return "(PluralType2 - value: '" + this.value.toString() + "', type: '" + this.type + "', primary: " + this.primary + ")";
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;

    }
}
