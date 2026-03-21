package io.flamemath.eval.builtins.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

class CeilFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("Ceil", new CeilFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Ceil()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Ceil(1, 2)"));
    }

    // --- Integer passthrough ---

    @Test
    void integerPositive() throws Exception {
        fm.assertExec("10", "Ceil(10)");
    }

    @Test
    void integerNegative() throws Exception {
        fm.assertExec("-1", "Ceil(-1)");
    }

    @Test
    void integerZero() throws Exception {
        fm.assertExec("0", "Ceil(0)");
    }

    // --- Real → ceiling ---

    @Test
    void realPositiveFraction() throws Exception {
        fm.assertExec("2", "Ceil(1.25)");
    }

    @Test
    void realNegativeFraction() throws Exception {
        fm.assertExec("-1", "Ceil(-1.25)");
    }

    @Test
    void realAlreadyInteger() throws Exception {
        fm.assertExec("3", "Ceil(3.0)");
    }

    @Test
    void realJustAboveInteger() throws Exception {
        fm.assertExec("2", "Ceil(1.001)");
    }

    @Test
    void realJustBelowInteger() throws Exception {
        fm.assertExec("2", "Ceil(1.999)");
    }

    @Test
    void realNegativeAlreadyInteger() throws Exception {
        fm.assertExec("-2", "Ceil(-2.0)");
    }

    @Test
    void realHalf() throws Exception {
        fm.assertExec("1", "Ceil(0.5)");
    }

    @Test
    void realNegativeHalf() throws Exception {
        fm.assertExec("0", "Ceil(-0.5)");
    }

    // --- Rational → ceiling ---

    @Test
    void rationalOneThird() throws Exception {
        fm.assertExec("1", "Ceil(1 / 3)");
    }

    @Test
    void rationalThreeHalves() throws Exception {
        fm.assertExec("2", "Ceil(3 / 2)");
    }

    @Test
    void rationalNegativeOneThird() throws Exception {
        fm.assertExec("0", "Ceil(-1 / 3)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicStaysUnevaluated() throws Exception {
        fm.assertExec("Ceil(x)", "Ceil(x)");
    }

    @Test
    void symbolicExpressionStaysUnevaluated() throws Exception {
        fm.assertExec("Ceil(Add(1, x))", "Ceil(1 + x)");
    }
}
