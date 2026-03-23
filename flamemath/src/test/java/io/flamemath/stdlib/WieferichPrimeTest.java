package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class WieferichPrimeTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Known Wieferich primes ---

    @Test
    void findsFirst1093() throws Exception {
        fm.assertExec("1093", "WieferichPrime(1093)");
    }

    @Test
    void findsFirst1093WithHigherBound() throws Exception {
        fm.assertExec("1093", "WieferichPrime(2000)");
    }

    @Test
    void findsFirst1093WithBound3511() throws Exception {
        fm.assertExec("1093", "WieferichPrime(3511)");
    }

    // --- Bound too low ---

    @Test
    void noneBelow1000() throws Exception {
        fm.assertExec("Null", "WieferichPrime(1000)");
    }

    @Test
    void noneBelow100() throws Exception {
        fm.assertExec("Null", "WieferichPrime(100)");
    }

    @Test
    void noneBelow2() throws Exception {
        fm.assertExec("Null", "WieferichPrime(2)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicReturnsRaw() throws Exception {
        fm.assertExec("WieferichPrime(x)", "WieferichPrime(x)");
    }
}
