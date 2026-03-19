package io.flamemath.eval.builtins.logical;

import io.flamemath.FlameTestingUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    @Test
    void name() {
        assertEquals("Or", new OrFunc().name());
    }

    // --- Zero and single argument ---

    @Test
    void noArgs() throws Exception {
        fm.assertExec("False", "Or()");
    }

    @Test
    void singleTrue() throws Exception {
        fm.assertExec("True", "Or(True)");
    }

    @Test
    void singleFalse() throws Exception {
        fm.assertExec("False", "Or(False)");
    }

    // --- Two arguments ---

    @Test
    void trueOrTrue() throws Exception {
        fm.assertExec("True", "True || True");
    }

    @Test
    void trueOrFalse() throws Exception {
        fm.assertExec("True", "True || False");
    }

    @Test
    void falseOrTrue() throws Exception {
        fm.assertExec("True", "False || True");
    }

    @Test
    void falseOrFalse() throws Exception {
        fm.assertExec("False", "False || False");
    }

    // --- Multiple arguments ---

    @Test
    void allFalse() throws Exception {
        fm.assertExec("False", "Or(False, False, False)");
    }

    @Test
    void oneAmongManyTrue() throws Exception {
        fm.assertExec("True", "Or(False, False, True, False)");
    }

    // --- Short-circuit evaluation ---

    @Test
    void shortCircuitsOnTrue() throws Exception {
        // True should prevent evaluation of the second arg;
        // if it did evaluate, 1/0 would throw
        fm.assertExec("True", "Or(True, 1/0)");
    }

    // --- With evaluated expressions ---

    @Test
    void withComparisons() throws Exception {
        fm.assertExec("True", "3 > 10 || 5 == 5");
    }

    @Test
    void withAllFalseComparisons() throws Exception {
        fm.assertExec("False", "3 > 10 || 5 == 6");
    }

    // --- Flattening ---

    @Test
    void nestedOrFlattens() throws Exception {
        fm.assertExec("False", "Or(Or(False, False), False)");
    }

    @Test
    void nestedOrTrueFlattens() throws Exception {
        fm.assertExec("True", "Or(Or(False, True), False)");
    }

    // --- Infix chaining ---

    @Test
    void chainedInfix() throws Exception {
        fm.assertExec("False", "False || False || False");
    }

    @Test
    void chainedInfixWithTrue() throws Exception {
        fm.assertExec("True", "False || True || False");
    }

    // --- Symbolic arguments ---

    @Test
    void singleSymbol() throws Exception {
        fm.assertExec("x", "Or(x)");
    }

    @Test
    void falseOrSymbol() throws Exception {
        fm.assertExec("x", "Or(False, x)");
    }

    @Test
    void symbolOrFalse() throws Exception {
        fm.assertExec("x", "Or(x, False)");
    }

    @Test
    void trueOrSymbol() throws Exception {
        fm.assertExec("True", "Or(True, x)");
    }

    @Test
    void twoSymbols() throws Exception {
        fm.assertExec("x || y", "Or(x, y)");
    }

    @Test
    void falseDroppedAmongSymbols() throws Exception {
        fm.assertExec("x || y", "Or(False, x, False, y)");
    }

    @Test
    void allFalseWithOneSymbol() throws Exception {
        fm.assertExec("x", "Or(False, False, x)");
    }

    // --- Attributes ---

    @Test
    void isFlat() {
        assertTrue(new OrFunc().isFlat());
    }

    @Test
    void isHoldAll() {
        assertTrue(new OrFunc().holdAll());
    }
}
