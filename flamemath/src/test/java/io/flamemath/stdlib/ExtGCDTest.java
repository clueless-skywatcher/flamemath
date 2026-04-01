package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class ExtGCDTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Two arguments ---

    @Test
    void twoArgsBasic() throws Exception {
        fm.assertExec("[4, [1, -1]]", "ExtGCD(12, 8)");
    }

    @Test
    void twoArgsCoprime() throws Exception {
        fm.assertExec("[1, [2, -1]]", "ExtGCD(7, 13)");
    }

    @Test
    void twoArgsOneDividesOther() throws Exception {
        fm.assertExec("[6, [1, 0]]", "ExtGCD(6, 18)");
    }

    @Test
    void twoArgsSame() throws Exception {
        fm.assertExec("[5, [0, 1]]", "ExtGCD(5, 5)");
    }

    @Test
    void twoArgsReversed() throws Exception {
        fm.assertExec("[4, [-1, 1]]", "ExtGCD(8, 12)");
    }

    // --- Zero handling ---

    @Test
    void zeroSecondArg() throws Exception {
        fm.assertExec("[5, [1, 0]]", "ExtGCD(5, 0)");
    }

    @Test
    void zeroFirstArg() throws Exception {
        fm.assertExec("[5, [0, 1]]", "ExtGCD(0, 5)");
    }

    // --- Negative arguments ---

    @Test
    void negativeFirst() throws Exception {
        fm.assertExec("[4, [-1, -1]]", "ExtGCD(-12, 8)");
    }

    @Test
    void negativeSecond() throws Exception {
        fm.assertExec("[4, [1, 1]]", "ExtGCD(12, -8)");
    }

    @Test
    void bothNegative() throws Exception {
        fm.assertExec("[4, [-1, 1]]", "ExtGCD(-12, -8)");
    }

    // --- Large numbers ---

    @Test
    void largeNumbers() throws Exception {
        fm.assertExec("[9, [-8, 1]]", "ExtGCD(123456789, 987654321)");
    }

    @Test
    void verify35and23() throws Exception {
        fm.assertExec("[1, [2, -3]]", "ExtGCD(35, 23)");
    }

    // --- Three arguments ---

    @Test
    void threeArgsClassic() throws Exception {
        fm.assertExec("[3, [-2, 1, 0]]", "ExtGCD(6, 15, 30)");
    }

    @Test
    void threeArgsCoprime() throws Exception {
        fm.assertExec("[1, [-14, 7, 1]]", "ExtGCD(6, 10, 15)");
    }

    @Test
    void threeArgsAllSame() throws Exception {
        fm.assertExec("[4, [0, 0, 1]]", "ExtGCD(4, 4, 4)");
    }

    @Test
    void threeArgs12_8_6() throws Exception {
        fm.assertExec("[2, [-1, 1, 1]]", "ExtGCD(12, 8, 6)");
    }

    @Test
    void threeArgs100_75_50() throws Exception {
        fm.assertExec("[25, [1, -1, 0]]", "ExtGCD(100, 75, 50)");
    }

    @Test
    void threeArgsCoprimePrimes() throws Exception {
        fm.assertExec("[1, [-3, 4, 0]]", "ExtGCD(17, 13, 11)");
    }

    @Test
    void threeArgs24_36_60() throws Exception {
        fm.assertExec("[12, [-1, 1, 0]]", "ExtGCD(24, 36, 60)");
    }

    @Test
    void threeArgsZeroFirst() throws Exception {
        fm.assertExec("[3, [0, -1, 1]]", "ExtGCD(0, 6, 9)");
    }

    // --- Four arguments ---

    @Test
    void fourArgs12_18_24_30() throws Exception {
        fm.assertExec("[6, [-1, 1, 0, 0]]", "ExtGCD(12, 18, 24, 30)");
    }

    @Test
    void fourArgs10_15_20_25() throws Exception {
        fm.assertExec("[5, [-1, 1, 0, 0]]", "ExtGCD(10, 15, 20, 25)");
    }

    @Test
    void fourArgsCoprimePrimes() throws Exception {
        fm.assertExec("[1, [-3, 2, 0, 0]]", "ExtGCD(7, 11, 13, 17)");
    }

    @Test
    void fourArgsDescending() throws Exception {
        fm.assertExec("[12, [0, 0, 0, 1]]", "ExtGCD(48, 36, 24, 12)");
    }

    // --- Five arguments ---

    @Test
    void fiveArgs6_10_15_21_35() throws Exception {
        fm.assertExec("[1, [-14, 7, 1, 0, 0]]", "ExtGCD(6, 10, 15, 21, 35)");
    }

    @Test
    void fiveArgs12_18_24_30_42() throws Exception {
        fm.assertExec("[6, [-1, 1, 0, 0, 0]]", "ExtGCD(12, 18, 24, 30, 42)");
    }

    @Test
    void fiveArgsCoprimePrimes() throws Exception {
        fm.assertExec("[1, [3, -2, 0, 0, 0]]", "ExtGCD(5, 7, 11, 13, 17)");
    }

    // --- Six arguments ---

    @Test
    void sixArgsLarge() throws Exception {
        fm.assertExec("[1, [-1872, 1248, 52, 1, 0, 0]]", "ExtGCD(30, 42, 70, 105, 210, 330)");
    }

    @Test
    void sixArgsCommonFactor() throws Exception {
        fm.assertExec("[4, [-1, 1, 0, 0, 0, 0]]", "ExtGCD(8, 12, 16, 20, 24, 28)");
    }

    // --- Symbolic returns raw ---

    @Test
    void symbolicReturnsRaw() throws Exception {
        fm.assertExec("ExtGCD(x, 5)", "ExtGCD(x, 5)");
    }

    @Test
    void symbolicSecondReturnsRaw() throws Exception {
        fm.assertExec("ExtGCD(5, y)", "ExtGCD(5, y)");
    }
}
