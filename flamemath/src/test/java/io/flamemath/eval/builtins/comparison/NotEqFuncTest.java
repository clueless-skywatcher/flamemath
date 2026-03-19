package io.flamemath.eval.builtins.comparison;

import io.flamemath.FlameTestingUtils;
import io.flamemath.eval.FlameArityException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotEqFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Integer inequality ---

    @Test
    void sameIntegers() throws Exception {
        fm.assertExec("False", "3 != 3");
    }

    @Test
    void differentIntegers() throws Exception {
        fm.assertExec("True", "3 != 4");
    }

    @Test
    void negativeVsPositive() throws Exception {
        fm.assertExec("True", "(-1) != 1");
    }

    // --- Real inequality ---

    @Test
    void sameReals() throws Exception {
        fm.assertExec("False", "3.14 != 3.14");
    }

    @Test
    void differentReals() throws Exception {
        fm.assertExec("True", "3.14 != 2.71");
    }

    // --- Cross-type numeric ---

    @Test
    void integerVsEqualReal() throws Exception {
        fm.assertExec("False", "3 != 3.0");
    }

    @Test
    void integerVsDifferentReal() throws Exception {
        fm.assertExec("True", "3 != 3.5");
    }

    // --- Symbols ---

    @Test
    void sameSymbols() throws Exception {
        fm.assertExec("False", "x != x");
    }

    @Test
    void differentSymbols() throws Exception {
        fm.assertExec("True", "x != y");
    }

    // --- Evaluated expressions ---

    @Test
    void evaluatedEqual() throws Exception {
        fm.assertExec("False", "2 + 3 != 5");
    }

    @Test
    void evaluatedDifferent() throws Exception {
        fm.assertExec("True", "2 + 3 != 7");
    }

    // --- Mixed types ---

    @Test
    void integerVsSymbol() throws Exception {
        fm.assertExec("True", "3 != x");
    }

    @Test
    void integerVsBoolean() throws Exception {
        fm.assertExec("True", "1 != True");
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("NotEq()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("NotEq(1)"));
    }

    @Test
    void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("NotEq(1, 2, 3)"));
    }
}
