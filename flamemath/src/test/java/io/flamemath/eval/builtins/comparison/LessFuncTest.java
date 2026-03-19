package io.flamemath.eval.builtins.comparison;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LessFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Integer comparison ---

    @Test
    void lessThan() throws Exception {
        fm.assertExec("True", "2 < 3");
    }

    @Test
    void notLessThanEqual() throws Exception {
        fm.assertExec("False", "3 < 3");
    }

    @Test
    void notLessThanGreater() throws Exception {
        fm.assertExec("False", "4 < 3");
    }

    @Test
    void negativeNumbers() throws Exception {
        fm.assertExec("True", "(-5) < (-2)");
    }

    @Test
    void negativeVsPositive() throws Exception {
        fm.assertExec("True", "(-1) < 1");
    }

    @Test
    void zeroLessThanPositive() throws Exception {
        fm.assertExec("True", "0 < 1");
    }

    // --- Real comparison ---

    @Test
    void realsLessThan() throws Exception {
        fm.assertExec("True", "2.5 < 3.0");
    }

    @Test
    void realsEqual() throws Exception {
        fm.assertExec("False", "3.0 < 3.0");
    }

    // --- Cross-type numeric ---

    @Test
    void integerLessThanReal() throws Exception {
        fm.assertExec("True", "2 < 2.5");
    }

    @Test
    void integerNotLessThanEqualReal() throws Exception {
        fm.assertExec("False", "3 < 3.0");
    }

    // --- Evaluated expressions ---

    @Test
    void evaluatedLessThan() throws Exception {
        fm.assertExec("True", "2 + 1 < 5");
    }

    @Test
    void evaluatedNotLessThan() throws Exception {
        fm.assertExec("False", "2 + 3 < 4");
    }

    // --- Symbolic stays unevaluated ---

    @Test
    void symbolicReturnsCompound() throws Exception {
        fm.assertExec("Less(x, 3)", "x < 3");
    }

    @Test
    void bothSymbolicReturnsCompound() throws Exception {
        fm.assertExec("Less(x, y)", "x < y");
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Less()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Less(1)"));
    }

    @Test
    void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Less(1, 2, 3)"));
    }
}
