package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class ZipTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Two lists ---

    @Test
    void zipTwoLists() throws Exception {
        fm.assertExec("[[1, 3], [2, 4]]", "Zip([1, 2], [3, 4])");
    }

    @Test
    void zipSingleElementLists() throws Exception {
        fm.assertExec("[[1, 2]]", "Zip([1], [2])");
    }

    @Test
    void zipThreeElements() throws Exception {
        fm.assertExec("[[1, 4], [2, 5], [3, 6]]", "Zip([1, 2, 3], [4, 5, 6])");
    }

    // --- Three or more lists ---

    @Test
    void zipThreeLists() throws Exception {
        fm.assertExec("[[1, 4, 7], [2, 5, 8], [3, 6, 9]]",
                "Zip([1, 2, 3], [4, 5, 6], [7, 8, 9])");
    }

    @Test
    void zipFourLists() throws Exception {
        fm.assertExec("[[1, 2, 3, 4]]",
                "Zip([1], [2], [3], [4])");
    }

    // --- Single list ---

    @Test
    void zipSingleList() throws Exception {
        fm.assertExec("[[1], [2], [3]]", "Zip([1, 2, 3])");
    }

    // --- Empty lists ---

    @Test
    void zipEmptyLists() throws Exception {
        fm.assertExec("[]", "Zip([], [])");
    }

    @Test
    void zipEmptyAmongNonEmpty() throws Exception {
        fm.assertExec("[]", "Zip([1, 2], [], [3, 4])");
    }

    // --- Unequal lengths (truncates to shortest) ---

    @Test
    void zipUnequalLengthsTruncates() throws Exception {
        fm.assertExec("[[1, 4], [2, 5]]", "Zip([1, 2, 3], [4, 5])");
    }

    @Test
    void zipUnequalLengthsThreeLists() throws Exception {
        fm.assertExec("[[1, 4, 7]]", "Zip([1, 2, 3], [4, 5], [7])");
    }

    // --- Mixed types ---

    @Test
    void zipStringsAndIntegers() throws Exception {
        fm.assertExec("[[\"a\", 1], [\"b\", 2]]", "Zip([\"a\", \"b\"], [1, 2])");
    }

    @Test
    void zipWithNestedLists() throws Exception {
        fm.assertExec("[[[1, 2], 3], [[4, 5], 6]]", "Zip([[1, 2], [4, 5]], [3, 6])");
    }

    // --- Variables ---

    @Test
    void zipFromVariables() throws Exception {
        fm.assertExec("[[1, 3], [2, 4]]", "{a = [1, 2]; b = [3, 4]; Zip(a, b)}");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void zipSymbolsReturnRaw() throws Exception {
        fm.assertExec("Zip(x, y)", "Zip(x, y)");
    }

    @Test
    void zipFirstArgNotListReturnsRaw() throws Exception {
        fm.assertExec("Zip(42, [1, 2])", "Zip(42, [1, 2])");
    }

    @Test
    void zipSecondArgNotListReturnsRaw() throws Exception {
        fm.assertExec("Zip([1, 2], 42)", "Zip([1, 2], 42)");
    }

    @Test
    void zipMiddleArgNotListReturnsRaw() throws Exception {
        fm.assertExec("Zip([1, 2], 42, [3, 4])", "Zip([1, 2], 42, [3, 4])");
    }

    @Test
    void zipSingleSymbolReturnsRaw() throws Exception {
        fm.assertExec("Zip(x)", "Zip(x)");
    }
}
