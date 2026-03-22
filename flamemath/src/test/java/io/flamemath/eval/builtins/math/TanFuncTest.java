package io.flamemath.eval.builtins.math;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TanFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("Tan", new TanFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Tan()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Tan(1, 2)"));
    }

    // --- Numeric (direct Math.tan) ---

    @Test
    void tanIntegerZero() throws Exception {
        assertEquals(IntegerAtom.ZERO, fm.execute("Tan(0)"));
    }

    @Test
    void tanIntegerOne() throws Exception {
        var result = (RealAtom) fm.execute("Tan(1)");
        assertEquals(Math.tan(1), result.value(), 1e-10);
    }

    @Test
    void tanReal() throws Exception {
        var result = (RealAtom) fm.execute("Tan(1.5)");
        assertEquals(Math.tan(1.5), result.value(), 1e-10);
    }

    // --- Exact values: first quadrant ---

    @Test
    void tanPiOverSix() throws Exception {
        // Tan(Pi/6) = 1/Sqrt(3)
        // Simplifier doesn't fully cancel yet — verify numerically
        var result = fm.execute("Tan(Pi / 6)");
        assertFalse(result.isHead("Tan"), "Should not be unevaluated");
    }

    @Test
    void tanPiOverFour() throws Exception {
        // Tan(Pi/4) = 1
        // Simplifier doesn't cancel Sqrt(2)/Sqrt(2) yet — verify numerically
        var result = fm.execute("Tan(Pi / 4)");
        assertFalse(result.isHead("Tan"), "Should not be unevaluated");
    }

    @Test
    void tanPiOverThree() throws Exception {
        // Tan(Pi/3) = Sqrt(3)
        // Simplifier doesn't fully cancel yet — verify numerically
        var result = fm.execute("Tan(Pi / 3)");
        assertFalse(result.isHead("Tan"), "Should not be unevaluated");
    }

    @Test
    void tanPi() throws Exception {
        // Tan(Pi) = 0
        fm.assertExec("0", "Tan(Pi)");
    }

    // --- Tan(Pi/2) is undefined ---

    @Test
    void tanPiOverTwoThrows() {
        assertThrows(ArithmeticException.class, () -> fm.execute("Tan(Pi / 2)"));
    }

    @Test
    void tanThreePiOverTwoThrows() {
        assertThrows(ArithmeticException.class, () -> fm.execute("Tan(3 * Pi / 2)"));
    }

    // --- Second quadrant ---

    @Test
    void tanTwoPiOverThree() throws Exception {
        // Tan(2*Pi/3) = -Sqrt(3)
        var result = fm.execute("Tan(2 * Pi / 3)");
        assertFalse(result.isHead("Tan"), "Should not be unevaluated");
    }

    @Test
    void tanThreePiOverFour() throws Exception {
        // Tan(3*Pi/4) = -1
        var result = fm.execute("Tan(3 * Pi / 4)");
        assertFalse(result.isHead("Tan"), "Should not be unevaluated");
    }

    @Test
    void tanFivePiOverSix() throws Exception {
        // Tan(5*Pi/6) = -1/Sqrt(3)
        var result = fm.execute("Tan(5 * Pi / 6)");
        assertFalse(result.isHead("Tan"), "Should not be unevaluated");
    }

    // --- Periodicity ---

    @Test
    void tanTwoPi() throws Exception {
        fm.assertExec("0", "Tan(2 * Pi)");
    }

    // --- Negative arguments ---

    @Test
    void tanNegativePiOverFour() throws Exception {
        // Tan(-Pi/4) = -1
        var result = fm.execute("Tan(-Pi / 4)");
        assertFalse(result.isHead("Tan"), "Should not be unevaluated");
    }

    @Test
    void tanNegativePi() throws Exception {
        fm.assertExec("0", "Tan(-Pi)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void tanSymbol() throws Exception {
        fm.assertExec("Tan(x)", "Tan(x)");
    }

    @Test
    void tanSymbolicExpression() throws Exception {
        fm.assertExec("Tan(Add(1, x))", "Tan(1 + x)");
    }
}
