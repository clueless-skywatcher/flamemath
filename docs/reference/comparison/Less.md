# Less

Tests whether the first expression is strictly less than the second.

## Syntax
```
a < b
Less(a, b)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `a` | Any | Left operand |
| `b` | Any | Right operand |

## Returns
- `True` or `False` when both arguments are numeric
- The unevaluated expression when arguments are symbolic

## Examples
```
Flame> 2 < 3
True
Flame> 3 < 2
False
Flame> x < y
Less(x, y)
```

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided

## See Also
- [LessEq](LessEq.md), [Greater](Greater.md), [GreaterEq](GreaterEq.md)
