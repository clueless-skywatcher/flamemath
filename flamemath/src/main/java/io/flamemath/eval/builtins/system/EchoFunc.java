package io.flamemath.eval.builtins.system;

import java.util.List;

import io.flamemath.ExprPrinter;
import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;

public class EchoFunc implements FlameFunction {

    @Override
    public String name() {
        return "Echo";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() < 1) {
            throw FlameArityException.atLeast(name(), 1, args.size());
        }
        Expr result = evaluator.eval(args.get(0));
        System.out.println(ExprPrinter.print(result));
        return result;
    }
    
}
