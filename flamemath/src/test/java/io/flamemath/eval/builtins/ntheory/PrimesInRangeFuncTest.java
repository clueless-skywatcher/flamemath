package io.flamemath.eval.builtins.ntheory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.flamemath.FlameTestingUtils;
import io.flamemath.exceptions.FlameArityException;

class PrimesInRangeFuncTest {

    private final FlameTestingUtils fm = new FlameTestingUtils();

    // --- Name ---

    @Test
    void name() {
        assertEquals("PrimesInRange", new PrimesInRangeFunc().name());
    }

    // --- Arity ---

    @Test
    void zeroArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("PrimesInRange()"));
    }

    @Test
    void oneArgThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("PrimesInRange(10)"));
    }

    @Test
    void threeArgsThrows() {
        assertThrows(FlameArityException.class, () -> fm.execute("PrimesInRange(1, 10, 20)"));
    }

    // --- Non-integer arguments ---

    @Test
    void nonIntegerFirstArgThrows() {
        assertThrows(Exception.class, () -> fm.execute("PrimesInRange(1.5, 10)"));
    }

    @Test
    void nonIntegerSecondArgThrows() {
        assertThrows(Exception.class, () -> fm.execute("PrimesInRange(1, 10.5)"));
    }

    // --- Basic ranges ---

    @Test
    void primesUpToTen() throws Exception {
        fm.assertExec("[2, 3, 5, 7]", "PrimesInRange(1, 10)");
    }

    @Test
    void primesUpToTwenty() throws Exception {
        fm.assertExec("[2, 3, 5, 7, 11, 13, 17, 19]", "PrimesInRange(1, 20)");
    }

    @Test
    void primesFromTenToThirty() throws Exception {
        fm.assertExec("[11, 13, 17, 19, 23, 29]", "PrimesInRange(10, 30)");
    }

    // --- Exact boundaries ---

    @Test
    void rangeStartingAtPrime() throws Exception {
        fm.assertExec("[7, 11, 13]", "PrimesInRange(7, 13)");
    }

    @Test
    void rangeEndingAtPrime() throws Exception {
        fm.assertExec("[23, 29]", "PrimesInRange(22, 29)");
    }

    @Test
    void singlePrimeRange() throws Exception {
        fm.assertExec("[7]", "PrimesInRange(7, 7)");
    }

    @Test
    void singleCompositeRange() throws Exception {
        fm.assertExec("[]", "PrimesInRange(4, 4)");
    }

    // --- Edge cases ---

    @Test
    void rangeFromZero() throws Exception {
        fm.assertExec("[2, 3, 5]", "PrimesInRange(0, 5)");
    }

    @Test
    void rangeFromNegative() throws Exception {
        fm.assertExec("[2, 3, 5]", "PrimesInRange(-10, 5)");
    }

    @Test
    void rangeWithNoPrimes() throws Exception {
        fm.assertExec("[]", "PrimesInRange(24, 28)");
    }

    @Test
    void rangeFromTwoToTwo() throws Exception {
        fm.assertExec("[2]", "PrimesInRange(2, 2)");
    }

    // --- Larger range ---

    @Test
    void primesInHundredRange() throws Exception {
        fm.assertExec("[101, 103, 107, 109, 113]", "PrimesInRange(100, 113)");
    }

    @Test
    void primesBeyondInitialSieve() throws Exception {
        fm.assertExec("[1009, 1013]", "PrimesInRange(1008, 1013)");
    }
}
