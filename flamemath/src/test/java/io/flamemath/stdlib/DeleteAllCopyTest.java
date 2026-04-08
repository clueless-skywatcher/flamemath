package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class DeleteAllCopyTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Basic delete all copy ---

    @Test
    void deleteAllCopySingleOccurrence() throws Exception {
        fm.assertExec("[1, 3]", "DeleteAllCopy([1, 2, 3], 2)");
    }

    @Test
    void deleteAllCopyMultipleOccurrences() throws Exception {
        fm.assertExec("[1, 3]", "DeleteAllCopy([1, 2, 3, 2], 2)");
    }

    @Test
    void deleteAllCopyConsecutiveOccurrences() throws Exception {
        fm.assertExec("[1, 3]", "DeleteAllCopy([1, 2, 2, 2, 3], 2)");
    }

    // --- Element not found ---

    @Test
    void deleteAllCopyAbsentElement() throws Exception {
        fm.assertExec("[1, 2, 3]", "DeleteAllCopy([1, 2, 3], 5)");
    }

    @Test
    void deleteAllCopyFromEmptyList() throws Exception {
        fm.assertExec("[]", "DeleteAllCopy([], 1)");
    }

    // --- All elements removed ---

    @Test
    void deleteAllCopyLeavesEmpty() throws Exception {
        fm.assertExec("[]", "DeleteAllCopy([2, 2, 2], 2)");
    }

    // --- Original list unchanged ---

    @Test
    void originalListUnchanged() throws Exception {
        fm.assertExec("[1, 2, 3, 2]", "{l = [1, 2, 3, 2]}; DeleteAllCopy(l, 2); l}");
    }

    @Test
    void returnsNewList() throws Exception {
        fm.assertExec("[1, 3]", "{l = [1, 2, 3, 2]}; r = DeleteAllCopy(l, 2); r}");
    }

    // --- Symbolic elements ---

    @Test
    void deleteAllCopySymbols() throws Exception {
        fm.assertExec("[y, z]", "DeleteAllCopy([x, y, x, z, x], x)");
    }

    // --- Unevaluated on non-list ---

    @Test
    void unevaluatedOnNonList() throws Exception {
        fm.assertExec("DeleteAllCopy(5, 1)", "DeleteAllCopy(5, 1)");
    }
}
