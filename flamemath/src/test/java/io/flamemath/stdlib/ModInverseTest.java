package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class ModInverseTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Basic cases ---

    @Test
    void basicInverse3Mod7() throws Exception {
        fm.assertExec("5", "ModInverse(3, 7)");
    }

    @Test
    void basicInverse2Mod5() throws Exception {
        fm.assertExec("3", "ModInverse(2, 5)");
    }

    @Test
    void basicInverse1ModAnything() throws Exception {
        fm.assertExec("1", "ModInverse(1, 13)");
    }

    @Test
    void inverseOfMinusOneMod() throws Exception {
        fm.assertExec("6", "ModInverse(-1, 7)");
    }

    // --- Negative a ---

    @Test
    void negativeA() throws Exception {
        fm.assertExec("2", "ModInverse(-3, 7)");
    }

    // --- Larger moduli ---

    @Test
    void inverse7Mod11() throws Exception {
        fm.assertExec("8", "ModInverse(7, 11)");
    }

    @Test
    void inverse10Mod17() throws Exception {
        fm.assertExec("12", "ModInverse(10, 17)");
    }

    @Test
    void largeCoprime() throws Exception {
        fm.assertExec("23", "ModInverse(17, 39)");
    }

    // --- Edge: a > m ---

    @Test
    void aGreaterThanM() throws Exception {
        fm.assertExec("5", "ModInverse(10, 7)");
    }

    // --- No inverse exists (not coprime) ---

    @Test
    void notCoprimeReturnsRaw() throws Exception {
        fm.assertExec("ModInverse(2, 4)", "ModInverse(2, 4)");
    }

    @Test
    void notCoprimeReturnsRaw2() throws Exception {
        fm.assertExec("ModInverse(6, 9)", "ModInverse(6, 9)");
    }

    // --- Invalid inputs return raw ---

    @Test
    void zeroModulusReturnsRaw() throws Exception {
        fm.assertExec("ModInverse(3, 0)", "ModInverse(3, 0)");
    }

    @Test
    void negativeModulusReturnsRaw() throws Exception {
        fm.assertExec("ModInverse(3, -5)", "ModInverse(3, -5)");
    }

    @Test
    void symbolicFirstReturnsRaw() throws Exception {
        fm.assertExec("ModInverse(x, 7)", "ModInverse(x, 7)");
    }

    @Test
    void symbolicSecondReturnsRaw() throws Exception {
        fm.assertExec("ModInverse(3, y)", "ModInverse(3, y)");
    }

    @Test
    void bothSymbolicReturnsRaw() throws Exception {
        fm.assertExec("ModInverse(x, y)", "ModInverse(x, y)");
    }
}
