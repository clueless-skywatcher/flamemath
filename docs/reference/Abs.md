# Abs

Returns the absolute value of an expression.

## Syntax
```
Abs(x)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `x` | Any | The expression to take the absolute value of |

## Returns
- The negated value for negative integers and reals
- The value unchanged for non-negative integers and reals
- An unevaluated `Abs(x)` for symbolic arguments

## Examples

```
Flame> Abs(-5)
5
Flame> Abs(3)
3
Flame> Abs(-2.5)
2.5
Flame> Abs(0)
0
```

Symbolic (unevaluated):
```
Flame> Abs(x)
Abs(x)
```

## Errors
- Throws `FlameArityException` if not exactly 1 argument is provided

## See Also
- [Num](Num.md) — force numeric evaluation
