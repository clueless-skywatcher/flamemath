package io.flamemath.eval.builtins.ntheory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

class LCMFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("LCM", new LCMFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("LCM()"));
    }

    @Test
    void oneArgReturnsSelf() throws Exception {
        fm.assertExec("12", "LCM(12)");
    }

    @Test
    void threeArgs() throws Exception {
        fm.assertExec("24", "LCM(4, 6, 8)");
    }

    @Test
    void fourArgs() throws Exception {
        fm.assertExec("60", "LCM(3, 4, 5, 6)");
    }

    // --- Basic cases ---

    @Test
    void basicLCM() throws Exception {
        fm.assertExec("24", "LCM(12, 8)");
    }

    @Test
    void coprime() throws Exception {
        fm.assertExec("91", "LCM(7, 13)");
    }

    @Test
    void sameArgs() throws Exception {
        fm.assertExec("5", "LCM(5, 5)");
    }

    @Test
    void oneAndN() throws Exception {
        fm.assertExec("100", "LCM(1, 100)");
    }

    @Test
    void oneDividesOther() throws Exception {
        fm.assertExec("18", "LCM(6, 18)");
    }

    @Test
    void commutative() throws Exception {
        fm.assertExec("LCM(12, 8)", "LCM(8, 12)");
    }

    // --- Zero ---

    @Test
    void lcmWithZero() throws Exception {
        fm.assertExec("0", "LCM(0, 5)");
    }

    @Test
    void lcmZeroReversed() throws Exception {
        fm.assertExec("0", "LCM(5, 0)");
    }

    @Test
    void lcmBothZero() throws Exception {
        fm.assertExec("0", "LCM(0, 0)");
    }

    // --- Negative arguments (LCM is always non-negative) ---

    @Test
    void negativeFirst() throws Exception {
        fm.assertExec("24", "LCM(-12, 8)");
    }

    @Test
    void negativeSecond() throws Exception {
        fm.assertExec("24", "LCM(12, -8)");
    }

    @Test
    void bothNegative() throws Exception {
        fm.assertExec("24", "LCM(-12, -8)");
    }

    // --- Large numbers ---

    @Test
    void largeNumbers() throws Exception {
        fm.assertExec("13548070123626141", "LCM(123456789, 987654321)");
    }
}
