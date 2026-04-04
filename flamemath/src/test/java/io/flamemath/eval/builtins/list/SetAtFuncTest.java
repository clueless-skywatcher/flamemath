package io.flamemath.eval.builtins.list;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

public class SetAtFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Basic SetAt ---

    @Test
    public void setAtFirstElement() throws Exception {
        fm.assertExec("[10, 2, 3]", "{l = [1, 2, 3]}; SetAt(l, 0, 10); l}");
    }

    @Test
    public void setAtMiddleElement() throws Exception {
        fm.assertExec("[1, 20, 3]", "{l = [1, 2, 3]}; SetAt(l, 1, 20); l}");
    }

    @Test
    public void setAtLastElement() throws Exception {
        fm.assertExec("[1, 2, 30]", "{l = [1, 2, 3]}; SetAt(l, 2, 30); l}");
    }

    // --- Negative indexing ---

    @Test
    public void setAtNegativeIndex() throws Exception {
        fm.assertExec("[1, 2, 30]", "{l = [1, 2, 3]}; SetAt(l, -1, 30); l}");
    }

    @Test
    public void setAtNegativeIndexFirst() throws Exception {
        fm.assertExec("[10, 2, 3]", "{l = [1, 2, 3]}; SetAt(l, -3, 10); l}");
    }

    // --- Returns Null ---

    @Test
    public void returnsNull() throws Exception {
        fm.assertExec("Null", "SetAt([1, 2, 3], 0, 10)");
    }

    // --- Multiple SetAt ---

    @Test
    public void multipleSetAt() throws Exception {
        fm.assertExec("[10, 20, 30]", "{l = [1, 2, 3]}; SetAt(l, 0, 10); SetAt(l, 1, 20); SetAt(l, 2, 30); l}");
    }

    // --- Symbolic values ---

    @Test
    public void setAtSymbol() throws Exception {
        fm.assertExec("[x, 2, 3]", "{l = [1, 2, 3]}; SetAt(l, 0, x); l}");
    }

    // --- Bracket assignment syntax ---

    @Test
    public void bracketAssignmentSyntax() throws Exception {
        fm.assertExec("[10, 2, 3]", "{l = [1, 2, 3]}; l[0] = 10; l}");
    }

    @Test
    public void bracketAssignmentMiddle() throws Exception {
        fm.assertExec("[1, 20, 3]", "{l = [1, 2, 3]}; l[1] = 20; l}");
    }

    @Test
    public void bracketAssignmentNegative() throws Exception {
        fm.assertExec("[1, 2, 30]", "{l = [1, 2, 3]}; l[-1] = 30; l}");
    }

    // --- Out of bounds ---

    @Test
    public void outOfBoundsThrows() {
        assertThrows(Exception.class, () -> fm.execute("SetAt([1, 2, 3], 5, 10)"));
    }

    @Test
    public void negativeOutOfBoundsThrows() {
        assertThrows(Exception.class, () -> fm.execute("SetAt([1, 2, 3], -4, 10)"));
    }

    // --- Type errors ---

    @Test
    public void setAtNonListThrows() {
        assertThrows(Exception.class, () -> fm.execute("SetAt(42, 0, 10)"));
    }

    @Test
    public void nonIntegerIndexThrows() {
        assertThrows(Exception.class, () -> fm.execute("SetAt([1, 2, 3], x, 10)"));
    }

    // --- Arity errors ---

    @Test
    public void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("SetAt()"));
    }

    @Test
    public void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("SetAt([1], 0)"));
    }

    @Test
    public void fourArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("SetAt([1], 0, 10, 20)"));
    }
}
