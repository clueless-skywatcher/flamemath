package io.flamemath.eval;

import java.util.HashMap;
import java.util.Map;

import io.flamemath.expr.Expr;
import io.flamemath.expr.Symbol;

public class FlameVironment {
    private Map<String, Expr> exprs = new HashMap<>();
    private FlameVironment parent;

    public FlameVironment() {
        this.parent = null;
    }

    public FlameVironment(FlameVironment parent) {
        this.parent = parent;
    }

    public void set(Symbol symbol, Expr expr) {
        exprs.put(symbol.name(), expr);
    }

    public Expr get(Symbol symbol) {
        if (!exprs.containsKey(symbol.name())) {
            if (parent != null) return parent.get(symbol);
            return symbol;
        }
        return exprs.get(symbol.name());
    }

    public boolean has(Symbol symbol) {
        return exprs.containsKey(symbol.name()) || (parent != null && parent.has(symbol));
    }

    public void clear(Symbol symbol) {
        exprs.remove(symbol.name());
    }

    public void clearAll() {
        exprs.clear();
    }
}
