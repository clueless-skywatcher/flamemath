package io.flamemath.eval.builtins.math;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArcCosFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("ArcCos", new ArcCosFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("ArcCos()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("ArcCos(1, 2)"));
    }

    // --- Numeric ---

    @Test
    void arcCosZero() throws Exception {
        var result = (RealAtom) fm.execute("ArcCos(0)");
        assertEquals(Math.acos(0), result.value(), 1e-10);
    }

    @Test
    void arcCosOne() throws Exception {
        assertEquals(IntegerAtom.ZERO, fm.execute("ArcCos(1)"));
    }

    @Test
    void arcCosMinusOne() throws Exception {
        var result = (RealAtom) fm.execute("ArcCos(-1)");
        assertEquals(Math.acos(-1), result.value(), 1e-10);
    }

    @Test
    void arcCosReal() throws Exception {
        var result = (RealAtom) fm.execute("ArcCos(0.5)");
        assertEquals(Math.acos(0.5), result.value(), 1e-10);
    }

    // --- Domain errors ---

    @Test
    void arcCosOutOfRangePositive() {
        assertThrows(ArithmeticException.class, () -> fm.execute("ArcCos(2)"));
    }

    @Test
    void arcCosOutOfRangeNegative() {
        assertThrows(ArithmeticException.class, () -> fm.execute("ArcCos(-2)"));
    }

    // --- Special symbolic values (positive) ---

    @Test
    void arcCosSymbolicOne() throws Exception {
        // ArcCos(Cos(0)) — Cos(0) evaluates to 1, then numeric path returns 0
        assertEquals(IntegerAtom.ZERO, fm.execute("ArcCos(Cos(0))"));
    }

    @Test
    void arcCosSqrt3Over2() throws Exception {
        // ArcCos(Sqrt(3)/2) = Pi/6
        fm.assertExec("(1/6) * Pi", "ArcCos((1/2) * Sqrt(3))");
    }

    @Test
    void arcCosSqrt2Over2() throws Exception {
        // ArcCos(Sqrt(2)/2) = Pi/4
        fm.assertExec("(1/4) * Pi", "ArcCos((1/2) * Sqrt(2))");
    }

    @Test
    void arcCosHalf() throws Exception {
        // ArcCos(1/2) = Pi/3
        fm.assertExec("(1/3) * Pi", "ArcCos(1/2)");
    }

    // --- Special symbolic values (negative) ---

    @Test
    void arcCosNegativeHalf() throws Exception {
        // ArcCos(-1/2) = 2*Pi/3
        fm.assertExec("(2/3) * Pi", "ArcCos(-1/2)");
    }

    @Test
    void arcCosNegativeSqrt2Over2() throws Exception {
        // ArcCos(-Sqrt(2)/2) = 3*Pi/4
        fm.assertExec("(3/4) * Pi", "ArcCos(-1 * (1/2) * Sqrt(2))");
    }

    @Test
    void arcCosNegativeSqrt3Over2() throws Exception {
        // ArcCos(-Sqrt(3)/2) = 5*Pi/6
        fm.assertExec("(5/6) * Pi", "ArcCos(-1 * (1/2) * Sqrt(3))");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void arcCosSymbol() throws Exception {
        fm.assertExec("ArcCos(x)", "ArcCos(x)");
    }

    @Test
    void arcCosSymbolicExpression() throws Exception {
        fm.assertExec("ArcCos(Add(1, x))", "ArcCos(1 + x)");
    }
}
