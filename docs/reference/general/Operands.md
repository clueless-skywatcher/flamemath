# Operands

Returns the children (operands) of a compound expression as a list.

## Syntax
```
Operands(expr)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `expr` | Any | The expression to extract operands from |

## Returns
- A list of the direct children of a compound expression
- Returns unevaluated for atomic expressions (symbols, numbers, strings, booleans)

## Examples

```
Flame> Operands(x + y + z)
[x, y, z]
Flame> Operands(x * y)
[x, y]
Flame> Operands(x^2)
[x, 2]
Flame> Operands(Sin(x))
[x]
```

Atomic expressions return unevaluated:
```
Flame> Operands(x)
Operands(x)
Flame> Operands(5)
Operands(5)
```

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Head](Head.md) — returns the head (type) of an expression
- [At](At.md) — access a specific element by index
