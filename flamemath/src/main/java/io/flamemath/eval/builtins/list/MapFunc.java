package io.flamemath.eval.builtins.list;

import java.util.ArrayList;
import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.Flambda;
import io.flamemath.expr.ListExpr;
import io.flamemath.expr.Symbol;

public class MapFunc implements FlameFunction {

    @Override
    public String name() {
        return "Map";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }
        Expr first = args.get(0);
        Expr second = args.get(1);

        if (second instanceof ListExpr l) {
            List<Expr> mapped = new ArrayList<>();
            for (var elem : l.exprs()) {
                mapped.add(applyFunc(first, elem, evaluator));
            }
            return new ListExpr(mapped);
        } else {
            return applyFunc(first, second, evaluator);
        }
    }

    private Expr applyFunc(Expr func, Expr arg, FlameValuator evaluator) throws Exception {
        if (func instanceof Symbol symbol) {
            return evaluator.eval(new Compound(symbol.name(), List.of(arg)));
        } else if (func instanceof Flambda lambda) {
            return evaluator.applyLambda(lambda, List.of(evaluator.eval(arg)));
        }
        return new Compound(name(), List.of(func, arg));
    }

}
