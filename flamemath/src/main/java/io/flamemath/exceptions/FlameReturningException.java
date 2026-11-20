package io.flamemath.exceptions;

import io.flamemath.expr.Expr;

public class FlameReturningException extends Exception {
    private Expr expr;

    public FlameReturningException(Expr expr) {
        super();
        this.expr = expr;
    }

    public Expr getExpr() {
        return this.expr;
    }
}
