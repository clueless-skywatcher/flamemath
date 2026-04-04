package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class ChineseRemainderTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Two congruences ---

    @Test
    void twoCongruencesBasic() throws Exception {
        fm.assertExec("8", "ChineseRemainder([2, 3], [3, 5])");
    }

    @Test
    void twoCongruences1Mod3_2Mod5() throws Exception {
        fm.assertExec("7", "ChineseRemainder([1, 2], [3, 5])");
    }

    @Test
    void twoCongruencesAllZero() throws Exception {
        fm.assertExec("0", "ChineseRemainder([0, 0], [3, 5])");
    }

    @Test
    void twoCongruencesRemaindersLargerThanModuli() throws Exception {
        fm.assertExec("13", "ChineseRemainder([10, 20], [3, 7])");
    }

    // --- Three congruences ---

    @Test
    void threeCongruencesClassic() throws Exception {
        fm.assertExec("23", "ChineseRemainder([2, 3, 2], [3, 5, 7])");
    }

    @Test
    void threeCongruencesAllOnes() throws Exception {
        fm.assertExec("1", "ChineseRemainder([1, 1, 1], [2, 3, 5])");
    }

    @Test
    void threeCongruencesAllZero() throws Exception {
        fm.assertExec("0", "ChineseRemainder([0, 0, 0], [3, 5, 7])");
    }

    // --- Four congruences ---

    @Test
    void fourCongruences() throws Exception {
        fm.assertExec("4216", "ChineseRemainder([1, 2, 3, 4], [5, 7, 11, 13])");
    }

    // --- Single congruence ---

    @Test
    void singleCongruence() throws Exception {
        fm.assertExec("3", "ChineseRemainder([3], [7])");
    }

    // --- Large moduli ---

    @Test
    void largeModuli() throws Exception {
        fm.assertExec("233341700024", "ChineseRemainder([1, 2], [1000003, 1000033])");
    }

    // --- Not pairwise coprime returns raw ---

    @Test
    void notCoprimeReturnsRaw() throws Exception {
        fm.assertExec("ChineseRemainder([1, 2], [4, 6])", "ChineseRemainder([1, 2], [4, 6])");
    }

    // --- Mismatched lengths returns raw ---

    @Test
    void mismatchedLengthsReturnsRaw() throws Exception {
        fm.assertExec("ChineseRemainder([1, 2], [3])", "ChineseRemainder([1, 2], [3])");
    }

    // --- Non-integer modulus returns raw ---

    @Test
    void nonIntegerModulusReturnsRaw() throws Exception {
        fm.assertExec("ChineseRemainder([1, 2], [3, x])", "ChineseRemainder([1, 2], [3, x])");
    }

    // --- Non-integer remainder returns raw ---

    @Test
    void nonIntegerRemainderReturnsRaw() throws Exception {
        fm.assertExec("ChineseRemainder([x, 2], [3, 5])", "ChineseRemainder([x, 2], [3, 5])");
    }

    // --- Non-positive modulus returns raw ---

    @Test
    void zeroModulusReturnsRaw() throws Exception {
        fm.assertExec("ChineseRemainder([1, 2], [0, 5])", "ChineseRemainder([1, 2], [0, 5])");
    }

    @Test
    void negativeModulusReturnsRaw() throws Exception {
        fm.assertExec("ChineseRemainder([1, 2], [-3, 5])", "ChineseRemainder([1, 2], [-3, 5])");
    }

    // --- Non-list arguments return raw ---

    @Test
    void nonListReturnsRaw() throws Exception {
        fm.assertExec("ChineseRemainder(2, 3)", "ChineseRemainder(2, 3)");
    }
}
