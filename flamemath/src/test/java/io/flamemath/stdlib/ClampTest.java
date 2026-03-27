package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class ClampTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Within range ---

    @Test
    void clampWithinRange() throws Exception {
        fm.assertExec("3", "Clamp(3, 1, 5)");
    }

    @Test
    void clampWithinRangeReal() throws Exception {
        fm.assertExec("3.5", "Clamp(3.5, 1.0, 5.0)");
    }

    // --- Above range ---

    @Test
    void clampAboveRange() throws Exception {
        fm.assertExec("5", "Clamp(7, 1, 5)");
    }

    @Test
    void clampAboveRangeReal() throws Exception {
        fm.assertExec("5.0", "Clamp(7.5, 1.0, 5.0)");
    }

    // --- Below range ---

    @Test
    void clampBelowRange() throws Exception {
        fm.assertExec("1", "Clamp(-3, 1, 5)");
    }

    @Test
    void clampBelowRangeReal() throws Exception {
        fm.assertExec("1.0", "Clamp(0.1, 1.0, 5.0)");
    }

    // --- Boundary values ---

    @Test
    void clampAtLowerBound() throws Exception {
        fm.assertExec("1", "Clamp(1, 1, 5)");
    }

    @Test
    void clampAtUpperBound() throws Exception {
        fm.assertExec("5", "Clamp(5, 1, 5)");
    }

    // --- Negative range ---

    @Test
    void clampNegativeRange() throws Exception {
        fm.assertExec("-5", "Clamp(-10, -5, -1)");
    }

    @Test
    void clampWithinNegativeRange() throws Exception {
        fm.assertExec("-3", "Clamp(-3, -5, -1)");
    }

    // --- Zero in range ---

    @Test
    void clampZeroWithinRange() throws Exception {
        fm.assertExec("0", "Clamp(0, -5, 5)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void clampSymbolicValueReturnsRaw() throws Exception {
        fm.assertExec("Clamp(x, 1, 5)", "Clamp(x, 1, 5)");
    }

    @Test
    void clampSymbolicLowReturnsRaw() throws Exception {
        fm.assertExec("Clamp(3, a, 5)", "Clamp(3, a, 5)");
    }

    @Test
    void clampSymbolicHiReturnsRaw() throws Exception {
        fm.assertExec("Clamp(3, 1, b)", "Clamp(3, 1, b)");
    }

    // --- Composed ---

    @Test
    void clampFromVariables() throws Exception {
        fm.assertExec("5", "{n = 10; Clamp(n, 1, 5)}");
    }

    @Test
    void clampOfExpression() throws Exception {
        fm.assertExec("5", "Clamp(3 + 4, 1, 5)");
    }
}
