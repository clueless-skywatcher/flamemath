package io.flamemath.eval.builtins.ntheory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

class GCDFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("GCD", new GCDFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("GCD()"));
    }

    @Test
    void oneArgReturnsSelf() throws Exception {
        fm.assertExec("12", "GCD(12)");
    }

    @Test
    void threeArgs() throws Exception {
        fm.assertExec("4", "GCD(12, 8, 4)");
    }

    @Test
    void fourArgs() throws Exception {
        fm.assertExec("3", "GCD(12, 9, 6, 3)");
    }

    // --- Basic cases ---

    @Test
    void basicGCD() throws Exception {
        fm.assertExec("4", "GCD(12, 8)");
    }

    @Test
    void coprime() throws Exception {
        fm.assertExec("1", "GCD(7, 13)");
    }

    @Test
    void sameArgs() throws Exception {
        fm.assertExec("5", "GCD(5, 5)");
    }

    @Test
    void oneAndN() throws Exception {
        fm.assertExec("1", "GCD(1, 100)");
    }

    @Test
    void oneDividesOther() throws Exception {
        fm.assertExec("6", "GCD(6, 18)");
    }

    @Test
    void commutative() throws Exception {
        fm.assertExec("GCD(12, 8)", "GCD(8, 12)");
    }

    // --- Zero ---

    @Test
    void gcdWithZero() throws Exception {
        fm.assertExec("5", "GCD(0, 5)");
    }

    @Test
    void gcdZeroReversed() throws Exception {
        fm.assertExec("5", "GCD(5, 0)");
    }

    @Test
    void gcdBothZero() throws Exception {
        fm.assertExec("0", "GCD(0, 0)");
    }

    // --- Negative arguments ---

    @Test
    void negativeFirst() throws Exception {
        fm.assertExec("4", "GCD(-12, 8)");
    }

    @Test
    void negativeSecond() throws Exception {
        fm.assertExec("4", "GCD(12, -8)");
    }

    @Test
    void bothNegative() throws Exception {
        fm.assertExec("4", "GCD(-12, -8)");
    }

    // --- Large numbers ---

    @Test
    void largeNumbers() throws Exception {
        fm.assertExec("9", "GCD(123456789, 987654321)");
    }
}
