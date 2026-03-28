package io.flamemath.eval.builtins.list;

import java.util.ArrayList;
import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.ListExpr;

public class IntersectionFunc implements FlameFunction {

    @Override
    public String name() {
        return "Intersection";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() < 2) {
            throw FlameArityException.atLeast(name(), 2, args.size());
        }

        if (!(args.get(0) instanceof ListExpr first)) {
            return new Compound(name(), args);
        }

        List<Expr> intersection = new ArrayList<>(first.exprs());

        for (int i = 1; i < args.size(); i++) {
            if (!(args.get(i) instanceof ListExpr l)) {
                return new Compound(name(), args);
            }
            List<Expr> next = new ArrayList<>();
            for (Expr elem : intersection) {
                if (contains(elem, l.exprs()) && !contains(elem, next)) {
                    next.add(elem);
                }
            }
            intersection = next;
        }

        return new ListExpr(intersection);
    }

    private boolean contains(Expr elem, List<Expr> l) {
        for (Expr e : l) {
            if (e.equals(elem))
                return true;
        }
        return false;
    }

    @Override
    public boolean isFlat() {
        return true;
    }
}
