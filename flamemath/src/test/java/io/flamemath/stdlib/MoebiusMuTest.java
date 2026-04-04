package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class MoebiusMuTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- mu(1) = 1 ---

    @Test
    void moebiusMuOf1() throws Exception {
        fm.assertExec("1", "MoebiusMu(1)");
    }

    // --- Primes: mu(p) = -1 ---

    @Test
    void moebiusMuOf2() throws Exception {
        fm.assertExec("-1", "MoebiusMu(2)");
    }

    @Test
    void moebiusMuOf3() throws Exception {
        fm.assertExec("-1", "MoebiusMu(3)");
    }

    @Test
    void moebiusMuOf7() throws Exception {
        fm.assertExec("-1", "MoebiusMu(7)");
    }

    @Test
    void moebiusMuOf13() throws Exception {
        fm.assertExec("-1", "MoebiusMu(13)");
    }

    // --- Product of even number of distinct primes: mu = 1 ---

    @Test
    void moebiusMuOf6() throws Exception {
        // 6 = 2 * 3, two distinct primes
        fm.assertExec("1", "MoebiusMu(6)");
    }

    @Test
    void moebiusMuOf10() throws Exception {
        // 10 = 2 * 5
        fm.assertExec("1", "MoebiusMu(10)");
    }

    @Test
    void moebiusMuOf15() throws Exception {
        // 15 = 3 * 5
        fm.assertExec("1", "MoebiusMu(15)");
    }

    // --- Product of odd number of distinct primes: mu = -1 ---

    @Test
    void moebiusMuOf30() throws Exception {
        // 30 = 2 * 3 * 5, three distinct primes
        fm.assertExec("-1", "MoebiusMu(30)");
    }

    @Test
    void moebiusMuOf42() throws Exception {
        // 42 = 2 * 3 * 7
        fm.assertExec("-1", "MoebiusMu(42)");
    }

    // --- Has squared factor: mu = 0 ---

    @Test
    void moebiusMuOf4() throws Exception {
        // 4 = 2^2
        fm.assertExec("0", "MoebiusMu(4)");
    }

    @Test
    void moebiusMuOf8() throws Exception {
        // 8 = 2^3
        fm.assertExec("0", "MoebiusMu(8)");
    }

    @Test
    void moebiusMuOf9() throws Exception {
        // 9 = 3^2
        fm.assertExec("0", "MoebiusMu(9)");
    }

    @Test
    void moebiusMuOf12() throws Exception {
        // 12 = 2^2 * 3
        fm.assertExec("0", "MoebiusMu(12)");
    }

    @Test
    void moebiusMuOf18() throws Exception {
        // 18 = 2 * 3^2
        fm.assertExec("0", "MoebiusMu(18)");
    }

    @Test
    void moebiusMuOf100() throws Exception {
        // 100 = 2^2 * 5^2
        fm.assertExec("0", "MoebiusMu(100)");
    }

    // --- Zero and negative (unevaluated) ---

    @Test
    void moebiusMuOfZeroReturnsRaw() throws Exception {
        fm.assertExec("MoebiusMu(0)", "MoebiusMu(0)");
    }

    @Test
    void moebiusMuOfNegativeReturnsRaw() throws Exception {
        fm.assertExec("MoebiusMu(-5)", "MoebiusMu(-5)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicReturnsRaw() throws Exception {
        fm.assertExec("MoebiusMu(x)", "MoebiusMu(x)");
    }
}
