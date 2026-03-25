package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class SignTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Positive ---

    @Test
    void signOfPositive() throws Exception {
        fm.assertExec("1", "Sign(5)");
    }

    @Test
    void signOfLargePositive() throws Exception {
        fm.assertExec("1", "Sign(1000000)");
    }

    // --- Negative ---

    @Test
    void signOfNegative() throws Exception {
        fm.assertExec("-1", "Sign(-5)");
    }

    @Test
    void signOfLargeNegative() throws Exception {
        fm.assertExec("-1", "Sign(-1000000)");
    }

    // --- Zero ---

    @Test
    void signOfZero() throws Exception {
        fm.assertExec("0", "Sign(0)");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void signOfSymbolReturnsRaw() throws Exception {
        fm.assertExec("Sign(x)", "Sign(x)");
    }

    @Test
    void signOfRealReturnsRaw() throws Exception {
        fm.assertExec("Sign(2.5)", "Sign(2.5)");
    }

    @Test
    void signOfStringReturnsRaw() throws Exception {
        fm.assertExec("Sign(\"hello\")", "Sign(\"hello\")");
    }

    // --- Composed ---

    @Test
    void signOfExpression() throws Exception {
        fm.assertExec("-1", "Sign(3 - 10)");
    }

    @Test
    void signFromVariable() throws Exception {
        fm.assertExec("1", "{n = 42; Sign(n)}");
    }
}
