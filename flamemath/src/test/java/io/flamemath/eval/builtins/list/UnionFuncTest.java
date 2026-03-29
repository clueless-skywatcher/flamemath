package io.flamemath.eval.builtins.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

class UnionFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    void name() {
        assertEquals("Union", new UnionFunc().name());
    }

    // --- Two lists ---

    @Test
    void unionTwoLists() throws Exception {
        fm.assertExec("[1, 2, 3, 4]", "Union([1, 2, 3], [2, 3, 4])");
    }

    @Test
    void unionNoOverlap() throws Exception {
        fm.assertExec("[1, 2, 3, 4]", "Union([1, 2], [3, 4])");
    }

    @Test
    void unionIdenticalLists() throws Exception {
        fm.assertExec("[1, 2, 3]", "Union([1, 2, 3], [1, 2, 3])");
    }

    @Test
    void unionWithEmpty() throws Exception {
        fm.assertExec("[1, 2]", "Union([1, 2], [])");
    }

    @Test
    void unionEmptyWithNonEmpty() throws Exception {
        fm.assertExec("[1, 2]", "Union([], [1, 2])");
    }

    @Test
    void unionTwoEmpty() throws Exception {
        fm.assertExec("[]", "Union([], [])");
    }

    // --- Duplicates ---

    @Test
    void unionRemovesDuplicatesWithinList() throws Exception {
        fm.assertExec("[1, 2, 3]", "Union([1, 1, 2], [2, 3])");
    }

    @Test
    void unionRemovesDuplicatesAcrossLists() throws Exception {
        fm.assertExec("[1, 2, 3]", "Union([1, 2], [2, 3])");
    }

    // --- Three or more lists ---

    @Test
    void unionThreeLists() throws Exception {
        fm.assertExec("[1, 2, 3, 4, 5]", "Union([1, 2], [3, 4], [4, 5])");
    }

    @Test
    void unionFourLists() throws Exception {
        fm.assertExec("[1, 2, 3, 4]", "Union([1], [2], [3], [4])");
    }

    // --- Nested lists ---

    @Test
    void unionNestedLists() throws Exception {
        fm.assertExec("[[1, 2], [3, 4], [5, 6]]", "Union([[1, 2], [3, 4]], [[3, 4], [5, 6]])");
    }

    // --- Mixed types ---

    @Test
    void unionMixedTypes() throws Exception {
        fm.assertExec("[1, \"a\", True, False]", "Union([1, \"a\", True], [1, \"a\", False])");
    }

    // --- Preserves order ---

    @Test
    void unionPreservesFirstOccurrenceOrder() throws Exception {
        fm.assertExec("[3, 1, 2]", "Union([3, 1], [1, 2])");
    }

    // --- From variables ---

    @Test
    void unionFromVariables() throws Exception {
        fm.assertExec("[1, 2, 3, 4]", "Union({ a = [1, 2, 3]; a }, [2, 3, 4])");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void unionNonListReturnsRaw() throws Exception {
        fm.assertExec("Union(x, [1, 2])", "Union(x, [1, 2])");
    }

    @Test
    void unionSecondNonListReturnsRaw() throws Exception {
        fm.assertExec("Union([1, 2], x)", "Union([1, 2], x)");
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Union()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Union([1])"));
    }
}
