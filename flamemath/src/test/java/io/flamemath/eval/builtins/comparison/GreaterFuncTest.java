package io.flamemath.eval.builtins.comparison;

import io.flamemath.FlameTestingUtils;
import io.flamemath.eval.FlameArityException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreaterFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Integer comparison ---

    @Test
    void greaterThan() throws Exception {
        fm.assertExec("True", "4 > 3");
    }

    @Test
    void notGreaterThanEqual() throws Exception {
        fm.assertExec("False", "3 > 3");
    }

    @Test
    void notGreaterThanLess() throws Exception {
        fm.assertExec("False", "2 > 3");
    }

    @Test
    void negativeNumbers() throws Exception {
        fm.assertExec("True", "(-2) > (-5)");
    }

    @Test
    void positiveVsNegative() throws Exception {
        fm.assertExec("True", "1 > (-1)");
    }

    // --- Real comparison ---

    @Test
    void realsGreater() throws Exception {
        fm.assertExec("True", "3.5 > 3.0");
    }

    @Test
    void realsEqual() throws Exception {
        fm.assertExec("False", "3.0 > 3.0");
    }

    // --- Cross-type numeric ---

    @Test
    void integerGreaterThanReal() throws Exception {
        fm.assertExec("True", "4 > 3.5");
    }

    @Test
    void integerNotGreaterThanEqualReal() throws Exception {
        fm.assertExec("False", "3 > 3.0");
    }

    // --- Evaluated expressions ---

    @Test
    void evaluatedGreater() throws Exception {
        fm.assertExec("True", "2 + 3 > 4");
    }

    @Test
    void evaluatedNotGreater() throws Exception {
        fm.assertExec("False", "2 + 1 > 5");
    }

    // --- Symbolic stays unevaluated ---

    @Test
    void symbolicReturnsCompound() throws Exception {
        fm.assertExec("Greater(x, 3)", "x > 3");
    }

    @Test
    void bothSymbolicReturnsCompound() throws Exception {
        fm.assertExec("Greater(x, y)", "x > y");
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Greater()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Greater(1)"));
    }

    @Test
    void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Greater(1, 2, 3)"));
    }
}
