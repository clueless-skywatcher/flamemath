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
import io.flamemath.internal.FlameInt;

public class ArcSinFunc implements FlameFunction {

    private static final Expr PI = new Symbol("Pi");

    // Maps Sin values back to their Pi-coefficient angles in [0, Pi/2]
    // ArcSin(0)=0, ArcSin(1/2)=Pi/6, ArcSin(Sqrt(2)/2)=Pi/4, ArcSin(Sqrt(3)/2)=Pi/3, ArcSin(1)=Pi/2
    private static final Map<String, Expr> SPECIAL_TABLE = Map.of(
        "0",           IntegerAtom.ZERO,                                           // ArcSin(0) = 0
        "1/2",         new Compound("Mul", List.of(RationalAtom.of(1, 6), PI)),    // ArcSin(1/2) = Pi/6
        "Sqrt(2)/2",   new Compound("Mul", List.of(RationalAtom.of(1, 4), PI)),    // ArcSin(Sqrt(2)/2) = Pi/4
        "Sqrt(3)/2",   new Compound("Mul", List.of(RationalAtom.of(1, 3), PI)),    // ArcSin(Sqrt(3)/2) = Pi/3
        "1",           new Compound("Mul", List.of(RationalAtom.of(1, 2), PI))     // ArcSin(1) = Pi/2
    );

    @Override
    public String name() {
        return "ArcSin";
    }

    @Override
    public Expr apply(List<Expr> args, FlameValuator evaluator) throws Exception {
        if (args.size() != 1) {
            throw new FlameArityException(name(), 1, args.size());
        }

        Expr arg = args.get(0);

        // Numeric → compute directly
        if (arg instanceof IntegerAtom i) {
            long iv = i.value().toLong();
            if (iv < -1 || iv > 1) {
                throw new ArithmeticException("ArcSin: argument out of range [-1, 1]");
            }
            return toNumericAtom(Math.asin(i.value().toDouble()));
        }
        if (arg instanceof RealAtom r) {
            if (r.value() < -1 || r.value() > 1) {
                throw new ArithmeticException("ArcSin: argument out of range [-1, 1]");
            }
            return toNumericAtom(Math.asin(r.value()));
        }

        // Check for special symbolic values
        Expr result = lookupSpecialValue(arg, evaluator);
        if (result != null) {
            return evaluator.eval(result);
        }

        return new Compound(name(), args);
    }

    private Expr lookupSpecialValue(Expr arg, FlameValuator evaluator) throws Exception {
        // Detect negative: if arg is Mul(-1, ...), negative rational, or Mul(negRational, ...)
        boolean negative = false;
        Expr absArg = arg;

        if (arg instanceof RationalAtom r
                && r.num() instanceof IntegerAtom rn
                && rn.value().isNegative()) {
            negative = true;
            absArg = new RationalAtom(new IntegerAtom(rn.value().negate()), r.denom());
        } else if (arg instanceof IntegerAtom i && i.value().isNegative()) {
            negative = true;
            absArg = new IntegerAtom(i.value().negate());
        } else if (arg instanceof Compound c && c.isHead("Mul")
                && !c.children().isEmpty()) {
            Expr first = c.children().get(0);
            if (first instanceof IntegerAtom i && i.value().equals(FlameInt.MINUS_ONE)) {
                negative = true;
                List<Expr> rest = c.children().subList(1, c.children().size());
                absArg = rest.size() == 1 ? rest.get(0) : new Compound("Mul", rest);
            } else if (first instanceof RationalAtom r
                    && r.num() instanceof IntegerAtom rn && rn.value().isNegative()) {
                // e.g. Mul((-1/2), Pow(2, (1/2))) → negate the rational
                negative = true;
                Expr posRational = new RationalAtom(new IntegerAtom(rn.value().negate()), r.denom());
                List<Expr> newChildren = new java.util.ArrayList<>();
                newChildren.add(posRational);
                newChildren.addAll(c.children().subList(1, c.children().size()));
                absArg = newChildren.size() == 1 ? newChildren.get(0) : new Compound("Mul", newChildren);
            } else if (first instanceof IntegerAtom i && i.value().isNegative()) {
                negative = true;
                List<Expr> newChildren = new java.util.ArrayList<>();
                newChildren.add(new IntegerAtom(i.value().negate()));
                newChildren.addAll(c.children().subList(1, c.children().size()));
                absArg = newChildren.size() == 1 ? newChildren.get(0) : new Compound("Mul", newChildren);
            }
        }

        String key = normalizeKey(absArg);
        Expr value = key != null ? SPECIAL_TABLE.get(key) : null;

        if (value == null) return null;

        if (negative) {
            if (value.isZero()) return IntegerAtom.ZERO;
            return new Compound("Mul", List.of(IntegerAtom.MINUS_ONE, value));
        }
        return value;
    }

    /**
     * Normalize an expression to a lookup key for the special table.
     * Recognizes: 0, 1/2, 1, and Sqrt(2)/2, Sqrt(3)/2 patterns.
     */
    static String normalizeKey(Expr arg) {
        if (arg instanceof IntegerAtom i) {
            if (i.value().isZero()) return "0";
            if (i.value().equals(FlameInt.ONE)) return "1";
            return null;
        }
        if (arg instanceof RationalAtom r
                && r.num() instanceof IntegerAtom rn
                && r.denom() instanceof IntegerAtom rd) {
            long n = rn.value().toLong();
            long d = rd.value().toLong();
            long gcd = FlameUtils.gcd(Math.abs(n), Math.abs(d));
            n /= gcd;
            d /= gcd;
            if (n == 1 && d == 2) return "1/2";
            return null;
        }
        // Match (1/2)*Sqrt(n) or (1/2)*Pow(n, (1/2)) pattern
        // Sqrt(n) evaluates to Pow(n, (1/2)) so we must match that form
        if (arg instanceof Compound c && c.isHead("Mul") && c.children().size() == 2) {
            Expr first = c.children().get(0);
            Expr second = c.children().get(1);
            if (first instanceof RationalAtom r
                    && r.num() instanceof IntegerAtom rn && rn.value().equals(FlameInt.ONE)
                    && r.denom() instanceof IntegerAtom rd && rd.value().toLong() == 2
                    && isSqrt(second) >= 0) {
                long base = isSqrt(second);
                if (base == 2) return "Sqrt(2)/2";
                if (base == 3) return "Sqrt(3)/2";
            }
        }
        return null;
    }

    /**
     * Returns the base if expr is Sqrt(base) or Pow(base, 1/2), else -1.
     */
    static long isSqrt(Expr expr) {
        if (expr instanceof Compound c && c.children().size() == 2) {
            if (c.isHead("Pow")) {
                Expr base = c.children().get(0);
                Expr exp = c.children().get(1);
                if (base instanceof IntegerAtom bi
                        && exp instanceof RationalAtom r
                        && r.num() instanceof IntegerAtom rn && rn.value().equals(FlameInt.ONE)
                        && r.denom() instanceof IntegerAtom rd && rd.value().toLong() == 2) {
                    return bi.value().toLong();
                }
            }
        }
        if (expr instanceof Compound c && c.isHead("Sqrt") && c.children().size() == 1
                && c.children().get(0) instanceof IntegerAtom si) {
            return si.value().toLong();
        }
        return -1;
    }

    @Override
    public Expr numerify(List<Expr> args) throws Exception {
        return toNumericAtom(Math.asin(FlameUtils.numericValue(args.get(0))));
    }
}
