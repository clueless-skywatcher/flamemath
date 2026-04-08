package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class SexyPrimesTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Basic cases ---

    @Test
    void firstPair() throws Exception {
        fm.assertExec("[[5, 11]]", "SexyPrimes(1)");
    }

    @Test
    void firstThreePairs() throws Exception {
        fm.assertExec("[[5, 11], [7, 13], [11, 17]]", "SexyPrimes(3)");
    }

    @Test
    void firstFivePairs() throws Exception {
        fm.assertExec("[[5, 11], [7, 13], [11, 17], [13, 19], [17, 23]]", "SexyPrimes(5)");
    }

    // --- More pairs ---

    @Test
    void firstTenPairs() throws Exception {
        fm.assertExec(
            "[[5, 11], [7, 13], [11, 17], [13, 19], [17, 23], [23, 29], [31, 37], [37, 43], [41, 47], [47, 53]]",
            "SexyPrimes(10)"
        );
    }

    // --- Composed ---

    @Test
    void fromVariable() throws Exception {
        fm.assertExec("[[5, 11], [7, 13], [11, 17]]", "{ n = 3; SexyPrimes(n) }");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicReturnsRaw() throws Exception {
        fm.assertExec("SexyPrimes(x)", "SexyPrimes(x)");
    }
}
