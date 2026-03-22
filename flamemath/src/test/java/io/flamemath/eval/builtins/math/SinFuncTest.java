package io.flamemath.eval.builtins.math;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SinFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("Sin", new SinFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Sin()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Sin(1, 2)"));
    }

    // --- Numeric (direct Math.sin) ---

    @Test
    void sinIntegerZero() throws Exception {
        assertEquals(IntegerAtom.ZERO, fm.execute("Sin(0)"));
    }

    @Test
    void sinIntegerOne() throws Exception {
        var result = (RealAtom) fm.execute("Sin(1)");
        assertEquals(Math.sin(1), result.value(), 1e-10);
    }

    @Test
    void sinReal() throws Exception {
        var result = (RealAtom) fm.execute("Sin(1.5)");
        assertEquals(Math.sin(1.5), result.value(), 1e-10);
    }

    // --- Seed table: first quadrant ---

    @Test
    void sinZeroPi() throws Exception {
        // Sin(0 * Pi) — handled by numeric path: Sin(0) = 0
        assertEquals(IntegerAtom.ZERO, fm.execute("Sin(0)"));
    }

    @Test
    void sinPiOverSix() throws Exception {
        // Sin(Pi/6) = 1/2
        fm.assertExec("(1/2)", "Sin(Pi / 6)");
    }

    @Test
    void sinPiOverFour() throws Exception {
        // Sin(Pi/4) = Sqrt(2)/2
        fm.assertExec("(1/2) * Sqrt(2)", "Sin(Pi / 4)");
    }

    @Test
    void sinPiOverThree() throws Exception {
        // Sin(Pi/3) = Sqrt(3)/2
        fm.assertExec("(1/2) * Sqrt(3)", "Sin(Pi / 3)");
    }

    @Test
    void sinPiOverTwo() throws Exception {
        // Sin(Pi/2) = 1
        fm.assertExec("1", "Sin(Pi / 2)");
    }

    @Test
    void sinPi() throws Exception {
        // Sin(Pi) = 0
        fm.assertExec("0", "Sin(Pi)");
    }

    // --- Quadrant reduction: second quadrant (Pi/2, Pi) ---

    @Test
    void sinTwoPiOverThree() throws Exception {
        // Sin(2*Pi/3) = Sqrt(3)/2
        fm.assertExec("(1/2) * Sqrt(3)", "Sin(2 * Pi / 3)");
    }

    @Test
    void sinThreePiOverFour() throws Exception {
        // Sin(3*Pi/4) = Sqrt(2)/2
        fm.assertExec("(1/2) * Sqrt(2)", "Sin(3 * Pi / 4)");
    }

    @Test
    void sinFivePiOverSix() throws Exception {
        // Sin(5*Pi/6) = 1/2
        fm.assertExec("(1/2)", "Sin(5 * Pi / 6)");
    }

    // --- Quadrant reduction: third quadrant (Pi, 3*Pi/2) ---

    @Test
    void sinSevenPiOverSix() throws Exception {
        // Sin(7*Pi/6) = -1/2
        fm.assertExec("(-1/2)", "Sin(7 * Pi / 6)");
    }

    @Test
    void sinFivePiOverFour() throws Exception {
        // Sin(5*Pi/4) = -Sqrt(2)/2
        fm.assertExec("-1 * (1/2) * Sqrt(2)", "Sin(5 * Pi / 4)");
    }

    @Test
    void sinFourPiOverThree() throws Exception {
        // Sin(4*Pi/3) = -Sqrt(3)/2
        fm.assertExec("-1 * (1/2) * Sqrt(3)", "Sin(4 * Pi / 3)");
    }

    // --- Quadrant reduction: fourth quadrant (3*Pi/2, 2*Pi) ---

    @Test
    void sinFivePiOverThree() throws Exception {
        // Sin(5*Pi/3) = -Sqrt(3)/2
        fm.assertExec("-1 * (1/2) * Sqrt(3)", "Sin(5 * Pi / 3)");
    }

    @Test
    void sinSevenPiOverFour() throws Exception {
        // Sin(7*Pi/4) = -Sqrt(2)/2
        fm.assertExec("-1 * (1/2) * Sqrt(2)", "Sin(7 * Pi / 4)");
    }

    @Test
    void sinElevenPiOverSix() throws Exception {
        // Sin(11*Pi/6) = -1/2
        fm.assertExec("(-1/2)", "Sin(11 * Pi / 6)");
    }

    // --- Periodicity ---

    @Test
    void sinTwoPi() throws Exception {
        fm.assertExec("0", "Sin(2 * Pi)");
    }

    @Test
    void sinThreePi() throws Exception {
        fm.assertExec("0", "Sin(3 * Pi)");
    }

    @Test
    void sinFourPi() throws Exception {
        fm.assertExec("0", "Sin(4 * Pi)");
    }

    // --- Negative arguments ---

    @Test
    void sinNegativePi() throws Exception {
        fm.assertExec("0", "Sin(-Pi)");
    }

    @Test
    void sinNegativePiOverThree() throws Exception {
        // Sin(-Pi/3) = -Sqrt(3)/2
        fm.assertExec("-1 * (1/2) * Sqrt(3)", "Sin(-Pi / 3)");
    }

    @Test
    void sinNegativePiOverTwo() throws Exception {
        // Sin(-Pi/2) = -1
        fm.assertExec("-1", "Sin(-Pi / 2)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void sinSymbol() throws Exception {
        fm.assertExec("Sin(x)", "Sin(x)");
    }

    @Test
    void sinSymbolicExpression() throws Exception {
        fm.assertExec("Sin(Add(1, x))", "Sin(1 + x)");
    }
}
