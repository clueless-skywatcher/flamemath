package io.flamemath.eval.builtins.construct;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.NullExpr;

public class WhileFunc implements FlameFunction {
    @Override
    public String name() {
        return "While";
    }

    @Override
    public boolean holdAll() {
        return true;
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }
        while (evaluator.eval(args.get(0)).isTrue()) {
            evaluator.eval(args.get(1));
        }
        return NullExpr.INSTANCE;
    }
}
