package io.flamemath.eval.builtins.math;

import io.flamemath.FlameTestingUtils;
import io.flamemath.FlameUtils;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;
import io.flamemath.expr.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("Num", new NumFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Num()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Num(1, 2)"));
    }

    // --- Pass-through for numeric values ---

    @Test
    void integerBecomesReal() throws Exception {
        assertEquals(new RealAtom(42.0), fm.execute("Num(42)"));
    }

    @Test
    void realPassesThrough() throws Exception {
        assertEquals(new RealAtom(3.14), fm.execute("Num(3.14)"));
    }

    // --- Constants ---

    @Test
    void piEvaluatesToNumeric() throws Exception {
        assertEquals(new RealAtom(Math.PI), fm.execute("Num(Pi)"));
    }

    @Test
    void eEvaluatesToNumeric() throws Exception {
        assertEquals(new RealAtom(Math.E), fm.execute("Num(E)"));
    }

    // --- Unknown symbols stay symbolic ---

    @Test
    void unknownSymbolStaysSymbolic() throws Exception {
        assertEquals(new Symbol("x"), fm.execute("Num(x)"));
    }

    // --- Arithmetic with constants ---

    @Test
    void piPlusOne() throws Exception {
        var result = (RealAtom) fm.execute("Num(Pi + 1)");
        assertEquals(Math.PI + 1, result.value(), 1e-10);
    }

    @Test
    void twoPi() throws Exception {
        var result = (RealAtom) fm.execute("Num(2 * Pi)");
        assertEquals(2 * Math.PI, result.value(), 1e-10);
    }

    @Test
    void ePowTwo() throws Exception {
        var result = (RealAtom) fm.execute("Num(E^2)");
        assertEquals(Math.E * Math.E, result.value(), 1e-10);
    }

    // --- Nested expressions ---

    @Test
    void nestedArithmetic() throws Exception {
        var result = (RealAtom) fm.execute("Num(Pi + E)");
        assertEquals(Math.PI + Math.E, result.value(), 1e-10);
    }

    // --- Num with future math builtins (Sqrt, Sin, Cos, Tan, Log, Exp, Abs) ---
    // These tests are written ahead of implementation.
    // They will fail until each function is implemented.

    // -- Sqrt --

    @Test
    void numSqrtTwo() throws Exception {
        var result = (RealAtom) fm.execute("Num(Sqrt(2))");
        assertEquals(Math.sqrt(2), result.value(), 1e-10);
    }

    @Test
    void numSqrtPi() throws Exception {
        var result = (RealAtom) fm.execute("Num(Sqrt(Pi))");
        assertEquals(Math.sqrt(Math.PI), result.value(), 1e-10);
    }

    // -- Sin --

    @Test
    void numSinZero() throws Exception {
        var result = (RealAtom) fm.execute("Num(Sin(0))");
        assertEquals(0.0, result.value(), 1e-10);
    }

    @Test
    void numSinPiOverTwo() throws Exception {
        var result = (RealAtom) fm.execute("Num(Sin(Pi / 2))");
        assertEquals(1.0, result.value(), 1e-10);
    }

    @Test
    void numSinPi() throws Exception {
        var result = (RealAtom) fm.execute("Num(Sin(Pi))");
        assertEquals(0.0, result.value(), 1e-10);
    }

    // -- Cos --

    @Test
    void numCosZero() throws Exception {
        var result = (RealAtom) fm.execute("Num(Cos(0))");
        assertEquals(1.0, result.value(), 1e-10);
    }

    @Test
    void numCosPi() throws Exception {
        var result = (RealAtom) fm.execute("Num(Cos(Pi))");
        assertEquals(-1.0, result.value(), 1e-10);
    }

    @Test
    void numCosPiOverTwo() throws Exception {
        var result = (RealAtom) fm.execute("Num(Cos(Pi / 2))");
        assertEquals(0.0, result.value(), 1e-10);
    }

    // -- Tan --

    @Test
    void numTanZero() throws Exception {
        var result = (RealAtom) fm.execute("Num(Tan(0))");
        assertEquals(0.0, result.value(), 1e-10);
    }

    @Test
    @org.junit.jupiter.api.Disabled("Tan(Pi/4) produces unsimplified Mul — Sqrt cancellation not yet implemented")
    void numTanPiOverFour() throws Exception {
        var result = fm.execute("Num(Tan(Pi / 4))");
        assertEquals(1.0, FlameUtils.numericValue(result), 1e-10);
    }

    // -- Log --

    @Test
    void numLogOne() throws Exception {
        var result = (RealAtom) fm.execute("Num(Log(1))");
        assertEquals(0.0, result.value(), 1e-10);
    }

    @Test
    void numLogE() throws Exception {
        var result = (RealAtom) fm.execute("Num(Log(E))");
        assertEquals(1.0, result.value(), 1e-10);
    }

    @Test
    void numLogTen() throws Exception {
        var result = (RealAtom) fm.execute("Num(Log(10))");
        assertEquals(Math.log(10), result.value(), 1e-10);
    }

    // -- Exp --

    @Test
    void numExpZero() throws Exception {
        var result = (RealAtom) fm.execute("Num(Exp(0))");
        assertEquals(1.0, result.value(), 1e-10);
    }

    @Test
    void numExpOne() throws Exception {
        var result = (RealAtom) fm.execute("Num(Exp(1))");
        assertEquals(Math.E, result.value(), 1e-10);
    }

    @Test
    void numExpTwo() throws Exception {
        var result = (RealAtom) fm.execute("Num(Exp(2))");
        assertEquals(Math.exp(2), result.value(), 1e-10);
    }

    // -- Abs --

    @Test
    void numAbsNegative() throws Exception {
        assertEquals(new RealAtom(5.0), fm.execute("Num(Abs(-5))"));
    }

    @Test
    void numAbsPositive() throws Exception {
        assertEquals(new RealAtom(3.0), fm.execute("Num(Abs(3))"));
    }

    @Test
    void numAbsNegativeReal() throws Exception {
        assertEquals(new RealAtom(2.5), fm.execute("Num(Abs(-2.5))"));
    }

    // -- Composed expressions --

    @Test
    void numSinSqrtTwo() throws Exception {
        var result = (RealAtom) fm.execute("Num(Sin(Sqrt(2)))");
        assertEquals(Math.sin(Math.sqrt(2)), result.value(), 1e-10);
    }

    @Test
    void numLogExpThree() throws Exception {
        // Exp(3) → Pow(E, 3) (symbolic), Log(Pow(E, 3)) → numerify → Log(RealAtom) → 3.0
        var result = fm.execute("Num(Log(Exp(3)))");
        assertEquals(3.0, FlameUtils.numericValue(result), 1e-10);
    }

    @Test
    void numSqrtPiPlusE() throws Exception {
        var result = (RealAtom) fm.execute("Num(Sqrt(Pi + E))");
        assertEquals(Math.sqrt(Math.PI + Math.E), result.value(), 1e-10);
    }

    @Test
    void numExpSinPiOverTwo() throws Exception {
        var result = (RealAtom) fm.execute("Num(Exp(Sin(Pi / 2)))");
        assertEquals(Math.exp(1.0), result.value(), 1e-10);
    }
}
