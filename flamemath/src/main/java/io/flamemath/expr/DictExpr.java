package io.flamemath.expr;

import java.util.HashMap;
import java.util.Map;

public record DictExpr(Map<Expr, Expr> dict) implements Expr {
    public DictExpr() {
        this(new HashMap<>());
    }
    
    @Override
    public boolean isAtomic() {
        return false;
    }

    @Override
    public String head() {
        return "Dict";
    }

    @Override
    public int hash() {
        throw new UnsupportedOperationException("Dictionaries are not hashable");
    }
    
}
