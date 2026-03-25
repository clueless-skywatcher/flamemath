package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class TakeTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Positive n (take first n) ---

    @Test
    void takeFirstTwo() throws Exception {
        fm.assertExec("[1, 2]", "Take([1, 2, 3, 4], 2)");
    }

    @Test
    void takeFirstOne() throws Exception {
        fm.assertExec("[1]", "Take([1, 2, 3], 1)");
    }

    @Test
    void takeZero() throws Exception {
        fm.assertExec("[]", "Take([1, 2, 3], 0)");
    }

    @Test
    void takeAll() throws Exception {
        fm.assertExec("[1, 2, 3]", "Take([1, 2, 3], 3)");
    }

    @Test
    void takeFromSingleElement() throws Exception {
        fm.assertExec("[5]", "Take([5], 1)");
    }

    // --- Negative n (take last n) ---

    @Test
    void takeLastTwo() throws Exception {
        fm.assertExec("[3, 4]", "Take([1, 2, 3, 4], -2)");
    }

    @Test
    void takeLastOne() throws Exception {
        fm.assertExec("[3]", "Take([1, 2, 3], -1)");
    }

    @Test
    void takeLastAll() throws Exception {
        fm.assertExec("[1, 2, 3]", "Take([1, 2, 3], -3)");
    }

    // --- Empty list ---

    @Test
    void takeFromEmpty() throws Exception {
        fm.assertExec("[]", "Take([], 0)");
    }

    // --- Mixed types ---

    @Test
    void takeStrings() throws Exception {
        fm.assertExec("[\"a\", \"b\"]", "Take([\"a\", \"b\", \"c\"], 2)");
    }

    // --- Variables ---

    @Test
    void takeFromVariable() throws Exception {
        fm.assertExec("[1, 2]", "{l = [1, 2, 3, 4]; Take(l, 2)}");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void takeSymbolListReturnsRaw() throws Exception {
        fm.assertExec("Take(x, 2)", "Take(x, 2)");
    }

    @Test
    void takeSymbolNReturnsRaw() throws Exception {
        fm.assertExec("Take([1, 2], n)", "Take([1, 2], n)");
    }

    @Test
    void takeBothSymbolicReturnsRaw() throws Exception {
        fm.assertExec("Take(x, n)", "Take(x, n)");
    }
}
