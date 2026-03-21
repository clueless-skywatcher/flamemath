package io.flamemath.eval.builtins.dict;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.DictExpr;
import io.flamemath.expr.Expr;

public class DictSetFunc implements FlameFunction {

    @Override
    public String name() {
        return "DictSet";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 3) {
            throw new FlameArityException(name(), 3, args.size());
        }
        if (args.get(0) instanceof DictExpr d) {
            args.get(1).hash();
            Map<Expr, Expr> newDict = new HashMap<>(d.dict());
            newDict.put(args.get(1), args.get(2));
            return new DictExpr(newDict);
        }
        throw new Exception("DictSet expects a Dict as first argument, got " + args.get(0).head());
    }
}
