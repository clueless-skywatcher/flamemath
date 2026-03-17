package io.flamemath;

import io.flamemath.expr.Expr;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;

public class FlameUtils {
    public static double numericValue(Expr expr) throws IllegalArgumentException {
        if (expr instanceof IntegerAtom integer) {
            return (double) integer.value();
        } else if (expr instanceof RealAtom real) {
            return real.value();
        }
        throw new IllegalArgumentException("Wrong argument type for numeric conversion: " + expr.head());
    }

    public static Expr toNumericAtom(double value) {
        if (value == (long) value) {
            return new IntegerAtom((long) value);
        } 
        return new RealAtom(value);
    }

    public static boolean isNegativeNumeric(Expr expr) {
        if (expr instanceof IntegerAtom integer) return integer.value() < 0;
        if (expr instanceof RealAtom real) return real.value() < 0;
        return false;
    }
}
