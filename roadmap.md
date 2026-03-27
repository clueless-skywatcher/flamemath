# FlameMath Roadmap

## v1.2.0 — Mathematical Foundations

### Phase 1 — Plumbing (Language Improvements)

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 1 | `Apply` | `Apply(Add, [1,2,3])` → `6` — splat a list as arguments to a function | Java | Done |
| 2 | `ReplaceAll` | `ReplaceAll(x^2 + x, x, 3)` → `12` — substitute a symbol with a value in an expression | Java | Done |
| 3 | Lambda pretty-print | Variadic lambdas print `(...rest)` instead of `Lambda<>` | Java | |

### Phase 2 — Number Theory (FlameLang)

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 4 | `GCD` / `LCM` | `GCD(12, 8)` → `4` | Java | Done |
| 5 | `IsPrime` | `IsPrime(17)` → `True` — deterministic Miller-Rabin with 12 fixed bases | Java | Done |
| 6 | `PowMod` | `PowMod(2, 10, 1000)` → `24` — modular exponentiation via BigInteger.modPow | Java | Done |
| 7 | `WieferichPrime` | `WieferichPrime(2000)` → `1093` — finds smallest Wieferich prime up to n | FlameLang | Done |
| 8 | `PrimeFactors` | `PrimeFactors(60)` → `[2, 2, 3, 5]` | FlameLang | |
| 9 | `Divisors` | `Divisors(12)` → `[1, 2, 3, 4, 6, 12]` | FlameLang | |
| 10 | `PrimesInRange` | `PrimesInRange(1, 100)` → `[2, 3, 5, ..., 97]` — segmented Sieve of Eratosthenes, returns all primes in range [m, n] | Java | Done |
| 11 | `Binomial` | `Binomial(5, 2)` → `10` — multiplicative formula, avoids full factorials | FlameLang | Done |

### Phase 3 — Inverse Trig & Hyperbolics

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 12 | `ArcSin` / `ArcCos` / `ArcTan` | Inverse trig — numeric eval + symbolic for special values | Java | Done |
| 13 | `Sinh` / `Cosh` / `Tanh` | Hyperbolic functions — e.g. `Sinh(x)` = `(Exp(x) - Exp(-x))/2` | FlameLang or Java | Done |
| 14 | `ArcTan2` | `ArcTan2(y, x)` — two-argument arctangent | Java | Done |

### Phase 4 — List Operations

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 15 | `Take` / `Drop` | `Take([1,2,3,4], 2)` → `[1,2]`, `Drop([1,2,3,4], 2)` → `[3,4]` | FlameLang | Done |
| 16 | `Count` | `Count([1, 2, 1, 3], 1)` → `2` — count occurrences of a value in a list | FlameLang | Done |
| 17 | `Tally` | `Tally([a, b, a, c, b, a])` → `[[a, 3], [b, 2], [c, 1]]` — frequency count of elements | FlameLang | |
| 18 | `Union` / `Intersection` | `Union([1,2,3], [2,3,4])` → `[1,2,3,4]` — set operations on lists | FlameLang | |
| 19 | `Accumulate` | `Accumulate([1,2,3,4], Add)` → `[1,3,6,10]` — cumulative fold | FlameLang | |

### Phase 5 — Numeric Utilities

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 20 | `Sign` | `Sign(-5)` → `-1`, `Sign(0)` → `0`, `Sign(3)` → `1` | FlameLang | Done |
| 21 | `Clamp` | `Clamp(7, 1, 5)` → `5` — restricts a value to a given range `[lo, hi]` | FlameLang | Done |
| 22 | `Linspace` | `Linspace(0, 1, 5)` → `[0, 0.25, 0.5, 0.75, 1.0]` — n evenly spaced points in [a, b] | FlameLang | |

### Phase 6 — String Functions

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 23 | `StrReplace` | `StrReplace("hello world", "world", "flame")` → `"hello flame"` | Java | Done |
| 24 | `StrHas` | `StrHas("hello", "ell")` → `True` | Java | Done |

### Phase 7 — Number Theory (additional)

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 25 | `EulerPhi` | `EulerPhi(12)` → `4` — Euler's totient function | FlameLang | |
| 26 | `NextPrime` | `NextPrime(10)` → `11` — smallest prime greater than n | FlameLang | |
| 27 | `MoebiusMu` | `MoebiusMu(30)` → `-1` — Möbius function: 0 if n has squared factor, (-1)^k if n is product of k distinct primes | FlameLang | |
| 28 | `DivisorSigma` | `DivisorSigma(12, 1)` → `28` — sum of k-th powers of divisors of n | FlameLang | |
| 29 | `ExtGCD` | `ExtGCD(35, 15)` → `[5, 1, -2]` — extended GCD returning [gcd, s, t] such that s·a + t·b = gcd | FlameLang | |
| 30 | `OrderMod` | `OrderMod(2, 7)` → `3` — multiplicative order of a modulo n | FlameLang | |
| 31 | `ChineseRemainder` | `ChineseRemainder([2, 3], [3, 5])` → `8` — solves system of congruences via CRT | FlameLang | |

### Phase 8 — Combinatorics

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 32 | `Catalan` | `Catalan(5)` → `42` — n-th Catalan number | FlameLang | |
| 33 | `StirlingS2` | `StirlingS2(5, 3)` → `25` — Stirling numbers of the second kind: ways to partition n elements into k non-empty subsets | FlameLang | |
| 34 | `IntegerPartitions` | `IntegerPartitions(4)` → `[[4],[3,1],[2,2],[2,1,1],[1,1,1,1]]` — all partitions of n | FlameLang | |
| 35 | `Compositions` | `Compositions(3, 2)` → `[[1,2],[2,1]]` — ordered compositions of n into k parts | FlameLang | |

### Phase 9 — Polish

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 36 | Multi-line REPL input | Detect unclosed `(`, `[`, `{` and wait for continuation lines | Java | |
