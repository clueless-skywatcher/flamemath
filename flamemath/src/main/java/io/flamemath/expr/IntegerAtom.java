package io.flamemath.expr;

public record IntegerAtom(long value) implements Expr {
    public static final IntegerAtom ZERO = new IntegerAtom(0);
    public static final IntegerAtom ONE = new IntegerAtom(1);
    public static final IntegerAtom MINUS_ONE = new IntegerAtom(-1);

    @Override public boolean isAtomic() { return true; }

    @Override
    public String head() {
        return "Integer";
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    @Override
    public boolean isNumeric() {
        return true;
    }

    @Override
    public int hash() {
        return Long.hashCode(value);
    }
}
