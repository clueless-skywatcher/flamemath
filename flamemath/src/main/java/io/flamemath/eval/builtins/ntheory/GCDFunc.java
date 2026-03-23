package io.flamemath.eval.builtins.ntheory;

import java.util.List;

import io.flamemath.FlameUtils;
import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;

public class GCDFunc implements FlameFunction {

    @Override
    public String name() {
        return "GCD";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() < 1) {
            throw FlameArityException.atLeast(name(), 1, args.size());
        }

        for (var arg: args) {
            if (!(arg instanceof IntegerAtom)) {
                throw new Exception("GCD arguments should be Integers");
            }
        }

        long gcd = ((IntegerAtom) args.get(0)).value();
        for (int i = 1; i < args.size(); i++) {
            long argInt = ((IntegerAtom) args.get(i)).value();
            gcd = FlameUtils.gcd(gcd, argInt);
        }

        if (gcd < 0) gcd = -gcd;

        return new IntegerAtom(gcd);
    }
    
}
