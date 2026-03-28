package io.flamemath.eval.builtins.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

class IntersectionFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    void name() {
        assertEquals("Intersection", new IntersectionFunc().name());
    }

    // --- Two lists ---

    @Test
    void intersectionTwoLists() throws Exception {
        fm.assertExec("[2, 3]", "Intersection([1, 2, 3], [2, 3, 4])");
    }

    @Test
    void intersectionNoOverlap() throws Exception {
        fm.assertExec("[]", "Intersection([1, 2], [3, 4])");
    }

    @Test
    void intersectionIdenticalLists() throws Exception {
        fm.assertExec("[1, 2, 3]", "Intersection([1, 2, 3], [1, 2, 3])");
    }

    @Test
    void intersectionWithEmpty() throws Exception {
        fm.assertExec("[]", "Intersection([1, 2], [])");
    }

    @Test
    void intersectionEmptyWithNonEmpty() throws Exception {
        fm.assertExec("[]", "Intersection([], [1, 2])");
    }

    @Test
    void intersectionTwoEmpty() throws Exception {
        fm.assertExec("[]", "Intersection([], [])");
    }

    // --- Duplicates ---

    @Test
    void intersectionRemovesDuplicates() throws Exception {
        fm.assertExec("[1]", "Intersection([1, 1, 2], [1, 3])");
    }

    @Test
    void intersectionDuplicatesInBoth() throws Exception {
        fm.assertExec("[1, 2]", "Intersection([1, 1, 2, 2], [2, 1, 1])");
    }

    // --- Three or more lists ---

    @Test
    void intersectionThreeLists() throws Exception {
        fm.assertExec("[3]", "Intersection([1, 2, 3], [2, 3, 4], [3, 4, 5])");
    }

    @Test
    void intersectionThreeListsNoOverlap() throws Exception {
        fm.assertExec("[]", "Intersection([1, 2], [3, 4], [5, 6])");
    }

    // --- Nested lists ---

    @Test
    void intersectionNestedLists() throws Exception {
        fm.assertExec("[[1, 2]]", "Intersection([[1, 2], [3, 4]], [[1, 2], [5, 6]])");
    }

    // --- Mixed types ---

    @Test
    void intersectionMixedTypes() throws Exception {
        fm.assertExec("[1, \"a\"]", "Intersection([1, \"a\", True], [1, \"a\", False])");
    }

    // --- From variables ---

    @Test
    void intersectionFromVariables() throws Exception {
        fm.assertExec("[2, 3]", "{ a = [1, 2, 3]; b = [2, 3, 4]; Intersection(a, b) }");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void intersectionNonListReturnsRaw() throws Exception {
        fm.assertExec("Intersection(x, [1, 2])", "Intersection(x, [1, 2])");
    }

    @Test
    void intersectionSecondNonListReturnsRaw() throws Exception {
        fm.assertExec("Intersection([1, 2], x)", "Intersection([1, 2], x)");
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Intersection()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Intersection([1])"));
    }
}
