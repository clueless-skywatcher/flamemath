package io.flamemath.eval;

import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;

import java.util.List;
import java.util.Set;

public interface FlameFunction {
    String name();
    Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception;

    default Set<Integer> heldArgIndexes() {
        return Set.of();
    }

    /**
     * Whether this function's arguments should be flattened before application.
     * When true, nested calls with the same head are unwrapped into a single argument list.
     * For example, if Add is flat: Add(1, Add(2, 3)) → Add(1, 2, 3).
     */
    default boolean isFlat() {
        return false;
    }

    default boolean holdAll() {
        return false;
    }

    default Expr numerify(List<Expr> args) throws Exception {
        return new Compound(name(), args);
    }
}
