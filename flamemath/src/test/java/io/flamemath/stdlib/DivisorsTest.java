package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class DivisorsTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Prime numbers (only 1 and itself) ---

    @Test
    void divisorsOf2() throws Exception {
        fm.assertExec("[1, 2]", "Divisors(2)");
    }

    @Test
    void divisorsOf7() throws Exception {
        fm.assertExec("[1, 7]", "Divisors(7)");
    }

    @Test
    void divisorsOf13() throws Exception {
        fm.assertExec("[1, 13]", "Divisors(13)");
    }

    // --- Small composites ---

    @Test
    void divisorsOf6() throws Exception {
        fm.assertExec("[1, 2, 3, 6]", "Divisors(6)");
    }

    @Test
    void divisorsOf12() throws Exception {
        fm.assertExec("[1, 2, 3, 4, 6, 12]", "Divisors(12)");
    }

    @Test
    void divisorsOf28() throws Exception {
        fm.assertExec("[1, 2, 4, 7, 14, 28]", "Divisors(28)");
    }

    // --- Perfect squares ---

    @Test
    void divisorsOf16() throws Exception {
        fm.assertExec("[1, 2, 4, 8, 16]", "Divisors(16)");
    }

    @Test
    void divisorsOf36() throws Exception {
        fm.assertExec("[1, 2, 3, 4, 6, 9, 12, 18, 36]", "Divisors(36)");
    }

    // --- Powers of primes ---

    @Test
    void divisorsOf8() throws Exception {
        fm.assertExec("[1, 2, 4, 8]", "Divisors(8)");
    }

    @Test
    void divisorsOf27() throws Exception {
        fm.assertExec("[1, 3, 9, 27]", "Divisors(27)");
    }

    // --- Larger composite ---

    @Test
    void divisorsOf60() throws Exception {
        fm.assertExec("[1, 2, 3, 4, 5, 6, 10, 12, 15, 20, 30, 60]", "Divisors(60)");
    }

    @Test
    void divisorsOf360() throws Exception {
        fm.assertExec("[1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 15, 18, 20, 24, 30, 36, 40, 45, 60, 72, 90, 120, 180, 360]", "Divisors(360)");
    }

    // --- Edge case: 1 ---

    @Test
    void divisorsOf1() throws Exception {
        fm.assertExec("[1]", "Divisors(1)");
    }

    // --- Negative inputs (same as positive) ---

    @Test
    void divisorsOfNeg6() throws Exception {
        fm.assertExec("[1, 2, 3, 6]", "Divisors(-6)");
    }

    @Test
    void divisorsOfNeg12() throws Exception {
        fm.assertExec("[1, 2, 3, 4, 6, 12]", "Divisors(-12)");
    }

    @Test
    void divisorsOfNeg1() throws Exception {
        fm.assertExec("[1]", "Divisors(-1)");
    }

    // --- Zero (unevaluated) ---

    @Test
    void divisorsOfZeroReturnsRaw() throws Exception {
        fm.assertExec("Divisors(0)", "Divisors(0)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicReturnsRaw() throws Exception {
        fm.assertExec("Divisors(x)", "Divisors(x)");
    }
}
