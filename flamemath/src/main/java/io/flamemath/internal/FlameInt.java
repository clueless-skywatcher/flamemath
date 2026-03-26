package io.flamemath.internal;

import java.util.Arrays;

public class FlameInt {
    private final int signum; // Sign of the integer
    // Each element of the magnitude contributes 2^32
    // So [2, 3, 26] will mean 2 * 2^64 + 3 * 2^32 + 26
    // Basically FlameInt is like a base-2^32 integer 
    private final int[] mags; 
    
    public static final FlameInt ONE = new FlameInt(1);
    public static final FlameInt ZERO = new FlameInt(0);

    public FlameInt(int signum, int[] mags) {
        this.signum = signum;
        this.mags = mags;
    }

    public FlameInt(int value) {
        this.signum = value < 0 ? -1 : (value > 0 ? 1 : 0);
        long abs = value < 0 ? -(long) value : value;
        this.mags = new int[]{(int) abs};
    }

    public FlameInt(long value) {
        this.signum = value < 0 ? -1 : (value > 0 ? 1 : 0);
        long abs = value == Long.MIN_VALUE ? Long.MIN_VALUE : (value < 0 ? -value : value);
        int lower32 = (int) abs;
        if ((abs >>> 32) == 0) {
            this.mags = new int[]{lower32};
        } else {
            int higher32 = (int) (abs >>> 32);
            this.mags = new int[]{higher32, lower32};
        }
    }

    @Override
    public String toString() {
        return "";
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
        if (comparison == 0) return ZERO;
        FlameInt smaller = comparison == -1 ? this : b;
        FlameInt larger = comparison == -1 ? b : this;
        return new FlameInt(larger.signum, 
            subtractMagnitudes(larger.mags, smaller.mags)
        );
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
            carry = (int)(limbSum >>> 32);
        }

        result[0] = carry;
        if (result[0] == 0) {
            return Arrays.copyOfRange(result, 1, result.length);
        }
        return result;
    }

    private int compareMagnitudes(int[] a, int[] b) {
        if (a.length > b.length) return 1;
        if (a.length < b.length) return -1;
        for (int i = 0; i < a.length; i++) {
            int compare = Integer.compareUnsigned(a[i], b[i]);
            if (compare != 0) return compare > 0 ? 1 : -1;
        }
        return 0;
    }

    public FlameInt mul(FlameInt b) {
        if (this.signum * b.signum == 0) return ZERO;
        return schoolBookMultiply(this, b);
    }

    private static FlameInt schoolBookMultiply(FlameInt a, FlameInt b) {
        int[] magA = a.mags;
        int[] magB = b.mags;

        int finalSgn = a.signum * b.signum;

        int[] result = new int[magA.length + magB.length];

        int carry = 0;
        for (int i = 0; i < magA.length; i++) {
            for (int j = 0; j < magB.length; j++) {
                long prod = (magA[i] & 0xFFFFFFFFL) * (magB[j] & 0xFFFFFFFFL) + carry;
                result[i + j + 1] = (int) prod;
                carry = (int) (prod >>> 32);
            }
        }

        return new FlameInt(finalSgn, result);
    }
}
