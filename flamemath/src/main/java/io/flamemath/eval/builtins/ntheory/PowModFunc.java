package io.flamemath.eval.builtins.ntheory;

import java.math.BigInteger;
import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;

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

        long base = ((IntegerAtom) args.get(0)).value();
        long exp = ((IntegerAtom) args.get(1)).value();
        long mod = ((IntegerAtom) args.get(2)).value();

        if (mod == 0) {
            throw new ArithmeticException("Modulus cannot be zero");
        }

        BigInteger result = BigInteger.valueOf(base)
            .modPow(BigInteger.valueOf(exp), BigInteger.valueOf(mod).abs());

        return new IntegerAtom(result.longValue());
    }
}
