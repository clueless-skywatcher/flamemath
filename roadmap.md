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
| 4 | `GCD` / `LCM` | `GCD(12, 8)` → `4`, extend to lists via Fold | stdlib | |
| 5 | `IsPrime` | `IsPrime(17)` → `True` — trial division | stdlib | |
| 6 | `PrimeFactors` | `PrimeFactors(60)` → `[2, 2, 3, 5]` | stdlib | |
| 7 | `Divisors` | `Divisors(12)` → `[1, 2, 3, 4, 6, 12]` | stdlib | |
| 8 | `Binomial` | `Binomial(5, 2)` → `10` — multiplicative formula, avoids full factorials | stdlib | Done |

### Phase 3 — Inverse Trig & Hyperbolics

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 9 | `ArcSin` / `ArcCos` / `ArcTan` | Inverse trig — numeric eval + symbolic for special values | Java | |
| 10 | `Sinh` / `Cosh` / `Tanh` | Hyperbolic functions — e.g. `Sinh(x)` = `(Exp(x) - Exp(-x))/2` | stdlib or Java | |
| 11 | `ArcTan2` | `ArcTan2(y, x)` — two-argument arctangent | Java | |

### Phase 4 — Symbolic Algebra

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 12 | `D` | `D(x^2, x)` → `2*x` — symbolic differentiation with sum, product, chain rules | Java | |
| 13 | `Expand` | `Expand((x+1)*(x+2))` → `x^2 + 3*x + 2` — distribute products over sums | Java | |
| 14 | `Coefficient` | `Coefficient(3*x^2 + 5*x, x^2)` → `3` — extract coefficient of a power | Java | |

### Phase 5 — Polish

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 15 | Multi-line REPL input | Detect unclosed `(`, `[`, `{` and wait for continuation lines | Java | |