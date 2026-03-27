package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class LastTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Basic ---

    @Test
    void lastOfList() throws Exception {
        fm.assertExec("3", "Last([1, 2, 3])");
    }

    @Test
    void lastOfSingleElement() throws Exception {
        fm.assertExec("5", "Last([5])");
    }

    @Test
    void lastOfStrings() throws Exception {
        fm.assertExec("\"c\"", "Last([\"a\", \"b\", \"c\"])");
    }

    @Test
    void lastOfNestedList() throws Exception {
        fm.assertExec("[3, 4]", "Last([[1, 2], [3, 4]])");
    }

    // --- Empty list ---

    @Test
    void lastOfEmptyReturnsNull() throws Exception {
        fm.assertExec("Null", "Last([])");
    }

    // --- Variables ---

    @Test
    void lastFromVariable() throws Exception {
        fm.assertExec("30", "{l = [10, 20, 30]; Last(l)}");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void lastSymbolReturnsRaw() throws Exception {
        fm.assertExec("Last(x)", "Last(x)");
    }

    @Test
    void lastIntegerReturnsRaw() throws Exception {
        fm.assertExec("Last(42)", "Last(42)");
    }
}
