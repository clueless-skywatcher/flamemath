package io.flamemath.eval.builtins.ntheory;

import java.util.ArrayList;
import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.ListExpr;
import io.flamemath.ntheory.NumberTheoryUtils;

public class PrimesInRangeFunc implements FlameFunction {

    @Override
    public String name() {
        return "PrimesInRange";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 2) {
            throw new FlameArityException(name(), 2, args.size());
        }
        if (!(args.get(0) instanceof IntegerAtom) || !(args.get(1) instanceof IntegerAtom)) {
            throw new Exception("Arguments should be integers");
        }

        long from = ((IntegerAtom) args.get(0)).value().toLong();
        long to = ((IntegerAtom) args.get(1)).value().toLong();

        List<Expr> primes = new ArrayList<>();

        long sieveEnd = Math.min(to, FlameValuator.SIEVE_LIMIT);
        if (from <= sieveEnd) {
            for (int p : evaluator.getSieve().primesInRange((int) Math.max(from, 2), (int) sieveEnd)) {
                primes.add(new IntegerAtom(p));
            }
        }

        for (long n = Math.max(from, FlameValuator.SIEVE_LIMIT + 1); n <= to; n++) {
            if (NumberTheoryUtils.millerRabin(n)) {
                primes.add(new IntegerAtom(n));
            }
        }

        return new ListExpr(primes);
    }
}
