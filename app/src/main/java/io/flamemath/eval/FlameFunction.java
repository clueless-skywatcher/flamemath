package io.flamemath.eval;

import io.flamemath.expr.Expr;

import java.util.List;
import java.util.Set;

public interface FlameFunction {
    String name();
    Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception;

    default Set<Integer> heldArgIndexes() {
        return Set.of();
    }
}
