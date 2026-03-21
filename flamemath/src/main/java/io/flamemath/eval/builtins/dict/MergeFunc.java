package io.flamemath.eval.builtins.dict;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.DictExpr;
import io.flamemath.expr.Expr;

public class MergeFunc implements FlameFunction {

    @Override
    public String name() {
        return "Merge";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }
        if (args.get(0) instanceof DictExpr d1 && args.get(1) instanceof DictExpr d2) {
            Map<Expr, Expr> merged = new HashMap<>(d1.dict());
            merged.putAll(d2.dict());
            return new DictExpr(merged);
        }
        throw new Exception("Merge expects two Dicts, got " + args.get(0).head() + " and " + args.get(1).head());
    }
}
