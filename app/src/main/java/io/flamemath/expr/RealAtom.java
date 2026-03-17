package io.flamemath.expr;

public record RealAtom(double value) implements Expr {
    @Override public boolean isAtomic() { return true; }

    @Override
    public String head() {
        return "Real";
    }
}
