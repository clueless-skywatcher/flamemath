package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class CountTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Basic counting ---

    @Test
    void countSingleOccurrence() throws Exception {
        fm.assertExec("1", "Count([1, 2, 3], 2)");
    }

    @Test
    void countMultipleOccurrences() throws Exception {
        fm.assertExec("2", "Count([1, 2, 1, 3], 1)");
    }

    @Test
    void countAllSame() throws Exception {
        fm.assertExec("3", "Count([5, 5, 5], 5)");
    }

    // --- No matches ---

    @Test
    void countNoOccurrences() throws Exception {
        fm.assertExec("0", "Count([1, 2, 3], 9)");
    }

    @Test
    void countEmptyList() throws Exception {
        fm.assertExec("0", "Count([], 1)");
    }

    // --- Different types ---

    @Test
    void countStrings() throws Exception {
        fm.assertExec("2", "Count([\"a\", \"b\", \"a\", \"c\"], \"a\")");
    }

    @Test
    void countBooleans() throws Exception {
        fm.assertExec("2", "Count([True, False, True], True)");
    }

    // --- Nested lists ---

    @Test
    void countNestedList() throws Exception {
        fm.assertExec("2", "Count([[1, 2], [3, 4], [1, 2]], [1, 2])");
    }

    // --- Variables ---

    @Test
    void countFromVariable() throws Exception {
        fm.assertExec("3", "{l = [1, 2, 1, 3, 1]; Count(l, 1)}");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void countSymbolListReturnsRaw() throws Exception {
        fm.assertExec("Count(x, 1)", "Count(x, 1)");
    }

    @Test
    void countIntegerReturnsRaw() throws Exception {
        fm.assertExec("Count(42, 1)", "Count(42, 1)");
    }
}
