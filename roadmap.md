# FlameMath Roadmap

## v1.3.0 — Number Theory & Combinatorics

### Phase 1 — Core Number Theory

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 1 | `PrimeFactors` | `PrimeFactors(60)` → `[2, 2, 3, 5]` | FlameLang | Done |
| 2 | `Divisors` | `Divisors(12)` → `[1, 2, 3, 4, 6, 12]` | FlameLang | Done |
| 3 | `EulerPhi` | `EulerPhi(12)` → `4` — Euler's totient function | FlameLang | Done |
| 4 | `MoebiusMu` | `MoebiusMu(30)` → `-1` — Mobius function: 0 if n has squared factor, (-1)^k if n is product of k distinct primes | FlameLang | Done |
| 5 | `DivisorSigma` | `DivisorSigma(12, 1)` → `28` — sum of k-th powers of divisors of n | FlameLang | Done |

### Phase 2 — Modular Arithmetic

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 6 | `ExtGCD` | `ExtGCD(35, 15)` → `[5, [1, -2]]` — extended GCD returning [gcd, [c1, c2, ...]] such that c1*a + c2*b + ... = gcd | FlameLang | Done |
| 7 | `OrderMod` | `OrderMod(2, 7)` → `3` — multiplicative order of a modulo n | FlameLang | Done |
| 8 | `ChineseRemainder` | `ChineseRemainder([2, 3], [3, 5])` → `8` — solves system of congruences via CRT | FlameLang | Done |
| 9 | `ModInverse` | `ModInverse(3, 7)` → `5` — modular multiplicative inverse of a modulo m | FlameLang | Done |
| 10 | `Coprime` | `Coprime(3, 5, 7)` → `True` — checks if all arguments are pairwise coprime (variadic) | FlameLang | Done |

### Phase 3 — Primes

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 12 | `NextPrime` | `NextPrime(10)` → `11` — smallest prime greater than n | FlameLang | Done |

### Phase 4 — Combinatorics

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 13 | `CatalanNumber` | `CatalanNumber(5)` → `42` — n-th Catalan number | FlameLang | Done |
| 14 | `StirlingII` | `StirlingII(5, 3)` → `25` — Stirling numbers of the second kind | FlameLang | Done |
| 15 | `IntegerPartitions` | `IntegerPartitions(4)` → `[[4],[3,1],[2,2],[2,1,1],[1,1,1,1]]` — all partitions of n | FlameLang | Done |
| 16 | `Compositions` | `Compositions(3, 2)` → `[[1,2],[2,1]]` — ordered compositions of n into k parts | FlameLang | Done |

### Phase 5 — Numeric Utilities

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 17 | `Linspace` | `Linspace(0, 1, 5)` → `[0, 0.25, 0.5, 0.75, 1.0]` — n evenly spaced points in [a, b] | FlameLang | Done |