# FlameMath 1.4.0

## New Functions

### Polynomial Inspection
- **`Vars(expr)`** — Extracts all free variables from an expression as a list, with duplicates removed. `Vars(x^2 + 3*x*y + y^2)` -> `[x, y]`. Works with any compound expression including arithmetic, trig, and nested function calls. Returns an empty list for purely numeric, string, or boolean inputs.
- **`FreeOf(expr, x)`** — Tests whether an expression is free of a given variable. `FreeOf(y + 1, x)` -> `True`, `FreeOf(x^2 + y, x)` -> `False`. Implemented as a stdlib function using `Vars` and `Has`.
- **`Degree(expr, x)`** - Find out the degree of a given variable `x` in `expr`. `Degree(x^3 + 2*x + 1, x)` -> `3`. Finds out the degree recursively for compound expressions.
- **`Coeff(expr, x)`** — Returns the coefficient of `x` in `expr`. `Coeff(x^2 + 2*x + 1, x)` -> `2`. Supports multivariate expressions.
- **`Expand(expr)`** — Expands an algebraic expression by distributing products over sums. Currently a stub that returns the expression as-is; expansion rules to be implemented.

### List Operations
- **`Delete(list, element)`** — Removes the first occurrence of an element from a list in place. `Delete(l, 2)` on `[1, 2, 3, 2]` gives `[1, 3, 2]`. No-op if the element is not found.
- **`DeleteAll(list, element)`** — Removes all occurrences of an element from a list in place. `DeleteAll(l, 2)` on `[1, 2, 3, 2]` gives `[1, 3]`.
- **`DeleteCopy(list, element)`** — Returns a new list with the first occurrence of an element removed. Original list is unchanged.
- **`DeleteAllCopy(list, element)`** — Returns a new list with all occurrences of an element removed. Original list is unchanged.

### Expression Inspection
- **`Operands(expr)`** — Returns the direct children (operands) of a compound expression as a list. `Operands(x + y + z)` -> `[x, y, z]`, `Operands(x^2)` -> `[x, 2]`. Returns unevaluated for atomic expressions. Operands of commutative operations (Add, Mul) are returned in canonical order.