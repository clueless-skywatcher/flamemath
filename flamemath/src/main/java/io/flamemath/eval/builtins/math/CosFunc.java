package io.flamemath.eval.builtins.math;

import java.util.List;
import java.util.Map;

import io.flamemath.eval.FlameFunction;
import io.flamemath.eval.FlameValuator;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.Compound;
import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RationalAtom;
import io.flamemath.expr.RealAtom;
import io.flamemath.expr.Symbol;
import static io.flamemath.FlameUtils.toNumericAtom;
import io.flamemath.FlameUtils;

public class CosFunc implements FlameFunction {

    // key = Pi coefficient as reduced fraction string, value = exact Cos result
    // Only [0, Pi/2] needed — quadrant reduction handles the rest
    private static final Expr SQRT_2_OVER_2 = new Compound("Mul", List.of(
            RationalAtom.of(1, 2),
            new Compound("Sqrt", List.of(new IntegerAtom(2)))));

    private static final Expr SQRT_3_OVER_2 = new Compound("Mul", List.of(
            RationalAtom.of(1, 2),
            new Compound("Sqrt", List.of(new IntegerAtom(3)))));

    private static final Map<String, Expr> SEED_TABLE = Map.of(
            "0", IntegerAtom.ONE, // Cos(0) = 1
            "1/6", SQRT_3_OVER_2, // Cos(Pi/6) = Sqrt(3)/2
            "1/4", SQRT_2_OVER_2, // Cos(Pi/4) = Sqrt(2)/2
            "1/3", RationalAtom.of(1, 2), // Cos(Pi/3) = 1/2
            "1/2", IntegerAtom.ZERO // Cos(Pi/2) = 0
    );

    @Override
    public String name() {
        return "Cos";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }
        Expr arg = args.get(0);

        if (arg instanceof IntegerAtom i) {
            return toNumericAtom(Math.cos(i.value()));
        }
        if (arg instanceof RealAtom r) {
            return toNumericAtom(Math.cos(r.value()));
        }

        long[] rationalCoeff = extractPiCoeffs(arg);
        if (rationalCoeff != null) {
            Expr result = evalFromCoeff(rationalCoeff[0], rationalCoeff[1]);
            if (result != null) {
                return evaluator.eval(result);
            }
        }

        return new Compound(name(), args);
    }

    private Expr evalFromCoeff(long n, long d) {
        if (d < 0) {
            d = -d;
            n = -n;
        }

        n = ((n % (2 * d)) + 2 * d) % (2 * d);

        int sign = 1;

        if (n > d) {
            n = n - d;
            sign = -sign;
        }

        if (2 * n > d) {
            n = d - n;
            sign = -sign;
        }

        long gcd = FlameUtils.gcd(n, d);
        long rp = n / gcd;
        long rq = d / gcd;

        String key = rq == 1 ? String.valueOf(rp) : rp + "/" + rq;
        Expr value = SEED_TABLE.get(key);

        if (value == null) {
            return null;
        }

        if (sign == -1) {
            if (value.isZero()) return IntegerAtom.ZERO;
            if (value instanceof IntegerAtom i) return new IntegerAtom(-i.value());
            if (value instanceof RationalAtom r
                    && r.num() instanceof IntegerAtom rn) {
                return new RationalAtom(new IntegerAtom(-rn.value()), r.denom());
            }
            return new Compound("Mul", List.of(IntegerAtom.MINUS_ONE, value));
        }
        return value;
    }

    @Override
    public Expr numerify(List<Expr> args) throws Exception {
        return toNumericAtom(Math.cos(FlameUtils.numericValue(args.get(0))));
    }

    static long[] extractPiCoeffs(Expr arg) {
        if (arg instanceof Symbol s && s.name().equals("Pi")) {
            return new long[] { 1, 1 };
        }
        if (arg instanceof Compound c && c.isHead("Mul")) {
            boolean foundPi = false;
            long num = 1;
            long denom = 1;

            for (Expr child : c.children()) {
                if (!foundPi && child instanceof Symbol s && s.name().equals("Pi")) {
                    foundPi = true;
                } else if (child instanceof IntegerAtom i) {
                    num *= i.value();
                } else if (child instanceof RationalAtom r
                        && r.num() instanceof IntegerAtom n
                        && r.denom() instanceof IntegerAtom d) {
                    num *= n.value();
                    denom *= d.value();
                } else {
                    // Non-numeric, non-Pi child — not a rational multiple of Pi
                    return null;
                }
            }

            if (foundPi) {
                return new long[] { num, denom };
            }
        }

        return null;
    }
}
