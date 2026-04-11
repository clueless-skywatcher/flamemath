package io.flamemath.eval.builtins.math;

import java.util.List;

import io.flamemath.FlameUtils;
import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.internal.FlameInt;

public class MultinomialFunc implements FlameFunction {

    @Override
    public String name() {
        return "Multinomial";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() < 2) {
            throw FlameArityException.atLeast(name(), 2, args.size());
        }

        // Return unevaluated if any argument is not an integer
        for (Expr arg : args) {
            if (!(arg instanceof IntegerAtom)) {
                return new Compound(name(), args);
            }
        }

        FlameInt n = ((IntegerAtom) args.get(0)).value();
        int[] exponents = new int[args.size() - 1];
        FlameInt sum = FlameInt.ZERO;

        for (int i = 1; i < args.size(); i++) {
            FlameInt val = ((IntegerAtom) args.get(i)).value();
            if (!val.fitsInLong()) {
                return new Compound(name(), args);
            }
            exponents[i - 1] = (int) val.toLong();
            sum = sum.add(val);
        }

        // Return unevaluated if n != sum of exponents
        if (!n.equals(sum)) {
            return new Compound(name(), args);
        }

        if (!n.fitsInLong()) {
            return new Compound(name(), args);
        }

        FlameInt result = FlameUtils.multinomialCoeff((int) n.toLong(), exponents);
        return new IntegerAtom(result);
    }
}
