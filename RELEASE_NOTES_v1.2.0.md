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
- **`Prime(n)`** — Returns the n-th prime number using Meissel-Lehmer prime counting with binary search for large n, sieve for small n. `Prime(4)` → `7`

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
- **`Tally(list)`** — Frequency count of elements, returning `[[element, count], ...]` pairs in first-occurrence order. Works with any element type including nested lists
- **`Union(list1, list2, ...)`** — Set union across multiple lists, preserving first-occurrence order
- **`Intersection(list1, list2, ...)`** — Set intersection across multiple lists
- **`FoldScan(f, start, list)`** — Cumulative fold (scan) over a list, returning all intermediate accumulator values. `FoldScan(Add, 0, [1,2,3,4])` → `[0,1,3,6,10]`

### String Functions
- **`StrHas(str, substring)`** — Tests whether a string contains a given substring. Case-sensitive
- **`StrReplace(str, target, replacement)`** — Replaces all occurrences of a substring with a replacement string

## Bug Fixes

- **Flat functions not flattened when called via symbol alias** — Built-in functions with the `isFlat` attribute (e.g., `Add`, `Mul`) were not being flattened when invoked indirectly through a symbol reference (e.g., passing `Add` to `FoldScan`). This caused incorrect canonical ordering in the result, such as `FoldScan(Add, 0, [a, b, c, d])` producing `c + a + b` instead of `a + b + c`

## Improvements

- **`Sqrt()` simplifies radicals** — `Sqrt` now extracts the largest perfect-square factor from integer arguments and numeric factors in products. `Sqrt(12)` → `2*√3`, `Sqrt(4*x^2)` → `2*√(x^2)`. Symbolic parts are left under the radical (no assumptions about variable signs)
- **Square root display** — `Pow(n, (1/2))` and `Sqrt(...)` expressions now render with the `√` symbol instead of function-call notation
- **`Zip()` generalized** — Now accepts any number of lists (variadic), not just two. `Zip([1,2], [3,4], [5,6])` → `[[1,3,5], [2,4,5]]`
- **`Zip()` bug fix** — Fixed incorrect element indexing in the inner loop
- **Documentation reorganized** — Function reference docs are now organized into category subfolders (math, list, ntheory, general, etc.)
- **Mathematical functions return integers where applicable** — Functions like `Sin(0)` now return `0` instead of `0.0`
- **Version tracking** — Added `version.properties` for runtime version identification

## FlameInt: Arbitrary-Precision Integer Migration

`IntegerAtom` has been migrated from `long` to `FlameInt`, a custom arbitrary-precision integer type using base-2^32 limb arrays. This removes the 64-bit ceiling on all integer arithmetic — functions like `Binomial(100, 50)`, `Factorial(50)`, and large `Pow` expressions now produce exact results instead of silently overflowing.

### Arithmetic
- **Addition, subtraction, multiplication** — Schoolbook algorithms operating on `int[]` magnitude arrays with unsigned limb arithmetic
- **Division (Knuth Algorithm D)** — Multi-limb long division with normalization, replacing a previous repeated-subtraction implementation. O(m·n) per division
- **Exponentiation (`FlameInt.pow`)** — Binary exponentiation (repeated squaring), replacing `Math.pow`/`long` casts in `PowFunc` that silently overflowed to `Long.MAX_VALUE`
- **Modular remainder** — `mod()` now extracts the remainder directly from the division algorithm instead of recomputing via separate `divide` + `multiply` + `subtract`

### Bug Fixes
- **Knuth D unsigned comparison** — The quotient refinement loop used signed `>` where `Long.compareUnsigned` was needed, producing wrong results for large multi-limb divisions
- **Knuth D normalization overflow** — Bits were lost during left-shift normalization; fixed by prepending a zero limb before shifting
- **`leadingZeros` computed popcount** — The helper counted set bits instead of leading zeros; replaced with `Integer.numberOfLeadingZeros`

## Internal
- Number theory utilities (`PrimeSieve`, `NumberTheoryUtils`) added as shared infrastructure
- `PrimeSieve` now supports Meissel-Lehmer π(x) computation, cached prime list, and prefix count array for O(1) lookups
- Function references compartmentalized into separate folders by category
