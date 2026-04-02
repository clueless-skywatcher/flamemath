package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class CoprimeTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Two arguments ---

    @Test
    void twoArgsCoprime() throws Exception {
        fm.assertExec("True", "Coprime(3, 5)");
    }

    @Test
    void twoArgsNotCoprime() throws Exception {
        fm.assertExec("False", "Coprime(6, 9)");
    }

    @Test
    void twoArgsCoprimeLarge() throws Exception {
        fm.assertExec("True", "Coprime(17, 31)");
    }

    @Test
    void twoArgsShareFactor() throws Exception {
        fm.assertExec("False", "Coprime(12, 8)");
    }

    @Test
    void oneAndAnything() throws Exception {
        fm.assertExec("True", "Coprime(1, 100)");
    }

    // --- Three arguments ---

    @Test
    void threeArgsCoprime() throws Exception {
        fm.assertExec("True", "Coprime(3, 5, 7)");
    }

    @Test
    void threeArgsNotCoprime() throws Exception {
        fm.assertExec("False", "Coprime(3, 5, 6)");
    }

    @Test
    void threeArgsAllShareFactor() throws Exception {
        fm.assertExec("False", "Coprime(6, 10, 15)");
    }

    // --- More arguments ---

    @Test
    void fourArgsCoprime() throws Exception {
        fm.assertExec("True", "Coprime(2, 3, 5, 7)");
    }

    @Test
    void fiveArgsNotCoprime() throws Exception {
        fm.assertExec("False", "Coprime(2, 3, 5, 7, 14)");
    }

    // --- Single argument ---

    @Test
    void singleArg() throws Exception {
        fm.assertExec("True", "Coprime(42)");
    }

    // --- Symbolic returns raw ---

    @Test
    void symbolicReturnsRaw() throws Exception {
        fm.assertExec("Coprime(x, 5)", "Coprime(x, 5)");
    }

    @Test
    void symbolicSecondReturnsRaw() throws Exception {
        fm.assertExec("Coprime(3, y)", "Coprime(3, y)");
    }
}
