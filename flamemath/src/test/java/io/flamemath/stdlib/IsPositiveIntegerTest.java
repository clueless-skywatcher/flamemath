package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class IsPositiveIntegerTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Positive integers ---

    @Test
    void smallPositive() throws Exception {
        fm.assertExec("True", "IsPositiveInteger(1)");
    }

    @Test
    void largePositive() throws Exception {
        fm.assertExec("True", "IsPositiveInteger(1000000)");
    }

    // --- Zero (included by the x >= 0 check) ---

    @Test
    void zeroAccepted() throws Exception {
        fm.assertExec("True", "IsPositiveInteger(0)");
    }

    // --- Negative integers ---

    @Test
    void negativeInteger() throws Exception {
        fm.assertExec("False", "IsPositiveInteger(-1)");
    }

    @Test
    void largeNegative() throws Exception {
        fm.assertExec("False", "IsPositiveInteger(-1000000)");
    }

    // --- Non-integer numerics ---

    @Test
    void positiveRealRejected() throws Exception {
        fm.assertExec("False", "IsPositiveInteger(3.14)");
    }

    @Test
    void negativeRealRejected() throws Exception {
        fm.assertExec("False", "IsPositiveInteger(-2.5)");
    }

    // --- Non-numeric arguments ---

    @Test
    void stringRejected() throws Exception {
        fm.assertExec("False", "IsPositiveInteger(\"5\")");
    }

    @Test
    void listRejected() throws Exception {
        fm.assertExec("False", "IsPositiveInteger([1])");
    }

    @Test
    void symbolRejected() throws Exception {
        fm.assertExec("False", "IsPositiveInteger(x)");
    }

    // --- Composed ---

    @Test
    void fromExpression() throws Exception {
        fm.assertExec("True", "IsPositiveInteger(2 + 3)");
    }

    @Test
    void fromVariable() throws Exception {
        fm.assertExec("True", "{n = 42; IsPositiveInteger(n)}");
    }
}
