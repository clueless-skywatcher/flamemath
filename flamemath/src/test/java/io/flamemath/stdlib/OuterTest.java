package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class OuterTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Basic functionality ---

    @Test
    void outerWithAdd() throws Exception {
        fm.assertExec("[[3, 4], [4, 5]]", "Outer((a, b) => a + b, [1, 2], [2, 3])");
    }

    @Test
    void outerWithMul() throws Exception {
        fm.assertExec("[[3, 4], [6, 8]]", "Outer((a, b) => a * b, [1, 2], [3, 4])");
    }

    @Test
    void outerWithBuiltinSymbol() throws Exception {
        fm.assertExec("[[2, 3], [3, 4]]", "Outer(Add, [1, 2], [1, 2])");
    }

    // --- Dimensions ---

    @Test
    void outerSingleBySingle() throws Exception {
        fm.assertExec("[[6]]", "Outer((a, b) => a * b, [2], [3])");
    }

    @Test
    void outerOneByThree() throws Exception {
        fm.assertExec("[[2, 3, 4]]", "Outer((a, b) => a + b, [1], [1, 2, 3])");
    }

    @Test
    void outerThreeByOne() throws Exception {
        fm.assertExec("[[2], [3], [4]]", "Outer((a, b) => a + b, [1, 2, 3], [1])");
    }

    @Test
    void outerThreeByThree() throws Exception {
        fm.assertExec("[[1, 2, 3], [2, 4, 6], [3, 6, 9]]",
                "Outer((a, b) => a * b, [1, 2, 3], [1, 2, 3])");
    }

    // --- Empty lists ---

    @Test
    void outerFirstEmpty() throws Exception {
        fm.assertExec("[]", "Outer((a, b) => a + b, [], [1, 2])");
    }

    @Test
    void outerSecondEmpty() throws Exception {
        fm.assertExec("[[], []]", "Outer((a, b) => a + b, [1, 2], [])");
    }

    // --- Mixed types ---

    @Test
    void outerStringsAndIntegers() throws Exception {
        fm.assertExec("[[List(\"a\", 1), List(\"a\", 2)], [List(\"b\", 1), List(\"b\", 2)]]",
                "Outer((a, b) => [a, b], [\"a\", \"b\"], [1, 2])");
    }

    // --- Custom function ---

    @Test
    void outerWithComparison() throws Exception {
        fm.assertExec("[[True, True], [False, False]]",
                "Outer((a, b) => a < b, [1, 2], [2, 2])");
    }

    // --- Variable binding ---

    @Test
    void outerFromVariables() throws Exception {
        fm.assertExec("[[2, 3], [3, 4]]",
                "{a = [1, 2]}; {b = [1, 2]}; Outer((x, y) => x + y, a, b)}");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void outerFirstArgNotListReturnsRaw() throws Exception {
        fm.assertExec("Outer(Add, x, [1, 2])", "Outer(Add, x, [1, 2])");
    }

    @Test
    void outerSecondArgNotListReturnsRaw() throws Exception {
        fm.assertExec("Outer(Add, [1, 2], y)", "Outer(Add, [1, 2], y)");
    }

    @Test
    void outerBothSymbolicReturnsRaw() throws Exception {
        fm.assertExec("Outer(Add, x, y)", "Outer(Add, x, y)");
    }
}
