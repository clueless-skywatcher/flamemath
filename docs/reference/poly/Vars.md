# Vars

Extracts the free variables from an expression.

## Syntax
```
Vars(expr)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `expr` | Any | The expression to extract variables from |

## Returns
- A list of distinct `Symbol` nodes found in the expression, in traversal order
- An empty list for purely numeric, string, or boolean expressions

## Examples

```
Flame> Vars(x)
[x]
Flame> Vars(x^2 + 3*x*y + y^2)
[x, y]
Flame> Vars(Sin(x) + Cos(y))
[x, y]
Flame> Vars(x^2 + 2*x + 1)
[x]
```

No variables:
```
Flame> Vars(5)
[]
Flame> Vars(3.14)
[]
Flame> Vars("hello")
[]
```

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Degree](Degree.md) — degree of a polynomial in a given variable
- [Coefficient](Coefficient.md) — coefficient of a term in a polynomial
