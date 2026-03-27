# Sub

Subtracts two values.

## Syntax
```
Sub(a, b)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `a` | Numeric | The minuend |
| `b` | Numeric | The subtrahend |

## Returns
The result of `a - b`.

## Examples
```
Flame> Sub(5, 3)
2
Flame> Sub(1, 4)
-3
Flame> Sub(2.5, 1.0)
1.5
```

## Notes
- Defined in `math.flame` as `(a, b) => a - b`.
- A convenience wrapper around the `-` operator.

## See Also
- [Add](../arithmetic/Add.md) — addition
- [Div](Div.md) — division
