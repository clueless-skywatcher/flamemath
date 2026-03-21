# Factorial

Computes the factorial of a non-negative integer.

## Syntax
```
Factorial(n)
```

## Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `n` | Integer | A non-negative integer |

## Returns
An `Integer` equal to `n!` (n factorial).

## Examples
```
Flame> Factorial(0)
1
Flame> Factorial(1)
1
Flame> Factorial(5)
120
Flame> Factorial(10)
3628800
```

## Notes
- Defined in `math.flame` as a recursive function.
- `Factorial(n)` returns `1` when `n <= 1`.

## See Also
- [Triangular](Triangular.md) — triangular number T(n)
