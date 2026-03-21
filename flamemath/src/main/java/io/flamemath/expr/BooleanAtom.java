package io.flamemath.expr;

public record BooleanAtom(boolean value) implements Expr {
    public static final BooleanAtom TRUE = new BooleanAtom(true);
    public static final BooleanAtom FALSE = new BooleanAtom(false);

    public static BooleanAtom of(boolean value) {
        return value ? TRUE : FALSE;
    }

    @Override public boolean isAtomic() { return true; }

    @Override
    public String head() {
        return "Boolean";
    }

    @Override
    public String toString() {
        return value ? "True" : "False";
    }

    @Override
    public boolean isTrue() {
        return value;
    }

    @Override
    public boolean isFalse() {
        return !value;
    }

    @Override
    public int hash() {
        return Boolean.hashCode(value);
    }
}
