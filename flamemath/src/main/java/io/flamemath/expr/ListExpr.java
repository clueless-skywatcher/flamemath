package io.flamemath.expr;

import java.util.List;

public record ListExpr(List<Expr> exprs) implements Expr {

    @Override
    public boolean isAtomic() {
        return false;
    }

    @Override
    public String head() {
        return "List";
    }
    
}
