package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class PrimeTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- First few primes ---

    @Test
    void firstPrimeIs2() throws Exception {
        fm.assertExec("2", "Prime(1)");
    }

    @Test
    void secondPrimeIs3() throws Exception {
        fm.assertExec("3", "Prime(2)");
    }

    @Test
    void thirdPrimeIs5() throws Exception {
        fm.assertExec("5", "Prime(3)");
    }

    @Test
    void fourthPrimeIs7() throws Exception {
        fm.assertExec("7", "Prime(4)");
    }

    @Test
    void fifthPrimeIs11() throws Exception {
        fm.assertExec("11", "Prime(5)");
    }

    @Test
    void sixthPrimeIs13() throws Exception {
        fm.assertExec("13", "Prime(6)");
    }

    // --- Larger indices ---

    @Test
    void tenthPrimeIs29() throws Exception {
        fm.assertExec("29", "Prime(10)");
    }

    @Test
    void twentyFifthPrimeIs97() throws Exception {
        fm.assertExec("97", "Prime(25)");
    }

    @Test
    void hundredthPrimeIs541() throws Exception {
        fm.assertExec("541", "Prime(100)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicReturnsRaw() throws Exception {
        fm.assertExec("Prime(x)", "Prime(x)");
    }
}
