package io.flamemath.eval.builtins.math;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoshFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("Cosh", new CoshFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Cosh()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Cosh(1, 2)"));
    }

    // --- Numeric ---

    @Test
    void coshZero() throws Exception {
        // Cosh(0) = 1
        assertEquals(IntegerAtom.ONE, fm.execute("Cosh(0)"));
    }

    @Test
    void coshOne() throws Exception {
        var result = (RealAtom) fm.execute("Cosh(1)");
        assertEquals(Math.cosh(1), result.value(), 1e-10);
    }

    @Test
    void coshNegativeOne() throws Exception {
        var result = (RealAtom) fm.execute("Cosh(-1)");
        assertEquals(Math.cosh(-1), result.value(), 1e-10);
    }

    @Test
    void coshReal() throws Exception {
        var result = (RealAtom) fm.execute("Cosh(1.5)");
        assertEquals(Math.cosh(1.5), result.value(), 1e-10);
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void coshSymbol() throws Exception {
        fm.assertExec("Cosh(x)", "Cosh(x)");
    }

    @Test
    void coshSymbolicExpression() throws Exception {
        fm.assertExec("Cosh(Add(1, x))", "Cosh(1 + x)");
    }
}
