# Degree

Returns the degree of a polynomial expression with respect to a given variable.

## Syntax
```
Degree(expr, x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `expr` | Any | The polynomial expression |
| `x` | Symbol | The variable to compute the degree with respect to |

## Returns
- An integer representing the highest power of `x` in the expression
- `0` for constants or expressions that do not contain `x`
- Returns unevaluated if `x` is not a `Symbol`

## Examples

```
Flame> Degree(x^3, x)
3
Flame> Degree(x^2 + x + 1, x)
2
Flame> Degree(3 * x^2 * y, x)
2
Flame> Degree(x^2 * y + x * y^3, y)
3
```

Constants and absent variables:
```
Flame> Degree(5, x)
0
Flame> Degree(y^4, x)
0
```

Unevaluated when second argument is not a symbol:
```
Flame> Degree(x^2, 3)
Degree(x^2, 3)
```

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided

## See Also
- [Vars](Vars.md) — extract free variables from an expression
- [FreeOf](FreeOf.md) — test whether an expression is free of a variable
