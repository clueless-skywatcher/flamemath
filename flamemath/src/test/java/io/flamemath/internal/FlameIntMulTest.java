package io.flamemath.internal;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class FlameIntMulTest {

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

    private void assertMul(long a, long b) throws Exception {
        FlameInt result = new FlameInt(a).mul(new FlameInt(b));
        BigInteger expected = BigInteger.valueOf(a).multiply(BigInteger.valueOf(b));
        assertEquals(expected, toBigInteger(result),
                a + " * " + b + " should equal " + expected);
    }

    // --- Basic ---

    @Test
    void mulTwoSmall() throws Exception {
        assertMul(3, 5);
    }

    @Test
    void mulByOne() throws Exception {
        assertMul(42, 1);
    }

    @Test
    void mulOneBy() throws Exception {
        assertMul(1, 42);
    }

    @Test
    void mulByZero() throws Exception {
        assertMul(42, 0);
    }

    @Test
    void mulZeroBy() throws Exception {
        assertMul(0, 42);
    }

    @Test
    void mulZeros() throws Exception {
        assertMul(0, 0);
    }

    // --- Carry propagation ---

    @Test
    void mulCausesCarry() throws Exception {
        assertMul(0xFFFFFFFFL, 2);
    }

    @Test
    void mulTwoMaxUint32() throws Exception {
        assertMul(0xFFFFFFFFL, 0xFFFFFFFFL);
    }

    @Test
    void mulLargeValues() throws Exception {
        assertMul(4294967300L, 4294967300L);
    }

    // --- Unequal limb lengths ---

    @Test
    void mulSingleLimbByDoubleLimb() throws Exception {
        assertMul(5, 4294967300L);
    }

    @Test
    void mulDoubleLimbBySingleLimb() throws Exception {
        assertMul(4294967300L, 5);
    }

    // --- Signs ---

    @Test
    void mulPositiveByNegative() throws Exception {
        assertMul(3, -5);
    }

    @Test
    void mulNegativeByPositive() throws Exception {
        assertMul(-3, 5);
    }

    @Test
    void mulTwoNegatives() throws Exception {
        assertMul(-3, -5);
    }

    @Test
    void mulNegativeByZero() throws Exception {
        assertMul(-42, 0);
    }

    @Test
    void mulLargeNegatives() throws Exception {
        assertMul(-4294967300L, -4294967300L);
    }

    // --- Edge cases ---

    @Test
    void mulIntMaxValue() throws Exception {
        assertMul(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Test
    void mulPowersOfTwo() throws Exception {
        // 2^16 * 2^16 = 2^32
        assertMul(65536, 65536);
    }

    @Test
    void mulLargeBySmall() throws Exception {
        assertMul(Long.MAX_VALUE, 2);
    }

    @Test
    void mulSmallByLarge() throws Exception {
        assertMul(2, Long.MAX_VALUE);
    }
}
