package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class ReverseTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    @Test
    void reverseMultipleElements() throws Exception {
        fm.assertExec("[3, 2, 1]", "Reverse([1, 2, 3])");
    }

    @Test
    void reverseSingleElement() throws Exception {
        fm.assertExec("[42]", "Reverse([42])");
    }

    @Test
    void reverseEmptyList() throws Exception {
        fm.assertExec("[]", "Reverse([])");
    }

    @Test
    void reverseTwoElements() throws Exception {
        fm.assertExec("[2, 1]", "Reverse([1, 2])");
    }

    @Test
    void reverseStrings() throws Exception {
        fm.assertExec("[\"c\", \"b\", \"a\"]", "Reverse([\"a\", \"b\", \"c\"])");
    }

    @Test
    void reverseMixedTypes() throws Exception {
        fm.assertExec("[True, \"b\", 1]", "Reverse([1, \"b\", True])");
    }

    @Test
    void reverseNestedLists() throws Exception {
        fm.assertExec("[[3, 4], [1, 2]]", "Reverse([[1, 2], [3, 4]])");
    }

    @Test
    void reversePreservesInnerLists() throws Exception {
        fm.assertExec("[[2, 1], [4, 3]]", "Reverse([[4, 3], [2, 1]])");
    }

    @Test
    void reverseFromVariable() throws Exception {
        fm.assertExec("[3, 2, 1]", "{l = [1, 2, 3]}; Reverse(l)}");
    }

    @Test
    void doubleReverseIsIdentity() throws Exception {
        fm.assertExec("[1, 2, 3]", "Reverse(Reverse([1, 2, 3]))");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void reverseSymbolReturnsRaw() throws Exception {
        fm.assertExec("Reverse(x)", "Reverse(x)");
    }

    @Test
    void reverseIntegerReturnsRaw() throws Exception {
        fm.assertExec("Reverse(42)", "Reverse(42)");
    }

    @Test
    void reverseStringReturnsRaw() throws Exception {
        fm.assertExec("Reverse(\"hello\")", "Reverse(\"hello\")");
    }
}
