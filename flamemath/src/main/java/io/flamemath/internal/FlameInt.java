package io.flamemath.internal;

import java.util.Arrays;
import java.util.List;

public class FlameInt {
    private final int signum; // Sign of the integer
    // Each element of the magnitude contributes 2^32
    // So [2, 3, 26] will mean 2 * 2^64 + 3 * 2^32 + 26
    // Basically FlameInt is like a base-2^32 integer
    private final int[] mags;

    public static final FlameInt ONE = new FlameInt(1);
    public static final FlameInt TWO = new FlameInt(2);
    public static final FlameInt ZERO = new FlameInt(0);
    public static final FlameInt MINUS_ONE = new FlameInt(-1);

    public FlameInt(int signum, int[] mags) {
        this.signum = signum;
        this.mags = mags;
    }

    public FlameInt(int value) {
        this.signum = value < 0 ? -1 : (value > 0 ? 1 : 0);
        long abs = value < 0 ? -(long) value : value;
        this.mags = new int[] { (int) abs };
    }

    public FlameInt(long value) {
        this.signum = value < 0 ? -1 : (value > 0 ? 1 : 0);
        long abs = value == Long.MIN_VALUE ? Long.MIN_VALUE : (value < 0 ? -value : value);
        int lower32 = (int) abs;
        if ((abs >>> 32) == 0) {
            this.mags = new int[] { lower32 };
        } else {
            int higher32 = (int) (abs >>> 32);
            this.mags = new int[] { higher32, lower32 };
        }
    }

    @Override
    public String toString() {
        if (signum == 0)
            return "0";

        String digits = "";
        int[] currentMags = mags;

        while (!Arrays.equals(currentMags, ZERO.mags)) {
            List<int[]> quotRem = divideBySingleLimb(currentMags, 1_000_000_000);
            currentMags = quotRem.get(0);
            int rem = quotRem.get(1)[0];
            if (Arrays.equals(currentMags, ZERO.mags)) {
                digits = Integer.toString(rem) + digits;
            } else {
                digits = zeroPad(rem, 9) + digits;
            }
        }

        if (signum == -1)
            digits = "-" + digits;
        return digits;
    }

    private String zeroPad(int num, int ndigits) {
        String stringRepr = Integer.toString(num);
        int numZeros = ndigits - stringRepr.length();
        return "0".repeat(numZeros) + stringRepr;
    }

    public FlameInt add(FlameInt b) {
        if (this.signum == 0) {
            return b;
        }
        if (b.signum == 0) {
            return this;
        }

        if (this.signum * b.signum > 0) {
            return new FlameInt(this.signum, addMagnitudes(this.mags, b.mags));
        }
        int comparison = compareMagnitudes(this.mags, b.mags);
        if (comparison == 0)
            return ZERO;
        FlameInt smaller = comparison == -1 ? this : b;
        FlameInt larger = comparison == -1 ? b : this;
        return new FlameInt(larger.signum,
                subtractMagnitudes(larger.mags, smaller.mags));
    }

    private int[] subtractMagnitudes(int[] larger, int[] smaller) {
        int last = larger.length;
        int[] result = new int[larger.length];
        int borrow = 0;

        for (int i = 0; i < last; i++) {
            int limbL = (larger.length - i - 1 >= 0) ? larger[larger.length - i - 1] : 0;
            int limbS = (smaller.length - i - 1 >= 0) ? smaller[smaller.length - i - 1] : 0;
            long diff = (limbL & 0xFFFFFFFFL) - (limbS & 0xFFFFFFFFL) - borrow;
            if (diff < 0) {
                diff += 0x100000000L;
                borrow = 1;
            } else {
                borrow = 0;
            }
            result[last - i - 1] = (int) diff;
        }

        int start = 0;
        while (start < result.length - 1 && result[start] == 0) {
            start++;
        }
        if (start > 0) {
            return Arrays.copyOfRange(result, start, result.length);
        }

        return result;
    }

