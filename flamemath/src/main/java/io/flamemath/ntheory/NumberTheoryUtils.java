package io.flamemath.ntheory;

import java.math.BigInteger;
import java.util.List;

public class NumberTheoryUtils {
    private static final List<Long> MILLER_RABIN_BASES = List.of(
        2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L
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

    private static long modPow(long a, long d, long p) {
        BigInteger base = BigInteger.valueOf(a);
        BigInteger exp = BigInteger.valueOf(d);
        BigInteger mod = BigInteger.valueOf(p);
        return base.modPow(exp, mod).longValue();
    }
}
