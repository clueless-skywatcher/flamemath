# FreeOf

Tests whether an expression is free of a given variable.

## Syntax
```
FreeOf(expr, x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `expr` | Any | The expression to inspect |
| `x` | Symbol | The variable to check for |

## Returns
- `True` if `x` does not appear as a free variable in `expr`
- `False` if `x` appears in `expr`

## Examples

```
Flame> FreeOf(y + 1, x)
True
Flame> FreeOf(x^2 + y, x)
False
Flame> FreeOf(Sin(x), x)
False
Flame> FreeOf(5, x)
True
```

## Implementation
Stdlib function defined as:
```
FreeOf = (expr, x) => !Has(Vars(expr), x)
```

## See Also
- [Vars](Vars.md) — extract free variables from an expression
- [Has](../list/Has.md) — list membership test
