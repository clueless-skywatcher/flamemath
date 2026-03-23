package io.flamemath;

import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RationalAtom;
import io.flamemath.expr.RealAtom;

public class FlameUtils {
    public static double numericValue(Expr expr) throws IllegalArgumentException {
        if (expr instanceof IntegerAtom integer) {
            return (double) integer.value();
        } else if (expr instanceof RealAtom real) {
            return real.value();
        } else if (expr instanceof RationalAtom r
                && r.num() instanceof IntegerAtom n
                && r.denom() instanceof IntegerAtom d) {
            return (double) n.value() / d.value();
        }
        throw new IllegalArgumentException("Wrong argument type for numeric conversion: " + expr.head());
    }

    public static Expr toNumericAtom(double value) {
        if (value == (long) value) {
            return new IntegerAtom((long) value);
        } 
        return new RealAtom(value);
    }

    public static Expr toRationalOrInteger(double value) {
        if (value == (long) value) {
            return new IntegerAtom((long) value);
        }
        // Convert double to rational via limiting denominator
        // For products of rationals, the result is exact in double
        // Find the fraction by multiplying out
        long denom = 1;
        double v = value;
        while (v != Math.floor(v) && denom < 1_000_000) {
            v *= 2;
            denom *= 2;
        }
        long num = (long) v;
        return new RationalAtom(new IntegerAtom(num), new IntegerAtom(denom)).reduce();
    }

    public static boolean isNegativeNumeric(Expr expr) {
        if (expr instanceof IntegerAtom integer) return integer.value() < 0;
        if (expr instanceof RealAtom real) return real.value() < 0;
        return false;
    }

    public static boolean isPerfectSquare(double value) {
        long root = (long) Math.sqrt(value);
        return root * root == value;
    }

    public static boolean isPerfectSquare(long value) {
        return isPerfectSquare((double) value);
    }

    public static long gcd(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
