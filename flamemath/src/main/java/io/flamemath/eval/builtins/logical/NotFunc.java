package io.flamemath.eval.builtins.logical;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.BooleanAtom;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;

public class NotFunc implements FlameFunction {

    @Override
    public String name() {
        return "Not";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }
        if (evaluator.eval(args.get(0)).isTrue()) {
            return BooleanAtom.FALSE;
        } else if (evaluator.eval(args.get(0)).isFalse()) {
            return BooleanAtom.TRUE;
        }
        return new Compound("Not", args);
    }
    
}
