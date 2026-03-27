package io.flamemath.internal;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class FlameIntDivTest {

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

    private static FlameInt factorial(int n) {
        FlameInt result = FlameInt.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.mul(new FlameInt(i));
        }
        return result;
    }

    @Test
    void divideSmall() throws Exception {
        FlameInt a = new FlameInt(100);
        FlameInt b = new FlameInt(7);
        assertEquals("14", a.divide(b).toString());
        assertEquals("2", a.mod(b).toString());
    }

    @Test
    void divideFactorials() throws Exception {
        // 20! / 10! should be exact via BigInteger check
        FlameInt f20 = factorial(20);
        FlameInt f10 = factorial(10);
        BigInteger expected = toBigInteger(f20).divide(toBigInteger(f10));
        assertEquals(expected, toBigInteger(f20.divide(f10)));
    }

    @Test
    void modFactorials() throws Exception {
        FlameInt f20 = factorial(20);
        FlameInt f10 = factorial(10);
        BigInteger bF20 = toBigInteger(f20);
        BigInteger bF10 = toBigInteger(f10);
        assertEquals(bF20.mod(bF10), toBigInteger(f20.mod(f10)));
    }

    @Test
    void gcdFactorials() throws Exception {
        FlameInt f20 = factorial(20);
        FlameInt f10 = factorial(10);
        BigInteger expected = toBigInteger(f20).gcd(toBigInteger(f10));
        assertEquals(expected, toBigInteger(f20.gcd(f10)));
    }

    @Test
    void gcdBinomial100_50() throws Exception {
        // numerator = Product(51..100), denominator = Product(1..50) = 50!
        FlameInt num = FlameInt.ONE;
        for (int i = 51; i <= 100; i++) num = num.mul(new FlameInt(i));
        FlameInt den = factorial(50);

        BigInteger bNum = toBigInteger(num);
        BigInteger bDen = toBigInteger(den);

        // GCD should equal den (since C(100,50) is an integer)
        BigInteger expectedGcd = bNum.gcd(bDen);
        FlameInt actualGcd = num.gcd(den);
        assertEquals(expectedGcd, toBigInteger(actualGcd), "GCD mismatch");

        // Division after GCD should give C(100,50)
        BigInteger expectedBinom = bNum.divide(bDen);
        FlameInt actualBinom = num.divide(den);
        assertEquals(expectedBinom, toBigInteger(actualBinom), "Binomial(100,50) mismatch");
    }

    @Test
    void divLargeByLarge() throws Exception {
        // 100! / 50!
        FlameInt f100 = factorial(100);
        FlameInt f50 = factorial(50);
        BigInteger expected = toBigInteger(f100).divide(toBigInteger(f50));
        assertEquals(expected, toBigInteger(f100.divide(f50)));
    }

    @Test
    void modLargeByLarge() throws Exception {
        FlameInt f100 = factorial(100);
        FlameInt f50 = factorial(50);
        BigInteger expected = toBigInteger(f100).mod(toBigInteger(f50));
        assertEquals(expected, toBigInteger(f100.mod(f50)));
    }
}
