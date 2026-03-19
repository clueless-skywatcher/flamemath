package io.flamemath.eval.builtins.logical;

import io.flamemath.FlameTestingUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AndFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    void name() {
        assertEquals("And", new AndFunc().name());
    }

    // --- Zero and single argument ---

    @Test
    void noArgs() throws Exception {
        fm.assertExec("True", "And()");
    }

    @Test
    void singleTrue() throws Exception {
        fm.assertExec("True", "And(True)");
    }

    @Test
    void singleFalse() throws Exception {
        fm.assertExec("False", "And(False)");
    }

    // --- Two arguments ---

    @Test
    void trueAndTrue() throws Exception {
        fm.assertExec("True", "True && True");
    }

    @Test
    void trueAndFalse() throws Exception {
        fm.assertExec("False", "True && False");
    }

    @Test
    void falseAndTrue() throws Exception {
        fm.assertExec("False", "False && True");
    }

    @Test
    void falseAndFalse() throws Exception {
        fm.assertExec("False", "False && False");
    }

    // --- Multiple arguments ---

    @Test
    void allTrue() throws Exception {
        fm.assertExec("True", "And(True, True, True)");
    }

    @Test
    void oneAmongManyFalse() throws Exception {
        fm.assertExec("False", "And(True, True, False, True)");
    }

    // --- Short-circuit evaluation ---

    @Test
    void shortCircuitsOnFalse() throws Exception {
        // False should prevent evaluation of the second arg;
        // if it did evaluate, 1/0 would throw
        fm.assertExec("False", "And(False, 1/0)");
    }

    // --- With evaluated expressions ---

    @Test
    void withComparisons() throws Exception {
        fm.assertExec("True", "3 > 1 && 5 == 5");
    }

    @Test
    void withFalseComparison() throws Exception {
        fm.assertExec("False", "3 > 1 && 5 == 6");
    }

    // --- Flattening ---

    @Test
    void nestedAndFlattens() throws Exception {
        fm.assertExec("True", "And(And(True, True), True)");
    }

    @Test
    void nestedAndFalseFlattens() throws Exception {
        fm.assertExec("False", "And(And(True, False), True)");
    }

    // --- Infix chaining ---

    @Test
    void chainedInfix() throws Exception {
        fm.assertExec("True", "True && True && True");
    }

    @Test
    void chainedInfixWithFalse() throws Exception {
        fm.assertExec("False", "True && False && True");
    }

    // --- Symbolic arguments ---

    @Test
    void singleSymbol() throws Exception {
        fm.assertExec("x", "And(x)");
    }

    @Test
    void trueAndSymbol() throws Exception {
        fm.assertExec("x", "And(True, x)");
    }

    @Test
    void symbolAndTrue() throws Exception {
        fm.assertExec("x", "And(x, True)");
    }

    @Test
    void falseAndSymbol() throws Exception {
        fm.assertExec("False", "And(False, x)");
    }

    @Test
    void twoSymbols() throws Exception {
        fm.assertExec("x && y", "And(x, y)");
    }

    @Test
    void trueDroppedAmongSymbols() throws Exception {
        fm.assertExec("x && y", "And(True, x, True, y)");
    }

    @Test
    void allTrueWithOneSymbol() throws Exception {
        fm.assertExec("x", "And(True, True, x)");
    }

    // --- Attributes ---

    @Test
    void isFlat() {
        assertTrue(new AndFunc().isFlat());
    }

    @Test
    void isHoldAll() {
        assertTrue(new AndFunc().holdAll());
    }
}
