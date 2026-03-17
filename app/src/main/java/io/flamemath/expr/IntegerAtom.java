package io.flamemath.expr;

public record IntegerAtom(long value) implements Expr {
    @Override public boolean isAtomic() { return true; }

    @Override
    public String head() {
        return "Integer";
    }
}
