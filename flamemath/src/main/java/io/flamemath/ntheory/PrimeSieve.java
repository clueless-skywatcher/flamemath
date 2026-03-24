package io.flamemath.ntheory;

import java.util.List;
import java.util.ArrayList;
import java.util.BitSet;

public class PrimeSieve {
    private BitSet sieve;
    private int currentLimit;

    public PrimeSieve() {
        currentLimit = 1000;
        sieve = new BitSet(currentLimit + 1);
        sieve.set(0);
        sieve.set(1);
        sieveRange(2, currentLimit);
    }

    private void sieveRange(int from, int to) {
        for (int p = 2; (long) p * p <= to; p++) {
            if (!sieve.get(p)) {
                int start = Math.max((int) ((long) p * p), roundUpToMultiple(from, p));
                for (int multiple = start; multiple <= to; multiple += p) {
                    sieve.set(multiple);
                }
            }
        }
    }

    public List<Integer> primesInRange(int from, int to) {
        if (from < 2) from = 2;
        ensureLimit(to);
        List<Integer> primes = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            if (!sieve.get(i)) {
                primes.add(i);
            }
        }
        return primes;
    }

    private void ensureLimit(int n) {
        if (n <= currentLimit) {
            return;
        }

        int newLimit = (int) Math.max(n, (long) currentLimit * 2);
        sieveRange(currentLimit + 1, newLimit);
        currentLimit = newLimit;
    }

    private int roundUpToMultiple(int n, int p) {
        int remainder = n % p;
        if (remainder == 0) return n;
        return n + (p - remainder);
    }

    public boolean isPrime(int n) {
        if (n < 2) return false;
        ensureLimit(n);
        return !sieve.get(n);
    }
}
