package io.flamemath.eval.builtins.construct;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;
import io.flamemath.expr.NullExpr;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhileFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    void name() {
        assertEquals("While", new WhileFunc().name());
    }

    @Test
    void isHoldAll() {
        assertTrue(new WhileFunc().holdAll());
    }

    // --- Returns Null ---

    @Test
    void returnsNull() throws Exception {
        assertSame(NullExpr.INSTANCE, fm.execute("{ x = 0; While(x < 1, x = x + 1) }"));
    }

    // --- Basic looping ---

    @Test
    void countsToFive() throws Exception {
        fm.assertExec("5", "{ x = 0; While(x < 5, x = x + 1); x }");
    }

    @Test
    void countsToTen() throws Exception {
        fm.assertExec("10", "{ x = 0; While(x < 10, x = x + 1); x }");
    }

    // --- Condition initially false ---

    @Test
    void falseConditionNeverExecutesBody() throws Exception {
        fm.assertExec("0", "{ x = 0; While(False, x = x + 1); x }");
    }

    @Test
    void conditionAlreadyFalse() throws Exception {
        fm.assertExec("42", "{ x = 42; While(x < 10, x = x + 1); x }");
    }

    // --- Accumulation ---

    @Test
    void sumFirstFiveIntegers() throws Exception {
        fm.assertExec("15", "{ i = 1; s = 0; While(i <= 5, { s = s + i; i = i + 1 }); s }");
    }

    @Test
    void factorial() throws Exception {
        fm.assertExec("120", "{ n = 5; f = 1; While(n > 1, { f = f * n; n = n - 1 }); f }");
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("While()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("While(True)"));
    }

    @Test
    void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("While(True, 1, 2)"));
    }
}
