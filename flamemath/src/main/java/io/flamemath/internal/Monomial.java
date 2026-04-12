package io.flamemath.internal;

import java.util.Arrays;

public final class Monomial {
    private final int[] exponents;

    public Monomial(int[] exponents) {
        this.exponents = exponents.clone();
    }

    public int exponent(int varIndex) {
        return exponents[varIndex];
    }

    public int[] exponents() {
        return exponents.clone();
    }

    public int size() {
        return exponents.length;
    }

    public int totalDegree() {
        int sum = 0;
        for (int e : exponents) sum += e;
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Monomial m)) return false;
        return Arrays.equals(exponents, m.exponents);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(exponents);
    }

    @Override
    public String toString() {
        return Arrays.toString(exponents);
    }
}
