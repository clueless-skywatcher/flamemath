package io.flamemath.eval.builtins.general;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.ListExpr;

public class OperandsFunc implements FlameFunction {

    @Override
    public String name() {
        return "Operands";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }

        Expr expr = args.get(0);

        if (expr instanceof Compound c) {
            return new ListExpr(c.children());
        }

        return new Compound(name(), args);
    }
}
