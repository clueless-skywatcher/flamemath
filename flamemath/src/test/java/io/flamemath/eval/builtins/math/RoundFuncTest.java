package io.flamemath.eval.builtins.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

class RoundFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("Round", new RoundFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Round()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Round(1, 2)"));
    }

    // --- Integer passthrough ---

    @Test
    void integerPositive() throws Exception {
        fm.assertExec("10", "Round(10)");
    }

    @Test
    void integerNegative() throws Exception {
        fm.assertExec("-1", "Round(-1)");
    }

    @Test
    void integerZero() throws Exception {
        fm.assertExec("0", "Round(0)");
    }

    // --- Real → round ---

    @Test
    void realRoundsDown() throws Exception {
        fm.assertExec("1", "Round(1.25)");
    }

    @Test
    void realRoundsUp() throws Exception {
        fm.assertExec("2", "Round(1.75)");
    }

    @Test
    void realHalfRoundsUp() throws Exception {
        // Math.round(0.5) = 1 (rounds toward positive infinity)
        fm.assertExec("1", "Round(0.5)");
    }

    @Test
    void realNegativeRoundsDown() throws Exception {
        fm.assertExec("-1", "Round(-1.25)");
    }

    @Test
    void realNegativeRoundsUp() throws Exception {
        fm.assertExec("-2", "Round(-1.75)");
    }

    @Test
    void realNegativeHalf() throws Exception {
        // Math.round(-0.5) = 0
        fm.assertExec("0", "Round(-0.5)");
    }

    @Test
    void realAlreadyInteger() throws Exception {
        fm.assertExec("3", "Round(3.0)");
    }

    @Test
    void realJustAboveHalf() throws Exception {
        fm.assertExec("2", "Round(1.5001)");
    }

    @Test
    void realJustBelowHalf() throws Exception {
        fm.assertExec("1", "Round(1.4999)");
    }

    // --- Rational → round ---

    @Test
    void rationalOneThird() throws Exception {
        fm.assertExec("0", "Round(1 / 3)");
    }

    @Test
    void rationalTwoThirds() throws Exception {
        fm.assertExec("1", "Round(2 / 3)");
    }

    @Test
    void rationalThreeHalves() throws Exception {
        fm.assertExec("2", "Round(3 / 2)");
    }

    @Test
    void rationalNegativeOneThird() throws Exception {
        fm.assertExec("0", "Round(-1 / 3)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicStaysUnevaluated() throws Exception {
        fm.assertExec("Round(x)", "Round(x)");
    }

    @Test
    void symbolicExpressionStaysUnevaluated() throws Exception {
        fm.assertExec("Round(Add(1, x))", "Round(1 + x)");
    }
}
