# FlameMath Roadmap

## v1.3.0 — Number Theory & Combinatorics

### Phase 1 — Core Number Theory

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 1 | `PrimeFactors` | `PrimeFactors(60)` → `[2, 2, 3, 5]` | FlameLang | Done |
| 2 | `Divisors` | `Divisors(12)` → `[1, 2, 3, 4, 6, 12]` | FlameLang | Done |
| 3 | `EulerPhi` | `EulerPhi(12)` → `4` — Euler's totient function | FlameLang | Done |
| 4 | `MoebiusMu` | `MoebiusMu(30)` → `-1` — Mobius function: 0 if n has squared factor, (-1)^k if n is product of k distinct primes | FlameLang | |
| 5 | `DivisorSigma` | `DivisorSigma(12, 1)` → `28` — sum of k-th powers of divisors of n | FlameLang | |

### Phase 2 — Modular Arithmetic

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 6 | `ExtGCD` | `ExtGCD(35, 15)` → `[5, 1, -2]` — extended GCD returning [gcd, s, t] such that s*a + t*b = gcd | FlameLang | |
| 7 | `OrderMod` | `OrderMod(2, 7)` → `3` — multiplicative order of a modulo n | FlameLang | |
| 8 | `ChineseRemainder` | `ChineseRemainder([2, 3], [3, 5])` → `8` — solves system of congruences via CRT | FlameLang | |

### Phase 3 — Primes

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 9 | `NextPrime` | `NextPrime(10)` → `11` — smallest prime greater than n | FlameLang | |

### Phase 4 — Combinatorics

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 10 | `Catalan` | `Catalan(5)` → `42` — n-th Catalan number | FlameLang | |
| 11 | `StirlingS2` | `StirlingS2(5, 3)` → `25` — Stirling numbers of the second kind | FlameLang | |
| 12 | `IntegerPartitions` | `IntegerPartitions(4)` → `[[4],[3,1],[2,2],[2,1,1],[1,1,1,1]]` — all partitions of n | FlameLang | |
| 13 | `Compositions` | `Compositions(3, 2)` → `[[1,2],[2,1]]` — ordered compositions of n into k parts | FlameLang | |

### Phase 5 — Numeric Utilities

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 14 | `Linspace` | `Linspace(0, 1, 5)` → `[0, 0.25, 0.5, 0.75, 1.0]` — n evenly spaced points in [a, b] | FlameLang | |