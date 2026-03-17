package io.flamemath.expr;

public record BooleanAtom(boolean value) implements Expr {
    @Override public boolean isAtomic() { return true; }

    @Override
    public String head() {
        return "Boolean";
    }

    @Override
    public String toString() {
        return value ? "True" : "False";
    }
}
