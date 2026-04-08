package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class LahNumberTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Base cases ---

    @Test
    void lahZeroZero() throws Exception {
        fm.assertExec("1", "LahNumber(0, 0)");
    }

    @Test
    void lahOneOne() throws Exception {
        fm.assertExec("1", "LahNumber(1, 1)");
    }

    @Test
    void lahNEqualsN() throws Exception {
        fm.assertExec("1", "LahNumber(5, 5)");
    }

    // --- Standard values ---

    @Test
    void lahThreeOne() throws Exception {
        fm.assertExec("6", "LahNumber(3, 1)");
    }

    @Test
    void lahThreeTwo() throws Exception {
        fm.assertExec("6", "LahNumber(3, 2)");
    }

    @Test
    void lahFourOne() throws Exception {
        fm.assertExec("24", "LahNumber(4, 1)");
    }

    @Test
    void lahFourTwo() throws Exception {
        fm.assertExec("36", "LahNumber(4, 2)");
    }

    @Test
    void lahFourThree() throws Exception {
        fm.assertExec("12", "LahNumber(4, 3)");
    }

    @Test
    void lahFiveTwo() throws Exception {
        fm.assertExec("240", "LahNumber(5, 2)");
    }

    @Test
    void lahFiveThree() throws Exception {
        fm.assertExec("120", "LahNumber(5, 3)");
    }

    @Test
    void lahFiveFour() throws Exception {
        fm.assertExec("20", "LahNumber(5, 4)");
    }

    // --- L(n, 1) = n! ---

    @Test
    void lahNOneEqualsFactorial() throws Exception {
        fm.assertExec("LahNumber(6, 1)", "Factorial(6)");
    }

    // --- Larger values ---

    @Test
    void lahSevenThree() throws Exception {
        fm.assertExec("12600", "LahNumber(7, 3)");
    }

    @Test
    void lahEightFour() throws Exception {
        fm.assertExec("58800", "LahNumber(8, 4)");
    }

    @Test
    void lahTenFive() throws Exception {
        fm.assertExec("3810240", "LahNumber(10, 5)");
    }

    // --- Composed ---

    @Test
    void lahFromVariables() throws Exception {
        fm.assertExec("120", "{ n = 5; k = 3; LahNumber(n, k) }");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void lahSymbolNReturnsRaw() throws Exception {
        fm.assertExec("LahNumber(x, 3)", "LahNumber(x, 3)");
    }

    @Test
    void lahSymbolKReturnsRaw() throws Exception {
        fm.assertExec("LahNumber(5, y)", "LahNumber(5, y)");
    }

    @Test
    void lahBothSymbolicReturnsRaw() throws Exception {
        fm.assertExec("LahNumber(x, y)", "LahNumber(x, y)");
    }
}
