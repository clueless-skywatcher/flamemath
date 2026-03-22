package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class MaxTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Two arguments ---

    @Test
    void maxTwoIntegers() throws Exception {
        fm.assertExec("2", "Max(1, 2)");
    }

    @Test
    void maxTwoIntegersReversed() throws Exception {
        fm.assertExec("2", "Max(2, 1)");
    }

    @Test
    void maxTwoEqual() throws Exception {
        fm.assertExec("3", "Max(3, 3)");
    }

    @Test
    void maxTwoReals() throws Exception {
        fm.assertExec("2.5", "Max(1.5, 2.5)");
    }

    @Test
    void maxNegatives() throws Exception {
        fm.assertExec("-1", "Max(-1, -3)");
    }

    @Test
    void maxMixedIntegerAndReal() throws Exception {
        fm.assertExec("1.5", "Max(1, 1.5)");
    }

    // --- List argument ---

    @Test
    void maxOfList() throws Exception {
        fm.assertExec("3", "Max([3, 1, 2])");
    }

    @Test
    void maxOfSingleElementList() throws Exception {
        fm.assertExec("42", "Max([42])");
    }

    @Test
    void maxOfListWithNegatives() throws Exception {
        fm.assertExec("3", "Max([3, -5, 1, 0])");
    }

    @Test
    void maxOfListWithDuplicates() throws Exception {
        fm.assertExec("3", "Max([3, 3, 1, 2])");
    }

    @Test
    void maxOfListWithReals() throws Exception {
        fm.assertExec("3.3", "Max([3.3, 0.5, 2.2])");
    }

    // --- Symbolic (unevaluated) ---

    @Test
    void maxNonListReturnsArg() throws Exception {
        fm.assertExec("42", "Max(42)");
    }

    @Test
    void maxSymbolReturnsSymbol() throws Exception {
        fm.assertExec("x", "Max(x)");
    }

    // --- Variable binding ---

    @Test
    void maxFromVariable() throws Exception {
        fm.assertExec("3", "{l = [3, 1, 2]}; Max(l)}");
    }

    // --- Composed ---

    @Test
    void maxOfRange() throws Exception {
        fm.assertExec("4", "Max(Range(1, 5))");
    }
}