    private int[] addMagnitudes(int[] a, int[] b) {
        int last = Math.max(a.length, b.length);
        int[] result = new int[last + 1]; // +1 due to a possible carry
        int carry = 0;
        for (int i = 0; i < last; i++) {
            int limbA = (a.length - i - 1 >= 0) ? a[a.length - i - 1] : 0;
            int limbB = (b.length - i - 1 >= 0) ? b[b.length - i - 1] : 0;

            long limbSum = (limbA & 0xFFFFFFFFL) + (limbB & 0xFFFFFFFFL) + carry;
            result[last - i] = (int) limbSum;
            carry = (int) (limbSum >>> 32);
        }

        result[0] = carry;
        if (result[0] == 0) {
            return Arrays.copyOfRange(result, 1, result.length);
        }
        return result;
    }

    private int compareMagnitudes(int[] a, int[] b) {
        if (a.length > b.length)
            return 1;
        if (a.length < b.length)
            return -1;
        for (int i = 0; i < a.length; i++) {
            int compare = Integer.compareUnsigned(a[i], b[i]);
            if (compare != 0)
                return compare > 0 ? 1 : -1;
        }
        return 0;
    }

    public FlameInt mul(FlameInt b) {
        if (this.signum * b.signum == 0)
            return ZERO;
        return schoolBookMultiply(this, b);
    }

    public List<int[]> quotRem(int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Divide by zero");
        }

