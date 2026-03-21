package io.flamemath.eval.builtins.dict;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.DictExpr;
import io.flamemath.expr.Expr;

public class DictRemoveFunc implements FlameFunction {

    @Override
    public String name() {
        return "DictRemove";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }
        if (args.get(0) instanceof DictExpr d) {
            Map<Expr, Expr> newDict = new HashMap<>(d.dict());
            newDict.remove(args.get(1));
            return new DictExpr(newDict);
        }
        throw new Exception("DictRemove expects a Dict as first argument, got " + args.get(0).head());
    }
}
