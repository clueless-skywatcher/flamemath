package io.flamemath.internal;

import org.junit.jupiter.api.Test;

import io.flamemath.internal.math.FlameInt;

import static org.junit.jupiter.api.Assertions.*;

class FlameIntPowTest {

    @Test
    void powZeroExponent() {
        assertEquals("1", new FlameInt(5).pow(0).toString());
    }

    @Test
    void powOneExponent() {
        assertEquals("5", new FlameInt(5).pow(1).toString());
    }

    @Test
    void powSmall() {
        assertEquals("1024", new FlameInt(2).pow(10).toString());
    }

    @Test
    void powCubed() {
        assertEquals("27", new FlameInt(3).pow(3).toString());
    }

    @Test
    void powZeroBase() {
        assertEquals("0", new FlameInt(0).pow(5).toString());
    }

    @Test
    void powOneBase() {
        assertEquals("1", new FlameInt(1).pow(100).toString());
    }

    @Test
    void powNegativeBaseEvenExponent() {
        assertEquals("4", new FlameInt(-2).pow(2).toString());
    }

    @Test
    void powNegativeBaseOddExponent() {
        assertEquals("-8", new FlameInt(-2).pow(3).toString());
    }

    @Test
    void powLargeResult() {
        assertEquals("1267650600228229401496703205376", new FlameInt(2).pow(100).toString());
    }

    @Test
    void powNegativeExponentThrows() {
        assertThrows(ArithmeticException.class, () -> new FlameInt(2).pow(-1));
    }
}
