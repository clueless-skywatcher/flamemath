package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class ZipTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    @Test
    void zipTwoLists() throws Exception {
        fm.assertExec("[[1, 3], [2, 4]]", "Zip([1, 2], [3, 4])");
    }

    @Test
    void zipSingleElementLists() throws Exception {
        fm.assertExec("[[1, 2]]", "Zip([1], [2])");
    }

    @Test
    void zipEmptyLists() throws Exception {
        fm.assertExec("[]", "Zip([], [])");
    }

    @Test
    void zipUnequalLengthsTruncates() throws Exception {
        fm.assertExec("[[1, 4], [2, 5]]", "Zip([1, 2, 3], [4, 5])");
    }

    @Test
    void zipStringsAndIntegers() throws Exception {
        fm.assertExec("[[\"a\", 1], [\"b\", 2]]", "Zip([\"a\", \"b\"], [1, 2])");
    }

    @Test
    void zipThreeElements() throws Exception {
        fm.assertExec("[[1, 4], [2, 5], [3, 6]]", "Zip([1, 2, 3], [4, 5, 6])");
    }

    @Test
    void zipFromVariables() throws Exception {
        fm.assertExec("[[1, 3], [2, 4]]", "{a = [1, 2]}; {b = [3, 4]}; Zip(a, b)}");
    }

    @Test
    void zipWithNestedLists() throws Exception {
        fm.assertExec("[[[1, 2], 3], [[4, 5], 6]]", "Zip([[1, 2], [4, 5]], [3, 6])");
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
}
