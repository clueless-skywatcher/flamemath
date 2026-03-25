package io.flamemath.eval.builtins.math;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SinhFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("Sinh", new SinhFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Sinh()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Sinh(1, 2)"));
    }

    // --- Numeric ---

    @Test
    void sinhZero() throws Exception {
        assertEquals(IntegerAtom.ZERO, fm.execute("Sinh(0)"));
    }

    @Test
    void sinhOne() throws Exception {
        var result = (RealAtom) fm.execute("Sinh(1)");
        assertEquals(Math.sinh(1), result.value(), 1e-10);
    }

    @Test
    void sinhNegativeOne() throws Exception {
        var result = (RealAtom) fm.execute("Sinh(-1)");
        assertEquals(Math.sinh(-1), result.value(), 1e-10);
    }

    @Test
    void sinhReal() throws Exception {
        var result = (RealAtom) fm.execute("Sinh(1.5)");
        assertEquals(Math.sinh(1.5), result.value(), 1e-10);
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void sinhSymbol() throws Exception {
        fm.assertExec("Sinh(x)", "Sinh(x)");
    }

    @Test
    void sinhSymbolicExpression() throws Exception {
        fm.assertExec("Sinh(Add(1, x))", "Sinh(1 + x)");
    }
}
