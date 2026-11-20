package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class DeleteAllTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Basic delete all ---

    @Test
    void deleteAllSingleOccurrence() throws Exception {
        fm.assertExec("[1, 3]", "{l = [1, 2, 3]}; DeleteAll(l, 2); l}");
    }

    @Test
    void deleteAllMultipleOccurrences() throws Exception {
        fm.assertExec("[1, 3]", "{l = [1, 2, 3, 2]}; DeleteAll(l, 2); l}");
    }

    @Test
    void deleteAllConsecutiveOccurrences() throws Exception {
        fm.assertExec("[1, 3]", "{l = [1, 2, 2, 2, 3]}; DeleteAll(l, 2); l}");
    }

    // --- Element not found ---

    @Test
    void deleteAllAbsentElement() throws Exception {
        fm.assertExec("[1, 2, 3]", "{l = [1, 2, 3]}; DeleteAll(l, 5); l}");
    }

    @Test
    void deleteAllFromEmptyList() throws Exception {
        fm.assertExec("[]", "{l = []}; DeleteAll(l, 1); l}");
    }

    // --- All elements removed ---

    @Test
    void deleteAllLeavesEmpty() throws Exception {
        fm.assertExec("[]", "{l = [2, 2, 2]}; DeleteAll(l, 2); l}");
    }

    // --- Returns Null ---

    @Test
    void returnsNull() throws Exception {
        fm.assertExec("Null", "DeleteAll([1, 2, 3], 2)");
    }

    // --- Symbolic elements ---

    @Test
    void deleteAllSymbols() throws Exception {
        fm.assertExec("[y, z]", "{l = [x, y, x, z, x]}; DeleteAll(l, x); l}");
    }

    // --- Unevaluated on non-list ---

    @Test
    void unevaluatedOnNonList() throws Exception {
        fm.assertExec("DeleteAll(5, 1)", "DeleteAll(5, 1)");
    }
}
