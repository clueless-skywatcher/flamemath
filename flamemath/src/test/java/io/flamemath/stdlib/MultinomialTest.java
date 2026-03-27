package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class MultinomialTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Standard values: Multinomial(n, k1, k2, ...) where n = sum(k) ---

    @Test
    void multinomialThreeTwoOne() throws Exception {
        // 6! / (3! * 2! * 1!) = 60
        fm.assertExec("60", "Multinomial(6, 3, 2, 1)");
    }

    @Test
    void multinomialTwoTwoTwo() throws Exception {
        // 6! / (2! * 2! * 2!) = 90
        fm.assertExec("90", "Multinomial(6, 2, 2, 2)");
    }

    @Test
    void multinomialFourThreeTwo() throws Exception {
        // 9! / (4! * 3! * 2!) = 1260
        fm.assertExec("1260", "Multinomial(9, 4, 3, 2)");
    }

    // --- Two arguments (reduces to Binomial) ---

    @Test
    void multinomialTwoArgsFiveTwo() throws Exception {
        // 5! / (3! * 2!) = 10
        fm.assertExec("10", "Multinomial(5, 3, 2)");
    }

    @Test
    void multinomialTwoArgsSixThree() throws Exception {
        // 6! / (3! * 3!) = 20
        fm.assertExec("20", "Multinomial(6, 3, 3)");
    }

    // --- Edge cases ---

    @Test
    void multinomialWithZeros() throws Exception {
        fm.assertExec("1", "Multinomial(0, 0)");
    }

    @Test
    void multinomialAllOnes() throws Exception {
        // 3! / (1! * 1! * 1!) = 6
        fm.assertExec("6", "Multinomial(3, 1, 1, 1)");
    }

    @Test
    void multinomialOneAndZero() throws Exception {
        fm.assertExec("1", "Multinomial(1, 1, 0)");
    }

    @Test
    void multinomialNAndN() throws Exception {
        // 5! / 5! = 1
        fm.assertExec("1", "Multinomial(5, 5)");
    }

    @Test
    void multinomialNAndZero() throws Exception {
        // 5! / (5! * 0!) = 1
        fm.assertExec("1", "Multinomial(5, 5, 0)");
    }

    // --- Big values (overflow long) ---

    @Test
    void multinomialTwentyTenFiveFive() throws Exception {
        // 20! / (10! * 5! * 5!) = 46558512
        fm.assertExec("46558512", "Multinomial(20, 10, 5, 5)");
    }

    @Test
    void multinomialThirtyTenTenTen() throws Exception {
        // 30! / (10! * 10! * 10!) = 5550996791340
        fm.assertExec("5550996791340", "Multinomial(30, 10, 10, 10)");
    }

    @Test
    void multinomialReducesToBinomial() throws Exception {
        // Multinomial(n, k, n-k) = Binomial(n, k)
        fm.assertExec("Binomial(100, 50)", "Multinomial(100, 50, 50)");
    }

    // --- n != Sum(k) returns raw ---

    @Test
    void multinomialMismatchReturnsRaw() throws Exception {
        fm.assertExec("Multinomial(4, 3, 2)", "Multinomial(4, 3, 2)");
    }

    // --- From variables ---

    @Test
    void multinomialFromVariables() throws Exception {
        fm.assertExec("60", "{ n = 6; a = 3; b = 2; c = 1; Multinomial(n, a, b, c) }");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void multinomialSymbolReturnsRaw() throws Exception {
        fm.assertExec("Multinomial(x, 2, 1)", "Multinomial(x, 2, 1)");
    }

    @Test
    void multinomialAllSymbolicReturnsRaw() throws Exception {
        fm.assertExec("Multinomial(x, y, z)", "Multinomial(x, y, z)");
    }

    @Test
    void multinomialSymbolicInRestReturnsRaw() throws Exception {
        fm.assertExec("Multinomial(3, x, 1)", "Multinomial(3, x, 1)");
    }
}
