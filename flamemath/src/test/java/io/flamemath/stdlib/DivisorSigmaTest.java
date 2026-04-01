package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class DivisorSigmaTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Sum of divisors (k=1) ---

    @Test
    void sigmaOf1K1() throws Exception {
        fm.assertExec("1", "DivisorSigma(1, 1)");
    }

    @Test
    void sigmaOf6K1() throws Exception {
        fm.assertExec("12", "DivisorSigma(6, 1)");
    }

    @Test
    void sigmaOf12K1() throws Exception {
        fm.assertExec("28", "DivisorSigma(12, 1)");
    }

    @Test
    void sigmaOf28K1() throws Exception {
        fm.assertExec("56", "DivisorSigma(28, 1)");
    }

    // --- Number of divisors (k=0) ---

    @Test
    void sigmaOf1K0() throws Exception {
        fm.assertExec("1", "DivisorSigma(1, 0)");
    }

    @Test
    void sigmaOf6K0() throws Exception {
        fm.assertExec("4", "DivisorSigma(6, 0)");
    }

    @Test
    void sigmaOf12K0() throws Exception {
        fm.assertExec("6", "DivisorSigma(12, 0)");
    }

    @Test
    void sigmaOf36K0() throws Exception {
        fm.assertExec("9", "DivisorSigma(36, 0)");
    }

    // --- Sum of squares of divisors (k=2) ---

    @Test
    void sigmaOf6K2() throws Exception {
        fm.assertExec("50", "DivisorSigma(6, 2)");
    }

    @Test
    void sigmaOf12K2() throws Exception {
        fm.assertExec("210", "DivisorSigma(12, 2)");
    }

    // --- Prime inputs ---

    @Test
    void sigmaOf7K1() throws Exception {
        fm.assertExec("8", "DivisorSigma(7, 1)");
    }

    @Test
    void sigmaOf7K0() throws Exception {
        fm.assertExec("2", "DivisorSigma(7, 0)");
    }

    // --- Zero and negative (unevaluated) ---

    @Test
    void sigmaOfZeroReturnsRaw() throws Exception {
        fm.assertExec("DivisorSigma(0, 1)", "DivisorSigma(0, 1)");
    }

    @Test
    void sigmaOfNegativeReturnsRaw() throws Exception {
        fm.assertExec("DivisorSigma(-5, 1)", "DivisorSigma(-5, 1)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicFirstArgReturnsRaw() throws Exception {
        fm.assertExec("DivisorSigma(x, 1)", "DivisorSigma(x, 1)");
    }

    @Test
    void symbolicSecondArgEvaluates() throws Exception {
        fm.assertExec("1 + 2^k + 3^k + 4^k + 6^k + 12^k", "DivisorSigma(12, k)");
    }

}
