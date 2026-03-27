package io.flamemath.eval.builtins.math;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArcSinFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("ArcSin", new ArcSinFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("ArcSin()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("ArcSin(1, 2)"));
    }

    // --- Numeric ---

    @Test
    void arcSinZero() throws Exception {
        assertEquals(IntegerAtom.ZERO, fm.execute("ArcSin(0)"));
    }

    @Test
    void arcSinOne() throws Exception {
        var result = (RealAtom) fm.execute("ArcSin(1)");
        assertEquals(Math.asin(1), result.value(), 1e-10);
    }

    @Test
    void arcSinMinusOne() throws Exception {
        var result = (RealAtom) fm.execute("ArcSin(-1)");
        assertEquals(Math.asin(-1), result.value(), 1e-10);
    }

    @Test
    void arcSinReal() throws Exception {
        var result = (RealAtom) fm.execute("ArcSin(0.5)");
        assertEquals(Math.asin(0.5), result.value(), 1e-10);
    }

    // --- Domain errors ---

    @Test
    void arcSinOutOfRangePositive() {
        assertThrows(ArithmeticException.class, () -> fm.execute("ArcSin(2)"));
    }

    @Test
    void arcSinOutOfRangeNegative() {
        assertThrows(ArithmeticException.class, () -> fm.execute("ArcSin(-2)"));
    }

    // --- Special symbolic values ---

    @Test
    void arcSinHalf() throws Exception {
        // ArcSin(1/2) = Pi/6
        fm.assertExec("(1/6) * Pi", "ArcSin(1/2)");
    }

    @Test
    void arcSinSqrt2Over2() throws Exception {
        // ArcSin(Sqrt(2)/2) = Pi/4
        fm.assertExec("(1/4) * Pi", "ArcSin((1/2) * Sqrt(2))");
    }

    @Test
    void arcSinSqrt3Over2() throws Exception {
        // ArcSin(Sqrt(3)/2) = Pi/3
        fm.assertExec("(1/3) * Pi", "ArcSin((1/2) * Sqrt(3))");
    }

    @Test
    void arcSinSymbolicOne() throws Exception {
        // ArcSin(Sin(Pi/2)) — Sin(Pi/2) evaluates to 1, then numeric path
        var result = (RealAtom) fm.execute("ArcSin(Sin(Pi/2))");
        assertEquals(Math.asin(1), result.value(), 1e-10);
    }

    // --- Negative special values ---

    @Test
    void arcSinNegativeHalf() throws Exception {
        // ArcSin(-1/2) = -Pi/6
        fm.assertExec("-1 * (1/6) * Pi", "ArcSin(-1/2)");
    }

    @Test
    void arcSinNegativeSqrt2Over2() throws Exception {
        // ArcSin(-Sqrt(2)/2) = -Pi/4
        fm.assertExec("-1 * (1/4) * Pi", "ArcSin(-1 * (1/2) * Sqrt(2))");
    }

    @Test
    void arcSinNegativeSqrt3Over2() throws Exception {
        // ArcSin(-Sqrt(3)/2) = -Pi/3
        fm.assertExec("-1 * (1/3) * Pi", "ArcSin(-1 * (1/2) * Sqrt(3))");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void arcSinSymbol() throws Exception {
        fm.assertExec("ArcSin(x)", "ArcSin(x)");
    }

    @Test
    void arcSinSymbolicExpression() throws Exception {
        fm.assertExec("ArcSin(Add(1, x))", "ArcSin(1 + x)");
    }
}
