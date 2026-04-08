package io.flamemath.eval.builtins.poly;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;

public class ExpandFunc implements FlameFunction {

    @Override
    public String name() {
        return "Expand";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }

        Expr expr = args.get(0);

        return expr;
    }
}
