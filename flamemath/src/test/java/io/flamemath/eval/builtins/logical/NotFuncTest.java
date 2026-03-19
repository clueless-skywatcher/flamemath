package io.flamemath.eval.builtins.logical;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    void name() {
        assertEquals("Not", new NotFunc().name());
    }

    // --- Boolean arguments ---

    @Test
    void notTrue() throws Exception {
        fm.assertExec("False", "!True");
    }

    @Test
    void notFalse() throws Exception {
        fm.assertExec("True", "!False");
    }

    // --- Double negation ---

    @Test
    void doubleNegationTrue() throws Exception {
        fm.assertExec("True", "!!True");
    }

    @Test
    void doubleNegationFalse() throws Exception {
        fm.assertExec("False", "!!False");
    }

    // --- With evaluated expressions ---

    @Test
    void notComparison() throws Exception {
        fm.assertExec("False", "!(3 == 3)");
    }

    @Test
    void notFalseComparison() throws Exception {
        fm.assertExec("True", "!(3 == 4)");
    }

    // --- Symbolic arguments ---

    @Test
    void notSymbol() throws Exception {
        fm.assertExec("!x", "!x");
    }

    @Test
    void notSymbolicExpr() throws Exception {
        fm.assertExec("!(x + y)", "!(x + y)");
    }

    // --- With variables ---

    @Test
    void notVariableTrue() throws Exception {
        fm.assertExec("False", "{ a = True; !a }");
    }

    @Test
    void notVariableFalse() throws Exception {
        fm.assertExec("True", "{ a = False; !a }");
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Not()"));
    }

    @Test
    void twoArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Not(True, False)"));
    }
}
