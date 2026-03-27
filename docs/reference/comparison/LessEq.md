# LessEq

Tests whether the first expression is less than or equal to the second.

## Syntax
```
a <= b
LessEq(a, b)
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
Flame> 2 <= 3
True
Flame> 3 <= 3
True
Flame> 4 <= 3
False
```

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided

## See Also
- [Less](Less.md), [Greater](Greater.md), [GreaterEq](GreaterEq.md)
