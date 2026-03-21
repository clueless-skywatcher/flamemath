package io.flamemath.eval.builtins.dict;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.BooleanAtom;
import io.flamemath.expr.DictExpr;
import io.flamemath.expr.Expr;

public class HasKeyFunc implements FlameFunction {

    @Override
    public String name() {
        return "HasKey";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }
        if (args.get(0) instanceof DictExpr d) {
            return BooleanAtom.of(d.dict().containsKey(args.get(1)));
        }
        throw new Exception("HasKey expects a Dict as first argument, got " + args.get(0).head());
    }
}
