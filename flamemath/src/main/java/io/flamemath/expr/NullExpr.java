package io.flamemath.expr;

public enum NullExpr implements Expr {
    INSTANCE;

    @Override
    public boolean isAtomic() {
        return true;
    }

    @Override
    public String head() {
        return "Null";
    }

    @Override
    public String toString() {
        return "Null";
    }
}
