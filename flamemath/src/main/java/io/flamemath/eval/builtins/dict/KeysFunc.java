package io.flamemath.eval.builtins.dict;

import java.util.ArrayList;
import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.DictExpr;
import io.flamemath.expr.Expr;
import io.flamemath.expr.ListExpr;

public class KeysFunc implements FlameFunction {

    @Override
    public String name() {
        return "Keys";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }
        if (args.get(0) instanceof DictExpr d) {
            return new ListExpr(new ArrayList<>(d.dict().keySet()));
        }
        throw new Exception("Keys expects a Dict, got " + args.get(0).head());
    }
}
