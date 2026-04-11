package io.flamemath.expr;

import java.util.List;

public record DictEntryExpr(Expr key, Expr value) implements Expr {
    @Override
    public boolean isAtomic() {
        return false;
    }

    @Override
    public List<Expr> getChildren() {
        return List.of(key, value);
    }

    @Override
    public String head() {
        return "DictEntry";
    }

    @Override
    public int hash() {
        throw new UnsupportedOperationException("Dict entries are not hashable");
    }
}
