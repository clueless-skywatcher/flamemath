package io.flamemath.eval.builtins.list;

import java.util.ArrayList;
import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.expr.Expr;
import io.flamemath.expr.ListExpr;

public class ListFunc implements FlameFunction {

    @Override
    public String name() {
        return "List";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        List<Expr> finalList = new ArrayList<>();
        for (var arg: args) {
            finalList.add(evaluator.eval(arg));
        }
        return new ListExpr(finalList);
    }
}
