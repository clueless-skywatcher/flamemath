package io.flamemath.eval.builtins.math;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArcTanFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("ArcTan", new ArcTanFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("ArcTan()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("ArcTan(1, 2)"));
    }

    // --- Numeric ---

    @Test
    void arcTanZero() throws Exception {
        assertEquals(IntegerAtom.ZERO, fm.execute("ArcTan(0)"));
    }

    @Test
    void arcTanOne() throws Exception {
        var result = (RealAtom) fm.execute("ArcTan(1)");
        assertEquals(Math.atan(1), result.value(), 1e-10);
    }

    @Test
    void arcTanMinusOne() throws Exception {
        var result = (RealAtom) fm.execute("ArcTan(-1)");
        assertEquals(Math.atan(-1), result.value(), 1e-10);
    }

    @Test
    void arcTanReal() throws Exception {
        var result = (RealAtom) fm.execute("ArcTan(0.5)");
        assertEquals(Math.atan(0.5), result.value(), 1e-10);
    }

    // --- Special symbolic values ---

    @Test
    void arcTanSqrt3() throws Exception {
        // ArcTan(Sqrt(3)) = Pi/3
        fm.assertExec("(1/3) * Pi", "ArcTan(Sqrt(3))");
    }

    // --- Negative special values ---

    @Test
    void arcTanNegativeSqrt3() throws Exception {
        // ArcTan(-Sqrt(3)) = -Pi/3
        fm.assertExec("-1 * (1/3) * Pi", "ArcTan(-1 * Sqrt(3))");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void arcTanSymbol() throws Exception {
        fm.assertExec("ArcTan(x)", "ArcTan(x)");
    }

    @Test
    void arcTanSymbolicExpression() throws Exception {
        fm.assertExec("ArcTan(Add(1, x))", "ArcTan(1 + x)");
    }
}
