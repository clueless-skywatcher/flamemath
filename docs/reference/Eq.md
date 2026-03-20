# Eq

Tests whether two expressions are equal.

## Syntax
```
a == b
Eq(a, b)
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
Flame> 3 == 3
True
Flame> 3 == 4
False
Flame> x == x
True
Flame> x == y
False
```

Numeric comparison works across types:
```
Flame> 3 == 3.0
True
```

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided

## See Also
- [NotEq](NotEq.md) — inequality test
