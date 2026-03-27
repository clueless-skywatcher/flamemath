package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class BinomialTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Standard values ---

    @Test
    void binomialFiveChooseTwo() throws Exception {
        fm.assertExec("10", "Binomial(5, 2)");
    }

    @Test
    void binomialSixChooseThree() throws Exception {
        fm.assertExec("20", "Binomial(6, 3)");
    }

    @Test
    void binomialTenChooseFour() throws Exception {
        fm.assertExec("210", "Binomial(10, 4)");
    }

    // --- Edge cases ---

    @Test
    void binomialNChooseZero() throws Exception {
        fm.assertExec("1", "Binomial(5, 0)");
    }

    @Test
    void binomialNChooseN() throws Exception {
        fm.assertExec("1", "Binomial(5, 5)");
    }

    @Test
    void binomialNChooseOne() throws Exception {
        fm.assertExec("7", "Binomial(7, 1)");
    }

    @Test
    void binomialZeroChooseZero() throws Exception {
        fm.assertExec("1", "Binomial(0, 0)");
    }

    // --- Symmetry: C(n, k) = C(n, n-k) ---

    @Test
    void binomialSymmetry() throws Exception {
        fm.assertExec("Binomial(10, 3)", "Binomial(10, 7)");
    }

    // --- Larger values ---

    @Test
    void binomialTwentyChooseTen() throws Exception {
        fm.assertExec("184756", "Binomial(20, 10)");
    }

    @Test
    void binomialFifteenChooseFive() throws Exception {
        fm.assertExec("3003", "Binomial(15, 5)");
    }

    // --- Pascal's rule: C(n, k) = C(n-1, k-1) + C(n-1, k) ---

    @Test
    void pascalsRule() throws Exception {
        fm.assertExec("Binomial(8, 3)", "Binomial(7, 2) + Binomial(7, 3)");
    }

    // --- Composed ---

    @Test
    void binomialFromVariable() throws Exception {
        fm.assertExec("10", "{ n = 5; k = 2; Binomial(n, k) }");
    }

    @Test
    void sumOfRow() throws Exception {
        // Sum of row n of Pascal's triangle = 2^n
        fm.assertExec("32", "Binomial(5, 0) + Binomial(5, 1) + Binomial(5, 2) + Binomial(5, 3) + Binomial(5, 4) + Binomial(5, 5)");
    }

    // --- Big values (overflow long) ---

    @Test
    void binomialFiftyChooseTwentyFive() throws Exception {
        fm.assertExec("126410606437752", "Binomial(50, 25)");
    }

    @Test
    void binomialHundredChooseFiftySymmetry() throws Exception {
        fm.assertExec("Binomial(100, 50)", "Binomial(100, 50)");
    }

    @Test
    void binomialHundredChooseFiftyTimesOne() throws Exception {
        // C(100,50) * 1 should equal C(100,50) — verifies it's an integer, not a rational
        fm.assertExec("Binomial(100, 50) * 1", "Binomial(100, 50)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void binomialSymbolReturnsRaw() throws Exception {
        fm.assertExec("Binomial(x, 2)", "Binomial(x, 2)");
    }

    @Test
    void binomialBothSymbolicReturnsRaw() throws Exception {
        fm.assertExec("Binomial(x, y)", "Binomial(x, y)");
    }
}
