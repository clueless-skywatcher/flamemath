package io.flamemath.eval.builtins.list;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class DeleteCopyFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Basic delete copy ---

    @Test
    public void deleteCopyExistingElement() throws Exception {
        fm.assertExec("[1, 3]", "DeleteCopy([1, 2, 3], 2)");
    }

    @Test
    public void deleteCopyFromSingletonList() throws Exception {
        fm.assertExec("[]", "DeleteCopy([1], 1)");
    }

    // --- Element not found ---

    @Test
    public void deleteCopyAbsentElement() throws Exception {
        fm.assertExec("[1, 2, 3]", "DeleteCopy([1, 2, 3], 5)");
    }

    @Test
    public void deleteCopyFromEmptyList() throws Exception {
        fm.assertExec("[]", "DeleteCopy([], 1)");
    }

    // --- Removes only first occurrence ---

    @Test
    public void deleteCopyFirstOccurrenceOnly() throws Exception {
        fm.assertExec("[1, 3, 2]", "DeleteCopy([1, 2, 3, 2], 2)");
    }

    // --- Returns new list, original unchanged ---

    @Test
    public void originalListUnchanged() throws Exception {
        fm.assertExec("[1, 2, 3]", "{l = [1, 2, 3]}; DeleteCopy(l, 2); l}");
    }

    @Test
    public void returnsNewList() throws Exception {
        fm.assertExec("[1, 3]", "{l = [1, 2, 3]}; r = DeleteCopy(l, 2); r}");
    }

    // --- Symbolic elements ---

    @Test
    public void deleteCopySymbol() throws Exception {
        fm.assertExec("[y, z]", "DeleteCopy([x, y, z], x)");
    }

    // --- Type errors ---

    @Test
    public void deleteCopyFromNonListThrows() {
        assertThrows(Exception.class, () -> fm.execute("DeleteCopy(42, 1)"));
    }

    @Test
    public void deleteCopyFromSymbolThrows() {
        assertThrows(Exception.class, () -> fm.execute("DeleteCopy(x, 1)"));
    }

    // --- Arity errors ---

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("DeleteCopy()"));
    }

    @Test
    public void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("DeleteCopy([1])"));
    }

    @Test
    public void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("DeleteCopy([1], 2, 3)"));
    }
}
