package io.flamemath.eval.builtins.ntheory;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.internal.FlameInt;

public class PowModFunc implements FlameFunction {

    @Override
    public String name() {
        return "PowMod";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 3) {
            throw new FlameArityException(name(), 3, args.size());
        }

        for (var arg : args) {
            if (!(arg instanceof IntegerAtom)) {
                throw new Exception("PowMod arguments should be Integers");
            }
        }

        FlameInt base = ((IntegerAtom) args.get(0)).value();
        FlameInt exp = ((IntegerAtom) args.get(1)).value();
        FlameInt mod = ((IntegerAtom) args.get(2)).value();

        if (mod.isZero()) {
            throw new ArithmeticException("Modulus cannot be zero");
        }

        return new IntegerAtom(base.modPow(exp, mod));
    }
}
