package io.flamemath.eval.builtins.construct;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.NullExpr;

public class IfFunc implements FlameFunction {

    @Override
    public String name() {
        return "If";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() < 2 || args.size() > 3) {
            throw new FlameArityException(name(), 2, args.size());
        }

        if (evaluator.eval(args.get(0)).isTrue()) {
            return evaluator.eval(args.get(1));
        } else if (args.size() == 3) {
            return evaluator.eval(args.get(2));
        }
        return NullExpr.INSTANCE;
    }

    @Override
    public boolean holdAll() {
        return true;
    }
    
}
