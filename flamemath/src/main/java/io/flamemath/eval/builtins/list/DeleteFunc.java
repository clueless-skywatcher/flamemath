package io.flamemath.eval.builtins.list;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.ListExpr;
import io.flamemath.expr.NullExpr;

public class DeleteFunc implements FlameFunction {

    @Override
    public String name() {
        return "Delete";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }

        if (!(args.get(0) instanceof ListExpr list)) {
            throw new Exception("Cannot delete expressions from " + args.get(0).head());
        }

        Expr elem = args.get(1);
        list.exprs().remove(elem);

        return NullExpr.INSTANCE;
    }
}
