# Div

Divides two values.

## Syntax
```
Div(a, b)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `a` | Numeric | The dividend |
| `b` | Numeric | The divisor |

## Returns
The result of `a / b`.

## Examples
```
Flame> Div(6, 3)
2
Flame> Div(7, 2)
7 / 2
Flame> Div(5.0, 2.0)
2.5
```

## Notes
- Defined in `math.flame` as `(a, b) => a / b`.
- A convenience wrapper around the `/` operator.
- Division by zero throws an error.

## See Also
- [Mul](../arithmetic/Mul.md) — multiplication
- [Sub](Sub.md) — subtraction
