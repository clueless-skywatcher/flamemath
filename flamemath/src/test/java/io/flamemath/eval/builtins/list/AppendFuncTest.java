package io.flamemath.eval.builtins.list;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class AppendFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Basic append ---

    @Test
    public void appendToEmptyList() throws Exception {
        fm.assertExec("[1]", "{l = []}; Append(l, 1); l}");
    }

    @Test
    public void appendToSingletonList() throws Exception {
        fm.assertExec("[1, 2]", "{l = [1]}; Append(l, 2); l}");
    }

    @Test
    public void appendToMultiElementList() throws Exception {
        fm.assertExec("[1, 2, 3]", "{l = [1, 2]}; Append(l, 3); l}");
    }

    // --- Append preserves order ---

    @Test
    public void multipleAppends() throws Exception {
        fm.assertExec("[1, 2, 3]", "{l = []}; Append(l, 1); Append(l, 2); Append(l, 3); l}");
    }

    // --- Returns Null ---

    @Test
    public void returnsNull() throws Exception {
        fm.assertExec("Null", "Append([1, 2], 3)");
    }

    // --- Nested lists ---

    @Test
    public void appendListElement() throws Exception {
        fm.assertExec("[1, [2, 3]]", "{l = [1]}; Append(l, [2, 3]); l}");
    }

    // --- Symbolic elements ---

    @Test
    public void appendSymbol() throws Exception {
        fm.assertExec("[1, x]", "{l = [1]}; Append(l, x); l}");
    }

    // --- Type errors ---

    @Test
    public void appendToNonListThrows() {
        assertThrows(Exception.class, () -> fm.execute("Append(42, 1)"));
    }

    @Test
    public void appendToSymbolThrows() {
        assertThrows(Exception.class, () -> fm.execute("Append(x, 1)"));
    }

    // --- Arity errors ---

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Append()"));
    }

    @Test
    public void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Append([1])"));
    }

    @Test
    public void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Append([1], 2, 3)"));
    }
}
