package io.flamemath.eval.builtins.list;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.ListExpr;

public class LenFunc implements FlameFunction {

    @Override
    public String name() {
        return "Len";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }
        Expr possibleIterable = args.get(0);
        if (possibleIterable instanceof ListExpr l) {
            return new IntegerAtom(l.exprs().size());
        }
        return IntegerAtom.ZERO;
    }
    
}
