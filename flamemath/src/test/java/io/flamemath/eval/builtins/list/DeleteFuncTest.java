package io.flamemath.eval.builtins.list;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class DeleteFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Basic delete ---

    @Test
    public void deleteExistingElement() throws Exception {
        fm.assertExec("[1, 3]", "{l = [1, 2, 3]}; Delete(l, 2); l}");
    }

    @Test
    public void deleteFromSingletonList() throws Exception {
        fm.assertExec("[]", "{l = [1]}; Delete(l, 1); l}");
    }

    // --- Element not found ---

    @Test
    public void deleteAbsentElementLeavesListUnchanged() throws Exception {
        fm.assertExec("[1, 2, 3]", "{l = [1, 2, 3]}; Delete(l, 5); l}");
    }

    @Test
    public void deleteFromEmptyList() throws Exception {
        fm.assertExec("[]", "{l = []}; Delete(l, 1); l}");
    }

    // --- Removes only first occurrence ---

    @Test
    public void deleteFirstOccurrenceOnly() throws Exception {
        fm.assertExec("[1, 3, 2]", "{l = [1, 2, 3, 2]}; Delete(l, 2); l}");
    }

    // --- Returns Null ---

    @Test
    public void returnsNull() throws Exception {
        fm.assertExec("Null", "Delete([1, 2, 3], 2)");
    }

    // --- Symbolic elements ---

    @Test
    public void deleteSymbol() throws Exception {
        fm.assertExec("[y, z]", "{l = [x, y, z]}; Delete(l, x); l}");
    }

    // --- Nested lists ---

    @Test
    public void deleteNestedList() throws Exception {
        fm.assertExec("[1, 3]", "{l = [1, [2], 3]}; Delete(l, [2]); l}");
    }

    // --- Type errors ---

    @Test
    public void deleteFromNonListThrows() {
        assertThrows(Exception.class, () -> fm.execute("Delete(42, 1)"));
    }

    @Test
    public void deleteFromSymbolThrows() {
        assertThrows(Exception.class, () -> fm.execute("Delete(x, 1)"));
    }

    // --- Arity errors ---

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Delete()"));
    }

    @Test
    public void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Delete([1])"));
    }

    @Test
    public void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Delete([1], 2, 3)"));
    }
}
