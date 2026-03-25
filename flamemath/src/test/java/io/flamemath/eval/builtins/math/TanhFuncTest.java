package io.flamemath.eval.builtins.math;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.IntegerAtom;
import io.flamemath.expr.RealAtom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TanhFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("Tanh", new TanhFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Tanh()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Tanh(1, 2)"));
    }

    // --- Numeric ---

    @Test
    void tanhZero() throws Exception {
        assertEquals(IntegerAtom.ZERO, fm.execute("Tanh(0)"));
    }

    @Test
    void tanhOne() throws Exception {
        var result = (RealAtom) fm.execute("Tanh(1)");
        assertEquals(Math.tanh(1), result.value(), 1e-10);
    }

    @Test
    void tanhNegativeOne() throws Exception {
        var result = (RealAtom) fm.execute("Tanh(-1)");
        assertEquals(Math.tanh(-1), result.value(), 1e-10);
    }

    @Test
    void tanhReal() throws Exception {
        var result = (RealAtom) fm.execute("Tanh(0.5)");
        assertEquals(Math.tanh(0.5), result.value(), 1e-10);
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void tanhSymbol() throws Exception {
        fm.assertExec("Tanh(x)", "Tanh(x)");
    }

    @Test
    void tanhSymbolicExpression() throws Exception {
        fm.assertExec("Tanh(Add(1, x))", "Tanh(1 + x)");
    }
}
