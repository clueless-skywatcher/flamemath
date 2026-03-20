package io.flamemath.eval.builtins.list;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class PrependFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Basic prepend ---

    @Test
    public void prependToEmptyList() throws Exception {
        fm.assertExec("[1]", "{l = []}; Prepend(l, 1); l}");
    }

    @Test
    public void prependToSingletonList() throws Exception {
        fm.assertExec("[2, 1]", "{l = [1]}; Prepend(l, 2); l}");
    }

    @Test
    public void prependToMultiElementList() throws Exception {
        fm.assertExec("[0, 1, 2]", "{l = [1, 2]}; Prepend(l, 0); l}");
    }

    // --- Prepend preserves order ---

    @Test
    public void multiplePrepends() throws Exception {
        fm.assertExec("[1, 2, 3]", "{l = []}; Prepend(l, 3); Prepend(l, 2); Prepend(l, 1); l}");
    }

    // --- Returns Null ---

    @Test
    public void returnsNull() throws Exception {
        fm.assertExec("Null", "Prepend([1, 2], 0)");
    }

    // --- Nested lists ---

    @Test
    public void prependListElement() throws Exception {
        fm.assertExec("[[0, 1], 2]", "{l = [2]}; Prepend(l, [0, 1]); l}");
    }

    // --- Symbolic elements ---

    @Test
    public void prependSymbol() throws Exception {
        fm.assertExec("[x, 1]", "{l = [1]}; Prepend(l, x); l}");
    }

    // --- Type errors ---

    @Test
    public void prependToNonListThrows() {
        assertThrows(Exception.class, () -> fm.execute("Prepend(42, 1)"));
    }

    @Test
    public void prependToSymbolThrows() {
        assertThrows(Exception.class, () -> fm.execute("Prepend(x, 1)"));
    }

    // --- Arity errors ---

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Prepend()"));
    }

    @Test
    public void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Prepend([1])"));
    }

    @Test
    public void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Prepend([1], 2, 3)"));
    }
}
