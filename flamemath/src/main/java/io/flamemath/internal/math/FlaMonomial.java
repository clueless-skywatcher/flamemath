package io.flamemath.internal.math;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.flamemath.expr.Symbol;

public final class FlaMonomial {
    private final Map<Symbol, Integer> exponents;

    public FlaMonomial(Map<Symbol, Integer> exponents) {
        // Only store non-zero exponents
        Map<Symbol, Integer> filtered = new HashMap<>();
        for (Map.Entry<Symbol, Integer> entry : exponents.entrySet()) {
            if (entry.getValue() != 0) {
                filtered.put(entry.getKey(), entry.getValue());
            }
        }
        this.exponents = Map.copyOf(filtered);
    }

    public static FlaMonomial of(Object... args) {
        if (args.length % 2 != 0) {
            throw new IllegalArgumentException("Expected alternating Symbol, int pairs");
        }
        Map<Symbol, Integer> exponents = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            if (!(args[i] instanceof Symbol s)) {
                throw new IllegalArgumentException("Expected Symbol at position " + i);
            }
            if (!(args[i + 1] instanceof Integer e)) {
                throw new IllegalArgumentException("Expected int at position " + (i + 1));
            }
            exponents.put(s, e);
        }
        return new FlaMonomial(exponents);
    }

    public int exponent(Symbol var) {
        return exponents.getOrDefault(var, 0);
    }

    public Set<Symbol> variables() {
        return exponents.keySet();
    }

    public int totalDegree() {
        int sum = 0;
        for (int e : exponents.values()) sum += e;
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlaMonomial m)) return false;
        return exponents.equals(m.exponents);
    }

    @Override
    public int hashCode() {
        return exponents.hashCode();
    }

    @Override
    public String toString() {
        return exponents.toString();
    }
}
