package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class PrimeLittleWTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Primes: omega(p) = 1 ---

    @Test
    void primeLittleWOf2() throws Exception {
        fm.assertExec("1", "PrimeLittleW(2)");
    }

    @Test
    void primeLittleWOf7() throws Exception {
        fm.assertExec("1", "PrimeLittleW(7)");
    }

    @Test
    void primeLittleWOf13() throws Exception {
        fm.assertExec("1", "PrimeLittleW(13)");
    }

    // --- Prime powers: omega(p^k) = 1 ---

    @Test
    void primeLittleWOf4() throws Exception {
        // 4 = 2^2, omega = 1
        fm.assertExec("1", "PrimeLittleW(4)");
    }

    @Test
    void primeLittleWOf8() throws Exception {
        // 8 = 2^3, omega = 1
        fm.assertExec("1", "PrimeLittleW(8)");
    }

    @Test
    void primeLittleWOf27() throws Exception {
        // 27 = 3^3, omega = 1
        fm.assertExec("1", "PrimeLittleW(27)");
    }

    // --- Two distinct primes ---

    @Test
    void primeLittleWOf6() throws Exception {
        // 6 = 2 * 3, omega = 2
        fm.assertExec("2", "PrimeLittleW(6)");
    }

    @Test
    void primeLittleWOf12() throws Exception {
        // 12 = 2^2 * 3, omega = 2
        fm.assertExec("2", "PrimeLittleW(12)");
    }

    @Test
    void primeLittleWOf36() throws Exception {
        // 36 = 2^2 * 3^2, omega = 2
        fm.assertExec("2", "PrimeLittleW(36)");
    }

    // --- Three distinct primes ---

    @Test
    void primeLittleWOf30() throws Exception {
        // 30 = 2 * 3 * 5, omega = 3
        fm.assertExec("3", "PrimeLittleW(30)");
    }

    @Test
    void primeLittleWOf60() throws Exception {
        // 60 = 2^2 * 3 * 5, omega = 3
        fm.assertExec("3", "PrimeLittleW(60)");
    }

    @Test
    void primeLittleWOf360() throws Exception {
        // 360 = 2^3 * 3^2 * 5, omega = 3
        fm.assertExec("3", "PrimeLittleW(360)");
    }

    // --- Edge case: 1 ---

    @Test
    void primeLittleWOf1() throws Exception {
        // 1 has no prime factors, omega = 0
        fm.assertExec("0", "PrimeLittleW(1)");
    }

    // --- Zero and negative (unevaluated) ---

    @Test
    void primeLittleWOfZeroReturnsRaw() throws Exception {
        fm.assertExec("PrimeLittleW(0)", "PrimeLittleW(0)");
    }

    @Test
    void primeLittleWOfNegativeReturnsRaw() throws Exception {
        fm.assertExec("PrimeLittleW(-5)", "PrimeLittleW(-5)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicReturnsRaw() throws Exception {
        fm.assertExec("PrimeLittleW(x)", "PrimeLittleW(x)");
    }
}
