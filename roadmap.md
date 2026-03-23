# FlameMath Roadmap

## v1.2.0 — Mathematical Foundations

### Phase 1 — Plumbing (Language Improvements)

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 1 | `Apply` | `Apply(Add, [1,2,3])` → `6` — splat a list as arguments to a function | Java | Done |
| 2 | `ReplaceAll` | `ReplaceAll(x^2 + x, x, 3)` → `12` — substitute a symbol with a value in an expression | Java | Done |
| 3 | Lambda pretty-print | Variadic lambdas print `(...rest)` instead of `Lambda<>` | Java | |

### Phase 2 — Number Theory (stdlib)

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 4 | `GCD` / `LCM` | `GCD(12, 8)` → `4` | Java | Done |
| 5 | `IsPrime` | `IsPrime(17)` → `True` — deterministic Miller-Rabin with 12 fixed bases | Java | Done |
| 6 | `PowMod` | `PowMod(2, 10, 1000)` → `24` — modular exponentiation via BigInteger.modPow | Java | Done |
| 7 | `WieferichPrime` | `WieferichPrime(2000)` → `1093` — finds smallest Wieferich prime up to n | stdlib | Done |
| 8 | `PrimeFactors` | `PrimeFactors(60)` → `[2, 2, 3, 5]` | stdlib | |
| 9 | `Divisors` | `Divisors(12)` → `[1, 2, 3, 4, 6, 12]` | stdlib | |
| 10 | `PrimeSieve` | `PrimeSieve(100)` → `[2, 3, 5, ..., 97]` — Sieve of Eratosthenes, returns all primes up to n | Java | |
| 11 | `Binomial` | `Binomial(5, 2)` → `10` — multiplicative formula, avoids full factorials | stdlib | Done |

### Phase 3 — Inverse Trig & Hyperbolics

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 12 | `ArcSin` / `ArcCos` / `ArcTan` | Inverse trig — numeric eval + symbolic for special values | Java | |
| 13 | `Sinh` / `Cosh` / `Tanh` | Hyperbolic functions — e.g. `Sinh(x)` = `(Exp(x) - Exp(-x))/2` | stdlib or Java | |
| 14 | `ArcTan2` | `ArcTan2(y, x)` — two-argument arctangent | Java | |

### Phase 4 — Symbolic Algebra

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 15 | `D` | `D(x^2, x)` → `2*x` — symbolic differentiation with sum, product, chain rules | Java | |
| 16 | `Expand` | `Expand((x+1)*(x+2))` → `x^2 + 3*x + 2` — distribute products over sums | Java | |
| 17 | `Coefficient` | `Coefficient(3*x^2 + 5*x, x^2)` → `3` — extract coefficient of a power | Java | |

### Phase 5 — Polish

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 18 | Multi-line REPL input | Detect unclosed `(`, `[`, `{` and wait for continuation lines | Java | |