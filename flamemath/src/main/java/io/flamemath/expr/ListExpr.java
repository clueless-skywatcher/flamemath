package io.flamemath.expr;

import java.util.ArrayList;
import java.util.List;

public record ListExpr(List<Expr> exprs) implements Expr {
    public ListExpr(List<Expr> exprs) {
        this.exprs = new ArrayList<>(exprs);
    }

    @Override
    public boolean isAtomic() {
        return false;
    }

    @Override
    public String head() {
        return "List";
    }

    @Override
    public int hash() {
        throw new UnsupportedOperationException("Lists are not hashable");
    }
}
