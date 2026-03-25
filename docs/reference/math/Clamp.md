# Clamp

Restricts a numeric value to a given range `[low, hi]`.

## Syntax
```
Clamp(n, low, hi)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Numeric | The value to clamp |
| `low` | Numeric | The lower bound of the range |
| `hi` | Numeric | The upper bound of the range |

## Returns
- `low` if `n <= low`
- `hi` if `n >= hi`
- `n` if `low < n < hi`
- An unevaluated `Clamp(n, low, hi)` if any argument is non-numeric

## Examples

```
Flame> Clamp(7, 1, 5)
5
Flame> Clamp(-3, 0, 10)
0
Flame> Clamp(3, 1, 5)
3
Flame> Clamp(1, 1, 5)
1
Flame> Clamp(5, 1, 5)
5
```

With reals:
```
Flame> Clamp(3.5, 1.0, 5.0)
3.5
Flame> Clamp(0.1, 1.0, 5.0)
1.0
```

Symbolic (unevaluated):
```
Flame> Clamp(x, 1, 5)
Clamp(x, 1, 5)
```

## Notes
- Defined in `math.flame`.
- All three arguments must be numeric for evaluation; otherwise returns unevaluated.
- Boundary values are inclusive: `Clamp(low, low, hi)` returns `low` and `Clamp(hi, low, hi)` returns `hi`.

## See Also
- [Sign](Sign.md) — sign of a number
- [Min](Min.md) — minimum of values
- [Max](Max.md) — maximum of values
