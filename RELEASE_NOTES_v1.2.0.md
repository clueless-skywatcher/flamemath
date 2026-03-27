# FlameMath 1.2.0

## New Functions

### Language Improvements
- **`Apply(f, list)`** — Splat a list as arguments to a function. `Apply(Add, [1, 2, 3])` → `6`
- **`Substitute(expr, symbol, value)`** — Substitute a symbol with a value in an expression. `Substitute(x^2 + x, x, 3)` → `12`
- **`Hold(expr)`** — Prevent evaluation of an expression, returning it unevaluated

### Number Theory
- **`GCD(a, b)`** / **`LCM(a, b)`** — Greatest common divisor and least common multiple
- **`IsPrime(n)`** — Primality testing via deterministic Miller-Rabin with 12 fixed bases; hybridly uses a global Sieve of Eratosthenes for small primes
- **`PrimesInRange(m, n)`** — Returns all primes in [m, n] using a segmented Sieve of Eratosthenes
- **`PowerMod(base, exp, mod)`** — Modular exponentiation via `BigInteger.modPow`
- **`Binomial(n, k)`** — Binomial coefficient using a multiplicative formula (avoids full factorials)
- **`Multinomial(n, k1, k2, ...)`** — Multinomial coefficient
- **`WieferichPrime(n)`** — Finds the smallest Wieferich prime up to n

### Inverse Trigonometric Functions
- **`ArcSin(x)`** — Inverse sine with exact symbolic values for well-known angles (0, Pi/6, Pi/4, Pi/3, Pi/2)
- **`ArcCos(x)`** — Inverse cosine with exact symbolic values across [0, Pi], including negative arguments
- **`ArcTan(x)`** — Inverse tangent with exact symbolic values for 0, 1, Sqrt(3), 1/Sqrt(3)
- **`ArcTan2(y, x)`** — Two-argument (quadrant-aware) arctangent

### Hyperbolic Functions
- **`Sinh(x)`** — Hyperbolic sine
- **`Cosh(x)`** — Hyperbolic cosine
- **`Tanh(x)`** — Hyperbolic tangent

### Numeric Utilities
- **`Sign(n)`** — Returns the sign of an integer: `-1`, `0`, or `1`
- **`Clamp(n, low, hi)`** — Restricts a numeric value to a given range `[low, hi]`

### List Operations
- **`Join(list1, list2, ...)`** — Concatenate multiple lists into one
- **`Take(list, n)`** — Return the first `n` elements (positive) or last `|n|` elements (negative)
- **`Drop(list, n)`** — Remove the first `n` elements (positive) or last `|n|` elements (negative)
- **`First(list)`** — Return the first element, or `Null` if empty
- **`Last(list)`** — Return the last element, or `Null` if empty
- **`Count(list, value)`** — Count occurrences of a value in a list

### String Functions
- **`StrHas(str, substring)`** — Tests whether a string contains a given substring. Case-sensitive
- **`StrReplace(str, target, replacement)`** — Replaces all occurrences of a substring with a replacement string

## Improvements

- **`Zip()` generalized** — Now accepts any number of lists (variadic), not just two. `Zip([1,2], [3,4], [5,6])` → `[[1,3,5], [2,4,5]]`
- **`Zip()` bug fix** — Fixed incorrect element indexing in the inner loop
- **Documentation reorganized** — Function reference docs are now organized into category subfolders (math, list, ntheory, general, etc.)
- **Mathematical functions return integers where applicable** — Functions like `Sin(0)` now return `0` instead of `0.0`
- **Version tracking** — Added `version.properties` for runtime version identification

## Internal
- Number theory utilities (`PrimeSieve`, `NumberTheoryUtils`) added as shared infrastructure
- Function references compartmentalized into separate folders by category
- IntegerAtom will now use BigInteger instead of Long
