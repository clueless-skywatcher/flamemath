package io.flamemath.eval.builtins.list;

import static io.flamemath.FlameUtils.numericValue;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.ListExpr;

public class SliceFunc implements FlameFunction {

    @Override
    public String name() {
        return "Slice";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 3) {
            throw new FlameArityException(name(), 3, args.size());
        }
        
        if (args.get(0) instanceof ListExpr l) {
            int startExpr = (int) numericValue(args.get(1));
            int endExpr = (int) numericValue(args.get(2));

            return new ListExpr(l.exprs().subList(startExpr, endExpr));
        }

        return new Compound(name(), args);
    }
    
}
