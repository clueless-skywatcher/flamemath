# FlameMath 1.4.0

## New Functions

### Polynomial Inspection
- **`Vars(expr)`** — Extracts all free variables from an expression as a list, with duplicates removed. `Vars(x^2 + 3*x*y + y^2)` -> `[x, y]`. Works with any compound expression including arithmetic, trig, and nested function calls. Returns an empty list for purely numeric, string, or boolean inputs.
- **`FreeOf(expr, x)`** — Tests whether an expression is free of a given variable. `FreeOf(y + 1, x)` -> `True`, `FreeOf(x^2 + y, x)` -> `False`. Implemented as a stdlib function using `Vars` and `Has`.
- **`Degree(expr, x)`** - Find out the degree of a given variable `x` in `expr`. `Degree(x^3 + 2*x + 1, x)` -> `3`. Finds out the degree recursively for compound expressions.

### Expression Inspection
- **`Operands(expr)`** — Returns the direct children (operands) of a compound expression as a list. `Operands(x + y + z)` -> `[x, y, z]`, `Operands(x^2)` -> `[x, 2]`. Returns unevaluated for atomic expressions. Operands of commutative operations (Add, Mul) are returned in canonical order.