package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class FlattenTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    @Test
    void flattenNestedLists() throws Exception {
        fm.assertExec("[1, 2, 3, 4]", "Flatten([[1, 2], [3, 4]])");
    }

    @Test
    void flattenDeeplyNested() throws Exception {
        fm.assertExec("[1, 2, 3, 4]", "Flatten([[1, [2]], [3, [4]]])");
    }

    @Test
    void flattenAlreadyFlat() throws Exception {
        fm.assertExec("[1, 2, 3]", "Flatten([1, 2, 3])");
    }

    @Test
    void flattenEmptyList() throws Exception {
        fm.assertExec("[]", "Flatten([])");
    }

    @Test
    void flattenEmptyInnerLists() throws Exception {
        fm.assertExec("[1, 2]", "Flatten([[], [1], [], [2], []])");
    }

    @Test
    void flattenSingleElement() throws Exception {
        fm.assertExec("[42]", "Flatten([42])");
    }

    @Test
    void flattenTripleNested() throws Exception {
        fm.assertExec("[1, 2, 3]", "Flatten([[[1]], [[2]], [[3]]])");
    }

    @Test
    void flattenMixedDepths() throws Exception {
        fm.assertExec("[1, 2, 3, 4, 5]", "Flatten([1, [2, 3], [[4, 5]]])");
    }

    @Test
    void flattenStrings() throws Exception {
        fm.assertExec("[\"a\", \"b\", \"c\"]", "Flatten([[\"a\"], [\"b\", \"c\"]])");
    }

    @Test
    void flattenFromVariable() throws Exception {
        fm.assertExec("[1, 2, 3, 4]", "{l = [[1, 2], [3, 4]]}; Flatten(l)}");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void flattenSymbolReturnsRaw() throws Exception {
        fm.assertExec("Flatten(x)", "Flatten(x)");
    }

    @Test
    void flattenIntegerReturnsRaw() throws Exception {
        fm.assertExec("Flatten(42)", "Flatten(42)");
    }

    @Test
    void flattenStringReturnsRaw() throws Exception {
        fm.assertExec("Flatten(\"hello\")", "Flatten(\"hello\")");
    }
}
