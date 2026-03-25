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
| 10 | `PrimesInRange` | `PrimesInRange(1, 100)` → `[2, 3, 5, ..., 97]` — segmented Sieve of Eratosthenes, returns all primes in range [m, n] | Java | |
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
| 15 | `Take` / `Drop` | `Take([1,2,3,4], 2)` → `[1,2]`, `Drop([1,2,3,4], 2)` → `[3,4]` | FlameLang |  |
| 16 | `Count` | `Count([1, 2, 1, 3], 1)` → `2` — count occurrences of a value in a list | FlameLang |  |

### Phase 5 — Numeric Utilities

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 17 | `Sign` | `Sign(-5)` → `-1`, `Sign(0)` → `0`, `Sign(3)` → `1` | FlameLang | Done |
| 18 | `Clamp` | `Clamp(7, 1, 5)` → `5` — restricts a value to a given range `[lo, hi]` | FlameLang | Done |

### Phase 6 — String Functions

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 20 | `StringReplace` | `StringReplace("hello world", "world", "flame")` → `"hello flame"` | Java | |
| 21 | `StringContains` | `StringContains("hello", "ell")` → `True` | Java | |

### Phase 7 — Number Theory (additional)

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 22 | `EulerPhi` | `EulerPhi(12)` → `4` — Euler's totient function | FlameLang | |
| 23 | `NextPrime` | `NextPrime(10)` → `11` — smallest prime greater than n | FlameLang | |

### Phase 8 — Polish

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 24 | Multi-line REPL input | Detect unclosed `(`, `[`, `{` and wait for continuation lines | Java | |