package io.flamemath.eval.builtins.list;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.ListExpr;
import io.flamemath.expr.NullExpr;

public class ExtendFunc implements FlameFunction {

    @Override
    public String name() {
        return "Extend";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }
        if (args.get(0) instanceof ListExpr l1 && args.get(1) instanceof ListExpr l2) {
            l1.exprs().addAll(l2.exprs());
        } else {
            throw new Exception(String.format("Cannot extend a %s to %s", args.get(0).head(), args.get(1).head()));
        }
        return NullExpr.INSTANCE;
    }
    
}