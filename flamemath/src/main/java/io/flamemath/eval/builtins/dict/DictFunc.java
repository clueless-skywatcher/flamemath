package io.flamemath.eval.builtins.dict;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.DictEntryExpr;
import io.flamemath.expr.DictExpr;
import io.flamemath.expr.Expr;

public class DictFunc implements FlameFunction {

    @Override
    public String name() {
        return "Dict";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        Map<Expr, Expr> dict = new HashMap<>();
        for (Expr arg : args) {
            if (arg instanceof DictEntryExpr entry) {
                entry.key().hash();
                dict.put(entry.key(), entry.value());
            } else {
                throw new Exception("Dict expects key:value entries, got " + arg.head());
            }
        }
        return new DictExpr(dict);
    }
}
