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
import static io.flamemath.FlameUtils.toNumericAtom;
import io.flamemath.FlameUtils;

public class SinFunc implements FlameFunction {

    private static final Expr SQRT_2_OVER_2 = new Compound("Mul", List.of(
            RationalAtom.of(1, 2),
            new Compound("Sqrt", List.of(new IntegerAtom(2)))));

    private static final Expr SQRT_3_OVER_2 = new Compound("Mul", List.of(
            RationalAtom.of(1, 2),
            new Compound("Sqrt", List.of(new IntegerAtom(3)))));

    private static final Map<String, Expr> SEED_TABLE = Map.of(
        "0",   IntegerAtom.ZERO,          // Sin(0)       = 0
        "1/6", RationalAtom.of(1, 2),     // Sin(Pi/6)    = 1/2
        "1/4", SQRT_2_OVER_2,             // Sin(Pi/4)    = Sqrt(2)/2
        "1/3", SQRT_3_OVER_2,             // Sin(Pi/3)    = Sqrt(3)/2
        "1/2", IntegerAtom.ONE            // Sin(Pi/2)    = 1
    );

    @Override
    public String name() {
        return "Sin";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }

        Expr arg = args.get(0);

        // Numeric → compute directly
        if (arg instanceof IntegerAtom i) {
            return toNumericAtom(Math.sin(i.value()));
        }
        if (arg instanceof RealAtom r) {
            return toNumericAtom(Math.sin(r.value()));
        }

        // Try to extract Pi coefficient
        long[] rationalCoeff = CosFunc.extractPiCoeffs(arg);
        if (rationalCoeff != null) {
            Expr result = evalFromCoeff(rationalCoeff[0], rationalCoeff[1]);
            if (result != null) {
                return evaluator.eval(result);
            }
        }

        return new Compound(name(), args);
    }

    /**
     * Evaluate Sin from a rational Pi coefficient p/q.
     * Reduces to [0, Pi/2] via symmetry:
     *   sin(k*Pi) for k in (1, 2)  → sin((2-k)*Pi)     [reflect across Pi]
     *   sin(k*Pi) for k in (1/2,1) → sin((1-k)*Pi)     [reflect across Pi/2]
     * Note: unlike Cos, reflection across Pi does NOT flip sign.
     */
    private Expr evalFromCoeff(long n, long d) {
        if (d < 0) {
            d = -d;
            n = -n;
        }

        // Reduce to [0, 2) via periodicity
        n = ((n % (2 * d)) + 2 * d) % (2 * d);

        int sign = 1;

        // If k > 1 (third/fourth quadrant): sin(k*Pi) = -sin((k-1)*Pi)
        if (n > d) {
            n = n - d;
            sign = -sign;
        }

        // If k > 1/2 (second quadrant): sin(k*Pi) = sin((1-k)*Pi) — no sign flip
        if (2 * n > d) {
            n = d - n;
        }

        // Now n/d is in [0, 1/2] — look up seed table
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
        return toNumericAtom(Math.sin(FlameUtils.numericValue(args.get(0))));
    }

}
