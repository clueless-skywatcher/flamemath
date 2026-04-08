# Expand

Expands an algebraic expression by distributing products over sums and applying other expansion rules.

## Syntax
```
Expand(expr)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `expr` | Any | The expression to expand |

## Returns
The expanded form of the expression.

## Examples

Currently returns the expression as-is (expansion rules are not yet implemented):
```
Flame> Expand(x + 1)
1 + x
Flame> Expand(5)
5
```

Expected future behavior:
```
Flame> Expand(x * (y + z))
x*y + x*z
Flame> Expand((x + 1)^2)
x^2 + 2*x + 1
Flame> Expand((a + b) * (c + d))
a*c + a*d + b*c + b*d
```

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Coeff](Coeff.md) — extract the coefficient of a term
- [Degree](Degree.md) — degree of a polynomial in a given variable
