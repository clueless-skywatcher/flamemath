# Greater

Tests whether the first expression is strictly greater than the second.

## Syntax
```
a > b
Greater(a, b)
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
Flame> 3 > 2
True
Flame> 2 > 3
False
Flame> x > y
Greater(x, y)
```

## Errors
- Throws `FlameArityException` if not exactly 2 arguments are provided

## See Also
- [GreaterEq](GreaterEq.md), [Less](Less.md), [LessEq](LessEq.md)
