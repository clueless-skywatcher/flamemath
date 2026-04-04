package io.flamemath.expr;

import io.flamemath.internal.FlameInt;

public record IntegerAtom(FlameInt value) implements Expr, Comparable<IntegerAtom> {
    public static final IntegerAtom ZERO = new IntegerAtom(FlameInt.ZERO);
    public static final IntegerAtom ONE = new IntegerAtom(FlameInt.ONE);
    public static final IntegerAtom MINUS_ONE = new IntegerAtom(FlameInt.MINUS_ONE);
    public static final IntegerAtom TWO = new IntegerAtom(FlameInt.TWO);

    @Override public boolean isAtomic() { return true; }

    @Override
    public String head() {
        return "Integer";
    }

    public IntegerAtom(long v) {
        this(new FlameInt(v));
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean isNumeric() {
        return true;
    }

    @Override
    public int hash() {
        return value.hashCode();
    }

    @Override
    public int compareTo(IntegerAtom arg0) {
        return value.compareTo(arg0.value);
    }
}
