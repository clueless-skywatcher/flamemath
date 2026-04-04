package io.flamemath.eval.builtins.ntheory;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;

class PrimeFactorsFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Small primes ---

    @Test
    void factorsOf2() throws Exception {
        fm.assertExec("{2: 1}", "PrimeFactors(2)");
    }

    @Test
    void factorsOf3() throws Exception {
        fm.assertExec("{3: 1}", "PrimeFactors(3)");
    }

    @Test
    void factorsOf7() throws Exception {
        fm.assertExec("{7: 1}", "PrimeFactors(7)");
    }

    // --- Prime powers ---

    @Test
    void factorsOf8() throws Exception {
        fm.assertExec("{2: 3}", "PrimeFactors(8)");
    }

    @Test
    void factorsOf27() throws Exception {
        fm.assertExec("{3: 3}", "PrimeFactors(27)");
    }

    @Test
    void factorsOf32() throws Exception {
        fm.assertExec("{2: 5}", "PrimeFactors(32)");
    }

    // --- Composites with small factors ---

    @Test
    void factorsOf12() throws Exception {
        fm.assertExec("{2: 2, 3: 1}", "PrimeFactors(12)");
    }

    @Test
    void factorsOf60() throws Exception {
        fm.assertExec("{2: 2, 3: 1, 5: 1}", "PrimeFactors(60)");
    }

    @Test
    void factorsOf360() throws Exception {
        fm.assertExec("{2: 3, 3: 2, 5: 1}", "PrimeFactors(360)");
    }

    @Test
    void factorsOf100() throws Exception {
        fm.assertExec("{2: 2, 5: 2}", "PrimeFactors(100)");
    }

    // --- Larger primes (still within trial division range) ---

    @Test
    void factorsOfLargePrime() throws Exception {
        fm.assertExec("{104729: 1}", "PrimeFactors(104729)");
    }

    @Test
    void factorsOf999961() throws Exception {
        fm.assertExec("{999961: 1}", "PrimeFactors(999961)");
    }

    // --- Products of two primes ---

    @Test
    void factorsOf91() throws Exception {
        fm.assertExec("{7: 1, 13: 1}", "PrimeFactors(91)");
    }

    @Test
    void factorsOf221() throws Exception {
        fm.assertExec("{13: 1, 17: 1}", "PrimeFactors(221)");
    }

    // --- Factors beyond trial division range (triggers Pollard's rho) ---

    @Test
    void factorsOfTwoLargePrimes() throws Exception {
        // 1000003 * 1000033 = 1000036000099
        fm.assertExec("{1000003: 1, 1000033: 1}", "PrimeFactors(1000036000099)");
    }

    @Test
    void factorsWithSmallAndLargePrime() throws Exception {
        // 2 * 1000003 * 1000033 = 2000072000198
        fm.assertExec("{2: 1, 1000003: 1, 1000033: 1}", "PrimeFactors(2000072000198)");
    }

    // --- Edge cases ---

    @Test
    void factorsOf1() throws Exception {
        fm.assertExec("{}", "PrimeFactors(1)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicReturnsRaw() throws Exception {
        fm.assertExec("PrimeFactors(x)", "PrimeFactors(x)");
    }

    @Test
    void symbolicExprReturnsRaw() throws Exception {
        fm.assertExec("PrimeFactors(x + 1)", "PrimeFactors(x + 1)");
    }
}
