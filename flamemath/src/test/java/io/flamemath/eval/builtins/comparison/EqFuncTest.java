package io.flamemath.eval.builtins.comparison;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EqFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    void name() {
        assertEquals("Eq", new EqFunc().name());
    }

    // --- Integer equality ---

    @Test
    void sameIntegers() throws Exception {
        fm.assertExec("True", "3 == 3");
    }

    @Test
    void differentIntegers() throws Exception {
        fm.assertExec("False", "3 == 4");
    }

    @Test
    void zeroEqualsZero() throws Exception {
        fm.assertExec("True", "0 == 0");
    }

    @Test
    void negativeIntegers() throws Exception {
        fm.assertExec("True", "(-5) == (-5)");
    }

    @Test
    void negativeVsPositive() throws Exception {
        fm.assertExec("False", "(-1) == 1");
    }

    // --- Real equality ---

    @Test
    void sameReals() throws Exception {
        fm.assertExec("True", "3.14 == 3.14");
    }

    @Test
    void differentReals() throws Exception {
        fm.assertExec("False", "3.14 == 2.71");
    }

    // --- Cross-type numeric ---

    @Test
    void integerVsReal() throws Exception {
        fm.assertExec("True", "3 == 3.0");
    }

    // --- Boolean equality ---

    @Test
    void sameBooleans() throws Exception {
        fm.assertExec("True", "True == True");
    }

    @Test
    void differentBooleans() throws Exception {
        fm.assertExec("False", "True == False");
    }

    // --- String equality ---

    @Test
    void sameStrings() throws Exception {
        fm.assertExec("True", "\"abc\" == \"abc\"");
    }

    @Test
    void differentStrings() throws Exception {
        fm.assertExec("False", "\"abc\" == \"xyz\"");
    }

    // --- Symbol equality ---

    @Test
    void sameSymbols() throws Exception {
        fm.assertExec("True", "x == x");
    }

    @Test
    void differentSymbols() throws Exception {
        fm.assertExec("False", "x == y");
    }

    // --- Evaluated expressions ---

    @Test
    void evaluatedSums() throws Exception {
        fm.assertExec("True", "2 + 3 == 5");
    }

    @Test
    void evaluatedProducts() throws Exception {
        fm.assertExec("True", "2 * 3 == 6");
    }

    @Test
    void evaluatedDifferent() throws Exception {
        fm.assertExec("False", "2 + 3 == 7");
    }

    // --- Compound equality ---

    @Test
    void sameSymbolicExpressions() throws Exception {
        fm.assertExec("True", "(x + y) == (x + y)");
    }

    @Test
    void differentSymbolicExpressions() throws Exception {
        fm.assertExec("False", "(x + y) == (x + z)");
    }

    // --- Equality with variables ---

    @Test
    void variableEqualsItsValue() throws Exception {
        fm.assertExec("True", "{ a = 5; a == 5 }");
    }

    @Test
    void variableNotEqualDifferentValue() throws Exception {
        fm.assertExec("False", "{ a = 5; a == 6 }");
    }

    // --- Mixed types ---

    @Test
    void integerVsSymbol() throws Exception {
        fm.assertExec("False", "3 == x");
    }

    @Test
    void integerVsBoolean() throws Exception {
        fm.assertExec("False", "1 == True");
    }

    @Test
    void integerVsString() throws Exception {
        fm.assertExec("False", "1 == \"1\"");
    }

    // --- Arity errors ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Eq()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Eq(1)"));
    }

    @Test
    void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("Eq(1, 2, 3)"));
    }
}
