package io.flamemath.eval.builtins.general;

import java.util.List;

import io.flamemath.eval.FlameArityException;
import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.Expr;
import io.flamemath.expr.Symbol;

public class HeadFunc implements FlameFunction {

    @Override
    public String name() {
        return "Head";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException("Head", 1, args.size());
        }
        return new Symbol(args.getFirst().head());
    }
}
