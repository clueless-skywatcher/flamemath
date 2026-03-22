package io.flamemath.eval.builtins.general;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.flamemath.ExprPrinter;
import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

class HoldFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    private String print(String input) throws Exception {
        return ExprPrinter.print(fm.execute(input));
    }

    @Test
    void name() {
        assertEquals("Hold", new HoldFunc().name());
    }

    // --- Prevents evaluation ---

    @Test
    void holdSymbol() throws Exception {
        assertEquals("x", print("Hold(x)"));
    }

    @Test
    void holdBoundVariable() throws Exception {
        assertEquals("x", print("{ x = 42; Hold(x) }"));
    }

    @Test
    void holdExpression() throws Exception {
        assertEquals("1 + 2", print("Hold(1 + 2)"));
    }

    @Test
    void holdFunctionCall() throws Exception {
        assertEquals("Sin(0)", print("Hold(Sin(0))"));
    }

    @Test
    void holdNestedExpression() throws Exception {
        assertEquals("1 + 2*3", print("Hold(1 + 2 * 3)"));
    }

    // --- Atomic values pass through ---

    @Test
    void holdInteger() throws Exception {
        fm.assertExec("42", "Hold(42)");
    }

    @Test
    void holdString() throws Exception {
        fm.assertExec("\"hello\"", "Hold(\"hello\")");
    }

    @Test
    void holdBoolean() throws Exception {
        assertEquals("True", print("Hold(True)"));
    }

    // --- Useful with Apply/Raw ---

    @Test
    void holdPreservesSymbolInList() throws Exception {
        assertEquals("[Sin, 1, 2]", print("Join([Hold(Sin)], [1, 2])"));
    }

    @Test
    void holdInApplyRawPattern() throws Exception {
        assertEquals("Sin(0)", print("Apply(Raw, [Hold(Sin), 0])"));
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Hold()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Hold(x, y)"));
    }
}
