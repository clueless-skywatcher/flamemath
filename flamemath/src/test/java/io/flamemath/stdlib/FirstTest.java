package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;
import io.flamemath.expr.IntegerAtom;

import static org.junit.jupiter.api.Assertions.*;

class FirstTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Basic ---

    @Test
    void firstOfList() throws Exception {
        fm.assertExec("1", "First([1, 2, 3])");
    }

    @Test
    void firstOfSingleElement() throws Exception {
        fm.assertExec("5", "First([5])");
    }

    @Test
    void firstOfStrings() throws Exception {
        fm.assertExec("\"a\"", "First([\"a\", \"b\", \"c\"])");
    }

    @Test
    void firstOfNestedList() throws Exception {
        fm.assertExec("[1, 2]", "First([[1, 2], [3, 4]])");
    }

    // --- Empty list ---

    @Test
    void firstOfEmptyReturnsNull() throws Exception {
        fm.assertExec("Null", "First([])");
    }

    // --- Variables ---

    @Test
    void firstFromVariable() throws Exception {
        fm.assertExec("10", "{l = [10, 20, 30]; First(l)}");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void firstSymbolReturnsRaw() throws Exception {
        fm.assertExec("First(x)", "First(x)");
    }

    @Test
    void firstIntegerReturnsRaw() throws Exception {
        fm.assertExec("First(42)", "First(42)");
    }
}
