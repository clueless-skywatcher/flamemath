package io.flamemath.eval.builtins.list;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

public class GenListFuncTest {
    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Single iterator ---

    @Test
    public void simpleRange() throws Exception {
        fm.assertExec("[1, 2, 3, 4, 5]", "GenList(i, [i, 1, 6])");
    }

    @Test
    public void squares() throws Exception {
        fm.assertExec("[1, 4, 9, 16]", "GenList(i^2, [i, 1, 5])");
    }

    @Test
    public void expressionBody() throws Exception {
        fm.assertExec("[3, 5, 7, 9]", "GenList(2*i + 1, [i, 1, 5])");
    }

    @Test
    public void startEqualsEndIsEmpty() throws Exception {
        fm.assertExec("[]", "GenList(i, [i, 3, 3])");
    }

    @Test
    public void startGreaterThanEndIsEmpty() throws Exception {
        fm.assertExec("[]", "GenList(i, [i, 5, 2])");
    }

    @Test
    public void singleElement() throws Exception {
        fm.assertExec("[7]", "GenList(i, [i, 7, 8])");
    }

    @Test
    public void negativeRange() throws Exception {
        fm.assertExec("[9, 4, 1]", "GenList(i^2, [i, -3, 0])");
    }

    @Test
    public void crossingZero() throws Exception {
        fm.assertExec("[-2, -1, 0, 1, 2]", "GenList(i, [i, -2, 3])");
    }

    // --- Symbolic body ---

    @Test
    public void symbolicExpression() throws Exception {
        fm.assertExec("[x + 1, x + 2, x + 3]", "GenList(x + i, [i, 1, 4])");
    }

    // --- Multiple iterators (nested output) ---

    @Test
    public void twoIterators() throws Exception {
        fm.assertExec("[[2, 3, 4], [3, 4, 5], [4, 5, 6]]",
                "GenList(i + j, [i, 1, 4], [j, 1, 4])");
    }

    @Test
    public void twoIteratorsProduct() throws Exception {
        fm.assertExec("[[1, 2, 3], [2, 4, 6], [3, 6, 9]]",
                "GenList(i * j, [i, 1, 4], [j, 1, 4])");
    }

    @Test
    public void twoIteratorsDifferentRanges() throws Exception {
        fm.assertExec("[[10, 20], [20, 40]]",
                "GenList(i * j, [i, 1, 3], [j, 10, 30, 10])");
    }

    @Test
    public void twoIteratorsInnerEmpty() throws Exception {
        fm.assertExec("[[], [], []]",
                "GenList(i + j, [i, 1, 4], [j, 5, 5])");
    }

    @Test
    public void twoIteratorsOuterEmpty() throws Exception {
        fm.assertExec("[]",
                "GenList(i + j, [i, 5, 5], [j, 1, 4])");
    }

    @Test
    public void threeIterators() throws Exception {
        fm.assertExec("[[[3, 4], [4, 5]], [[4, 5], [5, 6]]]",
                "GenList(i + j + k, [i, 1, 3], [j, 1, 3], [k, 1, 3])");
    }

    // --- Iterator with step ---

    @Test
    public void singleIteratorWithStep() throws Exception {
        fm.assertExec("[0, 2, 4, 6, 8]", "GenList(i, [i, 0, 10, 2])");
    }

    @Test
    public void stepOfThree() throws Exception {
        fm.assertExec("[1, 4, 7]", "GenList(i, [i, 1, 10, 3])");
    }

    @Test
    public void negativeStep() throws Exception {
        fm.assertExec("[10, 8, 6, 4, 2]", "GenList(i, [i, 10, 0, -2])");
    }

    @Test
    public void stepWrongDirectionIsEmpty() throws Exception {
        fm.assertExec("[]", "GenList(i, [i, 0, 10, -1])");
        fm.assertExec("[]", "GenList(i, [i, 10, 0, 1])");
    }

    @Test
    public void stepWithExpression() throws Exception {
        fm.assertExec("[0, 4, 16, 36]", "GenList(i^2, [i, 0, 8, 2])");
    }

    @Test
    public void twoIteratorsWithStep() throws Exception {
        fm.assertExec("[[2, 4], [4, 6]]",
                "GenList(i + j, [i, 1, 5, 2], [j, 1, 5, 2])");
    }

    @Test
    public void mixedStepAndNoStep() throws Exception {
        fm.assertExec("[[1, 2, 3], [3, 6, 9]]",
                "GenList(i * j, [i, 1, 4, 2], [j, 1, 4])");
    }

    // --- Iterator variable does not leak ---

    @Test
    public void iteratorDoesNotLeakOutside() throws Exception {
        fm.assertExec("i", "{GenList(i, [i, 1, 5])}; i}");
    }

    // --- Composed with other functions ---

    @Test
    public void lenOfGenList() throws Exception {
        fm.assertExec("5", "Len(GenList(i, [i, 0, 5]))");
    }

    @Test
    public void mapOverGenList() throws Exception {
        fm.assertExec("[2, 4, 6]", "Map((x) => x * 2, GenList(i, [i, 1, 4]))");
    }

    @Test
    public void nestedGenListManual() throws Exception {
        fm.assertExec("[[1, 2], [2, 4], [3, 6]]",
                "GenList(GenList(i * j, [j, 1, 3]), [i, 1, 4])");
    }

    // --- Arity errors ---

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("GenList()"));
    }

    @Test
    public void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("GenList(i)"));
    }
}
