package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class NextPrimeTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Basic cases ---

    @Test
    void nextPrimeAfter1() throws Exception {
        fm.assertExec("2", "NextPrime(1)");
    }

    @Test
    void nextPrimeAfter2() throws Exception {
        fm.assertExec("3", "NextPrime(2)");
    }

    @Test
    void nextPrimeAfter3() throws Exception {
        fm.assertExec("5", "NextPrime(3)");
    }

    @Test
    void nextPrimeAfter4() throws Exception {
        fm.assertExec("5", "NextPrime(4)");
    }

    @Test
    void nextPrimeAfter5() throws Exception {
        fm.assertExec("7", "NextPrime(5)");
    }

    // --- Composite inputs ---

    @Test
    void nextPrimeAfter10() throws Exception {
        fm.assertExec("11", "NextPrime(10)");
    }

    @Test
    void nextPrimeAfter14() throws Exception {
        fm.assertExec("17", "NextPrime(14)");
    }

    @Test
    void nextPrimeAfter20() throws Exception {
        fm.assertExec("23", "NextPrime(20)");
    }

    // --- Prime inputs (strictly greater) ---

    @Test
    void nextPrimeAfter7() throws Exception {
        fm.assertExec("11", "NextPrime(7)");
    }

    @Test
    void nextPrimeAfter11() throws Exception {
        fm.assertExec("13", "NextPrime(11)");
    }

    @Test
    void nextPrimeAfter13() throws Exception {
        fm.assertExec("17", "NextPrime(13)");
    }

    @Test
    void nextPrimeAfter29() throws Exception {
        fm.assertExec("31", "NextPrime(29)");
    }

    // --- Larger values ---

    @Test
    void nextPrimeAfter100() throws Exception {
        fm.assertExec("101", "NextPrime(100)");
    }

    @Test
    void nextPrimeAfter97() throws Exception {
        fm.assertExec("101", "NextPrime(97)");
    }

    // --- Edge cases ---

    @Test
    void nextPrimeAfter0() throws Exception {
        fm.assertExec("2", "NextPrime(0)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicReturnsRaw() throws Exception {
        fm.assertExec("NextPrime(x)", "NextPrime(x)");
    }
}
