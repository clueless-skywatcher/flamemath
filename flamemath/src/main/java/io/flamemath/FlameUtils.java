package io.flamemath;

import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RationalAtom;
import io.flamemath.expr.RealAtom;
import io.flamemath.internal.FlameInt;

public class FlameUtils {
    public static double numericValue(Expr expr) throws IllegalArgumentException {
        if (expr instanceof IntegerAtom integer) {
            return integer.value().toDouble();
        } else if (expr instanceof RealAtom real) {
            return real.value();
        } else if (expr instanceof RationalAtom r
                && r.num() instanceof IntegerAtom n
                && r.denom() instanceof IntegerAtom d) {
            return n.value().toDouble() / d.value().toDouble();
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
        if (expr instanceof IntegerAtom integer) return integer.value().isNegative();
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

    /**
     * Extracts the largest perfect-square factor from a positive integer.
     * Returns {root, remainder} where value = root² × remainder and remainder is square-free.
     */
    public static long[] extractSquareRoot(long value) {
        long remainder = value;
        long root = 1;
        for (long i = 2; i * i <= remainder; i++) {
            while (remainder % (i * i) == 0) {
                remainder /= (i * i);
                root *= i;
            }
        }
        return new long[]{root, remainder};
    }

    /**
     * Compute the multinomial coefficient n! / (e1! * e2! * ... * ek!).
     *
     * Uses FlameInt arithmetic for arbitrary precision.
     * Assumes all exponents are non-negative and sum to n.
     *
     * @param n         the total
     * @param exponents array of non-negative integers summing to n
     * @return the multinomial coefficient as a FlameInt
     */
    public static FlameInt multinomialCoeff(int n, int[] exponents) {
        FlameInt nFactorial = factorial(n);
        FlameInt denominator = FlameInt.ONE;
        for (int e : exponents) {
            denominator = denominator.mul(factorial(e));
        }
        return nFactorial.divide(denominator);
    }

    public static FlameInt factorial(int n) {
        FlameInt result = FlameInt.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.mul(new FlameInt(i));
        }
        return result;
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
