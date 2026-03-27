package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class DropTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Positive n (drop first n) ---

    @Test
    void dropFirstTwo() throws Exception {
        fm.assertExec("[3, 4]", "Drop([1, 2, 3, 4], 2)");
    }

    @Test
    void dropFirstOne() throws Exception {
        fm.assertExec("[2, 3]", "Drop([1, 2, 3], 1)");
    }

    @Test
    void dropZero() throws Exception {
        fm.assertExec("[1, 2, 3]", "Drop([1, 2, 3], 0)");
    }

    @Test
    void dropAll() throws Exception {
        fm.assertExec("[]", "Drop([1, 2, 3], 3)");
    }

    @Test
    void dropFromSingleElement() throws Exception {
        fm.assertExec("[]", "Drop([5], 1)");
    }

    // --- Negative n (drop last n) ---

    @Test
    void dropLastTwo() throws Exception {
        fm.assertExec("[1, 2]", "Drop([1, 2, 3, 4], -2)");
    }

    @Test
    void dropLastOne() throws Exception {
        fm.assertExec("[1, 2]", "Drop([1, 2, 3], -1)");
    }

    @Test
    void dropLastAll() throws Exception {
        fm.assertExec("[]", "Drop([1, 2, 3], -3)");
    }

    // --- Empty list ---

    @Test
    void dropFromEmpty() throws Exception {
        fm.assertExec("[]", "Drop([], 0)");
    }

    // --- Mixed types ---

    @Test
    void dropStrings() throws Exception {
        fm.assertExec("[\"c\"]", "Drop([\"a\", \"b\", \"c\"], 2)");
    }

    // --- Variables ---

    @Test
    void dropFromVariable() throws Exception {
        fm.assertExec("[3, 4]", "{l = [1, 2, 3, 4]; Drop(l, 2)}");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void dropSymbolListReturnsRaw() throws Exception {
        fm.assertExec("Drop(x, 2)", "Drop(x, 2)");
    }

    @Test
    void dropSymbolNReturnsRaw() throws Exception {
        fm.assertExec("Drop([1, 2], n)", "Drop([1, 2], n)");
    }

    @Test
    void dropBothSymbolicReturnsRaw() throws Exception {
        fm.assertExec("Drop(x, n)", "Drop(x, n)");
    }
}
