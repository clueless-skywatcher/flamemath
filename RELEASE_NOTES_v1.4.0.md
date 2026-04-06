# FlameMath 1.4.0

## New Functions

### Polynomial Inspection
- **`Vars(expr)`** — Extracts all free variables from an expression as a list, with duplicates removed. `Vars(x^2 + 3*x*y + y^2)` -> `[x, y]`. Works with any compound expression including arithmetic, trig, and nested function calls. Returns an empty list for purely numeric, string, or boolean inputs.
- **`FreeOf(expr, x)`** — Tests whether an expression is free of a given variable. `FreeOf(y + 1, x)` -> `True`, `FreeOf(x^2 + y, x)` -> `False`. Implemented as a stdlib function using `Vars` and `Has`.
