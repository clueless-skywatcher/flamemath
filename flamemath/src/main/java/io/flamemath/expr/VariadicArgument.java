package io.flamemath.expr;

public record VariadicArgument(Symbol symbol) implements Expr {
    @Override public boolean isAtomic() { return false; }

    @Override
    public String head() {
        return "Variadic";
    }

    @Override
    public String toString() {
        return "..." + symbol.name();
    }

    @Override
    public int hash() {
        return symbol.hashCode();
    }
}
