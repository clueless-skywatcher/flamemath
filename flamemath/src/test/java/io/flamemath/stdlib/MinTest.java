package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class MinTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Two arguments ---

    @Test
    void minTwoIntegers() throws Exception {
        fm.assertExec("1", "Min(1, 2)");
    }

    @Test
    void minTwoIntegersReversed() throws Exception {
        fm.assertExec("1", "Min(2, 1)");
    }

    @Test
    void minTwoEqual() throws Exception {
        fm.assertExec("3", "Min(3, 3)");
    }

    @Test
    void minTwoReals() throws Exception {
        fm.assertExec("1.5", "Min(1.5, 2.5)");
    }

    @Test
    void minNegatives() throws Exception {
        fm.assertExec("-3", "Min(-1, -3)");
    }

    @Test
    void minMixedIntegerAndReal() throws Exception {
        fm.assertExec("1", "Min(1, 1.5)");
    }

    // --- List argument ---

    @Test
    void minOfList() throws Exception {
        fm.assertExec("1", "Min([3, 1, 2])");
    }

    @Test
    void minOfSingleElementList() throws Exception {
        fm.assertExec("42", "Min([42])");
    }

    @Test
    void minOfListWithNegatives() throws Exception {
        fm.assertExec("-5", "Min([3, -5, 1, 0])");
    }

    @Test
    void minOfListWithDuplicates() throws Exception {
        fm.assertExec("1", "Min([1, 1, 2, 3])");
    }

    @Test
    void minOfListWithReals() throws Exception {
        fm.assertExec("0.5", "Min([3.3, 0.5, 2.2])");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void minNonListReturnsArg() throws Exception {
        fm.assertExec("42", "Min(42)");
    }

    @Test
    void minSymbolReturnsSymbol() throws Exception {
        fm.assertExec("x", "Min(x)");
    }

    // --- Variable binding ---

    @Test
    void minFromVariable() throws Exception {
        fm.assertExec("1", "{l = [3, 1, 2]}; Min(l)}");
    }

    // --- Composed ---

    @Test
    void minOfRange() throws Exception {
        fm.assertExec("1", "Min(Range(1, 5))");
    }
}
