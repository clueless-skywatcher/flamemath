# Add

Adds expressions together. Performs symbolic simplification when possible.

## Syntax
```
a + b
Add(a, b)
Add(a, b, c, ...)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `args` | Any (variadic) | Expressions to add together |

## Returns
- A numeric result if all arguments are numeric
- A simplified symbolic expression otherwise
- `0` if called with no arguments

## Examples

Numeric addition:
```
Flame> 2 + 3
5
Flame> 1 + 2 + 3
6
```

Symbolic simplification (like terms are combined):
```
Flame> x + x
2*x
Flame> 2*x + 3*x
5*x
Flame> x + 1 + x
1 + 2*x
```

Subtraction is desugared as addition by the negative:
```
Flame> 5 - 3
2
Flame> x - x
0
```

## Notes
- `Add` is flat: `Add(Add(a, b), c)` becomes `Add(a, b, c)`
- Collects like terms symbolically (e.g., `x + 2*x` becomes `3*x`)
- Mixed integer/real arithmetic promotes to real

## See Also
- [Mul](Mul.md) — multiplication
- [Pow](Pow.md) — exponentiation
