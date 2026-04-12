# FlameMath 1.4.0

## New Functions

### Polynomial Inspection
- **`Vars(expr)`** — Extracts all free variables from an expression as a list, with duplicates removed. `Vars(x^2 + 3*x*y + y^2)` -> `[x, y]`. Works with any compound expression including arithmetic, trig, and nested function calls. Returns an empty list for purely numeric, string, or boolean inputs.
- **`FreeOf(expr, x)`** — Tests whether an expression is free of a given variable. `FreeOf(y + 1, x)` -> `True`, `FreeOf(x^2 + y, x)` -> `False`. Implemented as a stdlib function using `Vars` and `Has`.
- **`Degree(expr, x)`** - Find out the degree of a given variable `x` in `expr`. `Degree(x^3 + 2*x + 1, x)` -> `3`. Finds out the degree recursively for compound expressions.
- **`Coeff(expr, x)`** — Returns the coefficient of `x` in `expr`. `Coeff(x^2 + 2*x + 1, x)` -> `2`. Supports multivariate expressions.
- **`Expand(expr)`** — Expands an algebraic expression by distributing products over sums and applying the multinomial theorem. `Expand((x + 1)^2)` -> `x^2 + 2*x + 1`, `Expand((a + b) * (c + d))` -> `a*c + a*d + b*c + b*d`. Supports arbitrary nesting and multivariate expressions.

### List Operations
- **`Delete(list, element)`** — Removes the first occurrence of an element from a list in place. `Delete(l, 2)` on `[1, 2, 3, 2]` gives `[1, 3, 2]`. No-op if the element is not found.
- **`DeleteAll(list, element)`** — Removes all occurrences of an element from a list in place. `DeleteAll(l, 2)` on `[1, 2, 3, 2]` gives `[1, 3]`.
- **`DeleteCopy(list, element)`** — Returns a new list with the first occurrence of an element removed. Original list is unchanged.
- **`DeleteAllCopy(list, element)`** — Returns a new list with all occurrences of an element removed. Original list is unchanged.

### Combinatorics
- **`LahNumber(n, k)`** — Computes the Lah number L(n, k), counting the ways to partition n elements into k non-empty linearly ordered subsets.

### Number Theory
- **`SexyPrimes(n)`** — Returns the first n pairs of sexy primes (primes that differ by 6).

### Expression Inspection
- **`Operands(expr)`** — Returns the direct children (operands) of a compound expression as a list. `Operands(x + y + z)` -> `[x, y, z]`, `Operands(x^2)` -> `[x, 2]`. Returns unevaluated for atomic expressions. Operands of commutative operations (Add, Mul) are returned in canonical order.

## Bug Fixes
- **`Pow(Sqrt(x), n)` now simplifies** — `Sqrt(x)^2` correctly evaluates to `x` instead of staying as `Pow(Sqrt(x), 2)`. More generally, `Pow(Sqrt(b), e)` now rewrites to `Pow(b, e * (1/2))`, enabling simplifications like `Sqrt(x)^4` -> `x^2` and `Sqrt(x)^3` -> `x^(3/2)`.

## Internal

### Stdlib to builtin migrations
- **`Factorial(n)`** and **`Multinomial(n, k1, k2, ...)`** have been moved from stdlib (FlameLang) to Java builtins backed by `FlameUtils.factorial()` and `FlameUtils.multinomialCoeff()`. Behavior is unchanged: symbolic arguments still return unevaluated forms. The shared utility functions in `FlameUtils` are also used by `ExpandFunc` for multinomial expansion.

### `Expr` interface additions
- **`getChildren()`** — Generic method to retrieve sub-expressions from any `Expr`. Returns an empty list for atomic types. Overridden in `Compound` (function arguments), `ListExpr` (list elements), `DictEntryExpr` (`[key, value]`), and `RationalAtom` (`[num, denom]`).
- **`isInteger()`** — Returns `true` only for `IntegerAtom` expressions.
- **`isPositive()`** — Returns `true` for numeric expressions (`IntegerAtom`, `RealAtom`, `RationalAtom`) with a positive value.
- **`isNegative()`** — Returns `true` for numeric expressions with a negative value.

### Expression display improvements
- **`GradedLexComparator`** — New comparator (`expr.comparators.GradedLexComparator`) implementing graded lexicographic order for polynomial term display. Add children are sorted by total degree descending, then by per-variable exponent vectors lexicographically, with negative-coefficient terms placed last for natural subtraction printing. Mul factors are sorted alphabetically by base variable name.
- **`ExprPrinter`** — Now uses `GradedLexComparator` for Add terms and lexicographic variable ordering for Mul factors. Expressions display in conventional mathematical order: `x^2 + 2*x + 1` instead of `1 + 2*x + x^2`, and `3*a^2*b` instead of `3*b*a^2`. Internal canonical representation is unchanged.

### Comparator refactoring
- `CanonicalComparator` moved from `io.flamemath.expr` to `io.flamemath.expr.comparators`.