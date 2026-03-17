package io.flamemath.expr;

public record ComplexAtom(double re, double im) implements Expr {
    @Override public boolean isAtomic() { return true; }

    @Override
    public String head() {
        return "Complex";
    }
}
