package io.flamemath.internal;

import org.junit.jupiter.api.Test;

import io.flamemath.internal.math.FlameInt;

import static org.junit.jupiter.api.Assertions.*;

class FlameIntToStringTest {

    // --- Zero ---

    @Test
    void zeroFromInt() {
        assertEquals("0", new FlameInt(0).toString());
    }

    @Test
    void zeroFromLong() {
        assertEquals("0", new FlameInt(0L).toString());
    }

    // --- Small positives ---

    @Test
    void singleDigit() {
        assertEquals("7", new FlameInt(7).toString());
    }

    @Test
    void multiDigit() {
        assertEquals("12345", new FlameInt(12345).toString());
    }

    @Test
    void intMaxValue() {
        assertEquals("2147483647", new FlameInt(Integer.MAX_VALUE).toString());
    }

    // --- Small negatives ---

    @Test
    void negativeSingleDigit() {
        assertEquals("-7", new FlameInt(-7).toString());
    }

    @Test
    void negativeMultiDigit() {
        assertEquals("-12345", new FlameInt(-12345).toString());
    }

    @Test
    void intMinValue() {
        assertEquals("-2147483648", new FlameInt(Integer.MIN_VALUE).toString());
    }

    // --- Two-limb values (exceeds 2^32) ---

    @Test
    void justAboveUint32() {
        assertEquals("4294967296", new FlameInt(4294967296L).toString());
    }

    @Test
    void twoLimbValue() {
        assertEquals("8589934601", new FlameInt(8589934601L).toString());
    }

    @Test
    void longMaxValue() {
        assertEquals("9223372036854775807", new FlameInt(Long.MAX_VALUE).toString());
    }

    @Test
    void longMinValue() {
        assertEquals("-9223372036854775808", new FlameInt(Long.MIN_VALUE).toString());
    }

    @Test
    void negativeTwoLimb() {
        assertEquals("-4294967300", new FlameInt(-4294967300L).toString());
    }

    // --- Padding (remainder < 10^9 in a non-leading group) ---

    @Test
    void requiresPadding() {
        // 4294967296 = 4 * 10^9 + 294967296
        // The lower group "294967296" is 9 digits, no padding needed
        assertEquals("4294967296", new FlameInt(4294967296L).toString());
    }

    @Test
    void requiresZeroPadding() {
        // 1000000000 = 1 * 10^9 + 0 → "1" + "000000000"
        assertEquals("1000000000", new FlameInt(1_000_000_000).toString());
    }

    @Test
    void requiresZeroPaddingLarge() {
        // 10000000001 = 10 * 10^9 + 1 → "10" + "000000001"
        assertEquals("10000000001", new FlameInt(10_000_000_001L).toString());
    }

    // --- Arithmetic results ---

    @Test
    void additionResult() {
        FlameInt a = new FlameInt(4294967295L);
        FlameInt b = new FlameInt(1);
        assertEquals("4294967296", a.add(b).toString());
    }

    @Test
    void multiplicationResult() {
        FlameInt a = new FlameInt(1000000);
        FlameInt b = new FlameInt(1000000);
        assertEquals("1000000000000", a.mul(b).toString());
    }

    @Test
    void largeMultiplicationResult() {
        FlameInt a = new FlameInt(Long.MAX_VALUE);
        FlameInt b = new FlameInt(2);
        assertEquals("18446744073709551614", a.mul(b).toString());
    }

    // --- Constants ---

    @Test
    void constantOne() {
        assertEquals("1", FlameInt.ONE.toString());
    }

    @Test
    void constantZero() {
        assertEquals("0", FlameInt.ZERO.toString());
    }
}
