package io.flamemath.eval.builtins.comparison;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LessEqFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Integer comparison ---

    @Test
    void lessThan() throws Exception {
        fm.assertExec("True", "2 <= 3");
    }

    @Test
    void equalTo() throws Exception {
        fm.assertExec("True", "3 <= 3");
    }

    @Test
    void greaterThan() throws Exception {
        fm.assertExec("False", "4 <= 3");
    }

    @Test
    void negativeNumbers() throws Exception {
        fm.assertExec("True", "(-5) <= (-2)");
    }

    @Test
    void negativeEqualNegative() throws Exception {
        fm.assertExec("True", "(-3) <= (-3)");
    }

    // --- Real comparison ---

    @Test
    void realsLessThan() throws Exception {
        fm.assertExec("True", "2.5 <= 3.0");
    }

    @Test
    void realsEqual() throws Exception {
        fm.assertExec("True", "3.0 <= 3.0");
    }

    @Test
    void realsGreater() throws Exception {
        fm.assertExec("False", "3.5 <= 3.0");
    }

    // --- Cross-type numeric ---

    @Test
    void integerLessEqReal() throws Exception {
        fm.assertExec("True", "3 <= 3.0");
    }

    @Test
    void integerLessThanReal() throws Exception {
        fm.assertExec("True", "2 <= 2.5");
    }

    // --- Evaluated expressions ---

    @Test
    void evaluatedLessEq() throws Exception {
        fm.assertExec("True", "2 + 3 <= 5");
    }

    @Test
    void evaluatedGreater() throws Exception {
        fm.assertExec("False", "2 + 3 <= 4");
    }

    // --- Symbolic stays unevaluated ---

    @Test
    void symbolicReturnsCompound() throws Exception {
        fm.assertExec("LessEq(x, 3)", "x <= 3");
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("LessEq()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("LessEq(1)"));
    }

    @Test
    void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("LessEq(1, 2, 3)"));
    }
}
