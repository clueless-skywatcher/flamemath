package io.flamemath.eval.builtins.math;

import static io.flamemath.FlameUtils.numericValue;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;

public class FloorFunc implements FlameFunction {

    @Override
    public String name() {
        return "Floor";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }

        Expr n = args.get(0);
        if (n.isNumeric()) {
            double numVal = numericValue(n);
            return new IntegerAtom((long) Math.floor(numVal));
        }

        return new Compound(name(), args);
    }
    
}
