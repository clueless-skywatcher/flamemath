package io.flamemath.eval.builtins.list;

import java.util.ArrayList;
import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.ListExpr;

public class UnionFunc implements FlameFunction {

    @Override
    public String name() {
        return "Union";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() < 2) {
            throw FlameArityException.atLeast(name(), 2, args.size());
        }

        if (!(args.get(0) instanceof ListExpr first)) {
            return new Compound(name(), args);
        }

        List<Expr> union = new ArrayList<>();
        for (Expr elem : first.exprs()) {
            if (!contains(elem, union)) {
                union.add(elem);
            }
        }

        for (int i = 1; i < args.size(); i++) {
            if (!(args.get(i) instanceof ListExpr l)) {
                return new Compound(name(), args);
            }
            for (Expr elem : l.exprs()) {
                if (!contains(elem, union)) {
                    union.add(elem);
                }
            }
        }

        return new ListExpr(union);
    }

    private boolean contains(Expr elem, List<Expr> l) {
        for (Expr e : l) {
            if (e.equals(elem)) return true;
        }
        return false;
    }
}
