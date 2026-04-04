package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class PrimeBigWTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Primes: Omega(p) = 1 ---

    @Test
    void primeBigWOf2() throws Exception {
        fm.assertExec("1", "PrimeBigW(2)");
    }

    @Test
    void primeBigWOf7() throws Exception {
        fm.assertExec("1", "PrimeBigW(7)");
    }

    @Test
    void primeBigWOf13() throws Exception {
        fm.assertExec("1", "PrimeBigW(13)");
    }

    // --- Prime powers ---

    @Test
    void primeBigWOf4() throws Exception {
        // 4 = 2^2, Omega = 2
        fm.assertExec("2", "PrimeBigW(4)");
    }

    @Test
    void primeBigWOf8() throws Exception {
        // 8 = 2^3, Omega = 3
        fm.assertExec("3", "PrimeBigW(8)");
    }

    @Test
    void primeBigWOf27() throws Exception {
        // 27 = 3^3, Omega = 3
        fm.assertExec("3", "PrimeBigW(27)");
    }

    // --- Composites ---

    @Test
    void primeBigWOf6() throws Exception {
        // 6 = 2 * 3, Omega = 2
        fm.assertExec("2", "PrimeBigW(6)");
    }

    @Test
    void primeBigWOf12() throws Exception {
        // 12 = 2^2 * 3, Omega = 3
        fm.assertExec("3", "PrimeBigW(12)");
    }

    @Test
    void primeBigWOf36() throws Exception {
        // 36 = 2^2 * 3^2, Omega = 4
        fm.assertExec("4", "PrimeBigW(36)");
    }

    @Test
    void primeBigWOf60() throws Exception {
        // 60 = 2^2 * 3 * 5, Omega = 4
        fm.assertExec("4", "PrimeBigW(60)");
    }

    @Test
    void primeBigWOf360() throws Exception {
        // 360 = 2^3 * 3^2 * 5, Omega = 6
        fm.assertExec("6", "PrimeBigW(360)");
    }

    // --- Edge case: 1 ---

    @Test
    void primeBigWOf1() throws Exception {
        // 1 has no prime factors, Omega = 0
        fm.assertExec("0", "PrimeBigW(1)");
    }

    // --- Zero and negative (unevaluated) ---

    @Test
    void primeBigWOfZeroReturnsRaw() throws Exception {
        fm.assertExec("PrimeBigW(0)", "PrimeBigW(0)");
    }

    @Test
    void primeBigWOfNegativeReturnsRaw() throws Exception {
        fm.assertExec("PrimeBigW(-5)", "PrimeBigW(-5)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicReturnsRaw() throws Exception {
        fm.assertExec("PrimeBigW(x)", "PrimeBigW(x)");
    }
}
