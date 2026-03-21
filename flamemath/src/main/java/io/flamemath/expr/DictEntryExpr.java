package io.flamemath.expr;

public record DictEntryExpr(Expr key, Expr value) implements Expr {
    @Override
    public boolean isAtomic() {
        return false;
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
