# GreaterEq

Tests whether the first expression is greater than or equal to the second.

## Syntax
```
a >= b
GreaterEq(a, b)
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
Flame> 3 >= 2
True
Flame> 3 >= 3
True
Flame> 2 >= 3
False
```

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided

## See Also
- [Greater](Greater.md), [Less](Less.md), [LessEq](LessEq.md)
