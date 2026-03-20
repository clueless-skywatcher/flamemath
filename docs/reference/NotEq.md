# NotEq

Tests whether two expressions are not equal.

## Syntax
```
a != b
NotEq(a, b)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `a` | Any | Left operand |
| `b` | Any | Right operand |

## Returns
`True` or `False`.

## Examples
```
Flame> 3 != 4
True
Flame> 3 != 3
False
Flame> x != y
True
Flame> x != x
False
```

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided

## See Also
- [Eq](Eq.md) — equality test
