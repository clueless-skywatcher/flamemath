# Linspace

Generates a list of n evenly spaced numbers from start to stop, inclusive.

## Syntax
```
Linspace(start, stop, n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `start` | Numeric | The first value |
| `stop` | Numeric | The last value |
| `n` | Integer | The number of points to generate |

## Returns
A `List` of n evenly spaced numeric values from `start` to `stop`. Returns an empty list if n < 1. Returns `[start]` if n = 1.

## Examples
```
Flame> Linspace(0, 1, 5)
[0.0, 0.25, 0.5, 0.75, 1.0]
Flame> Linspace(-1, 1, 3)
[-1.0, 0.0, 1.0]
Flame> Linspace(1, 0, 3)
[1.0, 0.5, 0.0]
Flame> Linspace(5, 5, 3)
[5.0, 5.0, 5.0]
Flame> Linspace(0, 10, 1)
[0]
Flame> Linspace(0, 1, 0)
[]
```

## Notes
- Each point is computed as `Num(start + i * step)` where `step = (stop - start) / (n - 1)`, avoiding floating-point drift from repeated addition.
- Returns unevaluated `Linspace(start, stop, n)` for non-numeric start/stop or non-integer n.

## See Also
- [Range](../list/Range.md) — generate integer ranges
- [GenList](../list/GenList.md) — generate lists from expressions
