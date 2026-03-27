package io.flamemath.eval.builtins.list;

import java.util.ArrayList;
import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.ListExpr;

public class JoinFunc implements FlameFunction {

    @Override
    public String name() {
        return "Join";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() < 2) {
            throw FlameArityException.atLeast(name(), 2, args.size());
        }

        for (Expr arg : args) {
            if (!(arg instanceof ListExpr)) {
                return new Compound(name(), args);
            }
        }

        List<Expr> result = new ArrayList<>();
        for (Expr arg : args) {
            result.addAll(((ListExpr) arg).exprs());
        }
        return new ListExpr(result);
    }
}
