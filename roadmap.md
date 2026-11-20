# FlameMath Roadmap

## v1.4.0 — Polynomials & Symbolic Algebra

### Phase 1 — Polynomial Inspection

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 1 | `Vars` | `Vars(x^2 + 3*x*y)` → `[x, y]` — extract free variables from an expression | Java | Done |
| 2 | `Degree` | `Degree(x^3 + 2*x, x)` → `3` — degree of a polynomial in a given variable | Java | Done |
| 3 | `Coeff` | `Coeff(3*x^2 + 5*x + 1, x, 2)` → `3` — coefficient of x^k in a polynomial | Java | Done |
| 4 | `CoeffList` | `CoeffList(x^3 + 2*x + 7, x)` → `[7, 2, 0, 1]` — list of coefficients from degree 0 upward | Java | Pending |
| 5 | `IsPoly` | `IsPoly(x^2 + 1, x)` → `True` — test whether an expression is a polynomial in a given variable | Java | Pending |

### Phase 2 — Expansion & Collection

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 6 | `Expand` | `Expand((x + 1)*(x + 2))` → `x^2 + 3*x + 2` — distribute products and expand powers | Java | Pending |
| 7 | `Collect` | `Collect(x*y + x*z + x, x)` → `x*(1 + y + z)` — group terms by a given variable | Java | Pending |
| 8 | `PowerExpand` | `PowerExpand((x*y)^n)` → `x^n * y^n` — expand powers of products and powers of powers | Java | Pending |

### Phase 3 — Polynomial Arithmetic

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 9 | `PolyQuotRem` | `PolyQuotRem(x^3 + 1, x + 1, x)` → `[x^2 - x + 1, 0]` — polynomial long division | Java | Pending |
| 10 | `PolyGCD` | `PolyGCD(x^2 - 1, x^2 + 2*x + 1, x)` → `x + 1` — GCD of univariate polynomials | Java | Pending |

### Phase 4 — Factoring

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 11 | `Factor` | `Factor(x^2 - 1)` → `(x - 1)*(x + 1)` — factor polynomials over the integers | Java | Pending |
| 12 | `FactorList` | `FactorList(12*x^3 - 12*x)` → `[[12, 1], [x, 1], [x - 1, 1], [x + 1, 1]]` — factor with multiplicities | Java | Pending |

### Phase 5 — Rational Expressions

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 13 | `Cancel` | `Cancel((x^2 - 1)/(x - 1))` → `x + 1` — cancel common factors in rational expressions | Java | Pending |
| 14 | `Together` | `Together(1/x + 1/y)` → `(x + y)/(x*y)` — combine fractions over a common denominator | Java | Pending |
| 15 | `Apart` | `Apart((x + 1)/(x^2 - 1), x)` → `1/(x - 1)` — partial fraction decomposition | Java | Pending |

### Phase 6 — Symbolic Calculus

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 16 | `D` | `D(x^3 + 2*x, x)` → `3*x^2 + 2` — symbolic differentiation with respect to a variable | Java | Pending |
| 17 | `D` (higher-order) | `D(x^5, x, 3)` → `60*x^2` — n-th derivative | Java | Pending |
| 18 | `D` (multivariate) | `D(x^2*y^3, x, y)` → `6*x*y^2` — mixed partial derivatives | Java | Pending |

### Phase 7 — Simplification

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 19 | `Simplify` | `Simplify(x^2 + 2*x + 1 - (x + 1)^2)` → `0` — basic algebraic simplification | Java | Pending |

## Unscheduled — Language / Control Flow

| # | Feature | Description | Type | Status |
|---|---------|-------------|------|--------|
| 20 | `Throw` | `Throw(value)` — non-local exit that unwinds past the enclosing lambda until caught. Pairs with `Catch` | Java | Pending |
| 21 | `Catch` | `Catch(body)` — run `body`; if a `Throw(v)` unwinds through it, return `v`. Held arg. Companion to `Throw` | Java | Pending |
