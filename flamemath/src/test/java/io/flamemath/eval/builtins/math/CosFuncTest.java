package io.flamemath.eval.builtins.math;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CosFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("Cos", new CosFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Cos()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Cos(1, 2)"));
    }

    // --- Numeric (direct Math.cos) ---

    @Test
    void cosIntegerZero() throws Exception {
        assertEquals(new RealAtom(1.0), fm.execute("Cos(0)"));
    }

    @Test
    void cosIntegerOne() throws Exception {
        var result = (RealAtom) fm.execute("Cos(1)");
        assertEquals(Math.cos(1), result.value(), 1e-10);
    }

    @Test
    void cosReal() throws Exception {
        var result = (RealAtom) fm.execute("Cos(1.5)");
        assertEquals(Math.cos(1.5), result.value(), 1e-10);
    }

    // --- Seed table: first quadrant ---

    @Test
    void cosPi() throws Exception {
        // Cos(Pi) = -1
        fm.assertExec("-1", "Cos(Pi)");
    }

    @Test
    void cosPiOverTwo() throws Exception {
        // Cos(Pi/2) = 0
        fm.assertExec("0", "Cos(Pi / 2)");
    }

    @Test
    void cosPiOverThree() throws Exception {
        // Cos(Pi/3) = 1/2
        fm.assertExec("(1/2)", "Cos(Pi / 3)");
    }

    @Test
    void cosPiOverFour() throws Exception {
        // Cos(Pi/4) = Sqrt(2)/2
        fm.assertExec("(1/2) * Sqrt(2)", "Cos(Pi / 4)");
    }

    @Test
    void cosPiOverSix() throws Exception {
        // Cos(Pi/6) = Sqrt(3)/2
        fm.assertExec("(1/2) * Sqrt(3)", "Cos(Pi / 6)");
    }

    // --- Quadrant reduction: second quadrant (Pi/2, Pi) ---

    @Test
    void cosTwoPiOverThree() throws Exception {
        // Cos(2*Pi/3) = -1/2
        fm.assertExec("-(1/2)", "Cos(2 * Pi / 3)");
    }

    @Test
    void cosThreePiOverFour() throws Exception {
        // Cos(3*Pi/4) = -Sqrt(2)/2
        fm.assertExec("-1 * (1/2) * Sqrt(2)", "Cos(3 * Pi / 4)");
    }

    @Test
    void cosFivePiOverSix() throws Exception {
        // Cos(5*Pi/6) = -Sqrt(3)/2
        fm.assertExec("-1 * (1/2) * Sqrt(3)", "Cos(5 * Pi / 6)");
    }

    // --- Quadrant reduction: third quadrant (Pi, 3*Pi/2) ---

    @Test
    void cosSevenPiOverSix() throws Exception {
        // Cos(7*Pi/6) = -Sqrt(3)/2
        fm.assertExec("-1 * (1/2) * Sqrt(3)", "Cos(7 * Pi / 6)");
    }

    @Test
    void cosFivePiOverFour() throws Exception {
        // Cos(5*Pi/4) = -Sqrt(2)/2
        fm.assertExec("-1 * (1/2) * Sqrt(2)", "Cos(5 * Pi / 4)");
    }

    @Test
    void cosFourPiOverThree() throws Exception {
        // Cos(4*Pi/3) = -1/2
        fm.assertExec("-(1/2)", "Cos(4 * Pi / 3)");
    }

    // --- Quadrant reduction: fourth quadrant (3*Pi/2, 2*Pi) ---

    @Test
    void cosFivePiOverThree() throws Exception {
        // Cos(5*Pi/3) = 1/2
        fm.assertExec("(1/2)", "Cos(5 * Pi / 3)");
    }

    @Test
    void cosSevenPiOverFour() throws Exception {
        // Cos(7*Pi/4) = Sqrt(2)/2
        fm.assertExec("(1/2) * Sqrt(2)", "Cos(7 * Pi / 4)");
    }

    @Test
    void cosElevenPiOverSix() throws Exception {
        // Cos(11*Pi/6) = Sqrt(3)/2
        fm.assertExec("(1/2) * Sqrt(3)", "Cos(11 * Pi / 6)");
    }

    // --- Periodicity ---

    @Test
    void cosTwoPi() throws Exception {
        // Cos(2*Pi) = 1
        fm.assertExec("1", "Cos(2 * Pi)");
    }

    @Test
    void cosThreePi() throws Exception {
        // Cos(3*Pi) = -1
        fm.assertExec("-1", "Cos(3 * Pi)");
    }

    @Test
    void cosFourPi() throws Exception {
        // Cos(4*Pi) = 1
        fm.assertExec("1", "Cos(4 * Pi)");
    }

    // --- Negative arguments ---

    @Test
    void cosNegativePi() throws Exception {
        // Cos(-Pi) = -1
        fm.assertExec("-1", "Cos(-Pi)");
    }

    @Test
    void cosNegativePiOverThree() throws Exception {
        // Cos(-Pi/3) = 1/2
        fm.assertExec("(1/2)", "Cos(-Pi / 3)");
    }

    @Test
    void cosNegativePiOverTwo() throws Exception {
        // Cos(-Pi/2) = 0
        fm.assertExec("0", "Cos(-Pi / 2)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void cosSymbol() throws Exception {
        fm.assertExec("Cos(x)", "Cos(x)");
    }

    @Test
    void cosSymbolicExpression() throws Exception {
        fm.assertExec("Cos(Add(1, x))", "Cos(1 + x)");
    }
}
