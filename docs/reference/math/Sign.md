# Sign

Returns the sign of a number: `-1` for negative, `0` for zero, `1` for positive.

## Syntax
```
Sign(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | The number to determine the sign of |

## Returns
- `-1` if `n < 0`
- `0` if `n == 0`
- `1` if `n > 0`
- An unevaluated `Sign(n)` for non-integer arguments

## Examples

```
Flame> Sign(-5)
-1
Flame> Sign(0)
0
Flame> Sign(3)
1
Flame> Sign(-100)
-1
```

Symbolic (unevaluated):
```
Flame> Sign(x)
Sign(x)
Flame> Sign(2.5)
Sign(2.5)
```

## Notes
- Defined in `math.flame`.
- Only evaluates for integer arguments; real and symbolic inputs return unevaluated.

## See Also
- [Abs](Abs.md) — absolute value
- [Clamp](Clamp.md) — restrict a value to a range
