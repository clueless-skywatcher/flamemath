package io.flamemath.expr;

import java.util.List;

import io.flamemath.eval.FlameVironment;

public record Flambda(
    List<Symbol> params,
    Expr body,
    FlameVironment env
) implements Expr {
    @Override
    public boolean isAtomic() {
        return false;
    }

    @Override
    public String head() {
        return "Lambda";
    }

    @Override
    public int hash() {
        throw new UnsupportedOperationException("Lambdas are not hashable");
    }
}
