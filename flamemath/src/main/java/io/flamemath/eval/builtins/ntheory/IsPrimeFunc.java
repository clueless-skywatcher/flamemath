package io.flamemath.eval.builtins.ntheory;

import java.math.BigInteger;
import java.util.List;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.BooleanAtom;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;

public class IsPrimeFunc implements FlameFunction {
    private final List<Long> BASES = List.of(
        2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L
    );

    @Override
    public String name() {
        return "IsPrime";
    }

    // Uses Miller-Rabin test
    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }
        if (!(args.get(0) instanceof IntegerAtom)) {
            throw new Exception("Argument should be an integer");
        }

        long value = ((IntegerAtom) args.get(0)).value();
        return new BooleanAtom(millerRabin(value));
    }
    
    private boolean millerRabin(long num) {
        if (num <= 1) return false;
        if (num == 2 || num == 3) return true;
        if (num % 2 == 0) return false;
        if (BASES.contains(num)) return true;

        long s = 0;
        long newNum = num - 1;
        while (newNum > 0 && newNum % 2 == 0) {
            s += 1;
            newNum = newNum / 2;
        }
        long d = newNum;

        int baseI = 0;
        while (baseI < BASES.size()) {
            long a = BASES.get(baseI);
            long x = exponent(a, d, num);
            if (x == 1 || x == num - 1) {
                baseI++;
                continue;
            }
            boolean moveToNext = false;
            for (int r = 1; r < s; r++) {
                x = exponent(x, 2, num);
                if (x == num - 1) {
                    baseI++;
                    moveToNext = true;
                    break;
                }
            }
            if (!moveToNext)
                return false;
        }

        return true;
    }

    private long exponent(long a, long d, long p) {
        BigInteger base = BigInteger.valueOf(a);
        BigInteger exp = BigInteger.valueOf(d);
        BigInteger mod = BigInteger.valueOf(p);
        return base.modPow(exp, mod).longValue();
    }
}
