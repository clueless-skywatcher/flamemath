package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class StirlingIITest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Base cases ---

    @Test
    void stirlingZeroZero() throws Exception {
        fm.assertExec("1", "StirlingII(0, 0)");
    }

    @Test
    void stirlingNZero() throws Exception {
        fm.assertExec("0", "StirlingII(5, 0)");
    }

    @Test
    void stirlingZeroK() throws Exception {
        fm.assertExec("0", "StirlingII(0, 3)");
    }

    @Test
    void stirlingNEqualsK() throws Exception {
        fm.assertExec("1", "StirlingII(5, 5)");
    }

    @Test
    void stirlingOneOne() throws Exception {
        fm.assertExec("1", "StirlingII(1, 1)");
    }

    // --- Standard values ---

    @Test
    void stirlingThreeTwo() throws Exception {
        fm.assertExec("3", "StirlingII(3, 2)");
    }

    @Test
    void stirlingFourTwo() throws Exception {
        fm.assertExec("7", "StirlingII(4, 2)");
    }

    @Test
    void stirlingFourThree() throws Exception {
        fm.assertExec("6", "StirlingII(4, 3)");
    }

    @Test
    void stirlingFiveTwo() throws Exception {
        fm.assertExec("15", "StirlingII(5, 2)");
    }

    @Test
    void stirlingFiveThree() throws Exception {
        fm.assertExec("25", "StirlingII(5, 3)");
    }

    @Test
    void stirlingFiveFour() throws Exception {
        fm.assertExec("10", "StirlingII(5, 4)");
    }

    @Test
    void stirlingSixThree() throws Exception {
        fm.assertExec("90", "StirlingII(6, 3)");
    }

    @Test
    void stirlingSevenThree() throws Exception {
        fm.assertExec("301", "StirlingII(7, 3)");
    }

    // --- S(n, 1) = 1 for all n >= 1 ---

    @Test
    void stirlingNOne() throws Exception {
        fm.assertExec("1", "StirlingII(7, 1)");
    }

    // --- k > n returns 0 ---

    @Test
    void stirlingKGreaterThanN() throws Exception {
        fm.assertExec("0", "StirlingII(3, 5)");
    }

    // --- Larger values ---

    @Test
    void stirlingTenFive() throws Exception {
        fm.assertExec("42525", "StirlingII(10, 5)");
    }

    @Test
    void stirlingEightFour() throws Exception {
        fm.assertExec("1701", "StirlingII(8, 4)");
    }

    // --- Negative arguments return unevaluated ---

    @Test
    void stirlingNegativeN() throws Exception {
        fm.assertExec("StirlingII(-1, 3)", "StirlingII(-1, 3)");
    }

    @Test
    void stirlingNegativeK() throws Exception {
        fm.assertExec("StirlingII(5, -2)", "StirlingII(5, -2)");
    }

    // --- Composed ---

    @Test
    void stirlingFromVariables() throws Exception {
        fm.assertExec("25", "{ n = 5; k = 3; StirlingII(n, k) }");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void stirlingSymbolReturnsRaw() throws Exception {
        fm.assertExec("StirlingII(x, 3)", "StirlingII(x, 3)");
    }

    @Test
    void stirlingBothSymbolicReturnsRaw() throws Exception {
        fm.assertExec("StirlingII(x, y)", "StirlingII(x, y)");
    }
}
