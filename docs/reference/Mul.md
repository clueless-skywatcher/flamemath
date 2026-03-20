# Mul

Multiplies expressions together. Performs symbolic simplification when possible.

## Syntax
```
a * b
Mul(a, b)
Mul(a, b, c, ...)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `args` | Any (variadic) | Expressions to multiply together |

## Returns
- A numeric result if all arguments are numeric
- A simplified symbolic expression otherwise
- `0` if called with no arguments or if any argument is `0`

## Examples

Numeric multiplication:
```
Flame> 2 * 3
6
Flame> 2 * 3 * 4
24
```

Symbolic simplification (like bases have exponents combined):
```
Flame> x * x
x^2
Flame> x^2 * x^3
x^5
```

Division is desugared as multiplication by the reciprocal:
```
Flame> 10 / 2
5
```

Short-circuits on zero:
```
Flame> 0 * x
0
```

## Notes
- `Mul` is flat: `Mul(Mul(a, b), c)` becomes `Mul(a, b, c)`
- Combines like bases by adding exponents (e.g., `x * x^2` becomes `x^3`)
- Mixed integer/real arithmetic promotes to real

## See Also
- [Add](Add.md) — addition
- [Pow](Pow.md) — exponentiation
