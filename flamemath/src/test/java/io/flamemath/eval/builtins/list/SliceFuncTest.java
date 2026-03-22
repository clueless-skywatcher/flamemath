package io.flamemath.eval.builtins.list;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class SliceFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Basic slicing ---

    @Test
    public void sliceMiddle() throws Exception {
        fm.assertExec("[2, 3]", "Slice([1, 2, 3, 4], 1, 3)");
    }

    @Test
    public void sliceFromStart() throws Exception {
        fm.assertExec("[1, 2]", "Slice([1, 2, 3, 4], 0, 2)");
    }

    @Test
    public void sliceToEnd() throws Exception {
        fm.assertExec("[3, 4]", "Slice([1, 2, 3, 4], 2, 4)");
    }

    @Test
    public void sliceEntireList() throws Exception {
        fm.assertExec("[1, 2, 3]", "Slice([1, 2, 3], 0, 3)");
    }

    @Test
    public void sliceSingleElement() throws Exception {
        fm.assertExec("[2]", "Slice([1, 2, 3], 1, 2)");
    }

    @Test
    public void sliceEmptyRange() throws Exception {
        fm.assertExec("[]", "Slice([1, 2, 3], 1, 1)");
    }

    // --- Different types ---

    @Test
    public void sliceStrings() throws Exception {
        fm.assertExec("[\"b\", \"c\"]", "Slice([\"a\", \"b\", \"c\", \"d\"], 1, 3)");
    }

    @Test
    public void sliceNestedLists() throws Exception {
        fm.assertExec("[[3, 4]]", "Slice([[1, 2], [3, 4], [5, 6]], 1, 2)");
    }

    // --- Variable binding ---

    @Test
    public void sliceFromVariable() throws Exception {
        fm.assertExec("[2, 3]", "{l = [1, 2, 3, 4]}; Slice(l, 1, 3)}");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    public void sliceSymbolReturnsRaw() throws Exception {
        fm.assertExec("Slice(x, 0, 1)", "Slice(x, 0, 1)");
    }

    @Test
    public void sliceIntegerReturnsRaw() throws Exception {
        fm.assertExec("Slice(42, 0, 1)", "Slice(42, 0, 1)");
    }

    @Test
    public void sliceStringReturnsRaw() throws Exception {
        fm.assertExec("Slice(\"hello\", 0, 1)", "Slice(\"hello\", 0, 1)");
    }

    // --- Arity errors ---

    @Test
    public void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Slice([1, 2], 0)"));
    }

    @Test
    public void fourArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Slice([1, 2], 0, 1, 2)"));
    }
}
