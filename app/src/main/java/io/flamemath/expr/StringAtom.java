package io.flamemath.expr;

public record StringAtom(String value) implements Expr {
    @Override public boolean isAtomic() { return true; }

    @Override
    public String head() {
        return "String";
    }
}