        List<int[]> quotRem = divideBySingleLimb(this.mags, divisor);
        return quotRem;
    }

    private static List<int[]> divideByKnuthD(int[] u, int[] v) {
        int n = v.length;
        int m = u.length - n;

        // Normalization — prepend 0 to u BEFORE shifting to catch overflow
        int shift = Integer.numberOfLeadingZeros(v[0]);
        int[] newU = new int[u.length + 1];
        System.arraycopy(u, 0, newU, 1, u.length);
        u = shiftLeft(newU, shift);
        v = shiftLeft(v, shift);

        int[] q = new int[m + 1];

        for (int j = 0; j <= m; j++) {
            long uHi = ((long) u[j] << 32) | (u[j + 1] & 0xFFFFFFFFL);
            long qHat;
            if (u[j] == v[0]) {
                qHat = 0xFFFFFFFFL;
            } else {
                qHat = Long.divideUnsigned(uHi, v[0] & 0xFFFFFFFFL);
            }

            long rHat = uHi - qHat * (v[0] & 0xFFFFFFFFL);

            while (qHat >= 0x100000000L || Long.compareUnsigned(qHat * (v[1] & 0xFFFFFFFFL), (rHat << 32) | (u[j + 2] & 0xFFFFFFFFL)) > 0) {
                qHat -= 1;
                rHat += (v[0] & 0xFFFFFFFFL);
                if (rHat >= 0x100000000L) {
                    break;
                }
            }

            long borrow = 0;

            for (int i = n - 1; i >= 0; i--) {
                long product = qHat * (v[i] & 0xFFFFFFFFL);
                long diff = (u[j + 1 + i] & 0xFFFFFFFFL) - (product & 0xFFFFFFFFL) - borrow;
                u[j + 1 + i] = (int) diff;
                borrow = (product >>> 32) - (diff >> 32);
            }

            long diff = (u[j] & 0xFFFFFFFFL) - borrow;
            u[j] = (int) diff;

            q[j] = (int) qHat;
            if (diff < 0) {
                q[j] -= 1;
                long carry = 0;
                for (int i = n - 1; i >= 0; i--) {
                    long sum = (u[j + 1 + i] & 0xFFFFFFFFL) + (v[i] & 0xFFFFFFFFL) + carry;
                    u[j + 1 + i] = (int) sum;
                    carry = sum >>> 32;
                }
                u[j] += (int) carry;
            }
        }

        int[] remainder = shiftRight(Arrays.copyOfRange(u, m + 1, m + n + 1), shift);
        int[] quotient = stripLeadingZeros(q);

        return List.of(quotient, remainder);
    }

    private static int[] shiftLeft(int[] v, int shift) {
        if (shift == 0)
            return v.clone();
        int n = v.length;
        int[] result = new int[n];
        int complement = 32 - shift;
        for (int i = 0; i < n - 1; i++) {
            result[i] = (v[i] << shift) | (v[i + 1] >>> complement);
        }
        result[n - 1] = v[n - 1] << shift;
        return result;
    }

    private static int[] shiftRight(int[] v, int shift) {
        if (shift == 0)
            return v.clone();
        int n = v.length;
        int[] result = new int[n];
        int complement = 32 - shift;
        for (int i = n - 1; i > 0; i--) {
            result[i] = (v[i] >>> shift) | (v[i - 1] << complement);
        }
        result[0] = v[0] >>> shift;
        return result;
    }

    private static List<int[]> divideBySingleLimb(int[] dividend, int divisor) {
        int[] quotient = new int[dividend.length];
        int remainder = 0;
        long dLong = divisor & 0xFFFFFFFFL;

        for (int i = 0; i < dividend.length; i++) {
            long current = ((long) remainder << 32) | (dividend[i] & 0xFFFFFFFFL);
            quotient[i] = (int) Long.divideUnsigned(current, dLong);
            remainder = (int) Long.remainderUnsigned(current, dLong);
        }

        int start = 0;
        while (start < quotient.length - 1 && quotient[start] == 0) {
            start++;
        }
        if (start > 0) {
            quotient = Arrays.copyOfRange(quotient, start, quotient.length);
        }

        return List.of(quotient, new int[] { remainder });
    }

    private static FlameInt schoolBookMultiply(FlameInt a, FlameInt b) {
        int[] magA = a.mags;
        int[] magB = b.mags;

        int finalSgn = a.signum * b.signum;

        int[] result = new int[magA.length + magB.length];

        for (int i = magA.length - 1; i >= 0; i--) {
            long carry = 0;
            for (int j = magB.length - 1; j >= 0; j--) {
                long prod = (magA[i] & 0xFFFFFFFFL) * (magB[j] & 0xFFFFFFFFL)
                        + (result[i + j + 1] & 0xFFFFFFFFL) + carry;
                result[i + j + 1] = (int) prod;
                carry = prod >>> 32;
            }
            result[i] += (int) carry;
        }

        int start = 0;
        while (start < result.length - 1 && result[start] == 0) {
            start++;
        }
        if (start > 0) {
            result = Arrays.copyOfRange(result, start, result.length);
        }

        return new FlameInt(finalSgn, result);
    }

    public FlameInt negate() {
        return new FlameInt(-signum, mags);
    }

    public boolean isZero() {
        return this.signum == 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FlameInt fi)
            return this.signum == fi.signum
                    && Arrays.equals(mags, fi.mags);
        return false;
    }

    public boolean isNegative() {
        return this.signum < 0;
    }

    public boolean isOne() {
        return this.equals(ONE);
    }

    public int signum() {
        return this.signum;
    }

    @Override
    public int hashCode() {
        return 31 * signum + Arrays.hashCode(mags);
    }

    public FlameInt abs() {
        if (signum >= 0)
            return this;
        return new FlameInt(1, mags);
    }

    public FlameInt subtract(FlameInt b) {
        return this.add(b.negate());
    }

    public int compareTo(FlameInt other) {
        if (this.signum != other.signum) {
            return Integer.compare(this.signum, other.signum);
        }
        if (this.signum == 0)
            return 0;
        int magCmp = compareMagnitudes(this.mags, other.mags);
        return this.signum > 0 ? magCmp : -magCmp;
    }

    public boolean fitsInLong() {
        if (signum == 0) return true;
        if (mags.length == 1) return true;
        if (mags.length > 2) return false;
        // mags.length == 2: check if value fits in long range
        long unsigned = ((mags[0] & 0xFFFFFFFFL) << 32) | (mags[1] & 0xFFFFFFFFL);
        if (signum > 0) return unsigned >= 0;
        return unsigned >= 0 || unsigned == Long.MIN_VALUE;
    }

    public long toLong() {
        if (signum == 0)
            return 0L;
        long result;
        if (mags.length == 1) {
            result = mags[0] & 0xFFFFFFFFL;
        } else if (mags.length == 2) {
            result = ((mags[0] & 0xFFFFFFFFL) << 32) | (mags[1] & 0xFFFFFFFFL);
        } else {
            throw new ArithmeticException("FlameInt too large to convert to long");
        }
        if (signum < 0) {
            result = -result;
            if (result > 0)
                throw new ArithmeticException("FlameInt too large to convert to long");
        } else {
            if (result < 0)
                throw new ArithmeticException("FlameInt too large to convert to long");
        }
        return result;
    }

    public double toDouble() {
        if (signum == 0)
            return 0.0;
        double result = 0.0;
        for (int limb : mags) {
            result = result * 0x100000000L + (limb & 0xFFFFFFFFL);
        }
        return signum < 0 ? -result : result;
    }

    public FlameInt(String s) {
        if (s.isEmpty())
            throw new IllegalArgumentException("Empty string");

        boolean negative = false;
        int start = 0;
        if (s.charAt(0) == '-') {
            negative = true;
            start = 1;
        } else if (s.charAt(0) == '+') {
            start = 1;
        }

        // Skip leading zeros
        while (start < s.length() - 1 && s.charAt(start) == '0') {
            start++;
        }

        String digits = s.substring(start);
        if (digits.equals("0")) {
            this.signum = 0;
            this.mags = new int[] { 0 };
            return;
        }

        // Build by repeated multiply-by-10^9 and add
        int[] result = new int[] { 0 };
        int i = 0;
        while (i < digits.length()) {
            int chunkLen = Math.min(9, digits.length() - i);
            int chunk = Integer.parseInt(digits.substring(i, i + chunkLen));

            // result = result * 10^chunkLen + chunk
            int multiplier = 1;
            for (int k = 0; k < chunkLen; k++)
                multiplier *= 10;

            result = mulMagByInt(result, multiplier);
            result = addIntToMag(result, chunk);
            i += chunkLen;
        }

        this.signum = negative ? -1 : 1;
        this.mags = result;
    }

    private static int[] mulMagByInt(int[] mag, int val) {
        long carry = 0;
        int[] result = new int[mag.length];
        for (int i = mag.length - 1; i >= 0; i--) {
            long prod = (mag[i] & 0xFFFFFFFFL) * (val & 0xFFFFFFFFL) + carry;
            result[i] = (int) prod;
            carry = prod >>> 32;
        }
        if (carry != 0) {
            int[] expanded = new int[result.length + 1];
            expanded[0] = (int) carry;
            System.arraycopy(result, 0, expanded, 1, result.length);
            return expanded;
        }
        return result;
    }

    private static int[] addIntToMag(int[] mag, int val) {
        long carry = val & 0xFFFFFFFFL;
        int[] result = mag.clone();
        for (int i = result.length - 1; i >= 0 && carry != 0; i--) {
            long sum = (result[i] & 0xFFFFFFFFL) + carry;
            result[i] = (int) sum;
            carry = sum >>> 32;
        }
        if (carry != 0) {
            int[] expanded = new int[result.length + 1];
            expanded[0] = (int) carry;
            System.arraycopy(result, 0, expanded, 1, result.length);
            return expanded;
        }
        return result;
    }

    public FlameInt divide(FlameInt divisor) {
        if (divisor.signum == 0)
            throw new ArithmeticException("Divide by zero");
        if (this.signum == 0)
            return ZERO;

        int cmp = compareMagnitudes(this.mags, divisor.mags);
        if (cmp < 0)
            return ZERO;
        if (cmp == 0)
            return new FlameInt(this.signum * divisor.signum, new int[] { 1 });

        // Single-limb divisor fast path
        if (divisor.mags.length == 1) {
            List<int[]> qr = divideBySingleLimb(this.mags, divisor.mags[0]);
            int resultSign = this.signum * divisor.signum;
            return new FlameInt(resultSign, qr.get(0));
        } else {
            List<int[]> qr = divideByKnuthD(this.mags, divisor.mags);
            int resultSign = this.signum * divisor.signum;
            return new FlameInt(resultSign, qr.get(0));
        }
    }

    public FlameInt mod(FlameInt divisor) {
        if (divisor.signum == 0)
            throw new ArithmeticException("Divide by zero");
        if (this.signum == 0)
            return ZERO;

        FlameInt absDivisor = divisor.abs();

        int cmp = compareMagnitudes(this.mags, absDivisor.mags);
        if (cmp == 0)
            return ZERO;
        if (cmp < 0) {
            if (this.signum < 0)
                return absDivisor.subtract(this.abs());
            return this;
        }

        int[] remMags;
        if (absDivisor.mags.length == 1) {
            List<int[]> qr = divideBySingleLimb(this.mags, absDivisor.mags[0]);
            remMags = qr.get(1);
        } else {
            List<int[]> qr = divideByKnuthD(this.mags, absDivisor.mags);
            remMags = qr.get(1);
        }

        remMags = stripLeadingZeros(remMags);
        if (remMags.length == 1 && remMags[0] == 0)
            return ZERO;

        if (this.signum < 0) {
            FlameInt rem = new FlameInt(1, remMags);
            return absDivisor.subtract(rem);
        }

        return new FlameInt(1, remMags);
    }

    public FlameInt pow(long exponent) {
        if (exponent < 0)
            throw new ArithmeticException("Negative exponent");
        if (exponent == 0)
            return ONE;
        if (exponent == 1)
            return this;
        if (this.isZero())
            return ZERO;
        if (this.isOne())
            return ONE;

        int resultSign = (signum < 0 && exponent % 2 != 0) ? -1 : 1;
        FlameInt base = this.abs();
        FlameInt result = ONE;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = result.mul(base);
            }
            base = base.mul(base);
            exponent >>= 1;
        }
        return new FlameInt(resultSign, result.mags);
    }

    public FlameInt modPow(FlameInt exponent, FlameInt modulus) {
        if (modulus.isZero())
            throw new ArithmeticException("Modulus cannot be zero");
        if (exponent.isNegative())
            throw new ArithmeticException("Negative exponent in modPow");
        FlameInt absMod = modulus.abs();
        if (exponent.isZero())
            return ONE.mod(absMod);

        FlameInt base = this.abs().mod(absMod);
        FlameInt result = ONE;
        FlameInt exp = exponent;
        while (!exp.isZero()) {
            if (!exp.mod(TWO).isZero()) {
                result = result.mul(base).mod(absMod);
            }
            base = base.mul(base).mod(absMod);
            exp = exp.divide(TWO);
        }
        // If base was negative and exponent is odd, negate then adjust to positive mod
        if (this.isNegative() && !exponent.mod(TWO).isZero() && !result.isZero()) {
            result = absMod.subtract(result);
        }
        return result;
    }

    public FlameInt gcd(FlameInt other) {
        FlameInt a = this.abs();
        FlameInt b = other.abs();
        while (!b.isZero()) {
            FlameInt temp = b;
            b = a.mod(b);
            a = temp;
        }
        return a;
    }

    private static int[] stripLeadingZeros(int[] v) {
        int start = 0;
        while (start < v.length - 1 && v[start] == 0) {
            start++;
        }
        if (start == 0)
            return v;
        return Arrays.copyOfRange(v, start, v.length);
    }

}
