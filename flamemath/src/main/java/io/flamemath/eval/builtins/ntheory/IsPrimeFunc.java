package io.flamemath.eval.builtins.ntheory;

import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.BooleanAtom;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.ntheory.NumberTheoryUtils;

public class IsPrimeFunc implements FlameFunction {
    @Override
    public String name() {
        return "IsPrime";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }
        if (!(args.get(0) instanceof IntegerAtom)) {
            throw new Exception("Argument should be an integer");
        }

        long value = ((IntegerAtom) args.get(0)).value();
        if (value <= FlameValuator.SIEVE_LIMIT) {
            return new BooleanAtom(evaluator.getSieve().isPrime((int) value));
        }
        return new BooleanAtom(NumberTheoryUtils.millerRabin(value));
    }
}
