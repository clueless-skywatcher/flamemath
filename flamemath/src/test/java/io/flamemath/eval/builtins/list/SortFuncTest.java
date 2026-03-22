package io.flamemath.eval.builtins.list;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class SortFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Basic sorting ---

    @Test
    public void sortIntegers() throws Exception {
        fm.assertExec("[1, 2, 3]", "Sort([3, 1, 2])");
    }

    @Test
    public void sortAlreadySorted() throws Exception {
        fm.assertExec("[1, 2, 3]", "Sort([1, 2, 3])");
    }

    @Test
    public void sortReverseSorted() throws Exception {
        fm.assertExec("[1, 2, 3]", "Sort([3, 2, 1])");
    }

    @Test
    public void sortSingleElement() throws Exception {
        fm.assertExec("[42]", "Sort([42])");
    }

    @Test
    public void sortEmptyList() throws Exception {
        fm.assertExec("[]", "Sort([])");
    }

    // --- Duplicates ---

    @Test
    public void sortWithDuplicates() throws Exception {
        fm.assertExec("[1, 1, 2, 3, 3]", "Sort([3, 1, 3, 2, 1])");
    }

    @Test
    public void sortAllSameElements() throws Exception {
        fm.assertExec("[5, 5, 5]", "Sort([5, 5, 5])");
    }

    // --- Negative numbers ---

    @Test
    public void sortNegativeNumbers() throws Exception {
        fm.assertExec("[-3, -1, 2, 4]", "Sort([4, -1, 2, -3])");
    }

    // --- Real numbers ---

    @Test
    public void sortReals() throws Exception {
        fm.assertExec("[1.1, 2.2, 3.3]", "Sort([3.3, 1.1, 2.2])");
    }

    @Test
    public void sortMixedIntegerAndReal() throws Exception {
        fm.assertExec("[1, 1.5, 2, 2.5, 3]", "Sort([2.5, 1, 3, 1.5, 2])");
    }

    // --- Strings ---

    @Test
    public void sortStrings() throws Exception {
        fm.assertExec("[\"apple\", \"banana\", \"cherry\"]", "Sort([\"cherry\", \"apple\", \"banana\"])");
    }

    // --- Custom comparator ---

    @Test
    public void sortWithComparatorDescending() throws Exception {
        fm.assertExec("[3, 2, 1]", "Sort([1, 3, 2], (a, b) => Greater(a, b))");
    }

    @Test
    public void sortWithComparatorByAbsValue() throws Exception {
        fm.assertExec("[1, -2, 3, -4]", "Sort([-4, 3, 1, -2], (a, b) => Less(Abs(a), Abs(b)))");
    }

    // --- Variable binding ---

    @Test
    public void sortListFromVariable() throws Exception {
        fm.assertExec("[1, 2, 3]", "{l = [3, 1, 2]}; Sort(l)}");
    }

    // --- Composed with other functions ---

    @Test
    public void sortRangeReverse() throws Exception {
        fm.assertExec("[1, 2, 3]", "Sort([3, 2, 1])");
    }

    @Test
    public void lenOfSorted() throws Exception {
        fm.assertExec("3", "Len(Sort([3, 1, 2]))");
    }

    @Test
    public void sortMappedList() throws Exception {
        fm.assertExec("[1, 4, 9]", "Sort(Map((x) => x * x, [3, 1, -2]))");
    }

    // --- Arity errors ---

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Sort()"));
    }

    @Test
    public void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Sort([1], (a, b) => Less(a, b), 99)"));
    }
}
