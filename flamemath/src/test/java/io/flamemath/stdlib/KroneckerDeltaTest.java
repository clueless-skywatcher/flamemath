package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class KroneckerDeltaTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Equal inputs (returns 1) ---

    @Test
    void equalZeros() throws Exception {
        fm.assertExec("1", "KroneckerDelta(0, 0)");
    }

    @Test
    void equalOnes() throws Exception {
        fm.assertExec("1", "KroneckerDelta(1, 1)");
    }

    @Test
    void equalLarge() throws Exception {
        fm.assertExec("1", "KroneckerDelta(100, 100)");
    }

    @Test
    void equalNegative() throws Exception {
        fm.assertExec("1", "KroneckerDelta(-5, -5)");
    }

    // --- Unequal inputs (returns 0) ---

    @Test
    void unequalPositive() throws Exception {
        fm.assertExec("0", "KroneckerDelta(1, 2)");
    }

    @Test
    void unequalMixed() throws Exception {
        fm.assertExec("0", "KroneckerDelta(-1, 1)");
    }

    @Test
    void unequalZeroAndOne() throws Exception {
        fm.assertExec("0", "KroneckerDelta(0, 1)");
    }

    @Test
    void unequalLarge() throws Exception {
        fm.assertExec("0", "KroneckerDelta(99, 100)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicFirstArg() throws Exception {
        fm.assertExec("KroneckerDelta(x, 1)", "KroneckerDelta(x, 1)");
    }

    @Test
    void symbolicSecondArg() throws Exception {
        fm.assertExec("KroneckerDelta(1, y)", "KroneckerDelta(1, y)");
    }

    @Test
    void symbolicBothArgs() throws Exception {
        fm.assertExec("KroneckerDelta(x, y)", "KroneckerDelta(x, y)");
    }
}
