package io.flamemath.eval.builtins.construct;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.exceptions.ReturningException;
import io.flamemath.expr.Expr;

public class ReturnFunc implements FlameFunction {

    @Override
    public String name() {
       return "Return";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }
        throw new ReturningException(evaluator.eval(args.get(0)));
    }
    
}
