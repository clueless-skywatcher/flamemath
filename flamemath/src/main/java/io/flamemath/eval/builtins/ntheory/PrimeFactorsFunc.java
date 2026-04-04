package io.flamemath.eval.builtins.ntheory;

import java.util.List;
import java.util.TreeMap;
import java.util.Map;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.DictExpr;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.internal.FlameInt;
import io.flamemath.ntheory.NumberTheoryUtils;

public class PrimeFactorsFunc implements FlameFunction {

    @Override
    public String name() {
        return "PrimeFactors";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }
        if (!(args.get(0) instanceof IntegerAtom)) {
            // Return unevaluated for symbolic args
            return null;
        }

        FlameInt n = ((IntegerAtom) args.get(0)).value();

        Map<Expr, Expr> factors = new TreeMap<>();
        if (n.equals(FlameInt.ONE)) return new DictExpr(factors);

        IntegerAtom two = new IntegerAtom(FlameInt.TWO);
        int twos = 0;
        while (n.mod(FlameInt.TWO).equals(FlameInt.ZERO)) {
            twos++;
            n = n.divide(FlameInt.TWO);
        }
        if (twos > 0) {
            factors.put(two, new IntegerAtom(twos));
        }

        FlameInt p = new FlameInt(3);
        FlameInt limit = new FlameInt(1000);

        while (p.compareTo(limit) <= 0 && p.mul(p).compareTo(n) <= 0) {
            int pS = 0;
            while (n.mod(p).equals(FlameInt.ZERO)) {
                pS++;
                n = n.divide(p);
            }
            if (pS > 0) factors.put(new IntegerAtom(p), new IntegerAtom(pS));
            p = p.add(FlameInt.TWO);
        }

        if (n.compareTo(FlameInt.ONE) > 0) {
            if (NumberTheoryUtils.millerRabin(n)) {
                factors.put(new IntegerAtom(n), new IntegerAtom(1));
            } else {
                while (!NumberTheoryUtils.millerRabin(n)) {
                    FlameInt d = pollardRho(n);
                    while (!NumberTheoryUtils.millerRabin(d)) {
                        d = pollardRho(d);
                    }
                    int dS = 0;
                    while (n.mod(d).equals(FlameInt.ZERO)) {
                        dS++;
                        n = n.divide(d);
                    }
                    factors.put(new IntegerAtom(d), new IntegerAtom(dS));
                }
                if (n.compareTo(FlameInt.ONE) > 0) {
                    factors.put(new IntegerAtom(n), new IntegerAtom(1));
                }
            }
        }

        return new DictExpr(factors);
    }

    private static FlameInt g(FlameInt x, FlameInt c, FlameInt n) {
        return x.mul(x).add(c).mod(n);
    }

    private static FlameInt pollardRho(FlameInt n) {
        FlameInt c = FlameInt.ONE;
        FlameInt d = n;

        while (d.equals(n)) {
            FlameInt x = FlameInt.TWO;
            FlameInt y = FlameInt.TWO;
            d = FlameInt.ONE;

            while (d.equals(FlameInt.ONE)) {
                x = g(x, c, n);
                y = g(g(y, c, n), c, n);
                d = n.gcd(x.subtract(y).abs());
            }
            c = c.add(FlameInt.ONE);
        }

        return d;
    }
}
