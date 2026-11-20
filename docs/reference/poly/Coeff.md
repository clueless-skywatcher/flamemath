# Coeff

Returns the coefficient of a given subexpression in an expression.

## Syntax
```
Coeff(expr, x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `expr` | Any | The expression to inspect |
| `x` | Any | The subexpression to find the coefficient of |

## Returns
- The coefficient of `x` in `expr`
- `0` if `x` does not appear in `expr`
- Can return a symbolic expression in multivariate cases

## Examples

```
Flame> Coeff(3*x, x)
3
Flame> Coeff(x^2 + 2*x + 1, x)
2
Flame> Coeff(x^2 + 2*x + 1, x^2)
1
```

Multivariate:
```
Flame> Coeff(x*y + x + 1, x)
Add(1, y)
Flame> Coeff(3*x^2 + x^2*y, x^2)
Add(3, y)
```

No match:
```
Flame> Coeff(x + 1, x^2)
0
```

## Implementation
Stdlib function defined in `poly.flame`. Splits the expression into additive terms, checks each term for an exact match or a multiplicative factor matching `x`, and sums the remaining factors.

## See Also
- [Degree](Degree.md) — degree of a polynomial in a given variable
- [Vars](Vars.md) — extract free variables from an expression
- [FreeOf](FreeOf.md) — test whether an expression is free of a variable
