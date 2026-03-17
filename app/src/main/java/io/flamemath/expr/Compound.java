package io.flamemath.expr;

import java.util.List;

public record Compound(String head, List<Expr> children) implements Expr {
    public Compound {
        children = List.copyOf(children);
    }

    @Override public boolean isAtomic() { return false; }
}
