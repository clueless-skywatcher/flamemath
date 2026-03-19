package io.flamemath.eval.builtins.comparison;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreaterEqFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Integer comparison ---

    @Test
    void greaterThan() throws Exception {
        fm.assertExec("True", "4 >= 3");
    }

    @Test
    void equalTo() throws Exception {
        fm.assertExec("True", "3 >= 3");
    }

    @Test
    void lessThan() throws Exception {
        fm.assertExec("False", "2 >= 3");
    }

    @Test
    void negativeNumbers() throws Exception {
        fm.assertExec("True", "(-2) >= (-5)");
    }

    @Test
    void negativeEqualNegative() throws Exception {
        fm.assertExec("True", "(-3) >= (-3)");
    }

    // --- Real comparison ---

    @Test
    void realsGreater() throws Exception {
        fm.assertExec("True", "3.5 >= 3.0");
    }

    @Test
    void realsEqual() throws Exception {
        fm.assertExec("True", "3.0 >= 3.0");
    }

    @Test
    void realsLess() throws Exception {
        fm.assertExec("False", "2.5 >= 3.0");
    }

    // --- Cross-type numeric ---

    @Test
    void integerGreaterEqReal() throws Exception {
        fm.assertExec("True", "3 >= 3.0");
    }

    @Test
    void integerGreaterThanReal() throws Exception {
        fm.assertExec("True", "4 >= 3.5");
    }

    // --- Evaluated expressions ---

    @Test
    void evaluatedGreaterEq() throws Exception {
        fm.assertExec("True", "2 + 3 >= 5");
    }

    @Test
    void evaluatedLess() throws Exception {
        fm.assertExec("False", "2 + 1 >= 5");
    }

    // --- Symbolic stays unevaluated ---

    @Test
    void symbolicReturnsCompound() throws Exception {
        fm.assertExec("GreaterEq(x, 3)", "x >= 3");
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("GreaterEq()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("GreaterEq(1)"));
    }

    @Test
    void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("GreaterEq(1, 2, 3)"));
    }
}
