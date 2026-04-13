package io.flamemath.internal;

import org.junit.jupiter.api.Test;

import io.flamemath.internal.math.FlameInt;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class FlameIntAddSubTest {

    /**
     * Convert a FlameInt back to BigInteger for verification.
     * Uses the raw constructor to reconstruct the value from limbs.
     */
    private static BigInteger toBigInteger(FlameInt fi) throws Exception {
        var signumField = FlameInt.class.getDeclaredField("signum");
        var magsField = FlameInt.class.getDeclaredField("mags");
        signumField.setAccessible(true);
        magsField.setAccessible(true);

        int signum = signumField.getInt(fi);
        int[] mags = (int[]) magsField.get(fi);

        if (signum == 0) return BigInteger.ZERO;

        BigInteger result = BigInteger.ZERO;
        BigInteger base = BigInteger.ONE.shiftLeft(32);
        for (int limb : mags) {
            result = result.multiply(base).add(BigInteger.valueOf(limb & 0xFFFFFFFFL));
        }
        return signum < 0 ? result.negate() : result;
    }

    private void assertAdd(long a, long b) throws Exception {
        FlameInt result = new FlameInt(a).add(new FlameInt(b));
        BigInteger expected = BigInteger.valueOf(a).add(BigInteger.valueOf(b));
        assertEquals(expected, toBigInteger(result),
                a + " + " + b + " should equal " + expected);
    }

    // --- Basic positive ---

    @Test
    void addTwoSmallPositives() throws Exception {
        assertAdd(3, 5);
    }

    @Test
    void addZeroToPositive() throws Exception {
        assertAdd(0, 42);
    }

    @Test
    void addPositiveToZero() throws Exception {
        assertAdd(42, 0);
    }

    @Test
    void addZeros() throws Exception {
        assertAdd(0, 0);
    }

    // --- Carry propagation ---

    @Test
    void addCausesCarry() throws Exception {
        // 0xFFFFFFFF + 1 = 2^32
        assertAdd(0xFFFFFFFFL, 1);
    }

    @Test
    void addTwoMaxInts() throws Exception {
        assertAdd(0xFFFFFFFFL, 0xFFFFFFFFL);
    }

    @Test
    void addLargeValues() throws Exception {
        // Both need two limbs
        assertAdd(4294967300L, 4294967300L);
    }

    @Test
    void addCrossesLimbBoundary() throws Exception {
        assertAdd(0xFFFFFFFFL, 0xFFFFFFFFL);
    }

    // --- Unequal limb lengths ---

    @Test
    void addSingleLimbToDoubleLimb() throws Exception {
        assertAdd(5, 4294967300L);
    }

    @Test
    void addDoubleLimbToSingleLimb() throws Exception {
        assertAdd(4294967300L, 5);
    }

    // --- Negative + Negative (same sign, add magnitudes) ---

    @Test
    void addTwoNegatives() throws Exception {
        assertAdd(-3, -5);
    }

    @Test
    void addTwoLargeNegatives() throws Exception {
        assertAdd(-4294967300L, -4294967300L);
    }

    // --- Mixed signs (subtraction path) ---

    @Test
    void addPositiveAndNegativeSameMagnitude() throws Exception {
        assertAdd(5, -5);
    }

    @Test
    void addPositiveAndSmallerNegative() throws Exception {
        assertAdd(10, -3);
    }

    @Test
    void addPositiveAndLargerNegative() throws Exception {
        assertAdd(3, -10);
    }

    @Test
    void addNegativeAndPositive() throws Exception {
        assertAdd(-10, 3);
    }

    @Test
    void addNegativeAndZero() throws Exception {
        assertAdd(-42, 0);
    }

    @Test
    void addZeroAndNegative() throws Exception {
        assertAdd(0, -42);
    }

    // --- Edge cases ---

    @Test
    void addIntegerMaxValue() throws Exception {
        assertAdd(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Test
    void addIntegerMinValue() throws Exception {
        assertAdd(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    @Test
    void addLongMaxAndOne() throws Exception {
        assertAdd(Long.MAX_VALUE, 1);
    }

    @Test
    void addOne() throws Exception {
        assertAdd(1, 1);
    }
}
