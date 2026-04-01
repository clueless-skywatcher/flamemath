# FlameMath 1.3.0

## New Functions

### Number Theory
- **`PrimeFactors(n)`** — Integer factorization returning a dictionary of prime -> exponent pairs. `PrimeFactors(360)` -> `{2: 3, 3: 2, 5: 1}`. Uses trial division for small factors, Pollard's rho for large factors, with Miller-Rabin primality testing. Supports arbitrary-precision integers.
- **`Divisors(n)`** — Returns a sorted list of all positive divisors of `n`. `Divisors(12)` -> `[1, 2, 3, 4, 6, 12]`. Generates divisors from the prime factorization via `PrimeFactors`.
- **`EulerPhi(n)`** — Euler's totient function. Returns the count of integers in `[1, n]` coprime to `n`. `EulerPhi(12)` -> `4`. Computed via the product formula using `PrimeFactors`.
- **`NextPrime(n)`** — Returns the smallest prime strictly greater than `n`. `NextPrime(10)` -> `11`. Searches sequentially using `IsPrime`.
- **`MoebiusMu(n)`** — Mobius function. Returns 0 if `n` has a squared prime factor, `(-1)^k` if `n` is a product of `k` distinct primes. `MoebiusMu(30)` -> `-1`. Uses a single `PrimeFactors` call.
- **`LiouvilleLambda(n)`** — Liouville function `λ(n) = (-1)^Ω(n)`. `LiouvilleLambda(12)` -> `-1`.
- **`PrimeBigW(n)`** — Number of prime factors of `n` counted with multiplicity (`Ω(n)`). `PrimeBigW(12)` -> `3`.
- **`PrimeLittleW(n)`** — Number of distinct prime factors of `n` (`ω(n)`). `PrimeLittleW(12)` -> `2`.
- **`DivisorSigma(n, k)`** — Sum of k-th powers of divisors of `n`. `DivisorSigma(12, 1)` -> `28`. σ_0 counts divisors, σ_1 is the classical sum-of-divisors. Uses `Divisors` and `Map`.
- **`KroneckerDelta(i, j)`** — Returns 1 if `i == j`, 0 otherwise.
- **`ExtGCD(a, b, ...)`** — Extended Euclidean algorithm. Returns `[gcd, [c1, c2, ...]]` where the Bezout coefficients satisfy `c1*a + c2*b + ... = gcd`. Supports any number of integer arguments (minimum 2). Chains pairwise extended GCD across all arguments. `ExtGCD(6, 15, 30)` -> `[3, [-2, 1, 0]]`.

### Dictionary Operations
- **`LookupDefault(d, key, default)`** — Look up a key in a dictionary, returning a default value if the key is not present

## Improvements

### Big Integer Support for Number Theory
- **`IsPrime(n)`** — Now works with arbitrary-precision integers via a FlameInt-based Miller-Rabin implementation. Previously limited to 64-bit values
- **`PowMod(base, exp, mod)`** — Now uses `FlameInt.modPow` directly instead of converting to `long`/`BigInteger`. Supports arbitrary-precision arguments
- **`Mod(a, b)`** — Added integer-integer fast path using `FlameInt.mod()` directly, avoiding unnecessary conversion through rational arithmetic

### Arithmetic
- **`Pow(n, 1/2)`** — Now delegates to `Sqrt` for non-perfect-square integer bases, so `12^(1/2)` simplifies to `2*Sqrt(3)` instead of staying as `Pow(12, 1/2)`

### Parser
- **Integer literal parsing** — The parser now uses `FlameInt` directly instead of `Long.parseLong`, allowing integer literals of any size to be entered without overflow

### Display
- **Dictionary printing** — `DictExpr` now prints as `{key: value, ...}` in the ExprPrinter, with deterministic key ordering via `TreeMap`

## FlameInt

### New Methods
- **`modPow(exponent, modulus)`** — Modular exponentiation via binary exponentiation with mod at each step. Handles negative bases correctly
- **`fitsInLong()`** — Returns whether the value fits in a Java `long`, used to dispatch between sieve and Miller-Rabin paths in `IsPrime`

### Bug Fixes
- **`divideBySingleLimb` signed division bug** — When the single-limb divisor exceeded 2^31 (bit 31 set), Java interpreted it as negative during signed division, producing incorrect quotients and remainders. Fixed by using `Long.divideUnsigned` and `Long.remainderUnsigned`. This bug silently corrupted results for any division where the divisor's unsigned value was >= 2,147,483,648, cascading into wrong GCD, mod, and factorization results

## Internal
- `NumberTheoryUtils` now has a `millerRabin(FlameInt)` overload for arbitrary-precision primality testing
- `PrimeFactors` implemented as a Java builtin (`PrimeFactorsFunc`) for performance, rather than in the FlameMath stdlib
