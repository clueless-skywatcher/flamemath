package io.flamemath.eval.builtins.general;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;

public class HoldFunc implements FlameFunction {

    @Override
    public String name() {
        return "Hold";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }
        return args.get(0);
    }

    @Override
    public boolean holdAll() {
        return true;
    }
}
