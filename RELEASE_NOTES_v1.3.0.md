# FlameMath 1.3.0

## New Functions

### Number Theory
- **`PrimeFactors(n)`** — Integer factorization returning a dictionary of prime -> exponent pairs. `PrimeFactors(360)` -> `{2: 3, 3: 2, 5: 1}`. Uses trial division for small factors, Pollard's rho for large factors, with Miller-Rabin primality testing. Supports arbitrary-precision integers.

### Dictionary Operations
- **`LookupDefault(d, key, default)`** — Look up a key in a dictionary, returning a default value if the key is not present

## Improvements

### Big Integer Support for Number Theory
- **`IsPrime(n)`** — Now works with arbitrary-precision integers via a FlameInt-based Miller-Rabin implementation. Previously limited to 64-bit values
- **`PowMod(base, exp, mod)`** — Now uses `FlameInt.modPow` directly instead of converting to `long`/`BigInteger`. Supports arbitrary-precision arguments
- **`Mod(a, b)`** — Added integer-integer fast path using `FlameInt.mod()` directly, avoiding unnecessary conversion through rational arithmetic

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
