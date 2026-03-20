package io.flamemath.eval.builtins.list;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.ListExpr;
import io.flamemath.expr.NullExpr;

public class PrependFunc implements FlameFunction {

    @Override
    public String name() {
        return "Prepend";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }
        if (args.get(0) instanceof ListExpr l) {
            l.exprs().add(0, args.get(1));
        } else {
            throw new Exception("Cannot prepend expressions to " + args.get(0).head());
        }
        return NullExpr.INSTANCE;
    }
    
}
