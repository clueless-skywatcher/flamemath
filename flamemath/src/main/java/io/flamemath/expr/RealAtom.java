package io.flamemath.expr;

public record RealAtom(double value) implements Expr {
    @Override public boolean isAtomic() { return true; }

    @Override
    public String head() {
        return "Real";
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean isNumeric() {
        return true;
    }

    @Override
    public int hash() {
        return Double.hashCode(value);
    }
}
