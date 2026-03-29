package io.flamemath.eval.builtins.ntheory;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;

public class PrimeFunc implements FlameFunction {
    @Override
    public String name() {
        return "Prime";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }

        Expr arg = args.get(0);

        if (!(arg instanceof IntegerAtom)) {
            return new Compound(name(), args);
        }

        long n = ((IntegerAtom) arg).value().toLong();

        if (n < 1) {
            throw new Exception("Argument must be a positive integer");
        }

        return new IntegerAtom(evaluator.getSieve().nthPrime(n));
    }
}
