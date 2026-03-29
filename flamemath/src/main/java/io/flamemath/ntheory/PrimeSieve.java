package io.flamemath.ntheory;

import java.util.List;

import io.flamemath.eval.FlameValuator;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;

public class PrimeSieve {
    private BitSet sieve;
    private int currentLimit;
    private HashMap<LongPair, Long> phiLookup = new HashMap<>();
    private List<Long> prefixCount = new ArrayList<>();
    private List<Long> primeList = new ArrayList<>();
    private int primeListLimit = -1;
    private int prefixCountLimit = 1;

    public PrimeSieve() {
        currentLimit = 1000;
        sieve = new BitSet(currentLimit + 1);
        sieve.set(0);
        sieve.set(1);
        sieveRange(2, currentLimit);
        prefixCount.add((long) 0);
        prefixCount.add((long) 0);
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

    public long nthPrime(long n) {
        if (n == 1) return 2;
        long low = 2;
        long hi = n * (long)(Math.log(n) + Math.log(Math.log(n)) + 2);

        while (low < hi) {
            long mid = (low + hi) / 2;
            if (primesPi(mid) >= n) {
                hi = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
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

    private void buildPrimeList(int limit) {
        if (limit <= primeListLimit) return;
        ensureLimit(limit);
        primeList.clear();
        for (int i = 2; i <= limit; i++) {
            if (!sieve.get(i)) {
                primeList.add((long) i);
            }
        }
        primeListLimit = limit;
    }

    private long sieveNthPrime(long n) {
        int idx = (int) n - 1;
        while (idx >= primeList.size()) {
            buildPrimeList(Math.max(primeListLimit * 2, 1000));
        }
        return primeList.get(idx);
    }

    public boolean isPrime(int n) {
        if (n < 2) return false;
        ensureLimit(n);
        return !sieve.get(n);
    }

    public long primesPi(long n) {
        if (n <= FlameValuator.SIEVE_LIMIT) {
            return countFromSieve(n);
        }
        return meisselLehmerPi(n);
    }

    private long meisselLehmerPi(long n) {
        int cbrtLimit = (int) Math.ceil(Math.pow(n, 2.0 / 3));
        ensureLimit(cbrtLimit);
        buildPrimeList(cbrtLimit);
        buildPrefixCount(cbrtLimit);

        long a = primesPi((long) Math.cbrt(n));
        long b = primesPi((long) Math.sqrt(n));

        long result = phi(n, a) + a - 1;

        for (long i = a + 1; i <= b; i++) {
            result -= primesPi(n / sieveNthPrime(i)) - (i - 1);
        }

        return result;
    }

    private void buildPrefixCount(int limit) {
        if (limit <= prefixCountLimit) return;
        ensureLimit(limit);
        for (int i = prefixCountLimit + 1; i <= limit; i++) {
            prefixCount.add(prefixCount.getLast() + (isPrime(i) ? 1 : 0));
        }
        prefixCountLimit = limit;
    }

    private long phi(long n, long a) {
        LongPair pair = new LongPair(n, a);
        if (phiLookup.containsKey(pair)) return phiLookup.get(pair);

        if (a == 0) {
            phiLookup.put(pair, n);
        }
        else {
            phiLookup.put(pair, phi(n, a - 1) - phi(Math.floorDiv(n, sieveNthPrime(a)), a - 1));
        }

        return phiLookup.get(pair);
    }

    private long countFromSieve(long n) {
        buildPrefixCount((int) n);
        return prefixCount.get((int) n);
    }
}
