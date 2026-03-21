package io.flamemath.expr;

import java.util.List;
import java.util.stream.Collectors;

public record Compound(String head, List<Expr> children) implements Expr {
    public Compound {
        children = List.copyOf(children);
    }

    @Override public boolean isAtomic() { return false; }

    @Override
    public String toString() {
        String args = children.stream()
                .map(Expr::toString)
                .collect(Collectors.joining(", "));
        return head + "(" + args + ")";
    }

    @Override
    public int hash() {
        throw new UnsupportedOperationException("Compound expressions are not hashable");
    }
}
