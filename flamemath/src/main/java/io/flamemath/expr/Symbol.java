package io.flamemath.expr;

public record Symbol(String name) implements Expr {
    @Override public boolean isAtomic() { return true; }

    @Override
    public String head() {
        return "Symbol";
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hash() {
        return name.hashCode();
    }
}
