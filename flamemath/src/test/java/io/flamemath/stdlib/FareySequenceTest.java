package io.flamemath.stdlib;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameStdlibTestUtils;

class FareySequenceTest {

    private final FlameStdlibTestUtils fm = new FlameStdlibTestUtils();

    // --- Basic cases ---

    @Test
    void orderOne() throws Exception {
        fm.assertExec("[0, 1]", "FareySequence(1)");
    }

    @Test
    void orderTwo() throws Exception {
        fm.assertExec("[0, 1/2, 1]", "FareySequence(2)");
    }

    @Test
    void orderThree() throws Exception {
        fm.assertExec("[0, 1/3, 1/2, 2/3, 1]", "FareySequence(3)");
    }

    @Test
    void orderFour() throws Exception {
        fm.assertExec("[0, 1/4, 1/3, 1/2, 2/3, 3/4, 1]", "FareySequence(4)");
    }

    @Test
    void orderFive() throws Exception {
        fm.assertExec(
            "[0, 1/5, 1/4, 1/3, 2/5, 1/2, 3/5, 2/3, 3/4, 4/5, 1]",
            "FareySequence(5)"
        );
    }

    // --- Length property: |F_n| = 1 + sum_{k=1..n} phi(k) ---

    @Test
    void orderSixLength() throws Exception {
        fm.assertExec("13", "Len(FareySequence(6))");
    }

    @Test
    void orderSevenLength() throws Exception {
        fm.assertExec("19", "Len(FareySequence(7))");
    }

    // --- Composed ---

    @Test
    void fromVariable() throws Exception {
        fm.assertExec("[0, 1/2, 1]", "{ n = 2; FareySequence(n) }");
    }

    // --- Symbolic / invalid (unevaluated) ---

    @Test
    void symbolicReturnsRaw() throws Exception {
        fm.assertExec("FareySequence(x)", "FareySequence(x)");
    }

    @Test
    void zeroReturnsRaw() throws Exception {
        fm.assertExec("FareySequence(0)", "FareySequence(0)");
    }

    @Test
    void negativeReturnsRaw() throws Exception {
        fm.assertExec("FareySequence(-3)", "FareySequence(-3)");
    }
}
