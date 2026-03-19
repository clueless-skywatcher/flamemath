package io.flamemath.eval.builtins.general;

import java.util.List;

import io.flamemath.eval.FlameArityException;
import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.Expr;

public class IfFunc implements FlameFunction {

    @Override
    public String name() {
        return "If";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 3) {
            throw new FlameArityException(name(), 3, args.size());
        }
        
        if (evaluator.eval(args.get(0)).isTrue()) {
            return evaluator.eval(args.get(1));
        } else {
            return evaluator.eval(args.get(2));
        }
    }

    @Override
    public boolean holdAll() {
        return true;
    }
    
}
