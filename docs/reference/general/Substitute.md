# Substitute

Substitutes symbols with values in an expression, then evaluates the result.

## Syntax
```
Substitute(expr, symbol, value)
Substitute(expr, s1, v1, s2, v2, ...)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `expr` | Any | The expression to substitute into (held, not evaluated before substitution) |
| `symbol` | Symbol | The symbol to replace |
| `value` | Any | The value to substitute in (evaluated before binding) |

Additional symbol-value pairs can be provided to substitute multiple symbols at once.

## Returns
- The result of evaluating `expr` with the given substitutions applied

## Examples

Basic substitution:
```
Flame> Substitute(x^2 + x, x, 3)
12
Flame> Substitute(x^2 + x, x, y)
y^2 + y
```

Multiple substitutions:
```
Flame> Substitute(x * y, x, 2, y, 3)
6
Flame> Substitute(x + y + z, x, 1, y, 2, z, 3)
6
```

With functions:
```
Flame> Substitute(Sin(x), x, 0)
0
Flame> Substitute(Sqrt(x), x, 25)
5
```

Partial substitution (unmatched symbols remain symbolic):
```
Flame> Substitute(x + y, x, 3)
y + 3
Flame> Substitute(Sin(x), x, y)
Sin(y)
```

No match returns original:
```
Flame> Substitute(y + 1, x, 99)
y + 1
```

## Notes
- All arguments are held (`holdAll`). The expression is not evaluated until after substitutions are applied. Replacement values are evaluated before binding.
- Substitutions do not leak into the outer scope. After evaluation, all original bindings are restored.
- Requires an odd number of arguments: one expression followed by symbol-value pairs.

## Errors
- Throws `FlameArityException` if fewer than 3 arguments are provided
- Throws if the number of arguments is even (incomplete symbol-value pair)
- Throws if any even-indexed argument (after the first) is not a symbol

## See Also
- [Set](../construct/Set.md) — permanent variable assignment
- [Apply](Apply.md) — apply a function to a list of arguments
