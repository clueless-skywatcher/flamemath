package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class LiouvilleLambdaTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- lambda(1) = 1 (zero prime factors) ---

    @Test
    void liouvilleLambdaOf1() throws Exception {
        fm.assertExec("1", "LiouvilleLambda(1)");
    }

    // --- Primes: lambda(p) = -1 (one factor) ---

    @Test
    void liouvilleLambdaOf2() throws Exception {
        fm.assertExec("-1", "LiouvilleLambda(2)");
    }

    @Test
    void liouvilleLambdaOf3() throws Exception {
        fm.assertExec("-1", "LiouvilleLambda(3)");
    }

    @Test
    void liouvilleLambdaOf7() throws Exception {
        fm.assertExec("-1", "LiouvilleLambda(7)");
    }

    // --- Two prime factors (with multiplicity): lambda = 1 ---

    @Test
    void liouvilleLambdaOf4() throws Exception {
        // 4 = 2^2, Omega = 2
        fm.assertExec("1", "LiouvilleLambda(4)");
    }

    @Test
    void liouvilleLambdaOf6() throws Exception {
        // 6 = 2 * 3, Omega = 2
        fm.assertExec("1", "LiouvilleLambda(6)");
    }

    @Test
    void liouvilleLambdaOf9() throws Exception {
        // 9 = 3^2, Omega = 2
        fm.assertExec("1", "LiouvilleLambda(9)");
    }

    // --- Three prime factors: lambda = -1 ---

    @Test
    void liouvilleLambdaOf8() throws Exception {
        // 8 = 2^3, Omega = 3
        fm.assertExec("-1", "LiouvilleLambda(8)");
    }

    @Test
    void liouvilleLambdaOf12() throws Exception {
        // 12 = 2^2 * 3, Omega = 3
        fm.assertExec("-1", "LiouvilleLambda(12)");
    }

    @Test
    void liouvilleLambdaOf30() throws Exception {
        // 30 = 2 * 3 * 5, Omega = 3
        fm.assertExec("-1", "LiouvilleLambda(30)");
    }

    // --- Four prime factors: lambda = 1 ---

    @Test
    void liouvilleLambdaOf16() throws Exception {
        // 16 = 2^4, Omega = 4
        fm.assertExec("1", "LiouvilleLambda(16)");
    }

    @Test
    void liouvilleLambdaOf36() throws Exception {
        // 36 = 2^2 * 3^2, Omega = 4
        fm.assertExec("1", "LiouvilleLambda(36)");
    }

    // --- Larger value ---

    @Test
    void liouvilleLambdaOf360() throws Exception {
        // 360 = 2^3 * 3^2 * 5, Omega = 6
        fm.assertExec("1", "LiouvilleLambda(360)");
    }

    // --- Zero and negative (unevaluated) ---

    @Test
    void liouvilleLambdaOfZeroReturnsRaw() throws Exception {
        fm.assertExec("LiouvilleLambda(0)", "LiouvilleLambda(0)");
    }

    @Test
    void liouvilleLambdaOfNegativeReturnsRaw() throws Exception {
        fm.assertExec("LiouvilleLambda(-3)", "LiouvilleLambda(-3)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void symbolicReturnsRaw() throws Exception {
        fm.assertExec("LiouvilleLambda(x)", "LiouvilleLambda(x)");
    }
}
