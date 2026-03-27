package io.flamemath.eval.builtins.math;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArcTan2FuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("ArcTan2", new ArcTan2Func().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("ArcTan2()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("ArcTan2(1)"));
    }

    @Test
    void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("ArcTan2(1, 2, 3)"));
    }

    // --- Numeric ---

    @Test
    void arcTan2ZeroOne() throws Exception {
        // ArcTan2(0, 1) = 0
        assertEquals(IntegerAtom.ZERO, fm.execute("ArcTan2(0, 1)"));
    }

    @Test
    void arcTan2OneZero() throws Exception {
        // ArcTan2(1, 0) = Pi/2
        var result = (RealAtom) fm.execute("ArcTan2(1, 0)");
        assertEquals(Math.atan2(1, 0), result.value(), 1e-10);
    }

    @Test
    void arcTan2OneOne() throws Exception {
        // ArcTan2(1, 1) = Pi/4
        var result = (RealAtom) fm.execute("ArcTan2(1, 1)");
        assertEquals(Math.atan2(1, 1), result.value(), 1e-10);
    }

    @Test
    void arcTan2NegativeOneZero() throws Exception {
        // ArcTan2(-1, 0) = -Pi/2
        var result = (RealAtom) fm.execute("ArcTan2(-1, 0)");
        assertEquals(Math.atan2(-1, 0), result.value(), 1e-10);
    }

    @Test
    void arcTan2ZeroNegativeOne() throws Exception {
        // ArcTan2(0, -1) = Pi
        var result = (RealAtom) fm.execute("ArcTan2(0, -1)");
        assertEquals(Math.atan2(0, -1), result.value(), 1e-10);
    }

    @Test
    void arcTan2Reals() throws Exception {
        var result = (RealAtom) fm.execute("ArcTan2(3.0, 4.0)");
        assertEquals(Math.atan2(3.0, 4.0), result.value(), 1e-10);
    }

    // --- Both zero is undefined ---

    @Test
    void arcTan2BothZeroThrows() {
        assertThrows(ArithmeticException.class, () -> fm.execute("ArcTan2(0, 0)"));
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void arcTan2Symbolic() throws Exception {
        fm.assertExec("ArcTan2(x, y)", "ArcTan2(x, y)");
    }

    @Test
    void arcTan2MixedSymbolicNumeric() throws Exception {
        fm.assertExec("ArcTan2(x, 1)", "ArcTan2(x, 1)");
    }
}
