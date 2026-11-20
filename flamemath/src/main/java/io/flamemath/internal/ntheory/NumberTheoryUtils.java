package io.flamemath.internal.ntheory;

import java.math.BigInteger;
import java.util.List;

import io.flamemath.internal.math.FlameInt;

public class NumberTheoryUtils {
    private static final List<Long> MILLER_RABIN_BASES = List.of(
        2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L
    );

    private static final List<FlameInt> MILLER_RABIN_BASES_FI = List.of(
        new FlameInt(2), new FlameInt(3), new FlameInt(5), new FlameInt(7),
        new FlameInt(11), new FlameInt(13), new FlameInt(17), new FlameInt(19),
        new FlameInt(23), new FlameInt(29), new FlameInt(31), new FlameInt(37)
    );

    public static boolean millerRabin(long num) {
        if (num <= 1) return false;
        if (num == 2 || num == 3) return true;
        if (num % 2 == 0) return false;
        if (MILLER_RABIN_BASES.contains(num)) return true;

        long s = 0;
        long newNum = num - 1;
        while (newNum > 0 && newNum % 2 == 0) {
            s += 1;
            newNum = newNum / 2;
        }
        long d = newNum;

        int baseI = 0;
        while (baseI < MILLER_RABIN_BASES.size()) {
            long a = MILLER_RABIN_BASES.get(baseI);
            long x = modPow(a, d, num);
            if (x == 1 || x == num - 1) {
                baseI++;
                continue;
            }
            boolean moveToNext = false;
            for (int r = 1; r < s; r++) {
                x = modPow(x, 2, num);
                if (x == num - 1) {
                    baseI++;
                    moveToNext = true;
                    break;
                }
            }
            if (!moveToNext)
                return false;
        }

        return true;
    }

    public static boolean millerRabin(FlameInt num) {
        if (num.compareTo(FlameInt.ONE) <= 0) return false;
        if (num.equals(FlameInt.TWO) || num.equals(new FlameInt(3))) return true;
        if (num.mod(FlameInt.TWO).isZero()) return false;

        for (FlameInt base : MILLER_RABIN_BASES_FI) {
            if (num.equals(base)) return true;
        }

        // Write num-1 as 2^s * d
        FlameInt numMinus1 = num.subtract(FlameInt.ONE);
        long s = 0;
        FlameInt d = numMinus1;
        while (!d.isZero() && d.mod(FlameInt.TWO).isZero()) {
            s++;
            d = d.divide(FlameInt.TWO);
        }

        int baseI = 0;
        while (baseI < MILLER_RABIN_BASES_FI.size()) {
            FlameInt a = MILLER_RABIN_BASES_FI.get(baseI);
            FlameInt x = a.modPow(d, num);
            if (x.equals(FlameInt.ONE) || x.equals(numMinus1)) {
                baseI++;
                continue;
            }
            boolean moveToNext = false;
            for (int r = 1; r < s; r++) {
                x = x.modPow(FlameInt.TWO, num);
                if (x.equals(numMinus1)) {
                    baseI++;
                    moveToNext = true;
                    break;
                }
            }
            if (!moveToNext)
                return false;
        }

        return true;
    }

    private static long modPow(long a, long d, long p) {
        BigInteger base = BigInteger.valueOf(a);
        BigInteger exp = BigInteger.valueOf(d);
        BigInteger mod = BigInteger.valueOf(p);
        return base.modPow(exp, mod).longValue();
    }
}
