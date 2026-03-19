package io.flamemath.exceptions;

import io.flamemath.expr.Expr;

public class ReturningException extends Exception {
    private Expr expr;

    public ReturningException(Expr expr) {
        super();
        this.expr = expr;
    }

    public Expr getExpr() {
        return this.expr;
    }
}
