package io.flamemath.eval.builtins.list;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class ExtendFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Basic extend ---

    @Test
    public void extendEmptyWithEmpty() throws Exception {
        fm.assertExec("[]", "{l = []}; Extend(l, []); l}");
    }

    @Test
    public void extendEmptyWithNonEmpty() throws Exception {
        fm.assertExec("[1, 2]", "{l = []}; Extend(l, [1, 2]); l}");
    }

    @Test
    public void extendNonEmptyWithEmpty() throws Exception {
        fm.assertExec("[1, 2]", "{l = [1, 2]}; Extend(l, []); l}");
    }

    @Test
    public void extendNonEmptyWithNonEmpty() throws Exception {
        fm.assertExec("[1, 2, 3, 4]", "{l = [1, 2]}; Extend(l, [3, 4]); l}");
    }

    // --- Multiple extends ---

    @Test
    public void multipleExtends() throws Exception {
        fm.assertExec("[1, 2, 3, 4, 5]", "{l = [1]}; Extend(l, [2, 3]); Extend(l, [4, 5]); l}");
    }

    // --- Returns Null ---

    @Test
    public void returnsNull() throws Exception {
        fm.assertExec("Null", "Extend([1, 2], [3, 4])");
    }

    // --- Nested lists ---

    @Test
    public void extendWithNestedList() throws Exception {
        fm.assertExec("[1, [2, 3]]", "{l = [1]}; Extend(l, [[2, 3]]); l}");
    }

    // --- Symbolic elements ---

    @Test
    public void extendWithSymbols() throws Exception {
        fm.assertExec("[1, x, y]", "{l = [1]}; Extend(l, [x, y]); l}");
    }

    // --- Type errors ---

    @Test
    public void extendNonListFirstArgThrows() {
        assertThrows(Exception.class, () -> fm.execute("Extend(42, [1])"));
    }

    @Test
    public void extendNonListSecondArgThrows() {
        assertThrows(Exception.class, () -> fm.execute("Extend([1], 42)"));
    }

    @Test
    public void extendBothNonListThrows() {
        assertThrows(Exception.class, () -> fm.execute("Extend(42, 43)"));
    }

    // --- Arity errors ---

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Extend()"));
    }

    @Test
    public void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Extend([1])"));
    }

    @Test
    public void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Extend([1], [2], [3])"));
    }
}
